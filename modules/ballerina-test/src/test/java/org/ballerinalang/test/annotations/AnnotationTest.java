/*
 * Copyright (c) 2017, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
 *
 * WSO2 Inc. licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.ballerinalang.test.annotations;

import org.ballerinalang.launcher.util.BAssertUtil;
import org.ballerinalang.launcher.util.BCompileUtil;
import org.ballerinalang.launcher.util.CompileResult;
import org.ballerinalang.util.codegen.AnnAttachmentInfo;
import org.ballerinalang.util.codegen.AnnAttributeValue;
import org.ballerinalang.util.codegen.attributes.AnnotationAttributeInfo;
import org.ballerinalang.util.codegen.attributes.AttributeInfo;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

/**
 * Test cases for user defined annotations in ballerina.
 */
public class AnnotationTest {

    private CompileResult compileResult;

    @BeforeClass
    public void setup() {
        compileResult = BCompileUtil.compile(this, "test-src", "lang/annotations/foo");
    }

    @Test(description = "Test function annotation")
    public void testFunctionAnnotation() {
        AnnotationAttributeInfo annotationInfo = (AnnotationAttributeInfo) compileResult.getProgFile()
                .getEntryPackage().getFunctionInfo("foo")
                .getAttributeInfo(AttributeInfo.Kind.ANNOTATIONS_ATTRIBUTE);
        AnnAttachmentInfo[] attachmentInfos = annotationInfo.getAttachmentInfoEntries();

        String attributeValue = attachmentInfos[0].getAttributeValue("value").getStringValue();
        Assert.assertEquals(attributeValue, "This is a test function");

        AnnAttributeValue firstElement = attachmentInfos[0].getAttributeValue("queryParamValue")
                .getAttributeValueArray()[0];
        attributeValue = firstElement.getAnnotationAttachmentValue()
                .getAttributeValue("name").getStringValue();
        Assert.assertEquals(attributeValue, "paramName3");

        AnnAttributeValue secondElement = attachmentInfos[0].getAttributeValue("queryParamValue")
                .getAttributeValueArray()[1];
        attributeValue = secondElement.getAnnotationAttachmentValue()
                .getAttributeValue("name").getStringValue();
        Assert.assertEquals(attributeValue, "paramName2");

        AnnAttributeValue thirdElement = attachmentInfos[0].getAttributeValue("queryParamValue")
                .getAttributeValueArray()[2];
        attributeValue = thirdElement.getAnnotationAttachmentValue()
                .getAttributeValue("name").getStringValue();
        Assert.assertEquals(attributeValue, "paramName");

        Assert.assertEquals(attachmentInfos[1].getAttributeValue("value").getStringValue(),
                "test @Args annotation");
    }

    @Test(description = "Test function parameter annotation")
    public void testParameterAnnotation() {
        AnnotationAttributeInfo annotationInfo = (AnnotationAttributeInfo) compileResult.getProgFile()
                .getEntryPackage().getFunctionInfo("foo")
                .getAttributeInfo(AttributeInfo.Kind.ANNOTATIONS_ATTRIBUTE);
        AnnAttachmentInfo[] attachmentInfos = annotationInfo.getAttachmentInfoEntries();
        // TODO ParamDef AnnAttachmentInfo are not available at AnnotationAttributeInfo
        // String attributeValue = annottations[0].getAttribute("value").getLiteralValue().stringValue();
        // Assert.assertEquals(attributeValue, "args: input parameter : type string");
    }

    @Test(description = "Test service annotation")
    public void testServiceAnnotation() {
        AnnotationAttributeInfo annotationInfo = (AnnotationAttributeInfo) compileResult.getProgFile()
                .getEntryPackage().getServiceInfo("PizzaService")
                .getAttributeInfo(AttributeInfo.Kind.ANNOTATIONS_ATTRIBUTE);
        AnnAttachmentInfo[] attachmentInfos = annotationInfo.getAttachmentInfoEntries();
        String attributeValue = attachmentInfos[0].getAttributeValue("value").getStringValue();
        Assert.assertEquals(attributeValue, "Pizza service");
    }

