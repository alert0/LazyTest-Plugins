package lazy.test.plugin.fundation.processor;

import org.apache.commons.lang3.StringUtils;
import org.eclipse.jdt.core.IMember;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.core.dom.*;
import org.eclipse.jface.viewers.IStructuredSelection;

import java.util.ArrayList;
import java.util.List;

import lazy.test.plugin.fundation.exception.AGTBizException;
import lazy.test.plugin.fundation.handler.ClassTransferHandler;
import lazy.test.plugin.fundation.model.ClassMetaDataModel;
import lazy.test.plugin.fundation.model.MethodMetaDataModel;
import lazy.test.plugin.fundation.model.ParamMetaDataModel;
import lazy.test.plugin.fundation.model.ReturnMetaDataModel;

/**
 * Created by wyzhouqiang on 2015/11/21.
 */
public class ASTParseProcessor {

    public static ClassMetaDataModel parseSelectedMethod(IStructuredSelection selection) {
        ClassMetaDataModel model = new ClassMetaDataModel();
        IMember member = ((IMember)selection.getFirstElement());
        ASTParser parser = ASTParser.newParser(AST.JLS4);
        parser.setKind(ASTParser.K_COMPILATION_UNIT);
        parser.setSource(member.getCompilationUnit());
        parser.setResolveBindings(true);
        ASTNode node = parser.createAST(null);
        try {
            node.accept(new ASTParseVisitor(model, member.getSourceRange().getOffset()));
        } catch (JavaModelException e) {
            throw new AGTBizException("EXP00000");
        }

        return model;
    }
}

class ASTParseVisitor extends ASTVisitor {

    private ClassMetaDataModel model;

    private Integer rangeIndex;

    protected ASTParseVisitor(ClassMetaDataModel model, Integer rangeIndex) {
        super();
        this.model = model;
        this.rangeIndex = rangeIndex;
    }

    @Override
    public boolean visit(PackageDeclaration node) {
        model.setPackageQualifiedName(node.getName().getFullyQualifiedName());
        return super.visit(node);
    }

    @Override
    public boolean visit(MethodDeclaration node) {
        if (!this.isSelected(node.getStartPosition())) {
            return super.visit(node);
        }
        if (node.isConstructor()) {
            throw new AGTBizException("构造方法无法生成测试用例", "EXP00001");
        }

        this.generateClass(node);
        this.generateMethod(node);

        return super.visit(node);
    }

    private void generateClass(MethodDeclaration node) {
        model.setClassSimpleName(node.resolveBinding().getDeclaringClass().getName());
        model.setClassQualifiedName(node.resolveBinding().getDeclaringClass().getQualifiedName());
    }

    private void generateMethod(MethodDeclaration node) {
        MethodMetaDataModel methodMetaDataModel = new MethodMetaDataModel();
        methodMetaDataModel.setMethodSimpleName(node.getName().getIdentifier());
        String params = "(" + StringUtils.substring(node.parameters().toString(), 1, node.parameters().toString().length() - 1) + ")";
        String methodQualifiedName = node.getName().getIdentifier() + params;
        methodMetaDataModel.setMethodQualifiedName(methodQualifiedName);
        methodMetaDataModel.setParamList(this.generateMethodParam(node));
        methodMetaDataModel.setReturnObject(this.generateMethodReturn(node));
        model.setMethodMetaDataModel(methodMetaDataModel);
    }

    private List<ParamMetaDataModel> generateMethodParam(MethodDeclaration node) {
        List<ParamMetaDataModel> list = new ArrayList<ParamMetaDataModel>();
        for (Object paramNode : node.parameters()) {
            if (paramNode instanceof SingleVariableDeclaration) {
                Type type = ((SingleVariableDeclaration) paramNode).getType();
                ParamMetaDataModel param = new ParamMetaDataModel();
                param.setParamName(((SingleVariableDeclaration) paramNode).getName().getIdentifier());
                param.setParamStr(type.resolveBinding().getQualifiedName());
                param.setParamMap(ClassTransferHandler.convertClassTypeMap(type.resolveBinding().getQualifiedName()));
                list.add(param);
            } else {
                throw new AGTBizException("无法处理的方法参数类型", "EXP00002");
            }
        }

        return list;
    }

    private ReturnMetaDataModel generateMethodReturn(MethodDeclaration node) {
        ReturnMetaDataModel returnObject = new ReturnMetaDataModel();

        Type type = node.getReturnType2();
        returnObject.setReturnStr(type.toString());
        returnObject.setReturnMap(ClassTransferHandler.convertClassTypeMap(type.resolveBinding().getQualifiedName()));
        return returnObject;
    }

    private boolean isSelected(Integer selectedIndex) {
        if (rangeIndex.equals(selectedIndex)) {
            return true;
        }
        return false;
    }
}
