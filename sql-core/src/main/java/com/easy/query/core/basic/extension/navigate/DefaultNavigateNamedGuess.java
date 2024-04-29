//package com.easy.query.core.basic.extension.navigate;
//
//import com.easy.query.core.enums.RelationTypeEnum;
//import com.easy.query.core.exception.EasyQueryInvalidOperationException;
//import com.easy.query.core.metadata.ColumnMetadata;
//import com.easy.query.core.metadata.EntityMetadata;
//import com.easy.query.core.metadata.NavigateMetadata;
//import com.easy.query.core.util.EasyStringUtil;
//
///**
// * create time 2024/4/29 13:17
// * 文件说明
// *
// * @author xuejiaming
// */
//public class DefaultNavigateNamedGuess implements NavigateNamedGuess {
//    @Override
//    public NamedGuessResult guessName(EntityMetadata fromMappingEntityMetadata, NavigateMetadata navigateMetadata) {
//        if (navigateMetadata.isBasicType()) {
//            if (EasyStringUtil.isNotBlank(navigateMetadata.getMappingProp())) {
//                if (navigateMetadata.getMappingProp().contains(".")) {
//                    String[] split = navigateMetadata.getMappingProp().split("//.");
//                    return new NamedGuessResult(split[0], split[1]);
//                }
//                return new NamedGuessResult(navigateMetadata.getMappingProp(), null);
//            }
//            NamedGuessResult namedGuessResult = null;
//            boolean toMany = navigateMetadata.getRelationType() == RelationTypeEnum.OneToMany || navigateMetadata.getRelationType() == RelationTypeEnum.ManyToMany;
//            String propertyName = toMany ? EasyStringUtil.endWithRemove(navigateMetadata.getPropertyName(), "s") : navigateMetadata.getPropertyName();
//            for (NavigateMetadata metadata : fromMappingEntityMetadata.getNavigateMetadatas()) {
//                if (propertyName.startsWith(metadata.getPropertyName())) {
//                    String propName = EasyStringUtil.startWithRemove(propertyName, metadata.getPropertyName());
//                    String realPropName = EasyStringUtil.toLowerCaseFirstOne(propName);
//                    ColumnMetadata columnOrNull = fromMappingEntityMetadata.getColumnOrNull(realPropName);
//                    if (columnOrNull != null) {
//                        namedGuessResult = new NamedGuessResult(metadata.getPropertyName(), columnOrNull.getPropertyName());
//                    }
//                }
//                if (namedGuessResult != null) {
//                    break;
//                }
//            }
//            if (namedGuessResult == null) {
//                throw new EasyQueryInvalidOperationException("cant guess navigate property name");
//            }
//            return namedGuessResult;
//
//        }
//        if (EasyStringUtil.isNotBlank(navigateMetadata.getMappingProp())) {
//            String propertyName = navigateMetadata.getMappingProp().split("//.")[0];
//            return new NamedGuessResult(propertyName, null);
//        }
//        return new NamedGuessResult(navigateMetadata.getPropertyName(), null);
//    }
//}