    @Test(description = "Test resource annotation")
    public void testResourceAnnotation() {
        AnnotationAttributeInfo annotationInfo = (AnnotationAttributeInfo) compileResult.getProgFile()
                .getEntryPackage().getServiceInfo("PizzaService").getResourceInfoEntries()[1]
                .getAttributeInfo(AttributeInfo.Kind.ANNOTATIONS_ATTRIBUTE);
        AnnAttachmentInfo[] attachmentInfos = annotationInfo.getAttachmentInfoEntries();

        String attributeValue = attachmentInfos[0].getAttributeValue("value").getStringValue();
        Assert.assertEquals(attributeValue, "Order pizza");

        annotationInfo = (AnnotationAttributeInfo) compileResult.getProgFile()
                .getEntryPackage().getServiceInfo("PizzaService").getResourceInfoEntries()[0]
                .getAttributeInfo(AttributeInfo.Kind.ANNOTATIONS_ATTRIBUTE);
        attachmentInfos = annotationInfo.getAttachmentInfoEntries();

        attributeValue = attachmentInfos[0].getAttributeValue("value").getStringValue();
        Assert.assertEquals(attributeValue, "Check order status");
        // TODO ParamDef AnnAttachmentInfo are not available at AnnotationAttributeInfo
        // String paramAnnotVal = orderPizzaResource.getParameterDefs()[0].getAnnotations()[0].getValue();
        // Assert.assertEquals(paramAnnotVal, "input parameter for oderPizza resource");
    }

    @Test(description = "Test connector annotation")
    public void testConnectorAnnotation() {
        AnnotationAttributeInfo annotationInfo = (AnnotationAttributeInfo) compileResult.getProgFile()
                .getEntryPackage().getConnectorInfoEntries()[0]
                .getAttributeInfo(AttributeInfo.Kind.ANNOTATIONS_ATTRIBUTE);
        AnnAttachmentInfo[] attachmentInfos = annotationInfo.getAttachmentInfoEntries();
        String attributeValue = attachmentInfos[0].getAttributeValue("value").getStringValue();
        Assert.assertEquals(attributeValue, "Test connector");
    }

    @Test(description = "Test action annotation")
    public void testActionAnnotation() {
        AnnotationAttributeInfo annotationInfo = (AnnotationAttributeInfo) compileResult.getProgFile()
                .getEntryPackage().getConnectorInfoEntries()[0].getActionInfoEntries()[1]
                .getAttributeInfo(AttributeInfo.Kind.ANNOTATIONS_ATTRIBUTE);
        AnnAttachmentInfo[] attachmentInfos = annotationInfo.getAttachmentInfoEntries();
        String attributeValue = attachmentInfos[0].getAttributeValue("value").getStringValue();
        Assert.assertEquals(attributeValue, "Test action of test connector");
    }

    @Test(description = "Test struct annotation")
    public void testStructAnnotation() {
        AnnotationAttributeInfo annotationInfo = (AnnotationAttributeInfo) compileResult.getProgFile()
                .getEntryPackage().getStructInfo("Person")
                .getAttributeInfo(AttributeInfo.Kind.ANNOTATIONS_ATTRIBUTE);
        AnnAttachmentInfo[] attachmentInfos = annotationInfo.getAttachmentInfoEntries();
        String attributeValue = attachmentInfos[0].getAttributeValue("value").getStringValue();
        Assert.assertEquals(attributeValue, "User defined struct : Person");
    }

    @Test(description = "Test constant annotation")
    public void testConstantAnnotation() {
//        if (varNode.symbol.flags == Flags.CONST) {
//            PackageVarInfo varInfo = currentPkgInfo.pkgVarInfoMap.get(varNode.getName().getValue());
//
//            int annotationAttribNameIndex = addUTF8CPEntry(currentPkgInfo,
//                    AttributeInfo.Kind.ANNOTATIONS_ATTRIBUTE.value());
//            AnnotationAttributeInfo attributeInfo = new AnnotationAttributeInfo(annotationAttribNameIndex);
//            varNode.annAttachments.forEach(annt -> visitServiceAnnotationAttachment(annt, attributeInfo));
//            varInfo.addAttributeInfo(AttributeInfo.Kind.ANNOTATIONS_ATTRIBUTE, attributeInfo);
//        }
//
//        AnnotationAttributeInfo annotationInfo = (AnnotationAttributeInfo) compileResult.getProgFile()
//                .getEntryPackage().getPackageInfoEntries()[0]
//                .getAttributeInfo(AttributeInfo.Kind.ANNOTATIONS_ATTRIBUTE);
//        AnnAttachmentInfo[] attachmentInfos = annotationInfo.getAttachmentInfoEntries();
//        String attributeValue = attachmentInfos[0].getAttributeValue("value").getStringValue();
//        Assert.assertEquals(attributeValue, "Constant holding the name of the current ballerina program");
    }

