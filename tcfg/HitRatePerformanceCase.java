package common;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import lazy.test.tools.util.CsvUtil;

import java.util.concurrent.atomic.AtomicInteger;

public abstract class HitRatePerformanceCase extends BasePerformanceCase {
	Logger logger = LoggerFactory.getLogger(HitRatePerformanceCase.class);

	private static Object object = new Object();

	// 请求参数对象List
	private static ArrayList<Object> request;

	// 线程计数器
	private static AtomicInteger threadCounter = new AtomicInteger();

	// 当前线程编号
	private int threadID = 0;

	// 当前线程被调用次数
	private int invockCount = 0;

	// 是否已经初始化好请求参数列表 标识
	private static boolean preRequestDoneFlag = false;

	/**
	 * 方法说明：初始化所有请求参数列表，返回当前线程的请求对象
	 * 每个线程可运行的次数最大值=（CSV文件行数/缓存命中率%）/总线程数，向下取整
	 * 即：[（fileName.size/hitCacheRate）*100]/threadSize，向下取整
	 * 此函数的入参，需要和PTP平台的入参（总线程数、线程运行次数）相匹配，PTP中线程启动时间填1
	 * @param threadSize
	 *            总线程数
	 * @param hitCacheRate
	 *            缓存命中率，从0到99之间的数字,如果命中缓存，则使用
	 *            request【0】，否则，使用request【线程ID+当前线程被调用次数】
	 * @param fileName
	 *            性能测试，请求参数CSV文件，主要针对查询接口，拿线上数据进行测试；
	 *            文件路径必须从src/test/resources/开头，否则PTP不能识别
	 *            文件路径必须从src/test/resources/开头，否则PTP不能识别
	 *            文件路径必须从src/test/resources/开头，否则PTP不能识别
	 * @return Object 当前线程的请求对象
	 */
	public Object initTestParam(int threadSize, int hitCacheRate, String fileName) {
//		int threadSize = (int) (tps * 1.5);

		if (hitCacheRate < 0 || hitCacheRate > 100) {
			throw new RuntimeException("缓存命中率小于0，或超过100%，请检查后重试");
		}
		// 初始化当前线程ID
		initThreadID();

		// 根据缓存命中率，准备所有请求对象
		pre(fileName, hitCacheRate, threadSize);

		// 线程使用次数，拿到请求参数对象 当前请求号 = （当前线程被调用次数*总线程个数）+当前线程ID
		Object thisRequestParam;
		try {
			thisRequestParam = request.get(invockCount * threadSize + threadID);
		} catch (Exception e) {
			throw new RuntimeException("数组越界，请重新计算线程数量、线程运行次数、缓存命中率和“初始准备的请求参数数”量之间的关系:" + invockCount + ".."
					+ (invockCount * threadSize + threadID));
		}

		// 线程被调用次数加1
		invockCount++;

		System.out.println("+++++++++++++++++++++++++++++++++++++++++++");
		System.out.println("当前线程ID：" + threadID);
		System.out.println("当前线程查询参数：" + thisRequestParam.toString());
		System.out.println("当前线程被调用次数：" + invockCount);
		System.out.println("当前线程使用数组ID：" + invockCount * threadSize + threadID);

		return thisRequestParam;
	}

	private void initThreadID() {
		if (threadID == 0) {
			threadID = threadCounter.addAndGet(1);
			System.out.println("threadCounter：" + threadCounter);
			System.out.println("threadID：" + threadID);
		}
	}

	private void pre(String fileName, int hitCacheRate, int threadSize) {
		// 避免所有线程都去调用同步方法，一个线程准备完成后，则跳过同步方法
		if (!preRequestDoneFlag) {
			preSynch(fileName, hitCacheRate, threadSize);
		}
	}

	private void preSynch(String fileName, int hitCacheRate, int threadSize) {
		synchronized (object) {
			// 所有线程同步地去等待其中一个线程，初始化好所有请求参数
			if (request == null) {
//				System.err.println("111111111111111111");
				request = new ArrayList<Object>(10000);

				// 从CSV中获得已构造的请求数据
				LinkedList<Object> originalRequestList = initRequestParam(CsvUtil.getDataFromCsv(fileName));

				if (hitCacheRate < 100 && originalRequestList.size() < threadSize) {
					System.out.println("构造后，请求列表大小******：" + request.size());
					System.out.println("构造后，originalRequestList列表大小******：" + originalRequestList.size());
					System.out.println("threadSize:" + threadSize);
					throw new RuntimeException("准备的数据文件，比线程数还少，做不了并发 ");
				}
				// 拿出第一个对象，命中缓存，均使用第一个对象
				Object firstObject;
				if (originalRequestList.size() != 0) {
					firstObject = originalRequestList.get(0);
				} else {
					throw new RuntimeException("准备的数据小于1行");
				}

				if (hitCacheRate == 100) {
					while (!originalRequestList.isEmpty()) {
						request.add(firstObject);
						originalRequestList.removeFirst();
					}
				} else {
					// 用于计数缓存命中率，先填充“缓存命中率”个同样的请求，再填入不重复的数据
					int j = 0;
					while (!originalRequestList.isEmpty()) {
						j++;
						if (j % 100 <= hitCacheRate) {
							request.add(firstObject);
						} else {
							Object temp = originalRequestList.removeFirst();
							request.add(temp);
						}
					}
				}
				preRequestDoneFlag = true;
				System.out.println("构造后，请求列表大小******：" + request.size());

			}
		}
	}

	// TODO 子类需重写，将读取的List<Map<String,String>>，根据命中率，转化list
	public abstract LinkedList<Object> initRequestParam(List<Map<String, String>> paramList);

}