    @Test(description = "Test self annotating and annotation")
    public void testSelfAnnotating() {
        CompileResult bLangProgram = BCompileUtil.compile(this, "test-src", "lang/annotations/doc/");
        // TODO Annotation definitions are not available complied program entry package
//        AnnotationAttachment[] annottations = bLangProgram.getProgFile().getEntryPackage().[0]
//                .getAnnotations();
//
//        String attributeValue = annottations[0].getAttribute("value").getLiteralValue().stringValue();
//        Assert.assertEquals(attributeValue, "Self annotating an annotation");
//
//        AnnotationAttributeValue firstElement = annottations[0].getAttribute("queryParamValue").getValueArray()[0];
//        attributeValue = firstElement.getAnnotationValue().getAttribute("name").getLiteralValue().stringValue();
//        Assert.assertEquals(attributeValue, "first query param name");
    }

    @Test(description = "Test annotation array")
    public void testAnnotationArray() {
        AnnotationAttributeInfo annotationInfo = (AnnotationAttributeInfo) compileResult.getProgFile()
                .getEntryPackage().getFunctionInfo("foo")
                .getAttributeInfo(AttributeInfo.Kind.ANNOTATIONS_ATTRIBUTE);
        AnnAttachmentInfo[] attachmentInfos = annotationInfo.getAttachmentInfoEntries();

        AnnAttributeValue[] annotationArray = attachmentInfos[0]
                .getAttributeValue("queryParamValue").getAttributeValueArray();
        Assert.assertEquals(annotationArray.length, 3, "Wrong annotation array length");

        String attributeValue = annotationArray[2]
                .getAnnotationAttachmentValue().getAttributeValue("name").getStringValue();
        Assert.assertEquals(attributeValue, "paramName");

        attributeValue = annotationArray[1]
                .getAnnotationAttachmentValue().getAttributeValue("name").getStringValue();
        Assert.assertEquals(attributeValue, "paramName2");

        attributeValue = annotationArray[0]
                .getAnnotationAttachmentValue().getAttributeValue("name").getStringValue();
        Assert.assertEquals(attributeValue, "paramName3");

    }

    @Test(description = "Test annotation attachment package valdation")
    public void testValidAnnoatationAttachmentPackage() {
        Assert.assertNotNull(BCompileUtil.compile(this, "test-src", "lang/annotations/pkg/valid").getProgFile());
    }

    @Test(description = "Test constant as annotation attribute value")
    public void testConstAsAttributeValue() {
        Assert.assertNotNull(BCompileUtil
                .compile(this, "test-src", "lang/annotations/constant-as-attribute-value.bal").getProgFile());
    }

    // Negative tests

    @Test(description = "Test child annotation from a wrong package")
    public void testInvalidChildAnnotation() {
        CompileResult resNegative = BCompileUtil.compile(this, "test-src",
                "lang/annotations/invalid-child-annotation.bal");
        Assert.assertEquals(resNegative.getErrorCount(), 1);
        BAssertUtil.validateError(resNegative, 0, "incompatible types: expected 'lang.annotations.doc:Args', " +
                "found 'Args'", 3, 24);
    }

    @Test(description = "Test array value for a non-array type attribute")
    public void testInvalidArrayValuedAttribute() {
        CompileResult resultNegative = BCompileUtil
                .compile(this, "test-src", "lang/annotations/invalid-array-valued-attribute.bal");
        Assert.assertEquals(resultNegative.getErrorCount(), 1);
        BAssertUtil.validateError(resultNegative, 0, "incompatible types: expected a 'string', found an array", 3, 1);
    }

    @Test(description = "Test non-array value for a array type attribute")
    public void testInvalidSingleValuedAttribute() {
        CompileResult resultNegative = BCompileUtil
                .compile(this, "test-src", "lang/annotations/invalid-single-valued-attribute.bal");
        Assert.assertEquals(resultNegative.getErrorCount(), 1);
        BAssertUtil.validateError(resultNegative, 0,
                "incompatible types: expected 'lang.annotations.doc:QueryParam[]', " +
                        "found 'lang.annotations.doc:QueryParam'", 3, 35);
    }

    @Test(description = "Test multi-typed attribute value array")
    public void testMultiTypedAttributeArray() {
        CompileResult resultNegative = BCompileUtil
                .compile(this, "test-src", "lang/annotations/multityped-attribute-array.bal");
        Assert.assertEquals(resultNegative.getErrorCount(), 1);
        BAssertUtil.validateError(resultNegative, 0,
                "incompatible types: expected 'lang.annotations.doc:QueryParam', found 'string'", 5, 42);

    }

    @Test(description = "Test an annotation attached in a wrong point")
    public void testWronglyAttachedAnnot() {
        CompileResult resultNegative = BCompileUtil
                .compile(this, "test-src", "lang/annotations/wrongly-attached-annot.bal");
        Assert.assertEquals(resultNegative.getErrorCount(), 1);
        BAssertUtil.validateError(resultNegative, 0,
                "annotation 'Bar' is not allowed in function", 7, 1);
    }

    @Test(description = "Test child annotation with an invalid attribute value")
    public void testInvalidInnerAttribute() {
        CompileResult resultNegative = BCompileUtil
                .compile(this, "test-src", "lang/annotations/invalid-inner-attributes.bal");
        Assert.assertEquals(resultNegative.getErrorCount(), 1);
        BAssertUtil.validateError(resultNegative, 0,
                "incompatible types: expected 'string', found 'int'", 4, 16);
    }

    @Test(description = "Test an invalid service annotation")
    public void testInvalidServiceAnnotation() {
        CompileResult resultNegative = BCompileUtil
                .compile(this, "test-src", "lang/annotations/invalid-service-annotation.bal");
        Assert.assertEquals(resultNegative.getErrorCount(), 1);
        BAssertUtil.validateError(resultNegative, 0,
                "incompatible types: expected 'string', found 'int'", 4, 24);
    }

    @Test(description = "Test an invalid resource annotation")
    public void testInvalidResourceAnnotation() {
        CompileResult resultNegative = BCompileUtil
                .compile(this, "test-src", "lang/annotations/invalid-resource-annotation.bal");
        Assert.assertEquals(resultNegative.getErrorCount(), 1);
        BAssertUtil.validateError(resultNegative, 0,
                "incompatible types: expected 'string', found 'int'", 12, 28);
    }

    @Test(description = "Test an invalid connector annotation")
    public void testInvalidConnectorAnnotation() {
        CompileResult resultNegative = BCompileUtil
                .compile(this, "test-src", "lang/annotations/invalid-connector-annotation.bal");
        Assert.assertEquals(resultNegative.getErrorCount(), 1);
        BAssertUtil.validateError(resultNegative, 0,
                "incompatible types: expected 'string', found 'int'", 3, 24);
    }

    @Test(description = "Test an invalid action annotation")
    public void testInvalidActionAnnotation() {
        CompileResult resultNegative = BCompileUtil
                .compile(this, "test-src", "lang/annotations/invalid-action-annotation.bal");
        Assert.assertEquals(resultNegative.getErrorCount(), 1);
        BAssertUtil.validateError(resultNegative, 0,
                "incompatible types: expected 'string', found 'int'", 6, 28);
    }

    @Test(description = "Test an invalid struct annotation")
    public void testInvalidStructAnnotation() {
        CompileResult resultNegative = BCompileUtil
                .compile(this, "test-src", "lang/annotations/invalid-struct-annotation.bal");
        Assert.assertEquals(resultNegative.getErrorCount(), 1);
        BAssertUtil.validateError(resultNegative, 0,
                "incompatible types: expected 'string', found 'int'", 3, 24);
    }

    @Test(description = "Test an invalid constant annotation")
    public void testInvalidConstantAnnotation() {
        CompileResult resultNegative = BCompileUtil
                .compile(this, "test-src", "lang/annotations/invalid-constant-annotation.bal");
        Assert.assertEquals(resultNegative.getErrorCount(), 1);
        BAssertUtil.validateError(resultNegative, 0,
                "incompatible types: expected 'string', found 'int'", 3, 24);
    }

    @Test(description = "Test invalid annotation attachment for service where annotation attachment is only valid" +
            "for given protocol package")
    public void testInvalidAttachmentInServiceWithDifferentProtocolPkg() {
        CompileResult resultNegative = BCompileUtil.compile(this, "test-src", "lang/annotations/pkg/error1");
        Assert.assertEquals(resultNegative.getErrorCount(), 1);
        BAssertUtil.validateError(resultNegative,
                0, "annotation 'lang.annotations.pkg.first:Sample' is not allowed in " +
                        "service<lang.annotations.pkg.second>", 6, 1);
    }

    @Test(description = "Test invalid annotation attachment for service where annotation attachment is only valid" +
            "for annotation def protocol package")
    public void testInvalidAttachmentInServiceWhenAttachPointIsDifferentPkg() {
        CompileResult resultNegative = BCompileUtil.compile(this, "test-src", "lang/annotations/pkg/error2");
        Assert.assertEquals(resultNegative.getErrorCount(), 1);
        BAssertUtil.validateError(resultNegative,
                0, "annotation 'lang.annotations.pkg.first:SampleConfigSecond' is not allowed in " +
                        "service<lang.annotations.pkg.first>", 5, 1);
    }

    @Test(description = "Test global variable as annotation attribute value")
    public void testVariableAsAttributeValue() {
        CompileResult resultNegative = BCompileUtil
                .compile(this, "test-src", "lang/annotations/variable-as-attribute-value.bal");
        Assert.assertEquals(resultNegative.getErrorCount(), 1);
        BAssertUtil.validateError(resultNegative, 0,
                "annotation attribute value should be either constant reference or a basic literal", 4, 1);
    }

    @Test(description = "Test type mismatch in annotation attribute value")
    public void testTypeMismatchInAttributeValue() {
        CompileResult resultNegative = BCompileUtil
                .compile(this, "test-src", "lang/annotations/attribute-value-type-mismatch.bal");
        Assert.assertEquals(resultNegative.getErrorCount(), 1);
        BAssertUtil.validateError(resultNegative, 0,
                "incompatible types: expected 'string', found 'int'", 4, 15);
    }

    @Test(description = "Test default values for annotation")
    public void testDefaultValues() {
        CompileResult compileResult = BCompileUtil.compile(this, "test-src", "lang/annotations/default-values.bal");
        AnnotationAttributeInfo annotationInfo = (AnnotationAttributeInfo) compileResult.getProgFile()
                .getEntryPackage().getFunctionInfo("foo")
                .getAttributeInfo(AttributeInfo.Kind.ANNOTATIONS_ATTRIBUTE);
        AnnAttachmentInfo[] attachmentInfos = annotationInfo.getAttachmentInfoEntries();

        // check for default values for basic literal attributes
        Assert.assertEquals(attachmentInfos[0].getAttributeValue("value").getStringValue(),
                "Description of the service/function");

        // check for default values for non-literal attributes
        Assert.assertEquals(attachmentInfos[0].getAttributeValue("queryParamValue"), null);

        // check for default values for nested annotations
        AnnAttachmentInfo nestedArgAnnot = attachmentInfos[0]
                .getAttributeValue("args").getAnnotationAttachmentValue();
        Assert.assertEquals(nestedArgAnnot.getAttributeValue("value").getStringValue(),
                "default value for 'Args' annotation in doc package");

        // check for default values for nested annotations arrays
        AnnAttachmentInfo nestedAnnot = attachmentInfos[0].getAttributeValue("queryParamValue2")
                .getAttributeValueArray()[0]
                .getAnnotationAttachmentValue();
        Assert.assertEquals(nestedAnnot.getAttributeValue("name").getStringValue(), "default name");
        Assert.assertEquals(nestedAnnot.getAttributeValue("value").getStringValue(), "default value");

        // check for default values for a local annotations
        Assert.assertEquals(attachmentInfos[1].getAttributeValue("value").getStringValue(),
                "default value for local 'Args' annotation");

        long status = attachmentInfos[3].getAttributeValue("status").getIntValue();
        Assert.assertEquals(status, 200);
    }
}
