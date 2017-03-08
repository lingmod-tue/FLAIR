var $wnd = $wnd || window.parent;
var __gwtModuleFunction = $wnd.flair;
var $sendStats = __gwtModuleFunction.__sendStats;
$sendStats('moduleStartup', 'moduleEvalStart');
var $gwt_version = "2.8.0";
var $strongName = '0605A1769BD87AEF3F086EA2EFDE4B46';
var $gwt = {};
var $doc = $wnd.document;
var $moduleName, $moduleBase;
function __gwtStartLoadingFragment(frag) {
var fragFile = 'deferredjs/' + $strongName + '/' + frag + '.cache.js';
return __gwtModuleFunction.__startLoadingFragment(fragFile);
}
function __gwtInstallCode(code) {return __gwtModuleFunction.__installRunAsyncCode(code);}
function __gwt_isKnownPropertyValue(propName, propValue) {
return __gwtModuleFunction.__gwt_isKnownPropertyValue(propName, propValue);
}
function __gwt_getMetaProperty(name) {
return __gwtModuleFunction.__gwt_getMetaProperty(name);
}
var $stats = $wnd.__gwtStatsEvent ? function(a) {
return $wnd.__gwtStatsEvent && $wnd.__gwtStatsEvent(a);
} : null;
var $sessionId = $wnd.__gwtStatsSessionId ? $wnd.__gwtStatsSessionId : null;
var $intern_0 = 'object', $intern_1 = 'function', $intern_2 = 'java.lang', $intern_3 = 'com.flair.client.localization', $intern_4 = {3:1, 8:1}, $intern_5 = {3:1, 9:1}, $intern_6 = 'aria-hidden', $intern_7 = 'true', $intern_8 = 'com.google.gwt.user.client.ui', $intern_9 = {5:1, 4:1}, $intern_10 = {207:1, 5:1, 4:1}, $intern_11 = 'btnWebSearchUI', $intern_12 = 'btnUploadUI', $intern_13 = 'btnAboutUI', $intern_14 = 'btnSwitchLangUI', $intern_15 = 'btnLangEnUI', $intern_16 = 'btnLangDeUI', $intern_17 = 'com.flair.client.views', $intern_18 = 'Web Search', $intern_19 = 'About FLAIR', $intern_20 = '\xDCber FLAIR', $intern_21 = {83:1, 64:1}, $intern_22 = 'navbar-collapse', $intern_23 = 'data-keyboard', $intern_24 = 'com.google.gwt.core.client', $intern_25 = '__noinit__', $intern_26 = '__java$exception', $intern_27 = {3:1, 6:1}, $intern_28 = 'com.google.gwt.core.client.impl', $intern_29 = 'null', $intern_30 = 'div', $intern_31 = {13:1, 3:1, 8:1}, $intern_32 = 'com.google.gwt.dom.client', $intern_33 = 'com.google.web.bindery.event.shared', $intern_34 = 'com.google.gwt.event.shared', $intern_35 = 'com.google.gwt.event.dom.client', $intern_36 = 'click', $intern_37 = {31:1, 3:1, 6:1}, $intern_38 = 'UmbrellaException', $intern_39 = 'DEFAULT', $intern_40 = 'com.google.gwt.safehtml.shared', $intern_41 = 'com.google.gwt.uibinder.client', $intern_42 = 'CSS1Compat', $intern_43 = 'com.google.gwt.user.client', $intern_44 = '.call(this)}', $intern_45 = 'return function() { w.__gwt_dispatchUnhandledEvent_', $intern_46 = {5:1, 7:1, 4:1}, $intern_47 = 'span', $intern_48 = {5:1, 7:1, 49:1, 4:1}, $intern_49 = 'Possible problem with your *.gwt.xml module file.\nThe compile time user.agent value (ie8) does not match the runtime user.agent value (', $intern_50 = ').\n', $intern_51 = 'Expect more errors.', $intern_52 = 'java.util', $intern_53 = '_gwt_modCount', $intern_54 = {17:1}, $intern_55 = 'delete', $intern_56 = 'org.gwtbootstrap3.client.shared.event', $intern_57 = 'org.gwtbootstrap3.client.ui.base', $intern_58 = 'javascript:;', $intern_59 = 'org.gwtbootstrap3.client.ui', $intern_60 = 'data-dismiss', $intern_61 = 'org.gwtbootstrap3.client.ui.base.button', $intern_62 = {5:1, 7:1, 4:1, 22:1}, $intern_63 = "<BUTTON type='button'><\/BUTTON>", $intern_64 = 'org.gwtbootstrap3.client.ui.html', $intern_65 = 'dropdown-toggle', $intern_66 = 'show.bs.modal', $intern_67 = 'shown.bs.modal', $intern_68 = 'hide.bs.modal', $intern_69 = 'hidden.bs.modal', $intern_70 = 'data-backdrop', $intern_71 = 'org.gwtbootstrap3.client.ui.gwt', $intern_72 = {5:1, 7:1, 4:1, 108:1}, $intern_73 = 'org.gwtbootstrap3.client.ui.base.mixin', $intern_74 = 'data-toggle', $intern_75 = 'org.gwtbootstrap3.client.ui.base.modal', $intern_76 = 'org.gwtbootstrap3.client.ui.constants', $intern_77 = 'NONE';
var _, com_google_gwt_lang_Runtime_prototypesByTypeId, com_google_gwt_lang_ModuleUtils_initFnList, com_google_gwt_lang_CollapsedPropertyHolder_permutationId = -1;
function com_google_gwt_lang_ModuleUtils_setGwtProperty__Ljava_lang_String_2Lcom_google_gwt_core_client_JavaScriptObject_2V(propertyName, propertyValue){
  typeof window === $intern_0 && typeof window['$gwt'] === $intern_0 && (window['$gwt'][propertyName] = propertyValue);
}

function com_google_gwt_lang_ModuleUtils_gwtOnLoad__Lcom_google_gwt_core_client_JavaScriptObject_2Lcom_google_gwt_core_client_JavaScriptObject_2Lcom_google_gwt_core_client_JavaScriptObject_2Lcom_google_gwt_core_client_JavaScriptObject_2V(errFn, modName, modBase, softPermutationId){
  com_google_gwt_lang_ModuleUtils_ensureModuleInit__V();
  var initFnList = com_google_gwt_lang_ModuleUtils_initFnList;
  $moduleName = modName;
  $moduleBase = modBase;
  com_google_gwt_lang_CollapsedPropertyHolder_permutationId = softPermutationId;
  function initializeModules(){
    for (var i = 0; i < initFnList.length; i++) {
      initFnList[i]();
    }
  }

  if (errFn) {
    try {
      $entry(initializeModules)();
    }
     catch (e) {
      errFn(modName, e);
    }
  }
   else {
    $entry(initializeModules)();
  }
}

function com_google_gwt_lang_ModuleUtils_ensureModuleInit__V(){
  com_google_gwt_lang_ModuleUtils_initFnList == null && (com_google_gwt_lang_ModuleUtils_initFnList = []);
}

function com_google_gwt_lang_ModuleUtils_addInitFunctions__V(){
  com_google_gwt_lang_ModuleUtils_ensureModuleInit__V();
  var initFnList = com_google_gwt_lang_ModuleUtils_initFnList;
  for (var i = 0; i < arguments.length; i++) {
    initFnList.push(arguments[i]);
  }
}

function com_google_gwt_lang_Runtime_typeMarkerFn__V(){
}

function com_google_gwt_lang_Runtime_toString__Ljava_lang_Object_2Ljava_lang_String_2(object){
  if (Array.isArray(object) && object.java_lang_Object_typeMarker === com_google_gwt_lang_Runtime_typeMarkerFn__V) {
    return java_lang_Class_$getName__Ljava_lang_Class_2Ljava_lang_String_2(java_lang_Object_getClass_1_1Ljava_1lang_1Class_1_1_1devirtual$__Ljava_lang_Object_2Ljava_lang_Class_2(object)) + '@' + (java_lang_Object_hashCode_1_1I_1_1devirtual$__Ljava_lang_Object_2I(object) >>> 0).toString(16);
  }
  return object.toString();
}

function com_google_gwt_lang_Runtime_portableObjCreate__Lcom_google_gwt_core_client_JavaScriptObject_2Lcom_google_gwt_core_client_JavaScriptObject_2(obj){
  function F(){
  }

  ;
  F.prototype = obj || {};
  return new F;
}

function com_google_gwt_lang_Runtime_emptyMethod__V(){
}

function com_google_gwt_lang_Runtime_defineClass__Lcom_google_gwt_core_client_JavaScriptObject_2Lcom_google_gwt_core_client_JavaScriptObject_2Lcom_google_gwt_core_client_JavaScriptObject_2V(typeId, superTypeIdOrPrototype, castableTypeMap){
  var prototypesByTypeId = com_google_gwt_lang_Runtime_prototypesByTypeId, com_google_gwt_lang_Runtime_createSubclassPrototype__Lcom_google_gwt_core_client_JavaScriptObject_2Lcom_google_gwt_core_client_JavaScriptObject_2_superPrototype_0;
  var prototype = prototypesByTypeId[typeId];
  var clazz = prototype instanceof Array?prototype[0]:null;
  if (prototype && !clazz) {
    _ = prototype;
  }
   else {
    _ = (com_google_gwt_lang_Runtime_createSubclassPrototype__Lcom_google_gwt_core_client_JavaScriptObject_2Lcom_google_gwt_core_client_JavaScriptObject_2_superPrototype_0 = superTypeIdOrPrototype && superTypeIdOrPrototype.prototype , !com_google_gwt_lang_Runtime_createSubclassPrototype__Lcom_google_gwt_core_client_JavaScriptObject_2Lcom_google_gwt_core_client_JavaScriptObject_2_superPrototype_0 && (com_google_gwt_lang_Runtime_createSubclassPrototype__Lcom_google_gwt_core_client_JavaScriptObject_2Lcom_google_gwt_core_client_JavaScriptObject_2_superPrototype_0 = com_google_gwt_lang_Runtime_prototypesByTypeId[superTypeIdOrPrototype]) , com_google_gwt_lang_Runtime_portableObjCreate__Lcom_google_gwt_core_client_JavaScriptObject_2Lcom_google_gwt_core_client_JavaScriptObject_2(com_google_gwt_lang_Runtime_createSubclassPrototype__Lcom_google_gwt_core_client_JavaScriptObject_2Lcom_google_gwt_core_client_JavaScriptObject_2_superPrototype_0));
    _.java_lang_Object_castableTypeMap = castableTypeMap;
    _.constructor = _;
    !superTypeIdOrPrototype && (_.java_lang_Object_typeMarker = com_google_gwt_lang_Runtime_typeMarkerFn__V);
    prototypesByTypeId[typeId] = _;
  }
  for (var i = 3; i < arguments.length; ++i) {
    arguments[i].prototype = _;
  }
  clazz && (_.java_lang_Object__1_1_1clazz = clazz);
}

function com_google_gwt_lang_Runtime_bootstrap__V(){
  com_google_gwt_lang_Runtime_prototypesByTypeId = {};
  !Array.isArray && (Array.isArray = function(vArg){
    return Object.prototype.toString.call(vArg) === '[object Array]';
  }
  );
}

com_google_gwt_lang_Runtime_bootstrap__V();
function java_lang_Object_Object__V(){
}

function java_lang_Object_equals_1Ljava_1lang_1Object_1_1Z_1_1devirtual$__Ljava_lang_Object_2Ljava_lang_Object_2Z(this$static, other){
  return com_google_gwt_lang_Cast_instanceOfString__Ljava_lang_Object_2Z(this$static)?java_lang_String_$equals__Ljava_lang_String_2Ljava_lang_Object_2Z(this$static, other):com_google_gwt_lang_Cast_instanceOfBoolean__Ljava_lang_Object_2Z(this$static)?(javaemul_internal_InternalPreconditions_checkCriticalNotNull__Ljava_lang_Object_2Ljava_lang_Object_2(this$static) , this$static === other):com_google_gwt_lang_Cast_hasJavaObjectVirtualDispatch__Ljava_lang_Object_2Z(this$static)?this$static.equals__Ljava_lang_Object_2Z(other):com_google_gwt_lang_Array_isJavaArray__Ljava_lang_Object_2Z(this$static)?this$static === other:!!this$static && !!this$static.equals?this$static.equals(other):com_google_gwt_lang_Cast_maskUndefined__Ljava_lang_Object_2Ljava_lang_Object_2(this$static) === com_google_gwt_lang_Cast_maskUndefined__Ljava_lang_Object_2Ljava_lang_Object_2(other);
}

function java_lang_Object_getClass_1_1Ljava_1lang_1Class_1_1_1devirtual$__Ljava_lang_Object_2Ljava_lang_Class_2(this$static){
  return com_google_gwt_lang_Cast_instanceOfString__Ljava_lang_Object_2Z(this$static)?com_google_gwt_lang_ClassLiteralHolder_Ljava_1lang_1String_12_1classLit:com_google_gwt_lang_Cast_instanceOfBoolean__Ljava_lang_Object_2Z(this$static)?com_google_gwt_lang_ClassLiteralHolder_Ljava_1lang_1Boolean_12_1classLit:com_google_gwt_lang_Cast_hasJavaObjectVirtualDispatch__Ljava_lang_Object_2Z(this$static)?this$static.java_lang_Object__1_1_1clazz:com_google_gwt_lang_Array_isJavaArray__Ljava_lang_Object_2Z(this$static)?this$static.java_lang_Object__1_1_1clazz:this$static.java_lang_Object__1_1_1clazz || Array.isArray(this$static) && com_google_gwt_lang_Array_getClassLiteralForArray__Ljava_lang_Class_2ILjava_lang_Class_2(com_google_gwt_lang_ClassLiteralHolder_Lcom_1google_1gwt_1core_1client_1JavaScriptObject_12_1classLit, 1) || com_google_gwt_lang_ClassLiteralHolder_Lcom_1google_1gwt_1core_1client_1JavaScriptObject_12_1classLit;
}

function java_lang_Object_hashCode_1_1I_1_1devirtual$__Ljava_lang_Object_2I(this$static){
  return com_google_gwt_lang_Cast_instanceOfString__Ljava_lang_Object_2Z(this$static)?javaemul_internal_StringHashCache_getHashCode__Ljava_lang_String_2I(this$static):com_google_gwt_lang_Cast_instanceOfBoolean__Ljava_lang_Object_2Z(this$static)?(javaemul_internal_InternalPreconditions_checkCriticalNotNull__Ljava_lang_Object_2Ljava_lang_Object_2(this$static) , this$static)?1231:1237:com_google_gwt_lang_Cast_hasJavaObjectVirtualDispatch__Ljava_lang_Object_2Z(this$static)?this$static.hashCode__I():com_google_gwt_lang_Array_isJavaArray__Ljava_lang_Object_2Z(this$static)?javaemul_internal_ObjectHashing_getHashCode__Ljava_lang_Object_2I(this$static):!!this$static && !!this$static.hashCode?this$static.hashCode():javaemul_internal_ObjectHashing_getHashCode__Ljava_lang_Object_2I(this$static);
}

com_google_gwt_lang_Runtime_defineClass__Lcom_google_gwt_core_client_JavaScriptObject_2Lcom_google_gwt_core_client_JavaScriptObject_2Lcom_google_gwt_core_client_JavaScriptObject_2V(1, null, {}, java_lang_Object_Object__V);
_.equals__Ljava_lang_Object_2Z = function java_lang_Object_equals__Ljava_lang_Object_2Z(other){
  return this === other;
}
;
_.getClass__Ljava_lang_Class_2 = function java_lang_Object_getClass__Ljava_lang_Class_2(){
  return this.java_lang_Object__1_1_1clazz;
}
;
_.hashCode__I = function java_lang_Object_hashCode__I(){
  return javaemul_internal_ObjectHashing_getHashCode__Ljava_lang_Object_2I(this);
}
;
_.toString__Ljava_lang_String_2 = function java_lang_Object_toString__Ljava_lang_String_2(){
  return java_lang_Class_$getName__Ljava_lang_Class_2Ljava_lang_String_2(java_lang_Object_getClass_1_1Ljava_1lang_1Class_1_1_1devirtual$__Ljava_lang_Object_2Ljava_lang_Class_2(this)) + '@' + (java_lang_Object_hashCode_1_1I_1_1devirtual$__Ljava_lang_Object_2I(this) >>> 0).toString(16);
}
;
_.equals = function(other){
  return this.equals__Ljava_lang_Object_2Z(other);
}
;
_.hashCode = function(){
  return this.hashCode__I();
}
;
_.toString = function(){
  return this.toString__Ljava_lang_String_2();
}
;
function com_google_gwt_lang_Cast_canCast__Ljava_lang_Object_2Lcom_google_gwt_core_client_JavaScriptObject_2Z(src_0, dstId){
  if (com_google_gwt_lang_Cast_instanceOfString__Ljava_lang_Object_2Z(src_0)) {
    return !!com_google_gwt_lang_Cast_stringCastMap[dstId];
  }
   else if (src_0.java_lang_Object_castableTypeMap) {
    return !!src_0.java_lang_Object_castableTypeMap[dstId];
  }
   else if (com_google_gwt_lang_Cast_instanceOfDouble__Ljava_lang_Object_2Z(src_0)) {
    return !!com_google_gwt_lang_Cast_doubleCastMap[dstId];
  }
   else if (com_google_gwt_lang_Cast_instanceOfBoolean__Ljava_lang_Object_2Z(src_0)) {
    return !!com_google_gwt_lang_Cast_booleanCastMap[dstId];
  }
  return false;
}

function com_google_gwt_lang_Cast_castTo__Ljava_lang_Object_2Lcom_google_gwt_core_client_JavaScriptObject_2Ljava_lang_Object_2(src_0, dstId){
  javaemul_internal_InternalPreconditions_checkCriticalType__ZV(src_0 == null || com_google_gwt_lang_Cast_canCast__Ljava_lang_Object_2Lcom_google_gwt_core_client_JavaScriptObject_2Z(src_0, dstId));
  return src_0;
}

function com_google_gwt_lang_Cast_castToJso__Ljava_lang_Object_2Ljava_lang_Object_2(src_0){
  javaemul_internal_InternalPreconditions_checkCriticalType__ZV(src_0 == null || com_google_gwt_lang_Cast_isJsObjectOrFunction__Ljava_lang_Object_2Z(src_0) && !(src_0.java_lang_Object_typeMarker === com_google_gwt_lang_Runtime_typeMarkerFn__V));
  return src_0;
}

function com_google_gwt_lang_Cast_castToString__Ljava_lang_Object_2Ljava_lang_Object_2(src_0){
  javaemul_internal_InternalPreconditions_checkCriticalType__ZV(src_0 == null || com_google_gwt_lang_Cast_instanceOfString__Ljava_lang_Object_2Z(src_0));
  return src_0;
}

function com_google_gwt_lang_Cast_hasJavaObjectVirtualDispatch__Ljava_lang_Object_2Z(src_0){
  return !Array.isArray(src_0) && src_0.java_lang_Object_typeMarker === com_google_gwt_lang_Runtime_typeMarkerFn__V;
}

function com_google_gwt_lang_Cast_instanceOf__Ljava_lang_Object_2Lcom_google_gwt_core_client_JavaScriptObject_2Z(src_0, dstId){
  return src_0 != null && com_google_gwt_lang_Cast_canCast__Ljava_lang_Object_2Lcom_google_gwt_core_client_JavaScriptObject_2Z(src_0, dstId);
}

function com_google_gwt_lang_Cast_instanceOfBoolean__Ljava_lang_Object_2Z(src_0){
  return typeof src_0 === 'boolean';
}

function com_google_gwt_lang_Cast_instanceOfDouble__Ljava_lang_Object_2Z(src_0){
  return typeof src_0 === 'number';
}

function com_google_gwt_lang_Cast_instanceOfJso__Ljava_lang_Object_2Z(src_0){
  return src_0 != null && com_google_gwt_lang_Cast_isJsObjectOrFunction__Ljava_lang_Object_2Z(src_0) && !(src_0.java_lang_Object_typeMarker === com_google_gwt_lang_Runtime_typeMarkerFn__V);
}

function com_google_gwt_lang_Cast_instanceOfString__Ljava_lang_Object_2Z(src_0){
  return typeof src_0 === 'string';
}

function com_google_gwt_lang_Cast_isJsObjectOrFunction__Ljava_lang_Object_2Z(src_0){
  return typeof src_0 === $intern_0 || typeof src_0 === $intern_1;
}

function com_google_gwt_lang_Cast_maskUndefined__Ljava_lang_Object_2Ljava_lang_Object_2(src_0){
  return src_0 == null?null:src_0;
}

function com_google_gwt_lang_Cast_throwClassCastExceptionUnlessNull__Ljava_lang_Object_2Ljava_lang_Object_2(o){
  javaemul_internal_InternalPreconditions_checkCriticalType__ZV(o == null);
  return o;
}

var com_google_gwt_lang_Cast_booleanCastMap, com_google_gwt_lang_Cast_doubleCastMap, com_google_gwt_lang_Cast_stringCastMap;
function java_lang_Class_$ensureNamesAreInitialized__Ljava_lang_Class_2V(this$static){
  if (this$static.java_lang_Class_typeName != null) {
    return;
  }
  java_lang_Class_initializeNames__Ljava_lang_Class_2V(this$static);
}

function java_lang_Class_$getName__Ljava_lang_Class_2Ljava_lang_String_2(this$static){
  java_lang_Class_$ensureNamesAreInitialized__Ljava_lang_Class_2V(this$static);
  return this$static.java_lang_Class_typeName;
}

function java_lang_Class_Class__V(){
  ++java_lang_Class_nextSequentialId;
  this.java_lang_Class_typeName = null;
  this.java_lang_Class_simpleName = null;
  this.java_lang_Class_packageName = null;
  this.java_lang_Class_compoundName = null;
  this.java_lang_Class_canonicalName = null;
  this.java_lang_Class_typeId = null;
  this.java_lang_Class_arrayLiterals = null;
}

function java_lang_Class_createClassObject__Ljava_lang_String_2Ljava_lang_String_2Lcom_google_gwt_core_client_JavaScriptObject_2Ljava_lang_Class_2(packageName, compoundClassName){
  var clazz;
  clazz = new java_lang_Class_Class__V;
  clazz.java_lang_Class_packageName = packageName;
  clazz.java_lang_Class_compoundName = compoundClassName;
  return clazz;
}

function java_lang_Class_createForClass__Ljava_lang_String_2Ljava_lang_String_2Lcom_google_gwt_core_client_JavaScriptObject_2Ljava_lang_Class_2Ljava_lang_Class_2(packageName, compoundClassName, typeId){
  var clazz;
  clazz = java_lang_Class_createClassObject__Ljava_lang_String_2Ljava_lang_String_2Lcom_google_gwt_core_client_JavaScriptObject_2Ljava_lang_Class_2(packageName, compoundClassName);
  java_lang_Class_maybeSetClassLiteral__Lcom_google_gwt_core_client_JavaScriptObject_2Ljava_lang_Class_2V(typeId, clazz);
  return clazz;
}

function java_lang_Class_createForEnum__Ljava_lang_String_2Ljava_lang_String_2Lcom_google_gwt_core_client_JavaScriptObject_2Ljava_lang_Class_2Lcom_google_gwt_core_client_JavaScriptObject_2Lcom_google_gwt_core_client_JavaScriptObject_2Ljava_lang_Class_2(packageName, compoundClassName, typeId, enumConstantsFunc){
  var clazz;
  clazz = java_lang_Class_createClassObject__Ljava_lang_String_2Ljava_lang_String_2Lcom_google_gwt_core_client_JavaScriptObject_2Ljava_lang_Class_2(packageName, compoundClassName);
  java_lang_Class_maybeSetClassLiteral__Lcom_google_gwt_core_client_JavaScriptObject_2Ljava_lang_Class_2V(typeId, clazz);
  clazz.java_lang_Class_modifiers = enumConstantsFunc?8:0;
  clazz.java_lang_Class_enumConstantsFunc = enumConstantsFunc;
  return clazz;
}

function java_lang_Class_getClassLiteralForArray__Ljava_lang_Class_2ILjava_lang_Class_2(leafClass, dimensions){
  var arrayLiterals = leafClass.java_lang_Class_arrayLiterals = leafClass.java_lang_Class_arrayLiterals || [];
  return arrayLiterals[dimensions] || (arrayLiterals[dimensions] = leafClass.private$java_lang_Class$createClassLiteralForArray__ILjava_lang_Class_2(dimensions));
}

function java_lang_Class_getPrototypeForClass__Ljava_lang_Class_2Lcom_google_gwt_core_client_JavaScriptObject_2(clazz){
  if (clazz.isPrimitive__Z()) {
    return null;
  }
  var typeId = clazz.java_lang_Class_typeId;
  var prototype = com_google_gwt_lang_Runtime_prototypesByTypeId[typeId];
  return prototype;
}

function java_lang_Class_initializeNames__Ljava_lang_Class_2V(clazz){
  if (clazz.isArray__Z()) {
    var componentType = clazz.java_lang_Class_componentType;
    componentType.isPrimitive__Z()?(clazz.java_lang_Class_typeName = '[' + componentType.java_lang_Class_typeId):!componentType.isArray__Z()?(clazz.java_lang_Class_typeName = '[L' + componentType.getName__Ljava_lang_String_2() + ';'):(clazz.java_lang_Class_typeName = '[' + componentType.getName__Ljava_lang_String_2());
    clazz.java_lang_Class_canonicalName = componentType.getCanonicalName__Ljava_lang_String_2() + '[]';
    clazz.java_lang_Class_simpleName = componentType.getSimpleName__Ljava_lang_String_2() + '[]';
    return;
  }
  var packageName = clazz.java_lang_Class_packageName;
  var compoundName = clazz.java_lang_Class_compoundName;
  compoundName = compoundName.split('/');
  clazz.java_lang_Class_typeName = java_lang_Class_join__Ljava_lang_String_2Lcom_google_gwt_core_client_JavaScriptObject_2Ljava_lang_String_2('.', [packageName, java_lang_Class_join__Ljava_lang_String_2Lcom_google_gwt_core_client_JavaScriptObject_2Ljava_lang_String_2('$', compoundName)]);
  clazz.java_lang_Class_canonicalName = java_lang_Class_join__Ljava_lang_String_2Lcom_google_gwt_core_client_JavaScriptObject_2Ljava_lang_String_2('.', [packageName, java_lang_Class_join__Ljava_lang_String_2Lcom_google_gwt_core_client_JavaScriptObject_2Ljava_lang_String_2('.', compoundName)]);
  clazz.java_lang_Class_simpleName = compoundName[compoundName.length - 1];
}

function java_lang_Class_join__Ljava_lang_String_2Lcom_google_gwt_core_client_JavaScriptObject_2Ljava_lang_String_2(separator, strings){
  var i = 0;
  while (!strings[i] || strings[i] == '') {
    i++;
  }
  var result = strings[i++];
  for (; i < strings.length; i++) {
    if (!strings[i] || strings[i] == '') {
      continue;
    }
    result += separator + strings[i];
  }
  return result;
}

function java_lang_Class_maybeSetClassLiteral__Lcom_google_gwt_core_client_JavaScriptObject_2Ljava_lang_Class_2V(typeId, clazz){
  var proto;
  if (!typeId) {
    return;
  }
  clazz.java_lang_Class_typeId = typeId;
  var prototype = java_lang_Class_getPrototypeForClass__Ljava_lang_Class_2Lcom_google_gwt_core_client_JavaScriptObject_2(clazz);
  if (!prototype) {
    com_google_gwt_lang_Runtime_prototypesByTypeId[typeId] = [clazz];
    return;
  }
  prototype.java_lang_Object__1_1_1clazz = clazz;
}

com_google_gwt_lang_Runtime_defineClass__Lcom_google_gwt_core_client_JavaScriptObject_2Lcom_google_gwt_core_client_JavaScriptObject_2Lcom_google_gwt_core_client_JavaScriptObject_2V(85, 1, {}, java_lang_Class_Class__V);
_.private$java_lang_Class$createClassLiteralForArray__ILjava_lang_Class_2 = function java_lang_Class_createClassLiteralForArray__ILjava_lang_Class_2(dimensions){
  var clazz;
  clazz = new java_lang_Class_Class__V;
  clazz.java_lang_Class_modifiers = 4;
  dimensions > 1?(clazz.java_lang_Class_componentType = java_lang_Class_getClassLiteralForArray__Ljava_lang_Class_2ILjava_lang_Class_2(this, dimensions - 1)):(clazz.java_lang_Class_componentType = this);
  return clazz;
}
;
_.getCanonicalName__Ljava_lang_String_2 = function java_lang_Class_getCanonicalName__Ljava_lang_String_2(){
  java_lang_Class_$ensureNamesAreInitialized__Ljava_lang_Class_2V(this);
  return this.java_lang_Class_canonicalName;
}
;
_.getName__Ljava_lang_String_2 = function java_lang_Class_getName__Ljava_lang_String_2(){
  return java_lang_Class_$getName__Ljava_lang_Class_2Ljava_lang_String_2(this);
}
;
_.getSimpleName__Ljava_lang_String_2 = function java_lang_Class_getSimpleName__Ljava_lang_String_2(){
  java_lang_Class_$ensureNamesAreInitialized__Ljava_lang_Class_2V(this);
  return this.java_lang_Class_simpleName;
}
;
_.isArray__Z = function java_lang_Class_isArray__Z(){
  return (this.java_lang_Class_modifiers & 4) != 0;
}
;
_.isPrimitive__Z = function java_lang_Class_isPrimitive__Z(){
  return (this.java_lang_Class_modifiers & 1) != 0;
}
;
_.toString__Ljava_lang_String_2 = function java_lang_Class_toString__Ljava_lang_String_2(){
  return ((this.java_lang_Class_modifiers & 2) != 0?'interface ':(this.java_lang_Class_modifiers & 1) != 0?'':'class ') + (java_lang_Class_$ensureNamesAreInitialized__Ljava_lang_Class_2V(this) , this.java_lang_Class_typeName);
}
;
_.java_lang_Class_modifiers = 0;
var java_lang_Class_nextSequentialId = 1;
var com_google_gwt_lang_ClassLiteralHolder_Ljava_1lang_1Object_12_1classLit = java_lang_Class_createForClass__Ljava_lang_String_2Ljava_lang_String_2Lcom_google_gwt_core_client_JavaScriptObject_2Ljava_lang_Class_2Ljava_lang_Class_2($intern_2, 'Object', 1);
var com_google_gwt_lang_ClassLiteralHolder_Ljava_1lang_1Class_12_1classLit = java_lang_Class_createForClass__Ljava_lang_String_2Ljava_lang_String_2Lcom_google_gwt_core_client_JavaScriptObject_2Ljava_lang_Class_2Ljava_lang_Class_2($intern_2, 'Class', 85);
function com_flair_client_ClientEndPoint_$clinit__V(){
  com_flair_client_ClientEndPoint_$clinit__V = com_google_gwt_lang_Runtime_emptyMethod__V;
  com_flair_client_ClientEndPoint_INSTANCE = new com_flair_client_ClientEndPoint_ClientEndPoint__V;
}

function com_flair_client_ClientEndPoint_$init__Lcom_flair_client_ClientEndPoint_2V(this$static){
  if (this$static.com_flair_client_ClientEndPoint_initialized)
    throw com_google_gwt_lang_Exceptions_toJs__Ljava_lang_Object_2Ljava_lang_Object_2(new java_lang_RuntimeException_RuntimeException__Ljava_lang_String_2V('Client endpoint already initialized'));
  this$static.com_flair_client_ClientEndPoint_initialized = true;
  this$static.com_flair_client_ClientEndPoint_viewport = new com_flair_client_views_MainViewport_MainViewport__V;
  com_google_gwt_user_client_ui_AbsolutePanel_$add__Lcom_google_gwt_user_client_ui_AbsolutePanel_2Lcom_google_gwt_user_client_ui_Widget_2V((com_google_gwt_user_client_ui_RootPanel_$clinit__V() , com_google_gwt_user_client_ui_RootPanel_get__Ljava_lang_String_2Lcom_google_gwt_user_client_ui_RootPanel_2()), this$static.com_flair_client_ClientEndPoint_viewport);
}

function com_flair_client_ClientEndPoint_ClientEndPoint__V(){
  this.com_flair_client_ClientEndPoint_viewport = null;
  this.com_flair_client_ClientEndPoint_initialized = false;
}

com_google_gwt_lang_Runtime_defineClass__Lcom_google_gwt_core_client_JavaScriptObject_2Lcom_google_gwt_core_client_JavaScriptObject_2Lcom_google_gwt_core_client_JavaScriptObject_2V(115, 1, {}, com_flair_client_ClientEndPoint_ClientEndPoint__V);
_.com_flair_client_ClientEndPoint_initialized = false;
var com_flair_client_ClientEndPoint_INSTANCE;
var com_google_gwt_lang_ClassLiteralHolder_Lcom_1flair_1client_1ClientEndPoint_12_1classLit = java_lang_Class_createForClass__Ljava_lang_String_2Ljava_lang_String_2Lcom_google_gwt_core_client_JavaScriptObject_2Ljava_lang_Class_2Ljava_lang_Class_2('com.flair.client', 'ClientEndPoint', 115);
function com_flair_client_localization_LocalizationData_$get__Lcom_flair_client_localization_LocalizationData_2Ljava_lang_String_2Ljava_lang_String_2(this$static, desc){
  if (java_util_AbstractHashMap_$hasStringValue__Ljava_util_AbstractHashMap_2Ljava_lang_String_2Z(this$static.com_flair_client_localization_LocalizationData_store, desc)) {
    return com_google_gwt_lang_Cast_castToString__Ljava_lang_Object_2Ljava_lang_Object_2(java_util_AbstractHashMap_$getStringValue__Ljava_util_AbstractHashMap_2Ljava_lang_String_2Ljava_lang_Object_2(this$static.com_flair_client_localization_LocalizationData_store, desc));
  }
   else {
    throw com_google_gwt_lang_Exceptions_toJs__Ljava_lang_Object_2Ljava_lang_Object_2(new java_lang_RuntimeException_RuntimeException__Ljava_lang_String_2V('Descriptor not found'));
  }
}

function com_flair_client_localization_LocalizationData_$put__Lcom_flair_client_localization_LocalizationData_2Ljava_lang_String_2Ljava_lang_String_2V(this$static, desc, val){
  if (java_util_AbstractHashMap_$hasStringValue__Ljava_util_AbstractHashMap_2Ljava_lang_String_2Z(this$static.com_flair_client_localization_LocalizationData_store, desc))
    throw com_google_gwt_lang_Exceptions_toJs__Ljava_lang_Object_2Ljava_lang_Object_2(new java_lang_RuntimeException_RuntimeException__Ljava_lang_String_2V('Descriptor already registered'));
  else 
    java_util_AbstractHashMap_$putStringValue__Ljava_util_AbstractHashMap_2Ljava_lang_String_2Ljava_lang_Object_2Ljava_lang_Object_2(this$static.com_flair_client_localization_LocalizationData_store, desc, val);
}

function com_flair_client_localization_LocalizationData_LocalizationData__Lcom_flair_client_localization_LocalizationLanguage_2V(lang_0){
  this.com_flair_client_localization_LocalizationData_lang = lang_0;
  this.com_flair_client_localization_LocalizationData_store = new java_util_HashMap_HashMap__V;
}

com_google_gwt_lang_Runtime_defineClass__Lcom_google_gwt_core_client_JavaScriptObject_2Lcom_google_gwt_core_client_JavaScriptObject_2Lcom_google_gwt_core_client_JavaScriptObject_2V(53, 1, {53:1}, com_flair_client_localization_LocalizationData_LocalizationData__Lcom_flair_client_localization_LocalizationLanguage_2V);
var com_google_gwt_lang_ClassLiteralHolder_Lcom_1flair_1client_1localization_1LocalizationData_12_1classLit = java_lang_Class_createForClass__Ljava_lang_String_2Ljava_lang_String_2Lcom_google_gwt_core_client_JavaScriptObject_2Ljava_lang_Class_2Ljava_lang_Class_2($intern_3, 'LocalizationData', 53);
function com_flair_client_localization_LocalizationEngine_$clinit__V(){
  com_flair_client_localization_LocalizationEngine_$clinit__V = com_google_gwt_lang_Runtime_emptyMethod__V;
  com_flair_client_localization_LocalizationEngine_INSTANCE = new com_flair_client_localization_LocalizationEngine_LocalizationEngine__V;
}

function com_flair_client_localization_LocalizationEngine_$deregisterLocalizedView__Lcom_flair_client_localization_LocalizationEngine_2Lcom_flair_client_localization_LocalizedUI_2V(this$static, view){
  java_util_HashSet_$remove__Ljava_util_HashSet_2Ljava_lang_Object_2Z(this$static.com_flair_client_localization_LocalizationEngine_activeLocalizedViews, view);
}

function com_flair_client_localization_LocalizationEngine_$refreshActiveViews__Lcom_flair_client_localization_LocalizationEngine_2V(this$static){
  var entry, itr, itr$iterator, outerIter;
  for (itr$iterator = (outerIter = new java_util_AbstractHashMap$EntrySetIterator_AbstractHashMap$EntrySetIterator__Ljava_util_AbstractHashMap_2V((new java_util_AbstractHashMap$EntrySet_AbstractHashMap$EntrySet__Ljava_util_AbstractHashMap_2V((new java_util_AbstractMap$1_AbstractMap$1__Ljava_util_AbstractMap_2V(this$static.com_flair_client_localization_LocalizationEngine_activeLocalizedViews.java_util_HashSet_map)).java_util_AbstractMap$1_this$01)).java_util_AbstractHashMap$EntrySet_this$01) , new java_util_AbstractMap$1$1_AbstractMap$1$1__Ljava_util_AbstractMap$1_2V(outerIter)); itr$iterator.java_util_AbstractMap$1$1_val$outerIter2.java_util_AbstractHashMap$EntrySetIterator_hasNext;) {
    itr = (entry = java_util_AbstractHashMap$EntrySetIterator_$next__Ljava_util_AbstractHashMap$EntrySetIterator_2Ljava_util_Map$Entry_2(itr$iterator.java_util_AbstractMap$1$1_val$outerIter2) , com_google_gwt_lang_Cast_castTo__Ljava_lang_Object_2Lcom_google_gwt_core_client_JavaScriptObject_2Ljava_lang_Object_2(entry.getKey__Ljava_lang_Object_2(), 207));
    com_flair_client_localization_LocalizedCompositeView_$setLocalization__Lcom_flair_client_localization_LocalizedCompositeView_2Lcom_flair_client_localization_LocalizationLanguage_2V(itr, this$static.com_flair_client_localization_LocalizationEngine_currentLang);
  }
}

function com_flair_client_localization_LocalizationEngine_$registerLocalizedView__Lcom_flair_client_localization_LocalizationEngine_2Lcom_flair_client_localization_LocalizedUI_2V(this$static, view){
  java_util_HashSet_$add__Ljava_util_HashSet_2Ljava_lang_Object_2Z(this$static.com_flair_client_localization_LocalizationEngine_activeLocalizedViews, view);
}

function com_flair_client_localization_LocalizationEngine_$setLanguage__Lcom_flair_client_localization_LocalizationEngine_2Lcom_flair_client_localization_LocalizationLanguage_2V(this$static, lang_0){
  if (lang_0 != this$static.com_flair_client_localization_LocalizationEngine_currentLang) {
    this$static.com_flair_client_localization_LocalizationEngine_currentLang = lang_0;
    com_flair_client_localization_LocalizationEngine_$refreshActiveViews__Lcom_flair_client_localization_LocalizationEngine_2V(this$static);
  }
}

function com_flair_client_localization_LocalizationEngine_LocalizationEngine__V(){
  this.com_flair_client_localization_LocalizationEngine_currentLang = (com_flair_client_localization_LocalizationLanguage_$clinit__V() , com_flair_client_localization_LocalizationLanguage_ENGLISH);
  this.com_flair_client_localization_LocalizationEngine_activeLocalizedViews = new java_util_HashSet_HashSet__V;
}

com_google_gwt_lang_Runtime_defineClass__Lcom_google_gwt_core_client_JavaScriptObject_2Lcom_google_gwt_core_client_JavaScriptObject_2Lcom_google_gwt_core_client_JavaScriptObject_2V(158, 1, {}, com_flair_client_localization_LocalizationEngine_LocalizationEngine__V);
var com_flair_client_localization_LocalizationEngine_INSTANCE;
var com_google_gwt_lang_ClassLiteralHolder_Lcom_1flair_1client_1localization_1LocalizationEngine_12_1classLit = java_lang_Class_createForClass__Ljava_lang_String_2Ljava_lang_String_2Lcom_google_gwt_core_client_JavaScriptObject_2Ljava_lang_Class_2Ljava_lang_Class_2($intern_3, 'LocalizationEngine', 158);
function java_lang_Enum_Enum__Ljava_lang_String_2IV(name_0, ordinal){
  this.java_lang_Enum_name = name_0;
  this.java_lang_Enum_ordinal = ordinal;
}

com_google_gwt_lang_Runtime_defineClass__Lcom_google_gwt_core_client_JavaScriptObject_2Lcom_google_gwt_core_client_JavaScriptObject_2Lcom_google_gwt_core_client_JavaScriptObject_2V(12, 1, $intern_4);
_.equals__Ljava_lang_Object_2Z = function java_lang_Enum_equals__Ljava_lang_Object_2Z(other){
  return this === other;
}
;
_.hashCode__I = function java_lang_Enum_hashCode__I(){
  return javaemul_internal_ObjectHashing_getHashCode__Ljava_lang_Object_2I(this);
}
;
_.toString__Ljava_lang_String_2 = function java_lang_Enum_toString__Ljava_lang_String_2(){
  return this.java_lang_Enum_name != null?this.java_lang_Enum_name:'' + this.java_lang_Enum_ordinal;
}
;
_.java_lang_Enum_ordinal = 0;
var com_google_gwt_lang_ClassLiteralHolder_Ljava_1lang_1Enum_12_1classLit = java_lang_Class_createForClass__Ljava_lang_String_2Ljava_lang_String_2Lcom_google_gwt_core_client_JavaScriptObject_2Ljava_lang_Class_2Ljava_lang_Class_2($intern_2, 'Enum', 12);
function com_flair_client_localization_LocalizationLanguage_$clinit__V(){
  com_flair_client_localization_LocalizationLanguage_$clinit__V = com_google_gwt_lang_Runtime_emptyMethod__V;
  com_flair_client_localization_LocalizationLanguage_ENGLISH = new com_flair_client_localization_LocalizationLanguage_LocalizationLanguage__Ljava_lang_String_2IV('ENGLISH', 0);
  com_flair_client_localization_LocalizationLanguage_GERMAN = new com_flair_client_localization_LocalizationLanguage_LocalizationLanguage__Ljava_lang_String_2IV('GERMAN', 1);
}

function com_flair_client_localization_LocalizationLanguage_LocalizationLanguage__Ljava_lang_String_2IV(enum$name, enum$ordinal){
  java_lang_Enum_Enum__Ljava_lang_String_2IV.call(this, enum$name, enum$ordinal);
}

function com_flair_client_localization_LocalizationLanguage_values___3Lcom_flair_client_localization_LocalizationLanguage_2(){
  com_flair_client_localization_LocalizationLanguage_$clinit__V();
  return com_google_gwt_lang_Array_stampJavaTypeInfo__Ljava_lang_Class_2Lcom_google_gwt_core_client_JavaScriptObject_2Lcom_google_gwt_core_client_JavaScriptObject_2ILjava_lang_Object_2Ljava_lang_Object_2(com_google_gwt_lang_Array_getClassLiteralForArray__Ljava_lang_Class_2ILjava_lang_Class_2(com_google_gwt_lang_ClassLiteralHolder_Lcom_1flair_1client_1localization_1LocalizationLanguage_12_1classLit, 1), $intern_5, 76, 0, [com_flair_client_localization_LocalizationLanguage_ENGLISH, com_flair_client_localization_LocalizationLanguage_GERMAN]);
}

com_google_gwt_lang_Runtime_defineClass__Lcom_google_gwt_core_client_JavaScriptObject_2Lcom_google_gwt_core_client_JavaScriptObject_2Lcom_google_gwt_core_client_JavaScriptObject_2V(76, 12, $intern_4, com_flair_client_localization_LocalizationLanguage_LocalizationLanguage__Ljava_lang_String_2IV);
var com_flair_client_localization_LocalizationLanguage_ENGLISH, com_flair_client_localization_LocalizationLanguage_GERMAN;
var com_google_gwt_lang_ClassLiteralHolder_Lcom_1flair_1client_1localization_1LocalizationLanguage_12_1classLit = java_lang_Class_createForEnum__Ljava_lang_String_2Ljava_lang_String_2Lcom_google_gwt_core_client_JavaScriptObject_2Ljava_lang_Class_2Lcom_google_gwt_core_client_JavaScriptObject_2Lcom_google_gwt_core_client_JavaScriptObject_2Ljava_lang_Class_2($intern_3, 'LocalizationLanguage', 76, com_flair_client_localization_LocalizationLanguage_values___3Lcom_flair_client_localization_LocalizationLanguage_2);
function com_google_gwt_user_client_ui_UIObject_$addStyleName__Lcom_google_gwt_user_client_ui_UIObject_2Ljava_lang_String_2V(this$static, style){
  com_google_gwt_user_client_ui_UIObject_setStyleName__Lcom_google_gwt_dom_client_Element_2Ljava_lang_String_2ZV(this$static.com_google_gwt_user_client_ui_UIObject_element, style, true);
}

function com_google_gwt_user_client_ui_UIObject_$resolvePotentialElement__Lcom_google_gwt_user_client_ui_UIObject_2Lcom_google_gwt_dom_client_Element_2(){
  throw com_google_gwt_lang_Exceptions_toJs__Ljava_lang_Object_2Ljava_lang_Object_2(new java_lang_UnsupportedOperationException_UnsupportedOperationException__V);
}

function com_google_gwt_user_client_ui_UIObject_$setElement__Lcom_google_gwt_user_client_ui_UIObject_2Lcom_google_gwt_user_client_Element_2V(this$static, elem){
  this$static.com_google_gwt_user_client_ui_UIObject_element = elem;
}

function com_google_gwt_user_client_ui_UIObject_$setStyleName__Lcom_google_gwt_user_client_ui_UIObject_2Ljava_lang_String_2V(this$static, style){
  com_google_gwt_dom_client_Element_$setClassName__Lcom_google_gwt_dom_client_Element_2Ljava_lang_String_2V(this$static.com_google_gwt_user_client_ui_UIObject_element, style);
}

function com_google_gwt_user_client_ui_UIObject_setStyleName__Lcom_google_gwt_dom_client_Element_2Ljava_lang_String_2ZV(elem, style, add_0){
  if (!elem) {
    throw com_google_gwt_lang_Exceptions_toJs__Ljava_lang_Object_2Ljava_lang_Object_2(new java_lang_RuntimeException_RuntimeException__Ljava_lang_String_2V('Null widget handle. If you are creating a composite, ensure that initWidget() has been called.'));
  }
  style = java_lang_String_$trim__Ljava_lang_String_2Ljava_lang_String_2(style);
  if (style.length == 0) {
    throw com_google_gwt_lang_Exceptions_toJs__Ljava_lang_Object_2Ljava_lang_Object_2(new java_lang_IllegalArgumentException_IllegalArgumentException__Ljava_lang_String_2V('Style names cannot be empty'));
  }
  add_0?com_google_gwt_dom_client_Element_$addClassName__Lcom_google_gwt_dom_client_Element_2Ljava_lang_String_2Z(elem, style):com_google_gwt_dom_client_Element_$removeClassName__Lcom_google_gwt_dom_client_Element_2Ljava_lang_String_2Z(elem, style);
}

function com_google_gwt_user_client_ui_UIObject_setVisible__Lcom_google_gwt_dom_client_Element_2ZV(elem, visible){
  elem.style.display = visible?'':'none';
  visible?elem.removeAttribute($intern_6):elem.setAttribute($intern_6, $intern_7);
}

com_google_gwt_lang_Runtime_defineClass__Lcom_google_gwt_core_client_JavaScriptObject_2Lcom_google_gwt_core_client_JavaScriptObject_2Lcom_google_gwt_core_client_JavaScriptObject_2V(221, 1, {});
_.resolvePotentialElement__Lcom_google_gwt_dom_client_Element_2 = function com_google_gwt_user_client_ui_UIObject_resolvePotentialElement__Lcom_google_gwt_dom_client_Element_2(){
  return com_google_gwt_user_client_ui_UIObject_$resolvePotentialElement__Lcom_google_gwt_user_client_ui_UIObject_2Lcom_google_gwt_dom_client_Element_2();
}
;
_.toString__Ljava_lang_String_2 = function com_google_gwt_user_client_ui_UIObject_toString__Ljava_lang_String_2(){
  if (!this.com_google_gwt_user_client_ui_UIObject_element) {
    return '(null handle)';
  }
  return this.com_google_gwt_user_client_ui_UIObject_element.outerHTML;
}
;
var com_google_gwt_lang_ClassLiteralHolder_Lcom_1google_1gwt_1user_1client_1ui_1UIObject_12_1classLit = java_lang_Class_createForClass__Ljava_lang_String_2Ljava_lang_String_2Lcom_google_gwt_core_client_JavaScriptObject_2Ljava_lang_Class_2Ljava_lang_Class_2($intern_8, 'UIObject', 221);
function com_google_gwt_user_client_ui_Widget_$addDomHandler__Lcom_google_gwt_user_client_ui_Widget_2Lcom_google_gwt_event_shared_EventHandler_2Lcom_google_gwt_event_dom_client_DomEvent$Type_2Lcom_google_gwt_event_shared_HandlerRegistration_2(this$static, handler, type_0){
  var typeInt;
  typeInt = com_google_gwt_user_client_impl_DOMImpl_$eventGetTypeInt__Lcom_google_gwt_user_client_impl_DOMImpl_2Ljava_lang_String_2I(type_0.com_google_gwt_event_dom_client_DomEvent$Type_name);
  typeInt == -1?null:this$static.com_google_gwt_user_client_ui_Widget_eventsToSink == -1?com_google_gwt_user_client_DOM_sinkEvents__Lcom_google_gwt_dom_client_Element_2IV(this$static.com_google_gwt_user_client_ui_UIObject_element, typeInt | (this$static.com_google_gwt_user_client_ui_UIObject_element.__eventBits || 0)):(this$static.com_google_gwt_user_client_ui_Widget_eventsToSink |= typeInt);
  return com_google_gwt_event_shared_HandlerManager_$addHandler__Lcom_google_gwt_event_shared_HandlerManager_2Lcom_google_gwt_event_shared_GwtEvent$Type_2Lcom_google_gwt_event_shared_EventHandler_2Lcom_google_gwt_event_shared_HandlerRegistration_2(!this$static.com_google_gwt_user_client_ui_Widget_handlerManager?(this$static.com_google_gwt_user_client_ui_Widget_handlerManager = new com_google_gwt_event_shared_HandlerManager_HandlerManager__Ljava_lang_Object_2V(this$static)):this$static.com_google_gwt_user_client_ui_Widget_handlerManager, type_0, handler);
}

function com_google_gwt_user_client_ui_Widget_$addHandler__Lcom_google_gwt_user_client_ui_Widget_2Lcom_google_gwt_event_shared_EventHandler_2Lcom_google_gwt_event_shared_GwtEvent$Type_2Lcom_google_gwt_event_shared_HandlerRegistration_2(this$static, handler, type_0){
  return com_google_gwt_event_shared_HandlerManager_$addHandler__Lcom_google_gwt_event_shared_HandlerManager_2Lcom_google_gwt_event_shared_GwtEvent$Type_2Lcom_google_gwt_event_shared_EventHandler_2Lcom_google_gwt_event_shared_HandlerRegistration_2(!this$static.com_google_gwt_user_client_ui_Widget_handlerManager?(this$static.com_google_gwt_user_client_ui_Widget_handlerManager = new com_google_gwt_event_shared_HandlerManager_HandlerManager__Ljava_lang_Object_2V(this$static)):this$static.com_google_gwt_user_client_ui_Widget_handlerManager, type_0, handler);
}

function com_google_gwt_user_client_ui_Widget_$delegateEvent__Lcom_google_gwt_user_client_ui_Widget_2Lcom_google_gwt_user_client_ui_Widget_2Lcom_google_gwt_event_shared_GwtEvent_2V(target, event_0){
  !!target.com_google_gwt_user_client_ui_Widget_handlerManager && com_google_gwt_event_shared_HandlerManager_$fireEvent__Lcom_google_gwt_event_shared_HandlerManager_2Lcom_google_gwt_event_shared_GwtEvent_2V(target.com_google_gwt_user_client_ui_Widget_handlerManager, event_0);
}

function com_google_gwt_user_client_ui_Widget_$fireEvent__Lcom_google_gwt_user_client_ui_Widget_2Lcom_google_gwt_event_shared_GwtEvent_2V(this$static, event_0){
  !!this$static.com_google_gwt_user_client_ui_Widget_handlerManager && com_google_gwt_event_shared_HandlerManager_$fireEvent__Lcom_google_gwt_event_shared_HandlerManager_2Lcom_google_gwt_event_shared_GwtEvent_2V(this$static.com_google_gwt_user_client_ui_Widget_handlerManager, event_0);
}

function com_google_gwt_user_client_ui_Widget_$onAttach__Lcom_google_gwt_user_client_ui_Widget_2V(this$static){
  var bitsToAdd;
  if (this$static.isAttached__Z()) {
    throw com_google_gwt_lang_Exceptions_toJs__Ljava_lang_Object_2Ljava_lang_Object_2(new java_lang_IllegalStateException_IllegalStateException__Ljava_lang_String_2V("Should only call onAttach when the widget is detached from the browser's document"));
  }
  this$static.com_google_gwt_user_client_ui_Widget_attached = true;
  com_google_gwt_user_client_impl_DOMImpl_setEventListener__Lcom_google_gwt_dom_client_Element_2Lcom_google_gwt_user_client_EventListener_2V(this$static.com_google_gwt_user_client_ui_UIObject_element, this$static);
  bitsToAdd = this$static.com_google_gwt_user_client_ui_Widget_eventsToSink;
  this$static.com_google_gwt_user_client_ui_Widget_eventsToSink = -1;
  bitsToAdd > 0 && (this$static.com_google_gwt_user_client_ui_Widget_eventsToSink == -1?com_google_gwt_user_client_DOM_sinkEvents__Lcom_google_gwt_dom_client_Element_2IV(this$static.com_google_gwt_user_client_ui_UIObject_element, bitsToAdd | (this$static.com_google_gwt_user_client_ui_UIObject_element.__eventBits || 0)):(this$static.com_google_gwt_user_client_ui_Widget_eventsToSink |= bitsToAdd));
  this$static.doAttachChildren__V();
  this$static.onLoad__V();
}

function com_google_gwt_user_client_ui_Widget_$onBrowserEvent__Lcom_google_gwt_user_client_ui_Widget_2Lcom_google_gwt_user_client_Event_2V(this$static, event_0){
  var related;
  switch (com_google_gwt_user_client_impl_DOMImpl_$eventGetTypeInt__Lcom_google_gwt_user_client_impl_DOMImpl_2Ljava_lang_String_2I(event_0.type)) {
    case 16:
    case 32:
      related = event_0.relatedTarget || (event_0.type == 'mouseout'?event_0.toElement:event_0.fromElement);
      if (!!related && com_google_gwt_dom_client_DOMImplTrident_isOrHasChildImpl__Lcom_google_gwt_dom_client_Node_2Lcom_google_gwt_dom_client_Node_2Z(this$static.com_google_gwt_user_client_ui_UIObject_element, related)) {
        return;
      }

  }
  com_google_gwt_event_dom_client_DomEvent_fireNativeEvent__Lcom_google_gwt_dom_client_NativeEvent_2Lcom_google_gwt_event_shared_HasHandlers_2Lcom_google_gwt_dom_client_Element_2V(event_0, this$static, this$static.com_google_gwt_user_client_ui_UIObject_element);
}

function com_google_gwt_user_client_ui_Widget_$onDetach__Lcom_google_gwt_user_client_ui_Widget_2V(this$static){
  if (!this$static.isAttached__Z()) {
    throw com_google_gwt_lang_Exceptions_toJs__Ljava_lang_Object_2Ljava_lang_Object_2(new java_lang_IllegalStateException_IllegalStateException__Ljava_lang_String_2V("Should only call onDetach when the widget is attached to the browser's document"));
  }
  try {
    this$static.onUnload__V();
  }
   finally {
    try {
      this$static.doDetachChildren__V();
    }
     finally {
      this$static.com_google_gwt_user_client_ui_UIObject_element.__listener = null;
      this$static.com_google_gwt_user_client_ui_Widget_attached = false;
    }
  }
}

function com_google_gwt_user_client_ui_Widget_$removeFromParent__Lcom_google_gwt_user_client_ui_Widget_2V(this$static){
  if (!this$static.com_google_gwt_user_client_ui_Widget_parent) {
    com_google_gwt_user_client_ui_RootPanel_$clinit__V();
    java_util_HashSet_$contains__Ljava_util_HashSet_2Ljava_lang_Object_2Z(com_google_gwt_user_client_ui_RootPanel_widgetsToDetach, this$static) && com_google_gwt_user_client_ui_RootPanel_detachNow__Lcom_google_gwt_user_client_ui_Widget_2V(this$static);
  }
   else if (com_google_gwt_lang_Cast_instanceOf__Ljava_lang_Object_2Lcom_google_gwt_core_client_JavaScriptObject_2Z(this$static.com_google_gwt_user_client_ui_Widget_parent, 7)) {
    com_google_gwt_lang_Cast_castTo__Ljava_lang_Object_2Lcom_google_gwt_core_client_JavaScriptObject_2Ljava_lang_Object_2(this$static.com_google_gwt_user_client_ui_Widget_parent, 7).remove__Lcom_google_gwt_user_client_ui_Widget_2Z(this$static);
  }
   else if (this$static.com_google_gwt_user_client_ui_Widget_parent) {
    throw com_google_gwt_lang_Exceptions_toJs__Ljava_lang_Object_2Ljava_lang_Object_2(new java_lang_IllegalStateException_IllegalStateException__Ljava_lang_String_2V("This widget's parent does not implement HasWidgets"));
  }
}

function com_google_gwt_user_client_ui_Widget_$setParent__Lcom_google_gwt_user_client_ui_Widget_2Lcom_google_gwt_user_client_ui_Widget_2V(this$static, parent_0){
  var oldParent;
  oldParent = this$static.com_google_gwt_user_client_ui_Widget_parent;
  if (!parent_0) {
    try {
      !!oldParent && oldParent.isAttached__Z() && this$static.onDetach__V();
    }
     finally {
      this$static.com_google_gwt_user_client_ui_Widget_parent = null;
    }
  }
   else {
    if (oldParent) {
      throw com_google_gwt_lang_Exceptions_toJs__Ljava_lang_Object_2Ljava_lang_Object_2(new java_lang_IllegalStateException_IllegalStateException__Ljava_lang_String_2V('Cannot set a new parent without first clearing the old parent'));
    }
    this$static.com_google_gwt_user_client_ui_Widget_parent = parent_0;
    parent_0.isAttached__Z() && this$static.onAttach__V();
  }
}

function com_google_gwt_user_client_ui_Widget_$sinkEvents__Lcom_google_gwt_user_client_ui_Widget_2IV(this$static, eventBitsToAdd){
  this$static.com_google_gwt_user_client_ui_Widget_eventsToSink == -1?com_google_gwt_user_client_DOM_sinkEvents__Lcom_google_gwt_dom_client_Element_2IV(this$static.com_google_gwt_user_client_ui_UIObject_element, eventBitsToAdd | (this$static.com_google_gwt_user_client_ui_UIObject_element.__eventBits || 0)):(this$static.com_google_gwt_user_client_ui_Widget_eventsToSink |= eventBitsToAdd);
}

com_google_gwt_lang_Runtime_defineClass__Lcom_google_gwt_core_client_JavaScriptObject_2Lcom_google_gwt_core_client_JavaScriptObject_2Lcom_google_gwt_core_client_JavaScriptObject_2V(4, 221, $intern_9);
_.doAttachChildren__V = function com_google_gwt_user_client_ui_Widget_doAttachChildren__V(){
}
;
_.doDetachChildren__V = function com_google_gwt_user_client_ui_Widget_doDetachChildren__V(){
}
;
_.isAttached__Z = function com_google_gwt_user_client_ui_Widget_isAttached__Z(){
  return this.com_google_gwt_user_client_ui_Widget_attached;
}
;
_.onAttach__V = function com_google_gwt_user_client_ui_Widget_onAttach__V(){
  com_google_gwt_user_client_ui_Widget_$onAttach__Lcom_google_gwt_user_client_ui_Widget_2V(this);
}
;
_.onBrowserEvent__Lcom_google_gwt_user_client_Event_2V = function com_google_gwt_user_client_ui_Widget_onBrowserEvent__Lcom_google_gwt_user_client_Event_2V(event_0){
  com_google_gwt_user_client_ui_Widget_$onBrowserEvent__Lcom_google_gwt_user_client_ui_Widget_2Lcom_google_gwt_user_client_Event_2V(this, event_0);
}
;
_.onDetach__V = function com_google_gwt_user_client_ui_Widget_onDetach__V(){
  com_google_gwt_user_client_ui_Widget_$onDetach__Lcom_google_gwt_user_client_ui_Widget_2V(this);
}
;
_.onLoad__V = function com_google_gwt_user_client_ui_Widget_onLoad__V(){
}
;
_.onUnload__V = function com_google_gwt_user_client_ui_Widget_onUnload__V(){
}
;
_.com_google_gwt_user_client_ui_Widget_attached = false;
_.com_google_gwt_user_client_ui_Widget_eventsToSink = 0;
var com_google_gwt_lang_ClassLiteralHolder_Lcom_1google_1gwt_1user_1client_1ui_1Widget_12_1classLit = java_lang_Class_createForClass__Ljava_lang_String_2Ljava_lang_String_2Lcom_google_gwt_core_client_JavaScriptObject_2Ljava_lang_Class_2Ljava_lang_Class_2($intern_8, 'Widget', 4);
function com_google_gwt_user_client_ui_Composite_$checkInit__Lcom_google_gwt_user_client_ui_Composite_2V(this$static){
  if (!this$static.com_google_gwt_user_client_ui_Composite_widget) {
    throw com_google_gwt_lang_Exceptions_toJs__Ljava_lang_Object_2Ljava_lang_Object_2(new java_lang_IllegalStateException_IllegalStateException__Ljava_lang_String_2V('initWidget() is not called yet'));
  }
}

function com_google_gwt_user_client_ui_Composite_$initWidget__Lcom_google_gwt_user_client_ui_Composite_2Lcom_google_gwt_user_client_ui_Widget_2V(this$static, widget){
  var elem;
  if (this$static.com_google_gwt_user_client_ui_Composite_widget) {
    throw com_google_gwt_lang_Exceptions_toJs__Ljava_lang_Object_2Ljava_lang_Object_2(new java_lang_IllegalStateException_IllegalStateException__Ljava_lang_String_2V('Composite.initWidget() may only be called once.'));
  }
  if (!widget) {
    throw com_google_gwt_lang_Exceptions_toJs__Ljava_lang_Object_2Ljava_lang_Object_2(new java_lang_NullPointerException_NullPointerException__Ljava_lang_String_2V('widget cannot be null'));
  }
  com_google_gwt_user_client_ui_Widget_$removeFromParent__Lcom_google_gwt_user_client_ui_Widget_2V(widget);
  elem = widget.com_google_gwt_user_client_ui_UIObject_element;
  this$static.com_google_gwt_user_client_ui_UIObject_element = elem;
  (com_google_gwt_user_client_ui_PotentialElement_$clinit__V() , com_google_gwt_user_client_DOM_isPotential__Lcom_google_gwt_core_client_JavaScriptObject_2Z(elem)) && com_google_gwt_user_client_ui_PotentialElement_$setResolver__Lcom_google_gwt_user_client_ui_PotentialElement_2Lcom_google_gwt_user_client_ui_UIObject_2Lcom_google_gwt_dom_client_Element_2(elem, this$static);
  this$static.com_google_gwt_user_client_ui_Composite_widget = widget;
  com_google_gwt_user_client_ui_Widget_$setParent__Lcom_google_gwt_user_client_ui_Widget_2Lcom_google_gwt_user_client_ui_Widget_2V(widget, this$static);
}

function com_google_gwt_user_client_ui_Composite_$onAttach__Lcom_google_gwt_user_client_ui_Composite_2V(this$static){
  com_google_gwt_user_client_ui_Composite_$checkInit__Lcom_google_gwt_user_client_ui_Composite_2V(this$static);
  if (this$static.com_google_gwt_user_client_ui_Widget_eventsToSink != -1) {
    com_google_gwt_user_client_ui_Widget_$sinkEvents__Lcom_google_gwt_user_client_ui_Widget_2IV(this$static.com_google_gwt_user_client_ui_Composite_widget, this$static.com_google_gwt_user_client_ui_Widget_eventsToSink);
    this$static.com_google_gwt_user_client_ui_Widget_eventsToSink = -1;
  }
  this$static.com_google_gwt_user_client_ui_Composite_widget.onAttach__V();
  com_google_gwt_user_client_impl_DOMImpl_setEventListener__Lcom_google_gwt_dom_client_Element_2Lcom_google_gwt_user_client_EventListener_2V(this$static.com_google_gwt_user_client_ui_UIObject_element, this$static);
}

com_google_gwt_lang_Runtime_defineClass__Lcom_google_gwt_core_client_JavaScriptObject_2Lcom_google_gwt_core_client_JavaScriptObject_2Lcom_google_gwt_core_client_JavaScriptObject_2V(222, 4, $intern_9);
_.isAttached__Z = function com_google_gwt_user_client_ui_Composite_isAttached__Z(){
  if (this.com_google_gwt_user_client_ui_Composite_widget) {
    return this.com_google_gwt_user_client_ui_Composite_widget.com_google_gwt_user_client_ui_Widget_attached;
  }
  return false;
}
;
_.onAttach__V = function com_google_gwt_user_client_ui_Composite_onAttach__V(){
  com_google_gwt_user_client_ui_Composite_$onAttach__Lcom_google_gwt_user_client_ui_Composite_2V(this);
}
;
_.onBrowserEvent__Lcom_google_gwt_user_client_Event_2V = function com_google_gwt_user_client_ui_Composite_onBrowserEvent__Lcom_google_gwt_user_client_Event_2V(event_0){
  com_google_gwt_user_client_ui_Widget_$onBrowserEvent__Lcom_google_gwt_user_client_ui_Widget_2Lcom_google_gwt_user_client_Event_2V(this, event_0);
  this.com_google_gwt_user_client_ui_Composite_widget.onBrowserEvent__Lcom_google_gwt_user_client_Event_2V(event_0);
}
;
_.onDetach__V = function com_google_gwt_user_client_ui_Composite_onDetach__V(){
  com_google_gwt_user_client_ui_Widget_$onDetach__Lcom_google_gwt_user_client_ui_Widget_2V(this.com_google_gwt_user_client_ui_Composite_widget);
}
;
_.resolvePotentialElement__Lcom_google_gwt_dom_client_Element_2 = function com_google_gwt_user_client_ui_Composite_resolvePotentialElement__Lcom_google_gwt_dom_client_Element_2(){
  com_google_gwt_user_client_ui_UIObject_$setElement__Lcom_google_gwt_user_client_ui_UIObject_2Lcom_google_gwt_user_client_Element_2V(this, com_google_gwt_user_client_ui_UIObject_$resolvePotentialElement__Lcom_google_gwt_user_client_ui_UIObject_2Lcom_google_gwt_dom_client_Element_2());
  return this.com_google_gwt_user_client_ui_UIObject_element;
}
;
var com_google_gwt_lang_ClassLiteralHolder_Lcom_1google_1gwt_1user_1client_1ui_1Composite_12_1classLit = java_lang_Class_createForClass__Ljava_lang_String_2Ljava_lang_String_2Lcom_google_gwt_core_client_JavaScriptObject_2Ljava_lang_Class_2Ljava_lang_Class_2($intern_8, 'Composite', 222);
function com_flair_client_localization_LocalizedCompositeView_$getLocalizationData__Lcom_flair_client_localization_LocalizedCompositeView_2Lcom_flair_client_localization_LocalizationLanguage_2Lcom_flair_client_localization_LocalizationData_2(this$static, lang_0){
  if (java_util_AbstractHashMap_$containsKey__Ljava_util_AbstractHashMap_2Ljava_lang_Object_2Z(this$static.com_flair_client_localization_LocalizedCompositeView_localeData, lang_0))
    throw com_google_gwt_lang_Exceptions_toJs__Ljava_lang_Object_2Ljava_lang_Object_2(new java_lang_RuntimeException_RuntimeException__Ljava_lang_String_2V('Locale ' + lang_0 + ' missing for view ' + (java_lang_Class_$ensureNamesAreInitialized__Ljava_lang_Class_2V(com_google_gwt_lang_ClassLiteralHolder_Lcom_1flair_1client_1views_1MainViewport_12_1classLit) , com_google_gwt_lang_ClassLiteralHolder_Lcom_1flair_1client_1views_1MainViewport_12_1classLit.java_lang_Class_typeName)));
  else 
    return com_google_gwt_lang_Cast_castTo__Ljava_lang_Object_2Lcom_google_gwt_core_client_JavaScriptObject_2Ljava_lang_Object_2(java_util_AbstractHashMap_$get__Ljava_util_AbstractHashMap_2Ljava_lang_Object_2Ljava_lang_Object_2(this$static.com_flair_client_localization_LocalizedCompositeView_localeData, lang_0), 53);
}

function com_flair_client_localization_LocalizedCompositeView_$registerLocale__Lcom_flair_client_localization_LocalizedCompositeView_2Lcom_flair_client_localization_LocalizationData_2V(this$static, data_0){
  if (java_util_AbstractHashMap_$containsKey__Ljava_util_AbstractHashMap_2Ljava_lang_Object_2Z(this$static.com_flair_client_localization_LocalizedCompositeView_localeData, data_0.com_flair_client_localization_LocalizationData_lang))
    throw com_google_gwt_lang_Exceptions_toJs__Ljava_lang_Object_2Ljava_lang_Object_2(new java_lang_RuntimeException_RuntimeException__Ljava_lang_String_2V('Locale already registered'));
  else 
    java_util_AbstractHashMap_$put__Ljava_util_AbstractHashMap_2Ljava_lang_Object_2Ljava_lang_Object_2Ljava_lang_Object_2(this$static.com_flair_client_localization_LocalizedCompositeView_localeData, data_0.com_flair_client_localization_LocalizationData_lang, data_0);
}

function com_flair_client_localization_LocalizedCompositeView_$registerLocalizedWidget__Lcom_flair_client_localization_LocalizedCompositeView_2Lcom_flair_client_localization_LocalizedWidget_2V(this$static, widget){
  java_util_ArrayList_$add__Ljava_util_ArrayList_2Ljava_lang_Object_2Z(this$static.com_flair_client_localization_LocalizedCompositeView_localizedWidgets, widget);
}

function com_flair_client_localization_LocalizedCompositeView_$setLocalization__Lcom_flair_client_localization_LocalizedCompositeView_2Lcom_flair_client_localization_LocalizationLanguage_2V(this$static, lang_0){
  var itr, itr$iterator, ldata;
  ldata = com_flair_client_localization_LocalizedCompositeView_$getLocalizationData__Lcom_flair_client_localization_LocalizedCompositeView_2Lcom_flair_client_localization_LocalizationLanguage_2Lcom_flair_client_localization_LocalizationData_2(this$static, lang_0);
  for (itr$iterator = new java_util_ArrayList$1_ArrayList$1__Ljava_util_ArrayList_2V(this$static.com_flair_client_localization_LocalizedCompositeView_localizedWidgets); itr$iterator.java_util_ArrayList$1_i < itr$iterator.java_util_ArrayList$1_this$01.java_util_ArrayList_array.length;) {
    itr = com_google_gwt_lang_Cast_castTo__Ljava_lang_Object_2Lcom_google_gwt_core_client_JavaScriptObject_2Ljava_lang_Object_2(java_util_ArrayList$1_$next__Ljava_util_ArrayList$1_2Ljava_lang_Object_2(itr$iterator), 34);
    com_flair_client_localization_LocalizedWidget_$updateLocale__Lcom_flair_client_localization_LocalizedWidget_2Lcom_flair_client_localization_LocalizationData_2V(itr, ldata);
  }
}

com_google_gwt_lang_Runtime_defineClass__Lcom_google_gwt_core_client_JavaScriptObject_2Lcom_google_gwt_core_client_JavaScriptObject_2Lcom_google_gwt_core_client_JavaScriptObject_2V(118, 222, $intern_10);
_.onAttach__V = function com_flair_client_localization_LocalizedCompositeView_onAttach__V(){
  com_google_gwt_user_client_ui_Composite_$onAttach__Lcom_google_gwt_user_client_ui_Composite_2V(this);
  com_flair_client_localization_LocalizationEngine_$registerLocalizedView__Lcom_flair_client_localization_LocalizationEngine_2Lcom_flair_client_localization_LocalizedUI_2V((com_flair_client_localization_LocalizationEngine_$clinit__V() , com_flair_client_localization_LocalizationEngine_$clinit__V() , com_flair_client_localization_LocalizationEngine_INSTANCE), this);
}
;
_.onDetach__V = function com_flair_client_localization_LocalizedCompositeView_onDetach__V(){
  com_google_gwt_user_client_ui_Widget_$onDetach__Lcom_google_gwt_user_client_ui_Widget_2V(this.com_google_gwt_user_client_ui_Composite_widget);
  com_flair_client_localization_LocalizationEngine_$deregisterLocalizedView__Lcom_flair_client_localization_LocalizationEngine_2Lcom_flair_client_localization_LocalizedUI_2V((com_flair_client_localization_LocalizationEngine_$clinit__V() , com_flair_client_localization_LocalizationEngine_$clinit__V() , com_flair_client_localization_LocalizationEngine_INSTANCE), this);
}
;
var com_google_gwt_lang_ClassLiteralHolder_Lcom_1flair_1client_1localization_1LocalizedCompositeView_12_1classLit = java_lang_Class_createForClass__Ljava_lang_String_2Ljava_lang_String_2Lcom_google_gwt_core_client_JavaScriptObject_2Ljava_lang_Class_2Ljava_lang_Class_2($intern_3, 'LocalizedCompositeView', 118);
function com_flair_client_localization_LocalizedWidget_$updateLocale__Lcom_flair_client_localization_LocalizedWidget_2Lcom_flair_client_localization_LocalizationData_2V(this$static, data_0){
  if (!this$static.com_flair_client_localization_LocalizedWidget_invokable)
    throw com_google_gwt_lang_Exceptions_toJs__Ljava_lang_Object_2Ljava_lang_Object_2(new java_lang_RuntimeException_RuntimeException__Ljava_lang_String_2V('No update method specified'));
  else 
    this$static.com_flair_client_localization_LocalizedWidget_w.setText__Ljava_lang_String_2V(com_flair_client_localization_LocalizationData_$get__Lcom_flair_client_localization_LocalizationData_2Ljava_lang_String_2Ljava_lang_String_2(data_0, this$static.com_flair_client_localization_LocalizedWidget_desc));
}

function com_flair_client_localization_LocalizedWidget_LocalizedWidget__Ljava_lang_Object_2Ljava_lang_String_2Lcom_flair_client_localization_LocalizedWidget$Updateable_2V(w, desc, invokable){
  if (!com_google_gwt_lang_Cast_instanceOf__Ljava_lang_Object_2Lcom_google_gwt_core_client_JavaScriptObject_2Z(w, 4))
    throw com_google_gwt_lang_Exceptions_toJs__Ljava_lang_Object_2Ljava_lang_Object_2(new java_lang_RuntimeException_RuntimeException__Ljava_lang_String_2V('Invalid widget class'));
  this.com_flair_client_localization_LocalizedWidget_w = w;
  this.com_flair_client_localization_LocalizedWidget_desc = desc;
  this.com_flair_client_localization_LocalizedWidget_invokable = invokable;
}

com_google_gwt_lang_Runtime_defineClass__Lcom_google_gwt_core_client_JavaScriptObject_2Lcom_google_gwt_core_client_JavaScriptObject_2Lcom_google_gwt_core_client_JavaScriptObject_2V(34, 1, {34:1});
_.equals__Ljava_lang_Object_2Z = function com_flair_client_localization_LocalizedWidget_equals__Ljava_lang_Object_2Z(obj){
  var other;
  if (this === obj) {
    return true;
  }
  if (obj == null) {
    return false;
  }
  if (!com_google_gwt_lang_Cast_instanceOf__Ljava_lang_Object_2Lcom_google_gwt_core_client_JavaScriptObject_2Z(obj, 34)) {
    return false;
  }
  other = com_google_gwt_lang_Cast_castTo__Ljava_lang_Object_2Lcom_google_gwt_core_client_JavaScriptObject_2Ljava_lang_Object_2(obj, 34);
  if (this.com_flair_client_localization_LocalizedWidget_desc == null) {
    if (other.com_flair_client_localization_LocalizedWidget_desc != null) {
      return false;
    }
  }
   else if (!java_lang_String_$equals__Ljava_lang_String_2Ljava_lang_Object_2Z(this.com_flair_client_localization_LocalizedWidget_desc, other.com_flair_client_localization_LocalizedWidget_desc)) {
    return false;
  }
  if (!this.com_flair_client_localization_LocalizedWidget_w) {
    if (other.com_flair_client_localization_LocalizedWidget_w) {
      return false;
    }
  }
   else if (!java_lang_Object_equals_1Ljava_1lang_1Object_1_1Z_1_1devirtual$__Ljava_lang_Object_2Ljava_lang_Object_2Z(this.com_flair_client_localization_LocalizedWidget_w, other.com_flair_client_localization_LocalizedWidget_w)) {
    return false;
  }
  return true;
}
;
_.hashCode__I = function com_flair_client_localization_LocalizedWidget_hashCode__I(){
  var result;
  result = 31 + (this.com_flair_client_localization_LocalizedWidget_desc == null?0:javaemul_internal_StringHashCache_getHashCode__Ljava_lang_String_2I(this.com_flair_client_localization_LocalizedWidget_desc));
  result = 31 * result + (!this.com_flair_client_localization_LocalizedWidget_w?0:java_lang_Object_hashCode_1_1I_1_1devirtual$__Ljava_lang_Object_2I(this.com_flair_client_localization_LocalizedWidget_w));
  return result;
}
;
var com_google_gwt_lang_ClassLiteralHolder_Lcom_1flair_1client_1localization_1LocalizedWidget_12_1classLit = java_lang_Class_createForClass__Ljava_lang_String_2Ljava_lang_String_2Lcom_google_gwt_core_client_JavaScriptObject_2Ljava_lang_Class_2Ljava_lang_Class_2($intern_3, 'LocalizedWidget', 34);
function com_flair_client_localization_LocalizedTextWidget_LocalizedTextWidget__Lcom_google_gwt_user_client_ui_HasText_2Ljava_lang_String_2V(w, desc){
  com_flair_client_localization_LocalizedWidget_LocalizedWidget__Ljava_lang_Object_2Ljava_lang_String_2Lcom_flair_client_localization_LocalizedWidget$Updateable_2V.call(this, w, desc, new com_flair_client_localization_LocalizedTextWidget$lambda$0$Type_LocalizedTextWidget$lambda$0$Type__V);
}

com_google_gwt_lang_Runtime_defineClass__Lcom_google_gwt_core_client_JavaScriptObject_2Lcom_google_gwt_core_client_JavaScriptObject_2Lcom_google_gwt_core_client_JavaScriptObject_2V(35, 34, {34:1}, com_flair_client_localization_LocalizedTextWidget_LocalizedTextWidget__Lcom_google_gwt_user_client_ui_HasText_2Ljava_lang_String_2V);
var com_google_gwt_lang_ClassLiteralHolder_Lcom_1flair_1client_1localization_1LocalizedTextWidget_12_1classLit = java_lang_Class_createForClass__Ljava_lang_String_2Ljava_lang_String_2Lcom_google_gwt_core_client_JavaScriptObject_2Ljava_lang_Class_2Ljava_lang_Class_2($intern_3, 'LocalizedTextWidget', 35);
function com_flair_client_localization_LocalizedTextWidget$lambda$0$Type_LocalizedTextWidget$lambda$0$Type__V(){
}

com_google_gwt_lang_Runtime_defineClass__Lcom_google_gwt_core_client_JavaScriptObject_2Lcom_google_gwt_core_client_JavaScriptObject_2Lcom_google_gwt_core_client_JavaScriptObject_2V(139, 1, {}, com_flair_client_localization_LocalizedTextWidget$lambda$0$Type_LocalizedTextWidget$lambda$0$Type__V);
var com_google_gwt_lang_ClassLiteralHolder_Lcom_1flair_1client_1localization_1LocalizedTextWidget$lambda$0$Type_12_1classLit = java_lang_Class_createForClass__Ljava_lang_String_2Ljava_lang_String_2Lcom_google_gwt_core_client_JavaScriptObject_2Ljava_lang_Class_2Ljava_lang_Class_2($intern_3, 'LocalizedTextWidget/lambda$0$Type', 139);
function com_flair_client_views_MainViewport_$lambda$2__Lcom_flair_client_views_MainViewport_2Lcom_google_gwt_event_dom_client_ClickEvent_2V(this$static){
  switch ((com_flair_client_localization_LocalizationEngine_$clinit__V() , com_flair_client_localization_LocalizationEngine_$clinit__V() , com_flair_client_localization_LocalizationEngine_INSTANCE).com_flair_client_localization_LocalizationEngine_currentLang.java_lang_Enum_ordinal) {
    case 0:
      org_gwtbootstrap3_client_ui_Modal_$show__Lorg_gwtbootstrap3_client_ui_Modal_2V(this$static.com_flair_client_views_MainViewport_mdlAboutEnUI);
      break;
    case 1:
      org_gwtbootstrap3_client_ui_Modal_$show__Lorg_gwtbootstrap3_client_ui_Modal_2V(this$static.com_flair_client_views_MainViewport_mdlAboutDeUI);
  }
}

function com_flair_client_views_MainViewport_MainViewport__V(){
  this.com_flair_client_localization_LocalizedCompositeView_localeData = new java_util_HashMap_HashMap__V;
  this.com_flair_client_localization_LocalizedCompositeView_localizedWidgets = new java_util_ArrayList_ArrayList__V;
  this.com_flair_client_views_MainViewport_btnWebSearchLC = new com_flair_client_localization_LocalizedTextWidget_LocalizedTextWidget__Lcom_google_gwt_user_client_ui_HasText_2Ljava_lang_String_2V(this.com_flair_client_views_MainViewport_btnWebSearchUI, $intern_11);
  this.com_flair_client_views_MainViewport_btnUploadLC = new com_flair_client_localization_LocalizedTextWidget_LocalizedTextWidget__Lcom_google_gwt_user_client_ui_HasText_2Ljava_lang_String_2V(this.com_flair_client_views_MainViewport_btnUploadUI, $intern_12);
  this.com_flair_client_views_MainViewport_btnAboutLC = new com_flair_client_localization_LocalizedTextWidget_LocalizedTextWidget__Lcom_google_gwt_user_client_ui_HasText_2Ljava_lang_String_2V(this.com_flair_client_views_MainViewport_btnAboutUI, $intern_13);
  this.com_flair_client_views_MainViewport_btnSwitchLangLC = new com_flair_client_localization_LocalizedTextWidget_LocalizedTextWidget__Lcom_google_gwt_user_client_ui_HasText_2Ljava_lang_String_2V(this.com_flair_client_views_MainViewport_btnSwitchLangUI, $intern_14);
  this.com_flair_client_views_MainViewport_btnLangEnLC = new com_flair_client_localization_LocalizedTextWidget_LocalizedTextWidget__Lcom_google_gwt_user_client_ui_HasText_2Ljava_lang_String_2V(this.com_flair_client_views_MainViewport_btnLangEnUI, $intern_15);
  this.com_flair_client_views_MainViewport_btnLangDeLC = new com_flair_client_localization_LocalizedTextWidget_LocalizedTextWidget__Lcom_google_gwt_user_client_ui_HasText_2Ljava_lang_String_2V(this.com_flair_client_views_MainViewport_btnLangDeUI, $intern_16);
  com_google_gwt_user_client_ui_Composite_$initWidget__Lcom_google_gwt_user_client_ui_Composite_2Lcom_google_gwt_user_client_ui_Widget_2V(this, com_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_$build_1f_1HTMLPanel1__Lcom_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_2Lcom_google_gwt_user_client_ui_HTMLPanel_2(new com_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_MainViewport_1MainViewportUiBinderImpl$Widgets__Lcom_flair_client_views_MainViewport_1MainViewportUiBinderImpl_2Lcom_flair_client_views_MainViewport_2V(this)));
  com_flair_client_localization_LocalizedCompositeView_$registerLocale__Lcom_flair_client_localization_LocalizedCompositeView_2Lcom_flair_client_localization_LocalizationData_2V(this, (com_flair_client_views_MainViewport$MainViewPortLocale_$clinit__V() , com_flair_client_views_MainViewport$MainViewPortLocale_INSTANCE).com_flair_client_views_SimpleViewLocale_en);
  com_flair_client_localization_LocalizedCompositeView_$registerLocale__Lcom_flair_client_localization_LocalizedCompositeView_2Lcom_flair_client_localization_LocalizationData_2V(this, com_flair_client_views_MainViewport$MainViewPortLocale_INSTANCE.com_flair_client_views_SimpleViewLocale_de);
  com_flair_client_localization_LocalizedCompositeView_$registerLocalizedWidget__Lcom_flair_client_localization_LocalizedCompositeView_2Lcom_flair_client_localization_LocalizedWidget_2V(this, this.com_flair_client_views_MainViewport_btnWebSearchLC);
  com_flair_client_localization_LocalizedCompositeView_$registerLocalizedWidget__Lcom_flair_client_localization_LocalizedCompositeView_2Lcom_flair_client_localization_LocalizedWidget_2V(this, this.com_flair_client_views_MainViewport_btnUploadLC);
  com_flair_client_localization_LocalizedCompositeView_$registerLocalizedWidget__Lcom_flair_client_localization_LocalizedCompositeView_2Lcom_flair_client_localization_LocalizedWidget_2V(this, this.com_flair_client_views_MainViewport_btnAboutLC);
  com_flair_client_localization_LocalizedCompositeView_$registerLocalizedWidget__Lcom_flair_client_localization_LocalizedCompositeView_2Lcom_flair_client_localization_LocalizedWidget_2V(this, this.com_flair_client_views_MainViewport_btnSwitchLangLC);
  com_flair_client_localization_LocalizedCompositeView_$registerLocalizedWidget__Lcom_flair_client_localization_LocalizedCompositeView_2Lcom_flair_client_localization_LocalizedWidget_2V(this, this.com_flair_client_views_MainViewport_btnLangEnLC);
  com_flair_client_localization_LocalizedCompositeView_$registerLocalizedWidget__Lcom_flair_client_localization_LocalizedCompositeView_2Lcom_flair_client_localization_LocalizedWidget_2V(this, this.com_flair_client_views_MainViewport_btnLangDeLC);
  org_gwtbootstrap3_client_ui_base_AbstractAnchorListItem_$addClickHandler__Lorg_gwtbootstrap3_client_ui_base_AbstractAnchorListItem_2Lcom_google_gwt_event_dom_client_ClickHandler_2Lcom_google_gwt_event_shared_HandlerRegistration_2(this.com_flair_client_views_MainViewport_btnLangEnUI, new com_flair_client_views_MainViewport$lambda$0$Type_MainViewport$lambda$0$Type__V);
  org_gwtbootstrap3_client_ui_base_AbstractAnchorListItem_$addClickHandler__Lorg_gwtbootstrap3_client_ui_base_AbstractAnchorListItem_2Lcom_google_gwt_event_dom_client_ClickHandler_2Lcom_google_gwt_event_shared_HandlerRegistration_2(this.com_flair_client_views_MainViewport_btnLangDeUI, new com_flair_client_views_MainViewport$lambda$1$Type_MainViewport$lambda$1$Type__V);
  org_gwtbootstrap3_client_ui_base_AbstractAnchorListItem_$addClickHandler__Lorg_gwtbootstrap3_client_ui_base_AbstractAnchorListItem_2Lcom_google_gwt_event_dom_client_ClickHandler_2Lcom_google_gwt_event_shared_HandlerRegistration_2(this.com_flair_client_views_MainViewport_btnAboutUI, new com_flair_client_views_MainViewport$lambda$2$Type_MainViewport$lambda$2$Type__Lcom_flair_client_views_MainViewport_2V(this));
}

com_google_gwt_lang_Runtime_defineClass__Lcom_google_gwt_core_client_JavaScriptObject_2Lcom_google_gwt_core_client_JavaScriptObject_2Lcom_google_gwt_core_client_JavaScriptObject_2V(119, 118, $intern_10, com_flair_client_views_MainViewport_MainViewport__V);
var com_google_gwt_lang_ClassLiteralHolder_Lcom_1flair_1client_1views_1MainViewport_12_1classLit = java_lang_Class_createForClass__Ljava_lang_String_2Ljava_lang_String_2Lcom_google_gwt_core_client_JavaScriptObject_2Ljava_lang_Class_2Ljava_lang_Class_2($intern_17, 'MainViewport', 119);
com_google_gwt_lang_Runtime_defineClass__Lcom_google_gwt_core_client_JavaScriptObject_2Lcom_google_gwt_core_client_JavaScriptObject_2Lcom_google_gwt_core_client_JavaScriptObject_2V(120, 1, {});
var com_google_gwt_lang_ClassLiteralHolder_Lcom_1flair_1client_1views_1SimpleViewLocale_12_1classLit = java_lang_Class_createForClass__Ljava_lang_String_2Ljava_lang_String_2Lcom_google_gwt_core_client_JavaScriptObject_2Ljava_lang_Class_2Ljava_lang_Class_2($intern_17, 'SimpleViewLocale', 120);
function com_flair_client_views_MainViewport$MainViewPortLocale_$clinit__V(){
  com_flair_client_views_MainViewport$MainViewPortLocale_$clinit__V = com_google_gwt_lang_Runtime_emptyMethod__V;
  com_flair_client_views_MainViewport$MainViewPortLocale_INSTANCE = new com_flair_client_views_MainViewport$MainViewPortLocale_MainViewport$MainViewPortLocale__V;
}

function com_flair_client_views_MainViewport$MainViewPortLocale_MainViewport$MainViewPortLocale__V(){
  this.com_flair_client_views_SimpleViewLocale_en = new com_flair_client_localization_LocalizationData_LocalizationData__Lcom_flair_client_localization_LocalizationLanguage_2V((com_flair_client_localization_LocalizationLanguage_$clinit__V() , com_flair_client_localization_LocalizationLanguage_ENGLISH));
  this.com_flair_client_views_SimpleViewLocale_de = new com_flair_client_localization_LocalizationData_LocalizationData__Lcom_flair_client_localization_LocalizationLanguage_2V(com_flair_client_localization_LocalizationLanguage_GERMAN);
  com_flair_client_localization_LocalizationData_$put__Lcom_flair_client_localization_LocalizationData_2Ljava_lang_String_2Ljava_lang_String_2V(this.com_flair_client_views_SimpleViewLocale_en, $intern_11, $intern_18);
  com_flair_client_localization_LocalizationData_$put__Lcom_flair_client_localization_LocalizationData_2Ljava_lang_String_2Ljava_lang_String_2V(this.com_flair_client_views_SimpleViewLocale_en, $intern_12, 'Upload Corpus');
  com_flair_client_localization_LocalizationData_$put__Lcom_flair_client_localization_LocalizationData_2Ljava_lang_String_2Ljava_lang_String_2V(this.com_flair_client_views_SimpleViewLocale_en, $intern_13, $intern_19);
  com_flair_client_localization_LocalizationData_$put__Lcom_flair_client_localization_LocalizationData_2Ljava_lang_String_2Ljava_lang_String_2V(this.com_flair_client_views_SimpleViewLocale_en, $intern_14, 'Language');
  com_flair_client_localization_LocalizationData_$put__Lcom_flair_client_localization_LocalizationData_2Ljava_lang_String_2Ljava_lang_String_2V(this.com_flair_client_views_SimpleViewLocale_en, $intern_15, 'English');
  com_flair_client_localization_LocalizationData_$put__Lcom_flair_client_localization_LocalizationData_2Ljava_lang_String_2Ljava_lang_String_2V(this.com_flair_client_views_SimpleViewLocale_en, $intern_16, 'German');
  com_flair_client_localization_LocalizationData_$put__Lcom_flair_client_localization_LocalizationData_2Ljava_lang_String_2Ljava_lang_String_2V(this.com_flair_client_views_SimpleViewLocale_de, $intern_11, 'Internet Suche');
  com_flair_client_localization_LocalizationData_$put__Lcom_flair_client_localization_LocalizationData_2Ljava_lang_String_2Ljava_lang_String_2V(this.com_flair_client_views_SimpleViewLocale_de, $intern_12, 'Text Hochladen');
  com_flair_client_localization_LocalizationData_$put__Lcom_flair_client_localization_LocalizationData_2Ljava_lang_String_2Ljava_lang_String_2V(this.com_flair_client_views_SimpleViewLocale_de, $intern_13, $intern_20);
  com_flair_client_localization_LocalizationData_$put__Lcom_flair_client_localization_LocalizationData_2Ljava_lang_String_2Ljava_lang_String_2V(this.com_flair_client_views_SimpleViewLocale_de, $intern_14, 'Sprache');
  com_flair_client_localization_LocalizationData_$put__Lcom_flair_client_localization_LocalizationData_2Ljava_lang_String_2Ljava_lang_String_2V(this.com_flair_client_views_SimpleViewLocale_de, $intern_15, 'Englisch');
  com_flair_client_localization_LocalizationData_$put__Lcom_flair_client_localization_LocalizationData_2Ljava_lang_String_2Ljava_lang_String_2V(this.com_flair_client_views_SimpleViewLocale_de, $intern_16, 'Deutsch');
}

com_google_gwt_lang_Runtime_defineClass__Lcom_google_gwt_core_client_JavaScriptObject_2Lcom_google_gwt_core_client_JavaScriptObject_2Lcom_google_gwt_core_client_JavaScriptObject_2V(121, 120, {}, com_flair_client_views_MainViewport$MainViewPortLocale_MainViewport$MainViewPortLocale__V);
var com_flair_client_views_MainViewport$MainViewPortLocale_INSTANCE;
var com_google_gwt_lang_ClassLiteralHolder_Lcom_1flair_1client_1views_1MainViewport$MainViewPortLocale_12_1classLit = java_lang_Class_createForClass__Ljava_lang_String_2Ljava_lang_String_2Lcom_google_gwt_core_client_JavaScriptObject_2Ljava_lang_Class_2Ljava_lang_Class_2($intern_17, 'MainViewport/MainViewPortLocale', 121);
function com_flair_client_views_MainViewport$lambda$0$Type_MainViewport$lambda$0$Type__V(){
}

com_google_gwt_lang_Runtime_defineClass__Lcom_google_gwt_core_client_JavaScriptObject_2Lcom_google_gwt_core_client_JavaScriptObject_2Lcom_google_gwt_core_client_JavaScriptObject_2V(122, 1, $intern_21, com_flair_client_views_MainViewport$lambda$0$Type_MainViewport$lambda$0$Type__V);
_.onClick__Lcom_google_gwt_event_dom_client_ClickEvent_2V = function com_flair_client_views_MainViewport$lambda$0$Type_onClick__Lcom_google_gwt_event_dom_client_ClickEvent_2V(arg0){
  com_flair_client_localization_LocalizationEngine_$setLanguage__Lcom_flair_client_localization_LocalizationEngine_2Lcom_flair_client_localization_LocalizationLanguage_2V((com_flair_client_localization_LocalizationEngine_$clinit__V() , com_flair_client_localization_LocalizationEngine_$clinit__V() , com_flair_client_localization_LocalizationEngine_INSTANCE), (com_flair_client_localization_LocalizationLanguage_$clinit__V() , com_flair_client_localization_LocalizationLanguage_ENGLISH));
}
;
var com_google_gwt_lang_ClassLiteralHolder_Lcom_1flair_1client_1views_1MainViewport$lambda$0$Type_12_1classLit = java_lang_Class_createForClass__Ljava_lang_String_2Ljava_lang_String_2Lcom_google_gwt_core_client_JavaScriptObject_2Ljava_lang_Class_2Ljava_lang_Class_2($intern_17, 'MainViewport/lambda$0$Type', 122);
function com_flair_client_views_MainViewport$lambda$1$Type_MainViewport$lambda$1$Type__V(){
}

com_google_gwt_lang_Runtime_defineClass__Lcom_google_gwt_core_client_JavaScriptObject_2Lcom_google_gwt_core_client_JavaScriptObject_2Lcom_google_gwt_core_client_JavaScriptObject_2V(123, 1, $intern_21, com_flair_client_views_MainViewport$lambda$1$Type_MainViewport$lambda$1$Type__V);
_.onClick__Lcom_google_gwt_event_dom_client_ClickEvent_2V = function com_flair_client_views_MainViewport$lambda$1$Type_onClick__Lcom_google_gwt_event_dom_client_ClickEvent_2V(arg0){
  com_flair_client_localization_LocalizationEngine_$setLanguage__Lcom_flair_client_localization_LocalizationEngine_2Lcom_flair_client_localization_LocalizationLanguage_2V((com_flair_client_localization_LocalizationEngine_$clinit__V() , com_flair_client_localization_LocalizationEngine_$clinit__V() , com_flair_client_localization_LocalizationEngine_INSTANCE), (com_flair_client_localization_LocalizationLanguage_$clinit__V() , com_flair_client_localization_LocalizationLanguage_GERMAN));
}
;
var com_google_gwt_lang_ClassLiteralHolder_Lcom_1flair_1client_1views_1MainViewport$lambda$1$Type_12_1classLit = java_lang_Class_createForClass__Ljava_lang_String_2Ljava_lang_String_2Lcom_google_gwt_core_client_JavaScriptObject_2Ljava_lang_Class_2Ljava_lang_Class_2($intern_17, 'MainViewport/lambda$1$Type', 123);
function com_flair_client_views_MainViewport$lambda$2$Type_MainViewport$lambda$2$Type__Lcom_flair_client_views_MainViewport_2V($$outer_0){
  this.com_flair_client_views_MainViewport$lambda$2$Type_$$outer_10 = $$outer_0;
}

com_google_gwt_lang_Runtime_defineClass__Lcom_google_gwt_core_client_JavaScriptObject_2Lcom_google_gwt_core_client_JavaScriptObject_2Lcom_google_gwt_core_client_JavaScriptObject_2V(124, 1, $intern_21, com_flair_client_views_MainViewport$lambda$2$Type_MainViewport$lambda$2$Type__Lcom_flair_client_views_MainViewport_2V);
_.onClick__Lcom_google_gwt_event_dom_client_ClickEvent_2V = function com_flair_client_views_MainViewport$lambda$2$Type_onClick__Lcom_google_gwt_event_dom_client_ClickEvent_2V(arg0){
  com_flair_client_views_MainViewport_$lambda$2__Lcom_flair_client_views_MainViewport_2Lcom_google_gwt_event_dom_client_ClickEvent_2V(this.com_flair_client_views_MainViewport$lambda$2$Type_$$outer_10);
}
;
var com_google_gwt_lang_ClassLiteralHolder_Lcom_1flair_1client_1views_1MainViewport$lambda$2$Type_12_1classLit = java_lang_Class_createForClass__Ljava_lang_String_2Ljava_lang_String_2Lcom_google_gwt_core_client_JavaScriptObject_2Ljava_lang_Class_2Ljava_lang_Class_2($intern_17, 'MainViewport/lambda$2$Type', 124);
function com_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_$build_1f_1HTMLPanel1__Lcom_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_2Lcom_google_gwt_user_client_ui_HTMLPanel_2(this$static){
  var __attachRecord__, f_HTMLPanel1, pnlMainUI, com_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_$build_1f_1Navbar2__Lcom_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_2Lorg_gwtbootstrap3_client_ui_Navbar_2_f_Navbar2_0, com_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_$build_1mdlAboutEnUI__Lcom_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_2Lorg_gwtbootstrap3_client_ui_Modal_2_mdlAboutEnUI_0, com_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_$build_1mdlAboutDeUI__Lcom_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_2Lorg_gwtbootstrap3_client_ui_Modal_2_mdlAboutDeUI_0, com_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_$build_1f_1NavbarHeader3__Lcom_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_2Lorg_gwtbootstrap3_client_ui_NavbarHeader_2_f_NavbarHeader3_1, com_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_$build_1f_1NavbarHeader3__Lcom_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_2Lorg_gwtbootstrap3_client_ui_NavbarHeader_2_com_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_$build_1f_1NavbarBrand4__Lcom_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_2Lorg_gwtbootstrap3_client_ui_NavbarBrand_2_f_NavbarBrand4_0_0, com_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_$build_1f_1NavbarHeader3__Lcom_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_2Lorg_gwtbootstrap3_client_ui_NavbarHeader_2_com_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_$build_1f_1NavbarBrand4__Lcom_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_2Lorg_gwtbootstrap3_client_ui_NavbarBrand_2_com_flair_client_views_MainViewport_1MainViewportUiBinderImpl_1TemplateImpl_$html1__Lcom_flair_client_views_MainViewport_1MainViewportUiBinderImpl_1TemplateImpl_2Lcom_google_gwt_safehtml_shared_SafeHtml_2_sb_0_0_0, com_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_$build_1f_1NavbarHeader3__Lcom_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_2Lorg_gwtbootstrap3_client_ui_NavbarHeader_2_com_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_$build_1f_1NavbarCollapseButton5__Lcom_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_2Lorg_gwtbootstrap3_client_ui_NavbarCollapseButton_2_f_NavbarCollapseButton5_0_0, com_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_$build_1f_1NavbarCollapse6__Lcom_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_2Lorg_gwtbootstrap3_client_ui_NavbarCollapse_2_f_NavbarCollapse6_1, com_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_$build_1f_1NavbarCollapse6__Lcom_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_2Lorg_gwtbootstrap3_client_ui_NavbarCollapse_2_com_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_$build_1f_1NavbarNav7__Lcom_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_2Lorg_gwtbootstrap3_client_ui_NavbarNav_2_f_NavbarNav7_0_0, com_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_$build_1f_1NavbarCollapse6__Lcom_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_2Lorg_gwtbootstrap3_client_ui_NavbarCollapse_2_com_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_$build_1f_1NavbarNav8__Lcom_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_2Lorg_gwtbootstrap3_client_ui_NavbarNav_2_f_NavbarNav8_0_0, com_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_$build_1f_1ModalHeader11__Lcom_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_2Lorg_gwtbootstrap3_client_ui_ModalHeader_2_f_ModalHeader11_1, com_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_$build_1f_1ModalHeader11__Lcom_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_2Lorg_gwtbootstrap3_client_ui_ModalHeader_2_com_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_$build_1f_1HTML12__Lcom_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_2Lcom_google_gwt_user_client_ui_HTML_2_f_HTML12_0_0, com_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_$build_1f_1ModalBody13__Lcom_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_2Lorg_gwtbootstrap3_client_ui_ModalBody_2_f_ModalBody13_1, com_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_$build_1f_1ModalBody13__Lcom_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_2Lorg_gwtbootstrap3_client_ui_ModalBody_2_com_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_$build_1f_1HTML14__Lcom_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_2Lcom_google_gwt_user_client_ui_HTML_2_f_HTML14_0_0, com_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_$build_1f_1ModalBody13__Lcom_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_2Lorg_gwtbootstrap3_client_ui_ModalBody_2_com_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_$build_1f_1HTML14__Lcom_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_2Lcom_google_gwt_user_client_ui_HTML_2_com_flair_client_views_MainViewport_1MainViewportUiBinderImpl_1TemplateImpl_$html3__Lcom_flair_client_views_MainViewport_1MainViewportUiBinderImpl_1TemplateImpl_2Lcom_google_gwt_safehtml_shared_SafeHtml_2_sb_0_0_0, com_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_$build_1f_1ModalFooter15__Lcom_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_2Lorg_gwtbootstrap3_client_ui_ModalFooter_2_f_ModalFooter15_1, com_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_$build_1f_1ModalFooter15__Lcom_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_2Lorg_gwtbootstrap3_client_ui_ModalFooter_2_com_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_$build_1f_1Button16__Lcom_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_2Lorg_gwtbootstrap3_client_ui_Button_2_f_Button16_0_0, com_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_$build_1f_1ModalHeader17__Lcom_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_2Lorg_gwtbootstrap3_client_ui_ModalHeader_2_f_ModalHeader17_1, com_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_$build_1f_1ModalHeader17__Lcom_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_2Lorg_gwtbootstrap3_client_ui_ModalHeader_2_com_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_$build_1f_1HTML18__Lcom_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_2Lcom_google_gwt_user_client_ui_HTML_2_f_HTML18_0_0, com_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_$build_1f_1ModalHeader17__Lcom_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_2Lorg_gwtbootstrap3_client_ui_ModalHeader_2_com_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_$build_1f_1HTML18__Lcom_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_2Lcom_google_gwt_user_client_ui_HTML_2_com_flair_client_views_MainViewport_1MainViewportUiBinderImpl_1TemplateImpl_$html4__Lcom_flair_client_views_MainViewport_1MainViewportUiBinderImpl_1TemplateImpl_2Lcom_google_gwt_safehtml_shared_SafeHtml_2_sb_0_0_0, com_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_$build_1f_1ModalBody19__Lcom_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_2Lorg_gwtbootstrap3_client_ui_ModalBody_2_f_ModalBody19_1, com_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_$build_1f_1ModalBody19__Lcom_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_2Lorg_gwtbootstrap3_client_ui_ModalBody_2_com_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_$build_1f_1HTML20__Lcom_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_2Lcom_google_gwt_user_client_ui_HTML_2_f_HTML20_0_0, com_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_$build_1f_1ModalBody19__Lcom_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_2Lorg_gwtbootstrap3_client_ui_ModalBody_2_com_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_$build_1f_1HTML20__Lcom_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_2Lcom_google_gwt_user_client_ui_HTML_2_com_flair_client_views_MainViewport_1MainViewportUiBinderImpl_1TemplateImpl_$html5__Lcom_flair_client_views_MainViewport_1MainViewportUiBinderImpl_1TemplateImpl_2Lcom_google_gwt_safehtml_shared_SafeHtml_2_sb_0_0_0, com_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_$build_1f_1ModalFooter21__Lcom_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_2Lorg_gwtbootstrap3_client_ui_ModalFooter_2_f_ModalFooter21_1, com_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_$build_1f_1ModalFooter21__Lcom_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_2Lorg_gwtbootstrap3_client_ui_ModalFooter_2_com_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_$build_1f_1Button22__Lcom_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_2Lorg_gwtbootstrap3_client_ui_Button_2_f_Button22_0_0, com_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_$build_1btnWebSearchUI__Lcom_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_2Lorg_gwtbootstrap3_client_ui_AnchorListItem_2_btnWebSearchUI_1, com_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_$build_1btnUploadUI__Lcom_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_2Lorg_gwtbootstrap3_client_ui_AnchorListItem_2_btnUploadUI_1, com_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_$build_1btnAboutUI__Lcom_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_2Lorg_gwtbootstrap3_client_ui_AnchorListItem_2_btnAboutUI_1, com_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_$build_1f_1ListDropDown9__Lcom_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_2Lorg_gwtbootstrap3_client_ui_ListDropDown_2_f_ListDropDown9_1, com_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_$build_1f_1ListDropDown9__Lcom_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_2Lorg_gwtbootstrap3_client_ui_ListDropDown_2_com_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_$build_1btnSwitchLangUI__Lcom_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_2Lorg_gwtbootstrap3_client_ui_AnchorButton_2_btnSwitchLangUI_0_1, com_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_$build_1f_1ListDropDown9__Lcom_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_2Lorg_gwtbootstrap3_client_ui_ListDropDown_2_com_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_$build_1f_1DropDownMenu10__Lcom_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_2Lorg_gwtbootstrap3_client_ui_DropDownMenu_2_f_DropDownMenu10_0_1, com_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_$build_1f_1ListDropDown9__Lcom_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_2Lorg_gwtbootstrap3_client_ui_ListDropDown_2_com_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_$build_1f_1DropDownMenu10__Lcom_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_2Lorg_gwtbootstrap3_client_ui_DropDownMenu_2_com_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_$build_1btnLangEnUI__Lcom_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_2Lorg_gwtbootstrap3_client_ui_AnchorListItem_2_btnLangEnUI_0_0_1, com_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_$build_1f_1ListDropDown9__Lcom_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_2Lorg_gwtbootstrap3_client_ui_ListDropDown_2_com_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_$build_1f_1DropDownMenu10__Lcom_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_2Lorg_gwtbootstrap3_client_ui_DropDownMenu_2_com_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_$build_1btnLangDeUI__Lcom_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_2Lorg_gwtbootstrap3_client_ui_AnchorListItem_2_btnLangDeUI_0_0_1;
  f_HTMLPanel1 = new com_google_gwt_user_client_ui_HTMLPanel_HTMLPanel__Ljava_lang_String_2V(com_flair_client_views_MainViewport_1MainViewportUiBinderImpl_1TemplateImpl_$html6__Lcom_flair_client_views_MainViewport_1MainViewportUiBinderImpl_1TemplateImpl_2Ljava_lang_String_2Ljava_lang_String_2Ljava_lang_String_2Ljava_lang_String_2Lcom_google_gwt_safehtml_shared_SafeHtml_2(this$static.com_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_domId0, this$static.com_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_domId1, this$static.com_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_domId2, this$static.com_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_domId3).com_google_gwt_safehtml_shared_OnlyToBeUsedInGeneratedCodeStringBlessedAsSafeHtml_html);
  __attachRecord__ = com_google_gwt_uibinder_client_UiBinderUtil_attachToDom__Lcom_google_gwt_dom_client_Element_2Lcom_google_gwt_uibinder_client_UiBinderUtil$TempAttachment_2(f_HTMLPanel1.com_google_gwt_user_client_ui_UIObject_element);
  com_google_gwt_uibinder_client_LazyDomElement_$get__Lcom_google_gwt_uibinder_client_LazyDomElement_2Lcom_google_gwt_dom_client_Element_2(this$static.com_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_domId0Element);
  com_google_gwt_uibinder_client_LazyDomElement_$get__Lcom_google_gwt_uibinder_client_LazyDomElement_2Lcom_google_gwt_dom_client_Element_2(this$static.com_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_domId1Element);
  com_google_gwt_uibinder_client_LazyDomElement_$get__Lcom_google_gwt_uibinder_client_LazyDomElement_2Lcom_google_gwt_dom_client_Element_2(this$static.com_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_domId2Element);
  com_google_gwt_uibinder_client_LazyDomElement_$get__Lcom_google_gwt_uibinder_client_LazyDomElement_2Lcom_google_gwt_dom_client_Element_2(this$static.com_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_domId3Element);
  __attachRecord__.com_google_gwt_uibinder_client_UiBinderUtil$TempAttachment_origParent?com_google_gwt_dom_client_Node_$insertBefore__Lcom_google_gwt_dom_client_Node_2Lcom_google_gwt_dom_client_Node_2Lcom_google_gwt_dom_client_Node_2Lcom_google_gwt_dom_client_Node_2(__attachRecord__.com_google_gwt_uibinder_client_UiBinderUtil$TempAttachment_origParent, __attachRecord__.com_google_gwt_uibinder_client_UiBinderUtil$TempAttachment_element, __attachRecord__.com_google_gwt_uibinder_client_UiBinderUtil$TempAttachment_origSibling):com_google_gwt_uibinder_client_UiBinderUtil_orphan__Lcom_google_gwt_dom_client_Node_2V(__attachRecord__.com_google_gwt_uibinder_client_UiBinderUtil$TempAttachment_element);
  com_google_gwt_user_client_ui_HTMLPanel_$addAndReplaceElement__Lcom_google_gwt_user_client_ui_HTMLPanel_2Lcom_google_gwt_user_client_ui_Widget_2Lcom_google_gwt_user_client_Element_2V(f_HTMLPanel1, (com_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_$build_1f_1Navbar2__Lcom_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_2Lorg_gwtbootstrap3_client_ui_Navbar_2_f_Navbar2_0 = new org_gwtbootstrap3_client_ui_Navbar_Navbar__V , org_gwtbootstrap3_client_ui_base_ComplexWidget_$add__Lorg_gwtbootstrap3_client_ui_base_ComplexWidget_2Lcom_google_gwt_user_client_ui_Widget_2V(com_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_$build_1f_1Navbar2__Lcom_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_2Lorg_gwtbootstrap3_client_ui_Navbar_2_f_Navbar2_0, (com_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_$build_1f_1NavbarHeader3__Lcom_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_2Lorg_gwtbootstrap3_client_ui_NavbarHeader_2_f_NavbarHeader3_1 = new org_gwtbootstrap3_client_ui_NavbarHeader_NavbarHeader__V , com_google_gwt_user_client_ui_FlowPanel_$add__Lcom_google_gwt_user_client_ui_FlowPanel_2Lcom_google_gwt_user_client_ui_Widget_2V(com_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_$build_1f_1NavbarHeader3__Lcom_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_2Lorg_gwtbootstrap3_client_ui_NavbarHeader_2_f_NavbarHeader3_1, (com_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_$build_1f_1NavbarHeader3__Lcom_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_2Lorg_gwtbootstrap3_client_ui_NavbarHeader_2_com_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_$build_1f_1NavbarBrand4__Lcom_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_2Lorg_gwtbootstrap3_client_ui_NavbarBrand_2_f_NavbarBrand4_0_0 = new org_gwtbootstrap3_client_ui_NavbarBrand_NavbarBrand__V , org_gwtbootstrap3_client_ui_Anchor_$setHTML__Lorg_gwtbootstrap3_client_ui_Anchor_2Ljava_lang_String_2V(com_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_$build_1f_1NavbarHeader3__Lcom_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_2Lorg_gwtbootstrap3_client_ui_NavbarHeader_2_com_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_$build_1f_1NavbarBrand4__Lcom_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_2Lorg_gwtbootstrap3_client_ui_NavbarBrand_2_f_NavbarBrand4_0_0, (com_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_$build_1f_1NavbarHeader3__Lcom_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_2Lorg_gwtbootstrap3_client_ui_NavbarHeader_2_com_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_$build_1f_1NavbarBrand4__Lcom_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_2Lorg_gwtbootstrap3_client_ui_NavbarBrand_2_com_flair_client_views_MainViewport_1MainViewportUiBinderImpl_1TemplateImpl_$html1__Lcom_flair_client_views_MainViewport_1MainViewportUiBinderImpl_1TemplateImpl_2Lcom_google_gwt_safehtml_shared_SafeHtml_2_sb_0_0_0 = new java_lang_StringBuilder_StringBuilder__V , com_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_$build_1f_1NavbarHeader3__Lcom_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_2Lorg_gwtbootstrap3_client_ui_NavbarHeader_2_com_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_$build_1f_1NavbarBrand4__Lcom_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_2Lorg_gwtbootstrap3_client_ui_NavbarBrand_2_com_flair_client_views_MainViewport_1MainViewportUiBinderImpl_1TemplateImpl_$html1__Lcom_flair_client_views_MainViewport_1MainViewportUiBinderImpl_1TemplateImpl_2Lcom_google_gwt_safehtml_shared_SafeHtml_2_sb_0_0_0.java_lang_AbstractStringBuilder_string += "<img alt='FLAIR' class='logo' height='55px' src='img/logo_2.png'>" , new com_google_gwt_safehtml_shared_OnlyToBeUsedInGeneratedCodeStringBlessedAsSafeHtml_OnlyToBeUsedInGeneratedCodeStringBlessedAsSafeHtml__Ljava_lang_String_2V(com_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_$build_1f_1NavbarHeader3__Lcom_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_2Lorg_gwtbootstrap3_client_ui_NavbarHeader_2_com_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_$build_1f_1NavbarBrand4__Lcom_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_2Lorg_gwtbootstrap3_client_ui_NavbarBrand_2_com_flair_client_views_MainViewport_1MainViewportUiBinderImpl_1TemplateImpl_$html1__Lcom_flair_client_views_MainViewport_1MainViewportUiBinderImpl_1TemplateImpl_2Lcom_google_gwt_safehtml_shared_SafeHtml_2_sb_0_0_0.java_lang_AbstractStringBuilder_string)).com_google_gwt_safehtml_shared_OnlyToBeUsedInGeneratedCodeStringBlessedAsSafeHtml_html) , com_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_$build_1f_1NavbarHeader3__Lcom_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_2Lorg_gwtbootstrap3_client_ui_NavbarHeader_2_com_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_$build_1f_1NavbarBrand4__Lcom_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_2Lorg_gwtbootstrap3_client_ui_NavbarBrand_2_f_NavbarBrand4_0_0)) , com_google_gwt_user_client_ui_FlowPanel_$add__Lcom_google_gwt_user_client_ui_FlowPanel_2Lcom_google_gwt_user_client_ui_Widget_2V(com_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_$build_1f_1NavbarHeader3__Lcom_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_2Lorg_gwtbootstrap3_client_ui_NavbarHeader_2_f_NavbarHeader3_1, (com_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_$build_1f_1NavbarHeader3__Lcom_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_2Lorg_gwtbootstrap3_client_ui_NavbarHeader_2_com_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_$build_1f_1NavbarCollapseButton5__Lcom_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_2Lorg_gwtbootstrap3_client_ui_NavbarCollapseButton_2_f_NavbarCollapseButton5_0_0 = new org_gwtbootstrap3_client_ui_NavbarCollapseButton_NavbarCollapseButton__V , com_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_$build_1f_1NavbarHeader3__Lcom_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_2Lorg_gwtbootstrap3_client_ui_NavbarHeader_2_com_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_$build_1f_1NavbarCollapseButton5__Lcom_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_2Lorg_gwtbootstrap3_client_ui_NavbarCollapseButton_2_f_NavbarCollapseButton5_0_0.org_gwtbootstrap3_client_ui_NavbarCollapseButton_button.org_gwtbootstrap3_client_ui_base_button_AbstractButton_targetMixin.org_gwtbootstrap3_client_ui_base_mixin_AbstractMixin_uiObject.com_google_gwt_user_client_ui_UIObject_element.setAttribute('data-target', '#navbar-collapse') , com_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_$build_1f_1NavbarHeader3__Lcom_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_2Lorg_gwtbootstrap3_client_ui_NavbarHeader_2_com_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_$build_1f_1NavbarCollapseButton5__Lcom_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_2Lorg_gwtbootstrap3_client_ui_NavbarCollapseButton_2_f_NavbarCollapseButton5_0_0)) , com_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_$build_1f_1NavbarHeader3__Lcom_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_2Lorg_gwtbootstrap3_client_ui_NavbarHeader_2_f_NavbarHeader3_1)) , org_gwtbootstrap3_client_ui_base_ComplexWidget_$add__Lorg_gwtbootstrap3_client_ui_base_ComplexWidget_2Lcom_google_gwt_user_client_ui_Widget_2V(com_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_$build_1f_1Navbar2__Lcom_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_2Lorg_gwtbootstrap3_client_ui_Navbar_2_f_Navbar2_0, (com_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_$build_1f_1NavbarCollapse6__Lcom_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_2Lorg_gwtbootstrap3_client_ui_NavbarCollapse_2_f_NavbarCollapse6_1 = new org_gwtbootstrap3_client_ui_NavbarCollapse_NavbarCollapse__V , com_google_gwt_user_client_ui_FlowPanel_$add__Lcom_google_gwt_user_client_ui_FlowPanel_2Lcom_google_gwt_user_client_ui_Widget_2V(com_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_$build_1f_1NavbarCollapse6__Lcom_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_2Lorg_gwtbootstrap3_client_ui_NavbarCollapse_2_f_NavbarCollapse6_1, (com_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_$build_1f_1NavbarCollapse6__Lcom_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_2Lorg_gwtbootstrap3_client_ui_NavbarCollapse_2_com_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_$build_1f_1NavbarNav7__Lcom_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_2Lorg_gwtbootstrap3_client_ui_NavbarNav_2_f_NavbarNav7_0_0 = new org_gwtbootstrap3_client_ui_NavbarNav_NavbarNav__V , org_gwtbootstrap3_client_ui_base_ComplexWidget_$add__Lorg_gwtbootstrap3_client_ui_base_ComplexWidget_2Lcom_google_gwt_user_client_ui_Widget_2V(com_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_$build_1f_1NavbarCollapse6__Lcom_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_2Lorg_gwtbootstrap3_client_ui_NavbarCollapse_2_com_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_$build_1f_1NavbarNav7__Lcom_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_2Lorg_gwtbootstrap3_client_ui_NavbarNav_2_f_NavbarNav7_0_0, (com_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_$build_1btnWebSearchUI__Lcom_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_2Lorg_gwtbootstrap3_client_ui_AnchorListItem_2_btnWebSearchUI_1 = new org_gwtbootstrap3_client_ui_AnchorListItem_AnchorListItem__V , org_gwtbootstrap3_client_ui_Anchor_$setText__Lorg_gwtbootstrap3_client_ui_Anchor_2Ljava_lang_String_2V(com_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_$build_1btnWebSearchUI__Lcom_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_2Lorg_gwtbootstrap3_client_ui_AnchorListItem_2_btnWebSearchUI_1.org_gwtbootstrap3_client_ui_base_AbstractAnchorListItem_anchor, $intern_18) , this$static.com_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_owner.com_flair_client_views_MainViewport_btnWebSearchUI = com_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_$build_1btnWebSearchUI__Lcom_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_2Lorg_gwtbootstrap3_client_ui_AnchorListItem_2_btnWebSearchUI_1 , com_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_$build_1btnWebSearchUI__Lcom_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_2Lorg_gwtbootstrap3_client_ui_AnchorListItem_2_btnWebSearchUI_1)) , org_gwtbootstrap3_client_ui_base_ComplexWidget_$add__Lorg_gwtbootstrap3_client_ui_base_ComplexWidget_2Lcom_google_gwt_user_client_ui_Widget_2V(com_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_$build_1f_1NavbarCollapse6__Lcom_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_2Lorg_gwtbootstrap3_client_ui_NavbarCollapse_2_com_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_$build_1f_1NavbarNav7__Lcom_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_2Lorg_gwtbootstrap3_client_ui_NavbarNav_2_f_NavbarNav7_0_0, (com_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_$build_1btnUploadUI__Lcom_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_2Lorg_gwtbootstrap3_client_ui_AnchorListItem_2_btnUploadUI_1 = new org_gwtbootstrap3_client_ui_AnchorListItem_AnchorListItem__V , org_gwtbootstrap3_client_ui_Anchor_$setText__Lorg_gwtbootstrap3_client_ui_Anchor_2Ljava_lang_String_2V(com_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_$build_1btnUploadUI__Lcom_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_2Lorg_gwtbootstrap3_client_ui_AnchorListItem_2_btnUploadUI_1.org_gwtbootstrap3_client_ui_base_AbstractAnchorListItem_anchor, 'Upload') , this$static.com_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_owner.com_flair_client_views_MainViewport_btnUploadUI = com_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_$build_1btnUploadUI__Lcom_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_2Lorg_gwtbootstrap3_client_ui_AnchorListItem_2_btnUploadUI_1 , com_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_$build_1btnUploadUI__Lcom_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_2Lorg_gwtbootstrap3_client_ui_AnchorListItem_2_btnUploadUI_1)) , org_gwtbootstrap3_client_ui_base_ComplexWidget_$add__Lorg_gwtbootstrap3_client_ui_base_ComplexWidget_2Lcom_google_gwt_user_client_ui_Widget_2V(com_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_$build_1f_1NavbarCollapse6__Lcom_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_2Lorg_gwtbootstrap3_client_ui_NavbarCollapse_2_com_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_$build_1f_1NavbarNav7__Lcom_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_2Lorg_gwtbootstrap3_client_ui_NavbarNav_2_f_NavbarNav7_0_0, (com_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_$build_1btnAboutUI__Lcom_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_2Lorg_gwtbootstrap3_client_ui_AnchorListItem_2_btnAboutUI_1 = new org_gwtbootstrap3_client_ui_AnchorListItem_AnchorListItem__V , org_gwtbootstrap3_client_ui_Anchor_$setText__Lorg_gwtbootstrap3_client_ui_Anchor_2Ljava_lang_String_2V(com_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_$build_1btnAboutUI__Lcom_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_2Lorg_gwtbootstrap3_client_ui_AnchorListItem_2_btnAboutUI_1.org_gwtbootstrap3_client_ui_base_AbstractAnchorListItem_anchor, 'About') , this$static.com_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_owner.com_flair_client_views_MainViewport_btnAboutUI = com_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_$build_1btnAboutUI__Lcom_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_2Lorg_gwtbootstrap3_client_ui_AnchorListItem_2_btnAboutUI_1 , com_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_$build_1btnAboutUI__Lcom_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_2Lorg_gwtbootstrap3_client_ui_AnchorListItem_2_btnAboutUI_1)) , com_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_$build_1f_1NavbarCollapse6__Lcom_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_2Lorg_gwtbootstrap3_client_ui_NavbarCollapse_2_com_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_$build_1f_1NavbarNav7__Lcom_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_2Lorg_gwtbootstrap3_client_ui_NavbarNav_2_f_NavbarNav7_0_0)) , com_google_gwt_user_client_ui_FlowPanel_$add__Lcom_google_gwt_user_client_ui_FlowPanel_2Lcom_google_gwt_user_client_ui_Widget_2V(com_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_$build_1f_1NavbarCollapse6__Lcom_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_2Lorg_gwtbootstrap3_client_ui_NavbarCollapse_2_f_NavbarCollapse6_1, (com_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_$build_1f_1NavbarCollapse6__Lcom_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_2Lorg_gwtbootstrap3_client_ui_NavbarCollapse_2_com_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_$build_1f_1NavbarNav8__Lcom_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_2Lorg_gwtbootstrap3_client_ui_NavbarNav_2_f_NavbarNav8_0_0 = new org_gwtbootstrap3_client_ui_NavbarNav_NavbarNav__V , org_gwtbootstrap3_client_ui_base_ComplexWidget_$add__Lorg_gwtbootstrap3_client_ui_base_ComplexWidget_2Lcom_google_gwt_user_client_ui_Widget_2V(com_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_$build_1f_1NavbarCollapse6__Lcom_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_2Lorg_gwtbootstrap3_client_ui_NavbarCollapse_2_com_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_$build_1f_1NavbarNav8__Lcom_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_2Lorg_gwtbootstrap3_client_ui_NavbarNav_2_f_NavbarNav8_0_0, (com_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_$build_1f_1ListDropDown9__Lcom_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_2Lorg_gwtbootstrap3_client_ui_ListDropDown_2_f_ListDropDown9_1 = new org_gwtbootstrap3_client_ui_ListDropDown_ListDropDown__V , org_gwtbootstrap3_client_ui_ListDropDown_$add__Lorg_gwtbootstrap3_client_ui_ListDropDown_2Lcom_google_gwt_user_client_ui_Widget_2V(com_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_$build_1f_1ListDropDown9__Lcom_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_2Lorg_gwtbootstrap3_client_ui_ListDropDown_2_f_ListDropDown9_1, (com_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_$build_1f_1ListDropDown9__Lcom_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_2Lorg_gwtbootstrap3_client_ui_ListDropDown_2_com_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_$build_1btnSwitchLangUI__Lcom_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_2Lorg_gwtbootstrap3_client_ui_AnchorButton_2_btnSwitchLangUI_0_1 = new org_gwtbootstrap3_client_ui_AnchorButton_AnchorButton__V , org_gwtbootstrap3_client_ui_base_mixin_IconTextMixin_$setText__Lorg_gwtbootstrap3_client_ui_base_mixin_IconTextMixin_2Ljava_lang_String_2V(com_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_$build_1f_1ListDropDown9__Lcom_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_2Lorg_gwtbootstrap3_client_ui_ListDropDown_2_com_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_$build_1btnSwitchLangUI__Lcom_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_2Lorg_gwtbootstrap3_client_ui_AnchorButton_2_btnSwitchLangUI_0_1.org_gwtbootstrap3_client_ui_base_button_AbstractIconButton_iconTextMixin, 'Language') , org_gwtbootstrap3_client_ui_base_button_AbstractToggleButton_$setDataToggle__Lorg_gwtbootstrap3_client_ui_base_button_AbstractToggleButton_2Lorg_gwtbootstrap3_client_ui_constants_Toggle_2V(com_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_$build_1f_1ListDropDown9__Lcom_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_2Lorg_gwtbootstrap3_client_ui_ListDropDown_2_com_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_$build_1btnSwitchLangUI__Lcom_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_2Lorg_gwtbootstrap3_client_ui_AnchorButton_2_btnSwitchLangUI_0_1, (org_gwtbootstrap3_client_ui_constants_Toggle_$clinit__V() , org_gwtbootstrap3_client_ui_constants_Toggle_DROPDOWN)) , this$static.com_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_owner.com_flair_client_views_MainViewport_btnSwitchLangUI = com_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_$build_1f_1ListDropDown9__Lcom_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_2Lorg_gwtbootstrap3_client_ui_ListDropDown_2_com_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_$build_1btnSwitchLangUI__Lcom_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_2Lorg_gwtbootstrap3_client_ui_AnchorButton_2_btnSwitchLangUI_0_1 , com_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_$build_1f_1ListDropDown9__Lcom_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_2Lorg_gwtbootstrap3_client_ui_ListDropDown_2_com_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_$build_1btnSwitchLangUI__Lcom_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_2Lorg_gwtbootstrap3_client_ui_AnchorButton_2_btnSwitchLangUI_0_1)) , org_gwtbootstrap3_client_ui_ListDropDown_$add__Lorg_gwtbootstrap3_client_ui_ListDropDown_2Lcom_google_gwt_user_client_ui_Widget_2V(com_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_$build_1f_1ListDropDown9__Lcom_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_2Lorg_gwtbootstrap3_client_ui_ListDropDown_2_f_ListDropDown9_1, (com_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_$build_1f_1ListDropDown9__Lcom_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_2Lorg_gwtbootstrap3_client_ui_ListDropDown_2_com_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_$build_1f_1DropDownMenu10__Lcom_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_2Lorg_gwtbootstrap3_client_ui_DropDownMenu_2_f_DropDownMenu10_0_1 = new org_gwtbootstrap3_client_ui_DropDownMenu_DropDownMenu__V , org_gwtbootstrap3_client_ui_base_ComplexWidget_$add__Lorg_gwtbootstrap3_client_ui_base_ComplexWidget_2Lcom_google_gwt_user_client_ui_Widget_2V(com_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_$build_1f_1ListDropDown9__Lcom_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_2Lorg_gwtbootstrap3_client_ui_ListDropDown_2_com_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_$build_1f_1DropDownMenu10__Lcom_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_2Lorg_gwtbootstrap3_client_ui_DropDownMenu_2_f_DropDownMenu10_0_1, (com_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_$build_1f_1ListDropDown9__Lcom_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_2Lorg_gwtbootstrap3_client_ui_ListDropDown_2_com_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_$build_1f_1DropDownMenu10__Lcom_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_2Lorg_gwtbootstrap3_client_ui_DropDownMenu_2_com_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_$build_1btnLangEnUI__Lcom_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_2Lorg_gwtbootstrap3_client_ui_AnchorListItem_2_btnLangEnUI_0_0_1 = new org_gwtbootstrap3_client_ui_AnchorListItem_AnchorListItem__V , org_gwtbootstrap3_client_ui_Anchor_$setText__Lorg_gwtbootstrap3_client_ui_Anchor_2Ljava_lang_String_2V(com_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_$build_1f_1ListDropDown9__Lcom_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_2Lorg_gwtbootstrap3_client_ui_ListDropDown_2_com_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_$build_1f_1DropDownMenu10__Lcom_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_2Lorg_gwtbootstrap3_client_ui_DropDownMenu_2_com_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_$build_1btnLangEnUI__Lcom_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_2Lorg_gwtbootstrap3_client_ui_AnchorListItem_2_btnLangEnUI_0_0_1.org_gwtbootstrap3_client_ui_base_AbstractAnchorListItem_anchor, 'English') , this$static.com_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_owner.com_flair_client_views_MainViewport_btnLangEnUI = com_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_$build_1f_1ListDropDown9__Lcom_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_2Lorg_gwtbootstrap3_client_ui_ListDropDown_2_com_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_$build_1f_1DropDownMenu10__Lcom_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_2Lorg_gwtbootstrap3_client_ui_DropDownMenu_2_com_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_$build_1btnLangEnUI__Lcom_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_2Lorg_gwtbootstrap3_client_ui_AnchorListItem_2_btnLangEnUI_0_0_1 , com_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_$build_1f_1ListDropDown9__Lcom_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_2Lorg_gwtbootstrap3_client_ui_ListDropDown_2_com_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_$build_1f_1DropDownMenu10__Lcom_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_2Lorg_gwtbootstrap3_client_ui_DropDownMenu_2_com_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_$build_1btnLangEnUI__Lcom_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_2Lorg_gwtbootstrap3_client_ui_AnchorListItem_2_btnLangEnUI_0_0_1)) , org_gwtbootstrap3_client_ui_base_ComplexWidget_$add__Lorg_gwtbootstrap3_client_ui_base_ComplexWidget_2Lcom_google_gwt_user_client_ui_Widget_2V(com_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_$build_1f_1ListDropDown9__Lcom_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_2Lorg_gwtbootstrap3_client_ui_ListDropDown_2_com_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_$build_1f_1DropDownMenu10__Lcom_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_2Lorg_gwtbootstrap3_client_ui_DropDownMenu_2_f_DropDownMenu10_0_1, (com_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_$build_1f_1ListDropDown9__Lcom_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_2Lorg_gwtbootstrap3_client_ui_ListDropDown_2_com_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_$build_1f_1DropDownMenu10__Lcom_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_2Lorg_gwtbootstrap3_client_ui_DropDownMenu_2_com_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_$build_1btnLangDeUI__Lcom_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_2Lorg_gwtbootstrap3_client_ui_AnchorListItem_2_btnLangDeUI_0_0_1 = new org_gwtbootstrap3_client_ui_AnchorListItem_AnchorListItem__V , org_gwtbootstrap3_client_ui_Anchor_$setText__Lorg_gwtbootstrap3_client_ui_Anchor_2Ljava_lang_String_2V(com_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_$build_1f_1ListDropDown9__Lcom_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_2Lorg_gwtbootstrap3_client_ui_ListDropDown_2_com_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_$build_1f_1DropDownMenu10__Lcom_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_2Lorg_gwtbootstrap3_client_ui_DropDownMenu_2_com_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_$build_1btnLangDeUI__Lcom_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_2Lorg_gwtbootstrap3_client_ui_AnchorListItem_2_btnLangDeUI_0_0_1.org_gwtbootstrap3_client_ui_base_AbstractAnchorListItem_anchor, 'German') , this$static.com_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_owner.com_flair_client_views_MainViewport_btnLangDeUI = com_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_$build_1f_1ListDropDown9__Lcom_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_2Lorg_gwtbootstrap3_client_ui_ListDropDown_2_com_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_$build_1f_1DropDownMenu10__Lcom_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_2Lorg_gwtbootstrap3_client_ui_DropDownMenu_2_com_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_$build_1btnLangDeUI__Lcom_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_2Lorg_gwtbootstrap3_client_ui_AnchorListItem_2_btnLangDeUI_0_0_1 , com_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_$build_1f_1ListDropDown9__Lcom_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_2Lorg_gwtbootstrap3_client_ui_ListDropDown_2_com_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_$build_1f_1DropDownMenu10__Lcom_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_2Lorg_gwtbootstrap3_client_ui_DropDownMenu_2_com_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_$build_1btnLangDeUI__Lcom_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_2Lorg_gwtbootstrap3_client_ui_AnchorListItem_2_btnLangDeUI_0_0_1)) , com_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_$build_1f_1ListDropDown9__Lcom_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_2Lorg_gwtbootstrap3_client_ui_ListDropDown_2_com_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_$build_1f_1DropDownMenu10__Lcom_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_2Lorg_gwtbootstrap3_client_ui_DropDownMenu_2_f_DropDownMenu10_0_1)) , com_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_$build_1f_1ListDropDown9__Lcom_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_2Lorg_gwtbootstrap3_client_ui_ListDropDown_2_f_ListDropDown9_1)) , org_gwtbootstrap3_client_ui_NavbarNav_$setPull__Lorg_gwtbootstrap3_client_ui_NavbarNav_2Lorg_gwtbootstrap3_client_ui_constants_Pull_2V(com_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_$build_1f_1NavbarCollapse6__Lcom_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_2Lorg_gwtbootstrap3_client_ui_NavbarCollapse_2_com_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_$build_1f_1NavbarNav8__Lcom_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_2Lorg_gwtbootstrap3_client_ui_NavbarNav_2_f_NavbarNav8_0_0, (org_gwtbootstrap3_client_ui_constants_Pull_$clinit__V() , org_gwtbootstrap3_client_ui_constants_Pull_RIGHT)) , com_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_$build_1f_1NavbarCollapse6__Lcom_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_2Lorg_gwtbootstrap3_client_ui_NavbarCollapse_2_com_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_$build_1f_1NavbarNav8__Lcom_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_2Lorg_gwtbootstrap3_client_ui_NavbarNav_2_f_NavbarNav8_0_0)) , org_gwtbootstrap3_client_ui_base_mixin_IdMixin_$setId__Lorg_gwtbootstrap3_client_ui_base_mixin_IdMixin_2Ljava_lang_String_2V(com_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_$build_1f_1NavbarCollapse6__Lcom_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_2Lorg_gwtbootstrap3_client_ui_NavbarCollapse_2_f_NavbarCollapse6_1.org_gwtbootstrap3_client_ui_gwt_FlowPanel_idMixin, $intern_22) , com_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_$build_1f_1NavbarCollapse6__Lcom_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_2Lorg_gwtbootstrap3_client_ui_NavbarCollapse_2_f_NavbarCollapse6_1)) , com_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_$build_1f_1Navbar2__Lcom_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_2Lorg_gwtbootstrap3_client_ui_Navbar_2_f_Navbar2_0), com_google_gwt_uibinder_client_LazyDomElement_$get__Lcom_google_gwt_uibinder_client_LazyDomElement_2Lcom_google_gwt_dom_client_Element_2(this$static.com_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_domId0Element));
  com_google_gwt_user_client_ui_HTMLPanel_$addAndReplaceElement__Lcom_google_gwt_user_client_ui_HTMLPanel_2Lcom_google_gwt_user_client_ui_Widget_2Lcom_google_gwt_user_client_Element_2V(f_HTMLPanel1, (pnlMainUI = new com_google_gwt_user_client_ui_SimplePanel_SimplePanel__V , pnlMainUI), com_google_gwt_uibinder_client_LazyDomElement_$get__Lcom_google_gwt_uibinder_client_LazyDomElement_2Lcom_google_gwt_dom_client_Element_2(this$static.com_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_domId1Element));
  com_google_gwt_user_client_ui_HTMLPanel_$addAndReplaceElement__Lcom_google_gwt_user_client_ui_HTMLPanel_2Lcom_google_gwt_user_client_ui_Widget_2Lcom_google_gwt_user_client_Element_2V(f_HTMLPanel1, (com_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_$build_1mdlAboutEnUI__Lcom_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_2Lorg_gwtbootstrap3_client_ui_Modal_2_mdlAboutEnUI_0 = new org_gwtbootstrap3_client_ui_Modal_Modal__V , org_gwtbootstrap3_client_ui_Modal_$add__Lorg_gwtbootstrap3_client_ui_Modal_2Lcom_google_gwt_user_client_ui_Widget_2V(com_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_$build_1mdlAboutEnUI__Lcom_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_2Lorg_gwtbootstrap3_client_ui_Modal_2_mdlAboutEnUI_0, (com_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_$build_1f_1ModalHeader11__Lcom_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_2Lorg_gwtbootstrap3_client_ui_ModalHeader_2_f_ModalHeader11_1 = new org_gwtbootstrap3_client_ui_ModalHeader_ModalHeader__V , com_google_gwt_user_client_ui_FlowPanel_$add__Lcom_google_gwt_user_client_ui_FlowPanel_2Lcom_google_gwt_user_client_ui_Widget_2V(com_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_$build_1f_1ModalHeader11__Lcom_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_2Lorg_gwtbootstrap3_client_ui_ModalHeader_2_f_ModalHeader11_1, (com_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_$build_1f_1ModalHeader11__Lcom_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_2Lorg_gwtbootstrap3_client_ui_ModalHeader_2_com_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_$build_1f_1HTML12__Lcom_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_2Lcom_google_gwt_user_client_ui_HTML_2_f_HTML12_0_0 = new com_google_gwt_user_client_ui_HTML_HTML__V , com_google_gwt_user_client_ui_HTML_$setHTML__Lcom_google_gwt_user_client_ui_HTML_2Ljava_lang_String_2V(com_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_$build_1f_1ModalHeader11__Lcom_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_2Lorg_gwtbootstrap3_client_ui_ModalHeader_2_com_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_$build_1f_1HTML12__Lcom_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_2Lcom_google_gwt_user_client_ui_HTML_2_f_HTML12_0_0, com_flair_client_views_MainViewport_1MainViewportUiBinderImpl_1TemplateImpl_$html2__Lcom_flair_client_views_MainViewport_1MainViewportUiBinderImpl_1TemplateImpl_2Lcom_google_gwt_safehtml_shared_SafeHtml_2Lcom_google_gwt_safehtml_shared_SafeHtml_2Lcom_google_gwt_safehtml_shared_SafeHtml_2Lcom_google_gwt_safehtml_shared_SafeHtml_2Lcom_google_gwt_safehtml_shared_SafeHtml_2((com_google_gwt_safehtml_shared_SafeHtmlUtils_$clinit__V() , new com_google_gwt_safehtml_shared_SafeHtmlString_SafeHtmlString__Ljava_lang_String_2V($intern_19)), new com_google_gwt_safehtml_shared_SafeHtmlString_SafeHtmlString__Ljava_lang_String_2V('by'), new com_google_gwt_safehtml_shared_SafeHtmlString_SafeHtmlString__Ljava_lang_String_2V('supervised by'), new com_google_gwt_safehtml_shared_SafeHtmlString_SafeHtmlString__Ljava_lang_String_2V('University of T\xFCbingen, Germany')).com_google_gwt_safehtml_shared_OnlyToBeUsedInGeneratedCodeStringBlessedAsSafeHtml_html) , com_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_$build_1f_1ModalHeader11__Lcom_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_2Lorg_gwtbootstrap3_client_ui_ModalHeader_2_com_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_$build_1f_1HTML12__Lcom_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_2Lcom_google_gwt_user_client_ui_HTML_2_f_HTML12_0_0)) , com_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_$build_1f_1ModalHeader11__Lcom_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_2Lorg_gwtbootstrap3_client_ui_ModalHeader_2_f_ModalHeader11_1)) , org_gwtbootstrap3_client_ui_Modal_$add__Lorg_gwtbootstrap3_client_ui_Modal_2Lcom_google_gwt_user_client_ui_Widget_2V(com_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_$build_1mdlAboutEnUI__Lcom_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_2Lorg_gwtbootstrap3_client_ui_Modal_2_mdlAboutEnUI_0, (com_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_$build_1f_1ModalBody13__Lcom_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_2Lorg_gwtbootstrap3_client_ui_ModalBody_2_f_ModalBody13_1 = new org_gwtbootstrap3_client_ui_ModalBody_ModalBody__V , com_google_gwt_user_client_ui_FlowPanel_$add__Lcom_google_gwt_user_client_ui_FlowPanel_2Lcom_google_gwt_user_client_ui_Widget_2V(com_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_$build_1f_1ModalBody13__Lcom_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_2Lorg_gwtbootstrap3_client_ui_ModalBody_2_f_ModalBody13_1, (com_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_$build_1f_1ModalBody13__Lcom_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_2Lorg_gwtbootstrap3_client_ui_ModalBody_2_com_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_$build_1f_1HTML14__Lcom_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_2Lcom_google_gwt_user_client_ui_HTML_2_f_HTML14_0_0 = new com_google_gwt_user_client_ui_HTML_HTML__V , com_google_gwt_user_client_ui_HTML_$setHTML__Lcom_google_gwt_user_client_ui_HTML_2Ljava_lang_String_2V(com_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_$build_1f_1ModalBody13__Lcom_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_2Lorg_gwtbootstrap3_client_ui_ModalBody_2_com_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_$build_1f_1HTML14__Lcom_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_2Lcom_google_gwt_user_client_ui_HTML_2_f_HTML14_0_0, (com_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_$build_1f_1ModalBody13__Lcom_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_2Lorg_gwtbootstrap3_client_ui_ModalBody_2_com_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_$build_1f_1HTML14__Lcom_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_2Lcom_google_gwt_user_client_ui_HTML_2_com_flair_client_views_MainViewport_1MainViewportUiBinderImpl_1TemplateImpl_$html3__Lcom_flair_client_views_MainViewport_1MainViewportUiBinderImpl_1TemplateImpl_2Lcom_google_gwt_safehtml_shared_SafeHtml_2_sb_0_0_0 = new java_lang_StringBuilder_StringBuilder__V , com_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_$build_1f_1ModalBody13__Lcom_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_2Lorg_gwtbootstrap3_client_ui_ModalBody_2_com_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_$build_1f_1HTML14__Lcom_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_2Lcom_google_gwt_user_client_ui_HTML_2_com_flair_client_views_MainViewport_1MainViewportUiBinderImpl_1TemplateImpl_$html3__Lcom_flair_client_views_MainViewport_1MainViewportUiBinderImpl_1TemplateImpl_2Lcom_google_gwt_safehtml_shared_SafeHtml_2_sb_0_0_0.java_lang_AbstractStringBuilder_string += "<div style='text-align: center'> <p>FLAIR is an online tool for language teachers and learners that: <\/p> <ul style='text-align: left'> <li>searches the web for a topic of interest <\/li> <li>analyzes the results for grammatical constructions and readability levels <\/li> <li>re-ranks the results according to your (pedagogical or learning) needs specified in the settings<\/li> <\/ul>  <\/div>  <p> <b>PAPERS:<\/b> <\/p> <ul> <li> <a href='http://sfs.uni-tuebingen.de/~mchnkina/downloads/Chinkina_Maria_thesis_2015.pdf' target='_blank'>MA thesis by Maria Chinkina<\/a> <\/li> <li> <a href='http://sfs.uni-tuebingen.de/~mchnkina/downloads/Chinkina_Meurers_BEA_2016.pdf' target='_blank'>BEA paper<\/a> (Maria Chinkina, Detmar Meurers) <\/li> <li> <a href='http://sfs.uni-tuebingen.de/~mchnkina/downloads/Chinkina_Kannan_Meurers_ACL_2016.pdf' target='_blank'>System description paper<\/a> (Maria Chinkina, Madeeswaran Kannan, Detmar Meurers) <\/li> <\/ul> <br> <p> <b>ADDITIONAL MATERIAL:<\/b> <\/p> <ul> <li> <a href='http://sfs.uni-tuebingen.de/~mchnkina/downloads/FLAIR_Evaluation.pdf' target='_blank'>Evaluation of the FLAIR algorithm<\/a> (81 grammatical constructions: Precision, Recall, F1) <\/li> <li> <a href='http://sfs.uni-tuebingen.de/~mchnkina/downloads/AWL.pdf' target='_blank'>AWL: Academic Word List<\/a> ( <a href='http://onlinelibrary.wiley.com/doi/10.2307/3587951/abstract' target='_blank'>Coxhead, 2000<\/a> ) <\/li> <li> <a href='http://sfs.uni-tuebingen.de/~mchnkina/img/heatmap_vert_color.png' target='_blank'>Distribution of grammatical constructions in the Web<\/a> (top 55 hits for a query) <\/li> <\/ul> <br> <p> <b>Third-party tools:<\/b> <\/p> <ul> <li> Back-end: <a href='http://datamarket.azure.com/dataset/bing/search' target='_blank'>Microsoft Bing API<\/a> ( <a href='https://github.com/peculater/azure-bing-search-java' target='_blank'>Java implementation<\/a> ), <a href='https://github.com/kohlschutter/boilerpipe' target='_blank'>Boilerpipe<\/a> , <a href='http://stanfordnlp.github.io/CoreNLP/' target='_blank'>Stanford CoreNLP<\/a> <\/li> <li> Front-end: <a href='http://getbootstrap.com' target='_blank'>Bootstrap<\/a> , <a href='http://glyphicons.com/' target='_blank'>Glyphicons<\/a> , <a href='https://d3js.org' target='_blank'>d3<\/a> <\/li> <\/ul> <br> <p> <b>LICENSE:<\/b> <\/p> <a href='http://creativecommons.org/licenses/by-nc-sa/4.0/' rel='license'> <img alt='Creative Commons License' src='https://i.creativecommons.org/l/by-nc-sa/4.0/88x31.png' style='border-width:0'> <\/a> <br> FLAIR tool is licensed under a <a href='http://creativecommons.org/licenses/by-nc-sa/4.0/' rel='license'>Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International License<\/a>." , new com_google_gwt_safehtml_shared_OnlyToBeUsedInGeneratedCodeStringBlessedAsSafeHtml_OnlyToBeUsedInGeneratedCodeStringBlessedAsSafeHtml__Ljava_lang_String_2V(com_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_$build_1f_1ModalBody13__Lcom_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_2Lorg_gwtbootstrap3_client_ui_ModalBody_2_com_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_$build_1f_1HTML14__Lcom_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_2Lcom_google_gwt_user_client_ui_HTML_2_com_flair_client_views_MainViewport_1MainViewportUiBinderImpl_1TemplateImpl_$html3__Lcom_flair_client_views_MainViewport_1MainViewportUiBinderImpl_1TemplateImpl_2Lcom_google_gwt_safehtml_shared_SafeHtml_2_sb_0_0_0.java_lang_AbstractStringBuilder_string)).com_google_gwt_safehtml_shared_OnlyToBeUsedInGeneratedCodeStringBlessedAsSafeHtml_html) , com_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_$build_1f_1ModalBody13__Lcom_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_2Lorg_gwtbootstrap3_client_ui_ModalBody_2_com_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_$build_1f_1HTML14__Lcom_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_2Lcom_google_gwt_user_client_ui_HTML_2_f_HTML14_0_0)) , com_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_$build_1f_1ModalBody13__Lcom_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_2Lorg_gwtbootstrap3_client_ui_ModalBody_2_f_ModalBody13_1)) , org_gwtbootstrap3_client_ui_Modal_$add__Lorg_gwtbootstrap3_client_ui_Modal_2Lcom_google_gwt_user_client_ui_Widget_2V(com_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_$build_1mdlAboutEnUI__Lcom_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_2Lorg_gwtbootstrap3_client_ui_Modal_2_mdlAboutEnUI_0, (com_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_$build_1f_1ModalFooter15__Lcom_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_2Lorg_gwtbootstrap3_client_ui_ModalFooter_2_f_ModalFooter15_1 = new org_gwtbootstrap3_client_ui_ModalFooter_ModalFooter__V , com_google_gwt_user_client_ui_FlowPanel_$add__Lcom_google_gwt_user_client_ui_FlowPanel_2Lcom_google_gwt_user_client_ui_Widget_2V(com_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_$build_1f_1ModalFooter15__Lcom_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_2Lorg_gwtbootstrap3_client_ui_ModalFooter_2_f_ModalFooter15_1, (com_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_$build_1f_1ModalFooter15__Lcom_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_2Lorg_gwtbootstrap3_client_ui_ModalFooter_2_com_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_$build_1f_1Button16__Lcom_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_2Lorg_gwtbootstrap3_client_ui_Button_2_f_Button16_0_0 = new org_gwtbootstrap3_client_ui_Button_Button__V , org_gwtbootstrap3_client_ui_base_mixin_IconTextMixin_$setText__Lorg_gwtbootstrap3_client_ui_base_mixin_IconTextMixin_2Ljava_lang_String_2V(com_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_$build_1f_1ModalFooter15__Lcom_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_2Lorg_gwtbootstrap3_client_ui_ModalFooter_2_com_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_$build_1f_1Button16__Lcom_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_2Lorg_gwtbootstrap3_client_ui_Button_2_f_Button16_0_0.org_gwtbootstrap3_client_ui_base_button_AbstractIconButton_iconTextMixin, 'Close') , org_gwtbootstrap3_client_ui_base_button_AbstractButton_$setDataDismiss__Lorg_gwtbootstrap3_client_ui_base_button_AbstractButton_2Lorg_gwtbootstrap3_client_ui_constants_ButtonDismiss_2V(com_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_$build_1f_1ModalFooter15__Lcom_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_2Lorg_gwtbootstrap3_client_ui_ModalFooter_2_com_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_$build_1f_1Button16__Lcom_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_2Lorg_gwtbootstrap3_client_ui_Button_2_f_Button16_0_0, (org_gwtbootstrap3_client_ui_constants_ButtonDismiss_$clinit__V() , org_gwtbootstrap3_client_ui_constants_ButtonDismiss_MODAL)) , org_gwtbootstrap3_client_ui_base_helper_StyleHelper_addUniqueEnumStyleName__Lcom_google_gwt_user_client_ui_UIObject_2Ljava_lang_Class_2Lcom_google_gwt_dom_client_Style$HasCssName_2V(com_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_$build_1f_1ModalFooter15__Lcom_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_2Lorg_gwtbootstrap3_client_ui_ModalFooter_2_com_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_$build_1f_1Button16__Lcom_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_2Lorg_gwtbootstrap3_client_ui_Button_2_f_Button16_0_0, com_google_gwt_lang_ClassLiteralHolder_Lorg_1gwtbootstrap3_1client_1ui_1constants_1ButtonType_12_1classLit, (org_gwtbootstrap3_client_ui_constants_ButtonType_$clinit__V() , org_gwtbootstrap3_client_ui_constants_ButtonType_PRIMARY)) , com_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_$build_1f_1ModalFooter15__Lcom_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_2Lorg_gwtbootstrap3_client_ui_ModalFooter_2_com_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_$build_1f_1Button16__Lcom_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_2Lorg_gwtbootstrap3_client_ui_Button_2_f_Button16_0_0)) , com_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_$build_1f_1ModalFooter15__Lcom_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_2Lorg_gwtbootstrap3_client_ui_ModalFooter_2_f_ModalFooter15_1)) , com_google_gwt_user_client_ui_UIObject_setStyleName__Lcom_google_gwt_dom_client_Element_2Ljava_lang_String_2ZV(com_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_$build_1mdlAboutEnUI__Lcom_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_2Lorg_gwtbootstrap3_client_ui_Modal_2_mdlAboutEnUI_0.com_google_gwt_user_client_ui_UIObject_element, 'fade', true) , com_google_gwt_dom_client_Element_$setAttribute__Lcom_google_gwt_dom_client_Element_2Ljava_lang_String_2Ljava_lang_String_2V(com_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_$build_1mdlAboutEnUI__Lcom_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_2Lorg_gwtbootstrap3_client_ui_Modal_2_mdlAboutEnUI_0.com_google_gwt_user_client_ui_UIObject_element, $intern_23, (java_lang_Boolean_$clinit__V() , $intern_7)) , com_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_$build_1mdlAboutEnUI__Lcom_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_2Lorg_gwtbootstrap3_client_ui_Modal_2_mdlAboutEnUI_0.com_google_gwt_user_client_ui_UIObject_element.setAttribute('tabindex', '-1') , undefined , org_gwtbootstrap3_client_ui_ModalHeader_$setClosable__Lorg_gwtbootstrap3_client_ui_ModalHeader_2ZV(com_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_$build_1mdlAboutEnUI__Lcom_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_2Lorg_gwtbootstrap3_client_ui_Modal_2_mdlAboutEnUI_0.org_gwtbootstrap3_client_ui_Modal_header) , org_gwtbootstrap3_client_ui_base_mixin_IdMixin_$setId__Lorg_gwtbootstrap3_client_ui_base_mixin_IdMixin_2Ljava_lang_String_2V(com_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_$build_1mdlAboutEnUI__Lcom_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_2Lorg_gwtbootstrap3_client_ui_Modal_2_mdlAboutEnUI_0.org_gwtbootstrap3_client_ui_base_ComplexWidget_idMixin, 'modalAboutEn') , org_gwtbootstrap3_client_ui_ModalHeader_$setTitle__Lorg_gwtbootstrap3_client_ui_ModalHeader_2Ljava_lang_String_2V(com_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_$build_1mdlAboutEnUI__Lcom_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_2Lorg_gwtbootstrap3_client_ui_Modal_2_mdlAboutEnUI_0.org_gwtbootstrap3_client_ui_Modal_header, $intern_19) , org_gwtbootstrap3_client_ui_Modal_$setDataBackdrop__Lorg_gwtbootstrap3_client_ui_Modal_2Lorg_gwtbootstrap3_client_ui_constants_ModalBackdrop_2V(com_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_$build_1mdlAboutEnUI__Lcom_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_2Lorg_gwtbootstrap3_client_ui_Modal_2_mdlAboutEnUI_0, (org_gwtbootstrap3_client_ui_constants_ModalBackdrop_$clinit__V() , org_gwtbootstrap3_client_ui_constants_ModalBackdrop_STATIC)) , this$static.com_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_owner.com_flair_client_views_MainViewport_mdlAboutEnUI = com_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_$build_1mdlAboutEnUI__Lcom_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_2Lorg_gwtbootstrap3_client_ui_Modal_2_mdlAboutEnUI_0 , com_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_$build_1mdlAboutEnUI__Lcom_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_2Lorg_gwtbootstrap3_client_ui_Modal_2_mdlAboutEnUI_0), com_google_gwt_uibinder_client_LazyDomElement_$get__Lcom_google_gwt_uibinder_client_LazyDomElement_2Lcom_google_gwt_dom_client_Element_2(this$static.com_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_domId2Element));
  com_google_gwt_user_client_ui_HTMLPanel_$addAndReplaceElement__Lcom_google_gwt_user_client_ui_HTMLPanel_2Lcom_google_gwt_user_client_ui_Widget_2Lcom_google_gwt_user_client_Element_2V(f_HTMLPanel1, (com_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_$build_1mdlAboutDeUI__Lcom_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_2Lorg_gwtbootstrap3_client_ui_Modal_2_mdlAboutDeUI_0 = new org_gwtbootstrap3_client_ui_Modal_Modal__V , org_gwtbootstrap3_client_ui_Modal_$add__Lorg_gwtbootstrap3_client_ui_Modal_2Lcom_google_gwt_user_client_ui_Widget_2V(com_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_$build_1mdlAboutDeUI__Lcom_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_2Lorg_gwtbootstrap3_client_ui_Modal_2_mdlAboutDeUI_0, (com_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_$build_1f_1ModalHeader17__Lcom_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_2Lorg_gwtbootstrap3_client_ui_ModalHeader_2_f_ModalHeader17_1 = new org_gwtbootstrap3_client_ui_ModalHeader_ModalHeader__V , com_google_gwt_user_client_ui_FlowPanel_$add__Lcom_google_gwt_user_client_ui_FlowPanel_2Lcom_google_gwt_user_client_ui_Widget_2V(com_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_$build_1f_1ModalHeader17__Lcom_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_2Lorg_gwtbootstrap3_client_ui_ModalHeader_2_f_ModalHeader17_1, (com_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_$build_1f_1ModalHeader17__Lcom_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_2Lorg_gwtbootstrap3_client_ui_ModalHeader_2_com_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_$build_1f_1HTML18__Lcom_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_2Lcom_google_gwt_user_client_ui_HTML_2_f_HTML18_0_0 = new com_google_gwt_user_client_ui_HTML_HTML__V , com_google_gwt_user_client_ui_HTML_$setHTML__Lcom_google_gwt_user_client_ui_HTML_2Ljava_lang_String_2V(com_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_$build_1f_1ModalHeader17__Lcom_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_2Lorg_gwtbootstrap3_client_ui_ModalHeader_2_com_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_$build_1f_1HTML18__Lcom_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_2Lcom_google_gwt_user_client_ui_HTML_2_f_HTML18_0_0, (com_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_$build_1f_1ModalHeader17__Lcom_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_2Lorg_gwtbootstrap3_client_ui_ModalHeader_2_com_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_$build_1f_1HTML18__Lcom_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_2Lcom_google_gwt_user_client_ui_HTML_2_com_flair_client_views_MainViewport_1MainViewportUiBinderImpl_1TemplateImpl_$html4__Lcom_flair_client_views_MainViewport_1MainViewportUiBinderImpl_1TemplateImpl_2Lcom_google_gwt_safehtml_shared_SafeHtml_2_sb_0_0_0 = new java_lang_StringBuilder_StringBuilder__V , com_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_$build_1f_1ModalHeader17__Lcom_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_2Lorg_gwtbootstrap3_client_ui_ModalHeader_2_com_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_$build_1f_1HTML18__Lcom_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_2Lcom_google_gwt_user_client_ui_HTML_2_com_flair_client_views_MainViewport_1MainViewportUiBinderImpl_1TemplateImpl_$html4__Lcom_flair_client_views_MainViewport_1MainViewportUiBinderImpl_1TemplateImpl_2Lcom_google_gwt_safehtml_shared_SafeHtml_2_sb_0_0_0.java_lang_AbstractStringBuilder_string += "<center> <h3 class='modal-title'>\xDCber FLAIR <\/h3> <p><span style='color:darkorange'><b>von<\/b><\/span> <a href='http://sfs.uni-tuebingen.de/~mchnkina/' target='_blank'>Maria Chinkina<\/a> <a href='mailto:maria.chinkina@uni-tuebingen.de?Subject=FLAIR%20tool' target='_top' title='Maria Chinkina kontaktieren'><span class='glyphicon glyphicon-envelope'><\/span><\/a>  &amp; <a href='http://sfs.uni-tuebingen.de/~mkannan/' target='_blank'>Madeeswaran Kannan<\/a> <a href='mailto:mkannan@sfs.uni-tuebingen.de?Subject=FLAIR%20tool' target='_top' title='Madeeswaran Kannan kontaktieren'><span class='glyphicon glyphicon-envelope'><\/span><\/a> betreut von  <a href='http://sfs.uni-tuebingen.de/~dm/' target='_blank'>Prof. Dr. Detmar Meurers<\/a>  <br> <span style='color:darkorange'>@<\/span> Universit\xE4t T\xFCbingen, Deutschland <span style='color:darkorange'>|<\/span> 2015-2017 <br> Version 2.0 <\/p> <\/center>" , new com_google_gwt_safehtml_shared_OnlyToBeUsedInGeneratedCodeStringBlessedAsSafeHtml_OnlyToBeUsedInGeneratedCodeStringBlessedAsSafeHtml__Ljava_lang_String_2V(com_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_$build_1f_1ModalHeader17__Lcom_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_2Lorg_gwtbootstrap3_client_ui_ModalHeader_2_com_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_$build_1f_1HTML18__Lcom_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_2Lcom_google_gwt_user_client_ui_HTML_2_com_flair_client_views_MainViewport_1MainViewportUiBinderImpl_1TemplateImpl_$html4__Lcom_flair_client_views_MainViewport_1MainViewportUiBinderImpl_1TemplateImpl_2Lcom_google_gwt_safehtml_shared_SafeHtml_2_sb_0_0_0.java_lang_AbstractStringBuilder_string)).com_google_gwt_safehtml_shared_OnlyToBeUsedInGeneratedCodeStringBlessedAsSafeHtml_html) , com_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_$build_1f_1ModalHeader17__Lcom_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_2Lorg_gwtbootstrap3_client_ui_ModalHeader_2_com_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_$build_1f_1HTML18__Lcom_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_2Lcom_google_gwt_user_client_ui_HTML_2_f_HTML18_0_0)) , com_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_$build_1f_1ModalHeader17__Lcom_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_2Lorg_gwtbootstrap3_client_ui_ModalHeader_2_f_ModalHeader17_1)) , org_gwtbootstrap3_client_ui_Modal_$add__Lorg_gwtbootstrap3_client_ui_Modal_2Lcom_google_gwt_user_client_ui_Widget_2V(com_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_$build_1mdlAboutDeUI__Lcom_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_2Lorg_gwtbootstrap3_client_ui_Modal_2_mdlAboutDeUI_0, (com_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_$build_1f_1ModalBody19__Lcom_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_2Lorg_gwtbootstrap3_client_ui_ModalBody_2_f_ModalBody19_1 = new org_gwtbootstrap3_client_ui_ModalBody_ModalBody__V , com_google_gwt_user_client_ui_FlowPanel_$add__Lcom_google_gwt_user_client_ui_FlowPanel_2Lcom_google_gwt_user_client_ui_Widget_2V(com_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_$build_1f_1ModalBody19__Lcom_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_2Lorg_gwtbootstrap3_client_ui_ModalBody_2_f_ModalBody19_1, (com_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_$build_1f_1ModalBody19__Lcom_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_2Lorg_gwtbootstrap3_client_ui_ModalBody_2_com_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_$build_1f_1HTML20__Lcom_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_2Lcom_google_gwt_user_client_ui_HTML_2_f_HTML20_0_0 = new com_google_gwt_user_client_ui_HTML_HTML__V , com_google_gwt_user_client_ui_HTML_$setHTML__Lcom_google_gwt_user_client_ui_HTML_2Ljava_lang_String_2V(com_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_$build_1f_1ModalBody19__Lcom_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_2Lorg_gwtbootstrap3_client_ui_ModalBody_2_com_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_$build_1f_1HTML20__Lcom_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_2Lcom_google_gwt_user_client_ui_HTML_2_f_HTML20_0_0, (com_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_$build_1f_1ModalBody19__Lcom_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_2Lorg_gwtbootstrap3_client_ui_ModalBody_2_com_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_$build_1f_1HTML20__Lcom_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_2Lcom_google_gwt_user_client_ui_HTML_2_com_flair_client_views_MainViewport_1MainViewportUiBinderImpl_1TemplateImpl_$html5__Lcom_flair_client_views_MainViewport_1MainViewportUiBinderImpl_1TemplateImpl_2Lcom_google_gwt_safehtml_shared_SafeHtml_2_sb_0_0_0 = new java_lang_StringBuilder_StringBuilder__V , com_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_$build_1f_1ModalBody19__Lcom_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_2Lorg_gwtbootstrap3_client_ui_ModalBody_2_com_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_$build_1f_1HTML20__Lcom_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_2Lcom_google_gwt_user_client_ui_HTML_2_com_flair_client_views_MainViewport_1MainViewportUiBinderImpl_1TemplateImpl_$html5__Lcom_flair_client_views_MainViewport_1MainViewportUiBinderImpl_1TemplateImpl_2Lcom_google_gwt_safehtml_shared_SafeHtml_2_sb_0_0_0.java_lang_AbstractStringBuilder_string += "<div style='text-align: center'> <p>FLAIR ist ein Online-Tool f\xFCr Lerner und Lehrer von Sprachen. FLAIR: <\/p> <ul style='text-align: left'> <li>durchsucht das Web nach einem gew\xFCnschten Thema<\/li> <li>analysiert die Suchergebnisse auf Grammatikkonstruktionen und Schwierigkeitsgrade<\/li> <li>bewertet die Ergebnisse nach Ihren (p\xE4dagogischen oder lern-spezifischen) Bed\xFCrfnissen, die Sie in den Einstellungen festlegt haben<\/li> <\/ul> <\/div> <p><b>ARTIKEL:<\/b><\/p> <ul> <li><a href='http://sfs.uni-tuebingen.de/~mchnkina/downloads/Chinkina_Maria_thesis_2015.pdf' target='_blank'>MA-Arbeit von Maria Chinkina<\/a><\/li> <li><a href='http://sfs.uni-tuebingen.de/~mchnkina/downloads/Chinkina_Meurers_BEA_2016.pdf' target='_blank'>BEA-Artikel<\/a> (Maria Chinkina, Detmar Meurers)<\/li> <li><a href='http://sfs.uni-tuebingen.de/~mchnkina/downloads/Chinkina_Kannan_Meurers_ACL_2016.pdf' target='_blank'>Systembeschreibung<\/a> (Maria Chinkina, Madeeswaran Kannan, Detmar Meurers)<\/li> <\/ul> <br> <p><b>ZUS\xC4TZLICHES MATERIAL:<\/b><\/p> <ul> <li><a href='http://sfs.uni-tuebingen.de/~mchnkina/downloads/FLAIR_Evaluation.pdf' target='_blank'>Evaluation des FLAIR-Algorithmus<\/a> (81 grammatische Konstruktionen f\xFCr Englisch: Precision, Recall, F1)<\/li> <\/ul> <br> <p><b>DRITT-WERKZEUGE<\/b><\/p> <ul> <li>Back-end: <a href='http://datamarket.azure.com/dataset/bing/search' target='_blank'>Microsoft Bing API<\/a> (<a href='https://github.com/peculater/azure-bing-search-java' target='_blank'>Java implementation<\/a>), <a href='https://github.com/kohlschutter/boilerpipe' target='_blank'>Boilerpipe<\/a>, <a href='http://stanfordnlp.github.io/CoreNLP/' target='_blank'>Stanford CoreNLP<\/a><\/li> <li>Front-end: <a href='http://getbootstrap.com' target='_blank'>Bootstrap<\/a>, <a href='http://glyphicons.com/' target='_blank'>Glyphicons<\/a>, <a href='https://d3js.org' target='_blank'>d3<\/a><\/li>  <\/ul> <br> <p><b>LIZENZ<\/b> <\/p> <a href='https://creativecommons.org/licenses/by-nc-sa/4.0/deed.de' rel='license'><img alt='Creative Commons License' src='https://i.creativecommons.org/l/by-nc-sa/4.0/88x31.png' style='border-width:0'><\/a><br>FLAIR ist lizensiert unter einer <a href='https://creativecommons.org/licenses/by-nc-sa/4.0/deed.de' rel='license'>Creative Commons Namensnennung - Nicht-kommerziell - Weitergabe unter gleichen Bedingungen 4.0 Internationalen Lizenz<\/a>." , new com_google_gwt_safehtml_shared_OnlyToBeUsedInGeneratedCodeStringBlessedAsSafeHtml_OnlyToBeUsedInGeneratedCodeStringBlessedAsSafeHtml__Ljava_lang_String_2V(com_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_$build_1f_1ModalBody19__Lcom_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_2Lorg_gwtbootstrap3_client_ui_ModalBody_2_com_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_$build_1f_1HTML20__Lcom_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_2Lcom_google_gwt_user_client_ui_HTML_2_com_flair_client_views_MainViewport_1MainViewportUiBinderImpl_1TemplateImpl_$html5__Lcom_flair_client_views_MainViewport_1MainViewportUiBinderImpl_1TemplateImpl_2Lcom_google_gwt_safehtml_shared_SafeHtml_2_sb_0_0_0.java_lang_AbstractStringBuilder_string)).com_google_gwt_safehtml_shared_OnlyToBeUsedInGeneratedCodeStringBlessedAsSafeHtml_html) , com_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_$build_1f_1ModalBody19__Lcom_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_2Lorg_gwtbootstrap3_client_ui_ModalBody_2_com_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_$build_1f_1HTML20__Lcom_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_2Lcom_google_gwt_user_client_ui_HTML_2_f_HTML20_0_0)) , com_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_$build_1f_1ModalBody19__Lcom_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_2Lorg_gwtbootstrap3_client_ui_ModalBody_2_f_ModalBody19_1)) , org_gwtbootstrap3_client_ui_Modal_$add__Lorg_gwtbootstrap3_client_ui_Modal_2Lcom_google_gwt_user_client_ui_Widget_2V(com_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_$build_1mdlAboutDeUI__Lcom_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_2Lorg_gwtbootstrap3_client_ui_Modal_2_mdlAboutDeUI_0, (com_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_$build_1f_1ModalFooter21__Lcom_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_2Lorg_gwtbootstrap3_client_ui_ModalFooter_2_f_ModalFooter21_1 = new org_gwtbootstrap3_client_ui_ModalFooter_ModalFooter__V , com_google_gwt_user_client_ui_FlowPanel_$add__Lcom_google_gwt_user_client_ui_FlowPanel_2Lcom_google_gwt_user_client_ui_Widget_2V(com_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_$build_1f_1ModalFooter21__Lcom_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_2Lorg_gwtbootstrap3_client_ui_ModalFooter_2_f_ModalFooter21_1, (com_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_$build_1f_1ModalFooter21__Lcom_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_2Lorg_gwtbootstrap3_client_ui_ModalFooter_2_com_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_$build_1f_1Button22__Lcom_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_2Lorg_gwtbootstrap3_client_ui_Button_2_f_Button22_0_0 = new org_gwtbootstrap3_client_ui_Button_Button__V , org_gwtbootstrap3_client_ui_base_mixin_IconTextMixin_$setText__Lorg_gwtbootstrap3_client_ui_base_mixin_IconTextMixin_2Ljava_lang_String_2V(com_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_$build_1f_1ModalFooter21__Lcom_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_2Lorg_gwtbootstrap3_client_ui_ModalFooter_2_com_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_$build_1f_1Button22__Lcom_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_2Lorg_gwtbootstrap3_client_ui_Button_2_f_Button22_0_0.org_gwtbootstrap3_client_ui_base_button_AbstractIconButton_iconTextMixin, 'Schliessen') , org_gwtbootstrap3_client_ui_base_button_AbstractButton_$setDataDismiss__Lorg_gwtbootstrap3_client_ui_base_button_AbstractButton_2Lorg_gwtbootstrap3_client_ui_constants_ButtonDismiss_2V(com_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_$build_1f_1ModalFooter21__Lcom_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_2Lorg_gwtbootstrap3_client_ui_ModalFooter_2_com_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_$build_1f_1Button22__Lcom_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_2Lorg_gwtbootstrap3_client_ui_Button_2_f_Button22_0_0, org_gwtbootstrap3_client_ui_constants_ButtonDismiss_MODAL) , org_gwtbootstrap3_client_ui_base_helper_StyleHelper_addUniqueEnumStyleName__Lcom_google_gwt_user_client_ui_UIObject_2Ljava_lang_Class_2Lcom_google_gwt_dom_client_Style$HasCssName_2V(com_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_$build_1f_1ModalFooter21__Lcom_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_2Lorg_gwtbootstrap3_client_ui_ModalFooter_2_com_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_$build_1f_1Button22__Lcom_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_2Lorg_gwtbootstrap3_client_ui_Button_2_f_Button22_0_0, com_google_gwt_lang_ClassLiteralHolder_Lorg_1gwtbootstrap3_1client_1ui_1constants_1ButtonType_12_1classLit, org_gwtbootstrap3_client_ui_constants_ButtonType_PRIMARY) , com_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_$build_1f_1ModalFooter21__Lcom_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_2Lorg_gwtbootstrap3_client_ui_ModalFooter_2_com_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_$build_1f_1Button22__Lcom_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_2Lorg_gwtbootstrap3_client_ui_Button_2_f_Button22_0_0)) , com_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_$build_1f_1ModalFooter21__Lcom_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_2Lorg_gwtbootstrap3_client_ui_ModalFooter_2_f_ModalFooter21_1)) , com_google_gwt_user_client_ui_UIObject_setStyleName__Lcom_google_gwt_dom_client_Element_2Ljava_lang_String_2ZV(com_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_$build_1mdlAboutDeUI__Lcom_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_2Lorg_gwtbootstrap3_client_ui_Modal_2_mdlAboutDeUI_0.com_google_gwt_user_client_ui_UIObject_element, 'fade', true) , com_google_gwt_dom_client_Element_$setAttribute__Lcom_google_gwt_dom_client_Element_2Ljava_lang_String_2Ljava_lang_String_2V(com_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_$build_1mdlAboutDeUI__Lcom_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_2Lorg_gwtbootstrap3_client_ui_Modal_2_mdlAboutDeUI_0.com_google_gwt_user_client_ui_UIObject_element, $intern_23, $intern_7) , com_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_$build_1mdlAboutDeUI__Lcom_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_2Lorg_gwtbootstrap3_client_ui_Modal_2_mdlAboutDeUI_0.com_google_gwt_user_client_ui_UIObject_element.setAttribute('tabindex', '-1') , undefined , org_gwtbootstrap3_client_ui_ModalHeader_$setClosable__Lorg_gwtbootstrap3_client_ui_ModalHeader_2ZV(com_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_$build_1mdlAboutDeUI__Lcom_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_2Lorg_gwtbootstrap3_client_ui_Modal_2_mdlAboutDeUI_0.org_gwtbootstrap3_client_ui_Modal_header) , org_gwtbootstrap3_client_ui_base_mixin_IdMixin_$setId__Lorg_gwtbootstrap3_client_ui_base_mixin_IdMixin_2Ljava_lang_String_2V(com_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_$build_1mdlAboutDeUI__Lcom_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_2Lorg_gwtbootstrap3_client_ui_Modal_2_mdlAboutDeUI_0.org_gwtbootstrap3_client_ui_base_ComplexWidget_idMixin, 'modalAboutDe') , org_gwtbootstrap3_client_ui_ModalHeader_$setTitle__Lorg_gwtbootstrap3_client_ui_ModalHeader_2Ljava_lang_String_2V(com_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_$build_1mdlAboutDeUI__Lcom_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_2Lorg_gwtbootstrap3_client_ui_Modal_2_mdlAboutDeUI_0.org_gwtbootstrap3_client_ui_Modal_header, $intern_20) , org_gwtbootstrap3_client_ui_Modal_$setDataBackdrop__Lorg_gwtbootstrap3_client_ui_Modal_2Lorg_gwtbootstrap3_client_ui_constants_ModalBackdrop_2V(com_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_$build_1mdlAboutDeUI__Lcom_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_2Lorg_gwtbootstrap3_client_ui_Modal_2_mdlAboutDeUI_0, org_gwtbootstrap3_client_ui_constants_ModalBackdrop_STATIC) , this$static.com_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_owner.com_flair_client_views_MainViewport_mdlAboutDeUI = com_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_$build_1mdlAboutDeUI__Lcom_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_2Lorg_gwtbootstrap3_client_ui_Modal_2_mdlAboutDeUI_0 , com_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_$build_1mdlAboutDeUI__Lcom_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_2Lorg_gwtbootstrap3_client_ui_Modal_2_mdlAboutDeUI_0), com_google_gwt_uibinder_client_LazyDomElement_$get__Lcom_google_gwt_uibinder_client_LazyDomElement_2Lcom_google_gwt_dom_client_Element_2(this$static.com_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_domId3Element));
  return f_HTMLPanel1;
}

function com_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_MainViewport_1MainViewportUiBinderImpl$Widgets__Lcom_flair_client_views_MainViewport_1MainViewportUiBinderImpl_2Lcom_flair_client_views_MainViewport_2V(owner){
  this.com_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_owner = owner;
  this.com_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_domId0 = com_google_gwt_dom_client_Document_$createUniqueId__Lcom_google_gwt_dom_client_Document_2Ljava_lang_String_2($doc);
  this.com_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_domId1 = com_google_gwt_dom_client_Document_$createUniqueId__Lcom_google_gwt_dom_client_Document_2Ljava_lang_String_2($doc);
  this.com_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_domId2 = com_google_gwt_dom_client_Document_$createUniqueId__Lcom_google_gwt_dom_client_Document_2Ljava_lang_String_2($doc);
  this.com_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_domId3 = com_google_gwt_dom_client_Document_$createUniqueId__Lcom_google_gwt_dom_client_Document_2Ljava_lang_String_2($doc);
  this.com_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_domId0Element = new com_google_gwt_uibinder_client_LazyDomElement_LazyDomElement__Ljava_lang_String_2V(this.com_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_domId0);
  this.com_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_domId1Element = new com_google_gwt_uibinder_client_LazyDomElement_LazyDomElement__Ljava_lang_String_2V(this.com_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_domId1);
  this.com_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_domId2Element = new com_google_gwt_uibinder_client_LazyDomElement_LazyDomElement__Ljava_lang_String_2V(this.com_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_domId2);
  this.com_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_domId3Element = new com_google_gwt_uibinder_client_LazyDomElement_LazyDomElement__Ljava_lang_String_2V(this.com_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_domId3);
}

com_google_gwt_lang_Runtime_defineClass__Lcom_google_gwt_core_client_JavaScriptObject_2Lcom_google_gwt_core_client_JavaScriptObject_2Lcom_google_gwt_core_client_JavaScriptObject_2V(144, 1, {}, com_flair_client_views_MainViewport_1MainViewportUiBinderImpl$Widgets_MainViewport_1MainViewportUiBinderImpl$Widgets__Lcom_flair_client_views_MainViewport_1MainViewportUiBinderImpl_2Lcom_flair_client_views_MainViewport_2V);
var com_google_gwt_lang_ClassLiteralHolder_Lcom_1flair_1client_1views_1MainViewport_11MainViewportUiBinderImpl$Widgets_12_1classLit = java_lang_Class_createForClass__Ljava_lang_String_2Ljava_lang_String_2Lcom_google_gwt_core_client_JavaScriptObject_2Ljava_lang_Class_2Ljava_lang_Class_2($intern_17, 'MainViewport_MainViewportUiBinderImpl/Widgets', 144);
function com_flair_client_views_MainViewport_1MainViewportUiBinderImpl_1TemplateImpl_$html2__Lcom_flair_client_views_MainViewport_1MainViewportUiBinderImpl_1TemplateImpl_2Lcom_google_gwt_safehtml_shared_SafeHtml_2Lcom_google_gwt_safehtml_shared_SafeHtml_2Lcom_google_gwt_safehtml_shared_SafeHtml_2Lcom_google_gwt_safehtml_shared_SafeHtml_2Lcom_google_gwt_safehtml_shared_SafeHtml_2(arg0, arg1, arg2, arg3){
  var sb;
  sb = new java_lang_StringBuilder_StringBuilder__V;
  sb.java_lang_AbstractStringBuilder_string += "<center> <h3 class='modal-title'>";
  java_lang_StringBuilder_$append__Ljava_lang_StringBuilder_2Ljava_lang_String_2Ljava_lang_StringBuilder_2(sb, arg0.com_google_gwt_safehtml_shared_SafeHtmlString_html);
  sb.java_lang_AbstractStringBuilder_string += "<\/h3> <p> <span style='color:darkorange'> <b>";
  java_lang_StringBuilder_$append__Ljava_lang_StringBuilder_2Ljava_lang_String_2Ljava_lang_StringBuilder_2(sb, arg1.com_google_gwt_safehtml_shared_SafeHtmlString_html);
  sb.java_lang_AbstractStringBuilder_string += "<\/b> <\/span> <a href='http://sfs.uni-tuebingen.de/~mchnkina/' target='_blank'>Maria Chinkina<\/a> <a href='mailto:maria.chinkina@uni-tuebingen.de?Subject=FLAIR%20tool' target='_top' title='write to Maria Chinkina'> <span class='glyphicon glyphicon-envelope'><\/span> <\/a> &amp; <a href='http://sfs.uni-tuebingen.de/~mkannan/' target='_blank'>Madeeswaran Kannan<\/a> <a href='mailto:mkannan@sfs.uni-tuebingen.de?Subject=FLAIR%20tool' target='_top' title='write to Madeeswaran Kannan'> <span class='glyphicon glyphicon-envelope'><\/span> <\/a> ";
  java_lang_StringBuilder_$append__Ljava_lang_StringBuilder_2Ljava_lang_String_2Ljava_lang_StringBuilder_2(sb, arg2.com_google_gwt_safehtml_shared_SafeHtmlString_html);
  sb.java_lang_AbstractStringBuilder_string += " <a href='http://sfs.uni-tuebingen.de/~dm/' target='_blank'>Prof. Dr. Detmar Meurers<\/a> <br> <span style='color:darkorange'>@<\/span> ";
  java_lang_StringBuilder_$append__Ljava_lang_StringBuilder_2Ljava_lang_String_2Ljava_lang_StringBuilder_2(sb, arg3.com_google_gwt_safehtml_shared_SafeHtmlString_html);
  sb.java_lang_AbstractStringBuilder_string += " <span style='color:darkorange'>|<\/span> 2015-2017 <br> Version 2.0 <\/p> <\/center>";
  return new com_google_gwt_safehtml_shared_OnlyToBeUsedInGeneratedCodeStringBlessedAsSafeHtml_OnlyToBeUsedInGeneratedCodeStringBlessedAsSafeHtml__Ljava_lang_String_2V(sb.java_lang_AbstractStringBuilder_string);
}

function com_flair_client_views_MainViewport_1MainViewportUiBinderImpl_1TemplateImpl_$html6__Lcom_flair_client_views_MainViewport_1MainViewportUiBinderImpl_1TemplateImpl_2Ljava_lang_String_2Ljava_lang_String_2Ljava_lang_String_2Ljava_lang_String_2Lcom_google_gwt_safehtml_shared_SafeHtml_2(arg0, arg1, arg2, arg3){
  var sb;
  sb = new java_lang_StringBuilder_StringBuilder__V;
  sb.java_lang_AbstractStringBuilder_string += "### Main Navbar <span id='";
  java_lang_StringBuilder_$append__Ljava_lang_StringBuilder_2Ljava_lang_String_2Ljava_lang_StringBuilder_2(sb, com_google_gwt_safehtml_shared_SafeHtmlUtils_htmlEscape__Ljava_lang_String_2Ljava_lang_String_2(arg0));
  sb.java_lang_AbstractStringBuilder_string += "'><\/span>  ### Main Panel <span id='";
  java_lang_StringBuilder_$append__Ljava_lang_StringBuilder_2Ljava_lang_String_2Ljava_lang_StringBuilder_2(sb, com_google_gwt_safehtml_shared_SafeHtmlUtils_htmlEscape__Ljava_lang_String_2Ljava_lang_String_2(arg1));
  sb.java_lang_AbstractStringBuilder_string += "'><\/span> ### About Modals <span id='";
  java_lang_StringBuilder_$append__Ljava_lang_StringBuilder_2Ljava_lang_String_2Ljava_lang_StringBuilder_2(sb, com_google_gwt_safehtml_shared_SafeHtmlUtils_htmlEscape__Ljava_lang_String_2Ljava_lang_String_2(arg2));
  sb.java_lang_AbstractStringBuilder_string += "'><\/span>  <span id='";
  java_lang_StringBuilder_$append__Ljava_lang_StringBuilder_2Ljava_lang_String_2Ljava_lang_StringBuilder_2(sb, com_google_gwt_safehtml_shared_SafeHtmlUtils_htmlEscape__Ljava_lang_String_2Ljava_lang_String_2(arg3));
  sb.java_lang_AbstractStringBuilder_string += "'><\/span>";
  return new com_google_gwt_safehtml_shared_OnlyToBeUsedInGeneratedCodeStringBlessedAsSafeHtml_OnlyToBeUsedInGeneratedCodeStringBlessedAsSafeHtml__Ljava_lang_String_2V(sb.java_lang_AbstractStringBuilder_string);
}

function com_google_gwt_core_client_Duration_Duration__V(){
  this.com_google_gwt_core_client_Duration_start = com_google_gwt_core_client_JsDate_now__D();
}

com_google_gwt_lang_Runtime_defineClass__Lcom_google_gwt_core_client_JavaScriptObject_2Lcom_google_gwt_core_client_JavaScriptObject_2Lcom_google_gwt_core_client_JavaScriptObject_2V(138, 1, {}, com_google_gwt_core_client_Duration_Duration__V);
_.com_google_gwt_core_client_Duration_start = 0;
var com_google_gwt_lang_ClassLiteralHolder_Lcom_1google_1gwt_1core_1client_1Duration_12_1classLit = java_lang_Class_createForClass__Ljava_lang_String_2Ljava_lang_String_2Lcom_google_gwt_core_client_JavaScriptObject_2Ljava_lang_Class_2Ljava_lang_Class_2($intern_24, 'Duration', 138);
function java_lang_Throwable_$addSuppressed__Ljava_lang_Throwable_2Ljava_lang_Throwable_2V(this$static, exception){
  javaemul_internal_InternalPreconditions_checkCriticalNotNull__Ljava_lang_Object_2Ljava_lang_Object_2V(exception);
  javaemul_internal_InternalPreconditions_checkCriticalArgument__ZLjava_lang_Object_2V(exception != this$static);
  if (this$static.java_lang_Throwable_disableSuppression) {
    return;
  }
  this$static.java_lang_Throwable_suppressedExceptions == null?(this$static.java_lang_Throwable_suppressedExceptions = com_google_gwt_lang_Array_stampJavaTypeInfo__Ljava_lang_Class_2Lcom_google_gwt_core_client_JavaScriptObject_2Lcom_google_gwt_core_client_JavaScriptObject_2ILjava_lang_Object_2Ljava_lang_Object_2(com_google_gwt_lang_Array_getClassLiteralForArray__Ljava_lang_Class_2ILjava_lang_Class_2(com_google_gwt_lang_ClassLiteralHolder_Ljava_1lang_1Throwable_12_1classLit, 1), {3:1}, 6, 0, [exception])):(this$static.java_lang_Throwable_suppressedExceptions[this$static.java_lang_Throwable_suppressedExceptions.length] = exception);
}

function java_lang_Throwable_$fillInStackTrace__Ljava_lang_Throwable_2Ljava_lang_Throwable_2(this$static){
  this$static.java_lang_Throwable_writetableStackTrace && this$static.java_lang_Throwable_backingJsObject !== $intern_25 && this$static.private$java_lang_Throwable$initializeBackingError__V();
  return this$static;
}

function java_lang_Throwable_$setBackingJsObject__Ljava_lang_Throwable_2Ljava_lang_Object_2V(this$static, backingJsObject){
  this$static.java_lang_Throwable_backingJsObject = backingJsObject;
  backingJsObject != null && javaemul_internal_JsUtils_setPropertySafe__Ljava_lang_Object_2Ljava_lang_String_2Ljava_lang_Object_2V(backingJsObject, $intern_26, this$static);
}

function java_lang_Throwable_$toString__Ljava_lang_Throwable_2Ljava_lang_String_2Ljava_lang_String_2(this$static, message){
  var className;
  className = java_lang_Class_$getName__Ljava_lang_Class_2Ljava_lang_String_2(this$static.java_lang_Object__1_1_1clazz);
  return message == null?className:className + ': ' + message;
}

function java_lang_Throwable_Throwable__Ljava_lang_String_2Ljava_lang_Throwable_2V(message){
  this.java_lang_Throwable_detailMessage = message;
  java_lang_Throwable_$fillInStackTrace__Ljava_lang_Throwable_2Ljava_lang_Throwable_2(this);
  this.private$java_lang_Throwable$initializeBackingError__V();
}

function java_lang_Throwable_fixIE__Ljava_lang_Object_2Ljava_lang_Object_2(e){
  if (!('stack' in e)) {
    try {
      throw e;
    }
     catch (ignored) {
    }
  }
  return e;
}

com_google_gwt_lang_Runtime_defineClass__Lcom_google_gwt_core_client_JavaScriptObject_2Lcom_google_gwt_core_client_JavaScriptObject_2Lcom_google_gwt_core_client_JavaScriptObject_2V(6, 1, $intern_27);
_.package_private$java_lang$createError__Ljava_lang_String_2Ljava_lang_Object_2 = function java_lang_Throwable_createError__Ljava_lang_String_2Ljava_lang_Object_2(msg){
  return new $wnd.Error(msg);
}
;
_.getMessage__Ljava_lang_String_2 = function java_lang_Throwable_getMessage__Ljava_lang_String_2(){
  return this.java_lang_Throwable_detailMessage;
}
;
_.private$java_lang_Throwable$initializeBackingError__V = function java_lang_Throwable_initializeBackingError__V(){
  var className, errorMessage, message;
  message = this.java_lang_Throwable_detailMessage == null?null:this.java_lang_Throwable_detailMessage.replace(new $wnd.RegExp('\n', 'g'), ' ');
  errorMessage = (className = java_lang_Class_$getName__Ljava_lang_Class_2Ljava_lang_String_2(this.java_lang_Object__1_1_1clazz) , message == null?className:className + ': ' + message);
  java_lang_Throwable_$setBackingJsObject__Ljava_lang_Throwable_2Ljava_lang_Object_2V(this, java_lang_Throwable_fixIE__Ljava_lang_Object_2Ljava_lang_Object_2(this.package_private$java_lang$createError__Ljava_lang_String_2Ljava_lang_Object_2(errorMessage)));
  com_google_gwt_core_client_impl_StackTraceCreator_captureStackTrace__Ljava_lang_Object_2V(this);
}
;
_.toString__Ljava_lang_String_2 = function java_lang_Throwable_toString__Ljava_lang_String_2(){
  return java_lang_Throwable_$toString__Ljava_lang_Throwable_2Ljava_lang_String_2Ljava_lang_String_2(this, this.getMessage__Ljava_lang_String_2());
}
;
_.java_lang_Throwable_backingJsObject = $intern_25;
_.java_lang_Throwable_disableSuppression = false;
_.java_lang_Throwable_writetableStackTrace = true;
var com_google_gwt_lang_ClassLiteralHolder_Ljava_1lang_1Throwable_12_1classLit = java_lang_Class_createForClass__Ljava_lang_String_2Ljava_lang_String_2Lcom_google_gwt_core_client_JavaScriptObject_2Ljava_lang_Class_2Ljava_lang_Class_2($intern_2, 'Throwable', 6);
com_google_gwt_lang_Runtime_defineClass__Lcom_google_gwt_core_client_JavaScriptObject_2Lcom_google_gwt_core_client_JavaScriptObject_2Lcom_google_gwt_core_client_JavaScriptObject_2V(48, 6, $intern_27);
var com_google_gwt_lang_ClassLiteralHolder_Ljava_1lang_1Exception_12_1classLit = java_lang_Class_createForClass__Ljava_lang_String_2Ljava_lang_String_2Lcom_google_gwt_core_client_JavaScriptObject_2Ljava_lang_Class_2Ljava_lang_Class_2($intern_2, 'Exception', 48);
function java_lang_RuntimeException_RuntimeException__V(){
  java_lang_Throwable_$fillInStackTrace__Ljava_lang_Throwable_2Ljava_lang_Throwable_2(this);
  this.private$java_lang_Throwable$initializeBackingError__V();
}

function java_lang_RuntimeException_RuntimeException__Ljava_lang_String_2V(message){
  this.java_lang_Throwable_detailMessage = message;
  java_lang_Throwable_$fillInStackTrace__Ljava_lang_Throwable_2Ljava_lang_Throwable_2(this);
  this.private$java_lang_Throwable$initializeBackingError__V();
}

function java_lang_RuntimeException_RuntimeException__Ljava_lang_String_2Ljava_lang_Throwable_2V(message){
  java_lang_Throwable_Throwable__Ljava_lang_String_2Ljava_lang_Throwable_2V.call(this, message);
}

com_google_gwt_lang_Runtime_defineClass__Lcom_google_gwt_core_client_JavaScriptObject_2Lcom_google_gwt_core_client_JavaScriptObject_2Lcom_google_gwt_core_client_JavaScriptObject_2V(10, 48, $intern_27, java_lang_RuntimeException_RuntimeException__Ljava_lang_String_2V);
var com_google_gwt_lang_ClassLiteralHolder_Ljava_1lang_1RuntimeException_12_1classLit = java_lang_Class_createForClass__Ljava_lang_String_2Ljava_lang_String_2Lcom_google_gwt_core_client_JavaScriptObject_2Ljava_lang_Class_2Ljava_lang_Class_2($intern_2, 'RuntimeException', 10);
com_google_gwt_lang_Runtime_defineClass__Lcom_google_gwt_core_client_JavaScriptObject_2Lcom_google_gwt_core_client_JavaScriptObject_2Lcom_google_gwt_core_client_JavaScriptObject_2V(70, 10, $intern_27);
var com_google_gwt_lang_ClassLiteralHolder_Ljava_1lang_1JsException_12_1classLit = java_lang_Class_createForClass__Ljava_lang_String_2Ljava_lang_String_2Lcom_google_gwt_core_client_JavaScriptObject_2Ljava_lang_Class_2Ljava_lang_Class_2($intern_2, 'JsException', 70);
com_google_gwt_lang_Runtime_defineClass__Lcom_google_gwt_core_client_JavaScriptObject_2Lcom_google_gwt_core_client_JavaScriptObject_2Lcom_google_gwt_core_client_JavaScriptObject_2V(112, 70, $intern_27);
var com_google_gwt_lang_ClassLiteralHolder_Lcom_1google_1gwt_1core_1client_1impl_1JavaScriptExceptionBase_12_1classLit = java_lang_Class_createForClass__Ljava_lang_String_2Ljava_lang_String_2Lcom_google_gwt_core_client_JavaScriptObject_2Ljava_lang_Class_2Ljava_lang_Class_2($intern_28, 'JavaScriptExceptionBase', 112);
function com_google_gwt_core_client_JavaScriptException_$clinit__V(){
  com_google_gwt_core_client_JavaScriptException_$clinit__V = com_google_gwt_lang_Runtime_emptyMethod__V;
  com_google_gwt_core_client_JavaScriptException_NOT_1SET = new java_lang_Object_Object__V;
}

function com_google_gwt_core_client_JavaScriptException_$ensureInit__Lcom_google_gwt_core_client_JavaScriptException_2V(this$static){
  var exception;
  if (this$static.com_google_gwt_core_client_JavaScriptException_message == null) {
    exception = com_google_gwt_lang_Cast_maskUndefined__Ljava_lang_Object_2Ljava_lang_Object_2(this$static.com_google_gwt_core_client_JavaScriptException_e) === com_google_gwt_lang_Cast_maskUndefined__Ljava_lang_Object_2Ljava_lang_Object_2(com_google_gwt_core_client_JavaScriptException_NOT_1SET)?null:this$static.com_google_gwt_core_client_JavaScriptException_e;
    this$static.com_google_gwt_core_client_JavaScriptException_name = exception == null?$intern_29:com_google_gwt_lang_Cast_instanceOfJso__Ljava_lang_Object_2Z(exception)?com_google_gwt_core_client_JavaScriptException_getExceptionName0__Lcom_google_gwt_core_client_JavaScriptObject_2Ljava_lang_String_2(com_google_gwt_lang_Cast_castToJso__Ljava_lang_Object_2Ljava_lang_Object_2(exception)):com_google_gwt_lang_Cast_instanceOfString__Ljava_lang_Object_2Z(exception)?'String':java_lang_Class_$getName__Ljava_lang_Class_2Ljava_lang_String_2(java_lang_Object_getClass_1_1Ljava_1lang_1Class_1_1_1devirtual$__Ljava_lang_Object_2Ljava_lang_Class_2(exception));
    this$static.com_google_gwt_core_client_JavaScriptException_description = this$static.com_google_gwt_core_client_JavaScriptException_description + ': ' + (com_google_gwt_lang_Cast_instanceOfJso__Ljava_lang_Object_2Z(exception)?com_google_gwt_core_client_JavaScriptException_getExceptionDescription0__Lcom_google_gwt_core_client_JavaScriptObject_2Ljava_lang_String_2(com_google_gwt_lang_Cast_castToJso__Ljava_lang_Object_2Ljava_lang_Object_2(exception)):exception + '');
    this$static.com_google_gwt_core_client_JavaScriptException_message = '(' + this$static.com_google_gwt_core_client_JavaScriptException_name + ') ' + this$static.com_google_gwt_core_client_JavaScriptException_description;
  }
}

function com_google_gwt_core_client_JavaScriptException_JavaScriptException__Ljava_lang_Object_2V(e){
  com_google_gwt_core_client_JavaScriptException_$clinit__V();
  java_lang_Throwable_$fillInStackTrace__Ljava_lang_Throwable_2Ljava_lang_Throwable_2(this);
  this.java_lang_Throwable_backingJsObject = e;
  e != null && javaemul_internal_JsUtils_setPropertySafe__Ljava_lang_Object_2Ljava_lang_String_2Ljava_lang_Object_2V(e, $intern_26, this);
  this.java_lang_Throwable_detailMessage = e == null?$intern_29:com_google_gwt_lang_Runtime_toString__Ljava_lang_Object_2Ljava_lang_String_2(e);
  this.com_google_gwt_core_client_JavaScriptException_description = '';
  this.com_google_gwt_core_client_JavaScriptException_e = e;
  this.com_google_gwt_core_client_JavaScriptException_description = '';
}

function com_google_gwt_core_client_JavaScriptException_getExceptionDescription0__Lcom_google_gwt_core_client_JavaScriptObject_2Ljava_lang_String_2(e){
  return e == null?null:e.message;
}

function com_google_gwt_core_client_JavaScriptException_getExceptionName0__Lcom_google_gwt_core_client_JavaScriptObject_2Ljava_lang_String_2(e){
  return e == null?null:e.name;
}

com_google_gwt_lang_Runtime_defineClass__Lcom_google_gwt_core_client_JavaScriptObject_2Lcom_google_gwt_core_client_JavaScriptObject_2Lcom_google_gwt_core_client_JavaScriptObject_2V(40, 112, {40:1, 3:1, 6:1}, com_google_gwt_core_client_JavaScriptException_JavaScriptException__Ljava_lang_Object_2V);
_.getMessage__Ljava_lang_String_2 = function com_google_gwt_core_client_JavaScriptException_getMessage__Ljava_lang_String_2(){
  com_google_gwt_core_client_JavaScriptException_$ensureInit__Lcom_google_gwt_core_client_JavaScriptException_2V(this);
  return this.com_google_gwt_core_client_JavaScriptException_message;
}
;
_.getThrown__Ljava_lang_Object_2 = function com_google_gwt_core_client_JavaScriptException_getThrown__Ljava_lang_Object_2(){
  return com_google_gwt_lang_Cast_maskUndefined__Ljava_lang_Object_2Ljava_lang_Object_2(this.com_google_gwt_core_client_JavaScriptException_e) === com_google_gwt_lang_Cast_maskUndefined__Ljava_lang_Object_2Ljava_lang_Object_2(com_google_gwt_core_client_JavaScriptException_NOT_1SET)?null:this.com_google_gwt_core_client_JavaScriptException_e;
}
;
var com_google_gwt_core_client_JavaScriptException_NOT_1SET;
var com_google_gwt_lang_ClassLiteralHolder_Lcom_1google_1gwt_1core_1client_1JavaScriptException_12_1classLit = java_lang_Class_createForClass__Ljava_lang_String_2Ljava_lang_String_2Lcom_google_gwt_core_client_JavaScriptObject_2Ljava_lang_Class_2Ljava_lang_Class_2($intern_24, 'JavaScriptException', 40);
var com_google_gwt_lang_ClassLiteralHolder_Lcom_1google_1gwt_1core_1client_1JavaScriptObject_12_1classLit = java_lang_Class_createForClass__Ljava_lang_String_2Ljava_lang_String_2Lcom_google_gwt_core_client_JavaScriptObject_2Ljava_lang_Class_2Ljava_lang_Class_2($intern_24, 'JavaScriptObject$', 0);
function com_google_gwt_core_client_JsDate_now__D(){
  if (Date.now) {
    return Date.now();
  }
  return (new Date).getTime();
}

com_google_gwt_lang_Runtime_defineClass__Lcom_google_gwt_core_client_JavaScriptObject_2Lcom_google_gwt_core_client_JavaScriptObject_2Lcom_google_gwt_core_client_JavaScriptObject_2V(208, 1, {});
var com_google_gwt_lang_ClassLiteralHolder_Lcom_1google_1gwt_1core_1client_1Scheduler_12_1classLit = java_lang_Class_createForClass__Ljava_lang_String_2Ljava_lang_String_2Lcom_google_gwt_core_client_JavaScriptObject_2Ljava_lang_Class_2Ljava_lang_Class_2($intern_24, 'Scheduler', 208);
function com_google_gwt_core_client_ScriptInjector_$clinit__V(){
  com_google_gwt_core_client_ScriptInjector_$clinit__V = com_google_gwt_lang_Runtime_emptyMethod__V;
  com_google_gwt_core_client_ScriptInjector_TOP_1WINDOW = $wnd;
}

function com_google_gwt_core_client_ScriptInjector_nativeSetText__Lcom_google_gwt_core_client_JavaScriptObject_2Ljava_lang_String_2V(element, scriptBody){
  com_google_gwt_core_client_ScriptInjector_$clinit__V();
  element.text = scriptBody;
}

var com_google_gwt_core_client_ScriptInjector_TOP_1WINDOW;
function com_google_gwt_core_client_ScriptInjector$FromString_$inject__Lcom_google_gwt_core_client_ScriptInjector$FromString_2Lcom_google_gwt_core_client_JavaScriptObject_2(this$static){
  var doc, scriptElement, wnd;
  wnd = !this$static.com_google_gwt_core_client_ScriptInjector$FromString_window?(com_google_gwt_core_client_ScriptInjector_$clinit__V() , window):this$static.com_google_gwt_core_client_ScriptInjector$FromString_window;
  doc = (com_google_gwt_core_client_ScriptInjector_$clinit__V() , wnd.document);
  scriptElement = doc.createElement('script');
  com_google_gwt_core_client_ScriptInjector_nativeSetText__Lcom_google_gwt_core_client_JavaScriptObject_2Ljava_lang_String_2V(scriptElement, this$static.com_google_gwt_core_client_ScriptInjector$FromString_scriptBody);
  (doc.head || doc.getElementsByTagName('head')[0]).appendChild(scriptElement);
  this$static.com_google_gwt_core_client_ScriptInjector$FromString_removeTag && (scriptElement.parentNode.removeChild(scriptElement) , undefined);
  return scriptElement;
}

function com_google_gwt_core_client_ScriptInjector$FromString_$setWindow__Lcom_google_gwt_core_client_ScriptInjector$FromString_2Lcom_google_gwt_core_client_JavaScriptObject_2Lcom_google_gwt_core_client_ScriptInjector$FromString_2(this$static, window_0){
  this$static.com_google_gwt_core_client_ScriptInjector$FromString_window = window_0;
  return this$static;
}

function com_google_gwt_core_client_ScriptInjector$FromString_ScriptInjector$FromString__Ljava_lang_String_2V(scriptBody){
  this.com_google_gwt_core_client_ScriptInjector$FromString_scriptBody = scriptBody;
}

com_google_gwt_lang_Runtime_defineClass__Lcom_google_gwt_core_client_JavaScriptObject_2Lcom_google_gwt_core_client_JavaScriptObject_2Lcom_google_gwt_core_client_JavaScriptObject_2V(84, 1, {}, com_google_gwt_core_client_ScriptInjector$FromString_ScriptInjector$FromString__Ljava_lang_String_2V);
_.com_google_gwt_core_client_ScriptInjector$FromString_removeTag = true;
var com_google_gwt_lang_ClassLiteralHolder_Lcom_1google_1gwt_1core_1client_1ScriptInjector$FromString_12_1classLit = java_lang_Class_createForClass__Ljava_lang_String_2Ljava_lang_String_2Lcom_google_gwt_core_client_JavaScriptObject_2Ljava_lang_Class_2Ljava_lang_Class_2($intern_24, 'ScriptInjector/FromString', 84);
function com_google_gwt_core_client_impl_Impl_$clinit__V(){
  com_google_gwt_core_client_impl_Impl_$clinit__V = com_google_gwt_lang_Runtime_emptyMethod__V;
  !!(com_google_gwt_core_client_impl_StackTraceCreator_$clinit__V() , com_google_gwt_core_client_impl_StackTraceCreator_collector);
}

function com_google_gwt_core_client_impl_Impl_apply__Ljava_lang_Object_2Ljava_lang_Object_2Ljava_lang_Object_2Ljava_lang_Object_2(jsFunction, thisObj, args){
  return jsFunction.apply(thisObj, args);
  var _;
}

function com_google_gwt_core_client_impl_Impl_enter__Z(){
  var now_0;
  if (com_google_gwt_core_client_impl_Impl_entryDepth != 0) {
    now_0 = com_google_gwt_core_client_JsDate_now__D();
    if (now_0 - com_google_gwt_core_client_impl_Impl_watchdogEntryDepthLastScheduled > 2000) {
      com_google_gwt_core_client_impl_Impl_watchdogEntryDepthLastScheduled = now_0;
      com_google_gwt_core_client_impl_Impl_watchdogEntryDepthTimerId = $wnd.setTimeout(com_google_gwt_core_client_impl_Impl_watchdogEntryDepthRun__V, 10);
    }
  }
  if (com_google_gwt_core_client_impl_Impl_entryDepth++ == 0) {
    com_google_gwt_core_client_impl_SchedulerImpl_$flushEntryCommands__Lcom_google_gwt_core_client_impl_SchedulerImpl_2V((com_google_gwt_core_client_impl_SchedulerImpl_$clinit__V() , com_google_gwt_core_client_impl_SchedulerImpl_INSTANCE));
    return true;
  }
  return false;
}

function com_google_gwt_core_client_impl_Impl_entry__Lcom_google_gwt_core_client_JavaScriptObject_2Lcom_google_gwt_core_client_JavaScriptObject_2(jsFunction){
  com_google_gwt_core_client_impl_Impl_$clinit__V();
  return function(){
    return com_google_gwt_core_client_impl_Impl_entry0__Ljava_lang_Object_2Ljava_lang_Object_2Ljava_lang_Object_2Ljava_lang_Object_2(jsFunction, this, arguments);
    var _;
  }
  ;
}

function com_google_gwt_core_client_impl_Impl_entry0__Ljava_lang_Object_2Ljava_lang_Object_2Ljava_lang_Object_2Ljava_lang_Object_2(jsFunction, thisObj, args){
  var initialEntry;
  initialEntry = com_google_gwt_core_client_impl_Impl_enter__Z();
  try {
    return com_google_gwt_core_client_impl_Impl_apply__Ljava_lang_Object_2Ljava_lang_Object_2Ljava_lang_Object_2Ljava_lang_Object_2(jsFunction, thisObj, args);
  }
   finally {
    com_google_gwt_core_client_impl_Impl_exit__ZV(initialEntry);
  }
}

function com_google_gwt_core_client_impl_Impl_exit__ZV(initialEntry){
  initialEntry && com_google_gwt_core_client_impl_SchedulerImpl_$flushFinallyCommands__Lcom_google_gwt_core_client_impl_SchedulerImpl_2V((com_google_gwt_core_client_impl_SchedulerImpl_$clinit__V() , com_google_gwt_core_client_impl_SchedulerImpl_INSTANCE));
  --com_google_gwt_core_client_impl_Impl_entryDepth;
  if (initialEntry) {
    if (com_google_gwt_core_client_impl_Impl_watchdogEntryDepthTimerId != -1) {
      com_google_gwt_core_client_impl_Impl_watchdogEntryDepthCancel__IV(com_google_gwt_core_client_impl_Impl_watchdogEntryDepthTimerId);
      com_google_gwt_core_client_impl_Impl_watchdogEntryDepthTimerId = -1;
    }
  }
}

function com_google_gwt_core_client_impl_Impl_reportToBrowser__Ljava_lang_Object_2V(e){
  com_google_gwt_core_client_impl_Impl_$clinit__V();
  $wnd.setTimeout(function(){
    throw e;
  }
  , 0);
}

function com_google_gwt_core_client_impl_Impl_watchdogEntryDepthCancel__IV(timerId){
  $wnd.clearTimeout(timerId);
}

function com_google_gwt_core_client_impl_Impl_watchdogEntryDepthRun__V(){
  com_google_gwt_core_client_impl_Impl_entryDepth != 0 && (com_google_gwt_core_client_impl_Impl_entryDepth = 0);
  com_google_gwt_core_client_impl_Impl_watchdogEntryDepthTimerId = -1;
}

var com_google_gwt_core_client_impl_Impl_entryDepth = 0, com_google_gwt_core_client_impl_Impl_watchdogEntryDepthLastScheduled = 0, com_google_gwt_core_client_impl_Impl_watchdogEntryDepthTimerId = -1;
function com_google_gwt_core_client_impl_SchedulerImpl_$clinit__V(){
  com_google_gwt_core_client_impl_SchedulerImpl_$clinit__V = com_google_gwt_lang_Runtime_emptyMethod__V;
  com_google_gwt_core_client_impl_SchedulerImpl_INSTANCE = new com_google_gwt_core_client_impl_SchedulerImpl_SchedulerImpl__V;
}

function com_google_gwt_core_client_impl_SchedulerImpl_$flushEntryCommands__Lcom_google_gwt_core_client_impl_SchedulerImpl_2V(this$static){
  var oldQueue, rescheduled;
  if (this$static.com_google_gwt_core_client_impl_SchedulerImpl_entryCommands) {
    rescheduled = null;
    do {
      oldQueue = this$static.com_google_gwt_core_client_impl_SchedulerImpl_entryCommands;
      this$static.com_google_gwt_core_client_impl_SchedulerImpl_entryCommands = null;
      rescheduled = com_google_gwt_core_client_impl_SchedulerImpl_runScheduledTasks__Lcom_google_gwt_core_client_JsArray_2Lcom_google_gwt_core_client_JsArray_2Lcom_google_gwt_core_client_JsArray_2(oldQueue, rescheduled);
    }
     while (this$static.com_google_gwt_core_client_impl_SchedulerImpl_entryCommands);
    this$static.com_google_gwt_core_client_impl_SchedulerImpl_entryCommands = rescheduled;
  }
}

function com_google_gwt_core_client_impl_SchedulerImpl_$flushFinallyCommands__Lcom_google_gwt_core_client_impl_SchedulerImpl_2V(this$static){
  var oldQueue, rescheduled;
  if (this$static.com_google_gwt_core_client_impl_SchedulerImpl_finallyCommands) {
    rescheduled = null;
    do {
      oldQueue = this$static.com_google_gwt_core_client_impl_SchedulerImpl_finallyCommands;
      this$static.com_google_gwt_core_client_impl_SchedulerImpl_finallyCommands = null;
      rescheduled = com_google_gwt_core_client_impl_SchedulerImpl_runScheduledTasks__Lcom_google_gwt_core_client_JsArray_2Lcom_google_gwt_core_client_JsArray_2Lcom_google_gwt_core_client_JsArray_2(oldQueue, rescheduled);
    }
     while (this$static.com_google_gwt_core_client_impl_SchedulerImpl_finallyCommands);
    this$static.com_google_gwt_core_client_impl_SchedulerImpl_finallyCommands = rescheduled;
  }
}

function com_google_gwt_core_client_impl_SchedulerImpl_$flushPostEventPumpCommands__Lcom_google_gwt_core_client_impl_SchedulerImpl_2V(this$static){
  var oldDeferred;
  if (this$static.com_google_gwt_core_client_impl_SchedulerImpl_deferredCommands) {
    oldDeferred = this$static.com_google_gwt_core_client_impl_SchedulerImpl_deferredCommands;
    this$static.com_google_gwt_core_client_impl_SchedulerImpl_deferredCommands = null;
    !this$static.com_google_gwt_core_client_impl_SchedulerImpl_incrementalCommands && (this$static.com_google_gwt_core_client_impl_SchedulerImpl_incrementalCommands = []);
    com_google_gwt_core_client_impl_SchedulerImpl_runScheduledTasks__Lcom_google_gwt_core_client_JsArray_2Lcom_google_gwt_core_client_JsArray_2Lcom_google_gwt_core_client_JsArray_2(oldDeferred, this$static.com_google_gwt_core_client_impl_SchedulerImpl_incrementalCommands);
  }
  !!this$static.com_google_gwt_core_client_impl_SchedulerImpl_incrementalCommands && (this$static.com_google_gwt_core_client_impl_SchedulerImpl_incrementalCommands = com_google_gwt_core_client_impl_SchedulerImpl_$runRepeatingTasks__Lcom_google_gwt_core_client_impl_SchedulerImpl_2Lcom_google_gwt_core_client_JsArray_2Lcom_google_gwt_core_client_JsArray_2(this$static.com_google_gwt_core_client_impl_SchedulerImpl_incrementalCommands));
}

function com_google_gwt_core_client_impl_SchedulerImpl_$isWorkQueued__Lcom_google_gwt_core_client_impl_SchedulerImpl_2Z(this$static){
  return !!this$static.com_google_gwt_core_client_impl_SchedulerImpl_deferredCommands || !!this$static.com_google_gwt_core_client_impl_SchedulerImpl_incrementalCommands;
}

function com_google_gwt_core_client_impl_SchedulerImpl_$maybeSchedulePostEventPumpCommands__Lcom_google_gwt_core_client_impl_SchedulerImpl_2V(this$static){
  if (!this$static.com_google_gwt_core_client_impl_SchedulerImpl_shouldBeRunning) {
    this$static.com_google_gwt_core_client_impl_SchedulerImpl_shouldBeRunning = true;
    !this$static.com_google_gwt_core_client_impl_SchedulerImpl_flusher && (this$static.com_google_gwt_core_client_impl_SchedulerImpl_flusher = new com_google_gwt_core_client_impl_SchedulerImpl$Flusher_SchedulerImpl$Flusher__Lcom_google_gwt_core_client_impl_SchedulerImpl_2V(this$static));
    com_google_gwt_core_client_impl_SchedulerImpl_scheduleFixedDelayImpl__Lcom_google_gwt_core_client_Scheduler$RepeatingCommand_2IV(this$static.com_google_gwt_core_client_impl_SchedulerImpl_flusher, 1);
    !this$static.com_google_gwt_core_client_impl_SchedulerImpl_rescue && (this$static.com_google_gwt_core_client_impl_SchedulerImpl_rescue = new com_google_gwt_core_client_impl_SchedulerImpl$Rescuer_SchedulerImpl$Rescuer__Lcom_google_gwt_core_client_impl_SchedulerImpl_2V(this$static));
    com_google_gwt_core_client_impl_SchedulerImpl_scheduleFixedDelayImpl__Lcom_google_gwt_core_client_Scheduler$RepeatingCommand_2IV(this$static.com_google_gwt_core_client_impl_SchedulerImpl_rescue, 50);
  }
}

function com_google_gwt_core_client_impl_SchedulerImpl_$runRepeatingTasks__Lcom_google_gwt_core_client_impl_SchedulerImpl_2Lcom_google_gwt_core_client_JsArray_2Lcom_google_gwt_core_client_JsArray_2(tasks){
  var canceledSomeTasks, duration, executedSomeTask, i, length_0, newTasks, t;
  length_0 = tasks.length;
  if (length_0 == 0) {
    return null;
  }
  canceledSomeTasks = false;
  duration = new com_google_gwt_core_client_Duration_Duration__V;
  while (com_google_gwt_core_client_JsDate_now__D() - duration.com_google_gwt_core_client_Duration_start < 16) {
    executedSomeTask = false;
    for (i = 0; i < length_0; i++) {
      t = tasks[i];
      if (!t) {
        continue;
      }
      executedSomeTask = true;
      if (!t[0].execute__Z()) {
        tasks[i] = null;
        canceledSomeTasks = true;
      }
    }
    if (!executedSomeTask) {
      break;
    }
  }
  if (canceledSomeTasks) {
    newTasks = [];
    for (i = 0; i < length_0; i++) {
      !!tasks[i] && (newTasks[newTasks.length] = tasks[i] , undefined);
    }
    return newTasks.length == 0?null:newTasks;
  }
   else {
    return tasks;
  }
}

function com_google_gwt_core_client_impl_SchedulerImpl_$scheduleDeferred__Lcom_google_gwt_core_client_impl_SchedulerImpl_2Lcom_google_gwt_core_client_Scheduler$ScheduledCommand_2V(this$static, cmd){
  this$static.com_google_gwt_core_client_impl_SchedulerImpl_deferredCommands = com_google_gwt_core_client_impl_SchedulerImpl_push__Lcom_google_gwt_core_client_JsArray_2Lcom_google_gwt_core_client_impl_SchedulerImpl$Task_2Lcom_google_gwt_core_client_JsArray_2(this$static.com_google_gwt_core_client_impl_SchedulerImpl_deferredCommands, [cmd, false]);
  com_google_gwt_core_client_impl_SchedulerImpl_$maybeSchedulePostEventPumpCommands__Lcom_google_gwt_core_client_impl_SchedulerImpl_2V(this$static);
}

function com_google_gwt_core_client_impl_SchedulerImpl_SchedulerImpl__V(){
}

function com_google_gwt_core_client_impl_SchedulerImpl_execute__Lcom_google_gwt_core_client_Scheduler$RepeatingCommand_2Z(cmd){
  return cmd.execute__Z();
}

function com_google_gwt_core_client_impl_SchedulerImpl_push__Lcom_google_gwt_core_client_JsArray_2Lcom_google_gwt_core_client_impl_SchedulerImpl$Task_2Lcom_google_gwt_core_client_JsArray_2(queue, task){
  !queue && (queue = []);
  queue[queue.length] = task;
  return queue;
}

function com_google_gwt_core_client_impl_SchedulerImpl_runScheduledTasks__Lcom_google_gwt_core_client_JsArray_2Lcom_google_gwt_core_client_JsArray_2Lcom_google_gwt_core_client_JsArray_2(tasks, rescheduled){
  var e, i, j, t;
  for (i = 0 , j = tasks.length; i < j; i++) {
    t = tasks[i];
    try {
      t[1]?t[0].execute__Z() && (rescheduled = com_google_gwt_core_client_impl_SchedulerImpl_push__Lcom_google_gwt_core_client_JsArray_2Lcom_google_gwt_core_client_impl_SchedulerImpl$Task_2Lcom_google_gwt_core_client_JsArray_2(rescheduled, t)):t[0].execute__V();
    }
     catch ($e0) {
      $e0 = com_google_gwt_lang_Exceptions_toJava__Ljava_lang_Object_2Ljava_lang_Object_2($e0);
      if (com_google_gwt_lang_Cast_instanceOf__Ljava_lang_Object_2Lcom_google_gwt_core_client_JavaScriptObject_2Z($e0, 6)) {
        e = $e0;
        com_google_gwt_core_client_impl_Impl_$clinit__V();
        com_google_gwt_core_client_impl_Impl_reportToBrowser__Ljava_lang_Object_2V(com_google_gwt_lang_Cast_instanceOf__Ljava_lang_Object_2Lcom_google_gwt_core_client_JavaScriptObject_2Z(e, 40)?com_google_gwt_lang_Cast_castTo__Ljava_lang_Object_2Lcom_google_gwt_core_client_JavaScriptObject_2Ljava_lang_Object_2(e, 40).getThrown__Ljava_lang_Object_2():e);
      }
       else 
        throw com_google_gwt_lang_Exceptions_toJs__Ljava_lang_Object_2Ljava_lang_Object_2($e0);
    }
  }
  return rescheduled;
}

function com_google_gwt_core_client_impl_SchedulerImpl_scheduleFixedDelayImpl__Lcom_google_gwt_core_client_Scheduler$RepeatingCommand_2IV(cmd, delayMs){
  com_google_gwt_core_client_impl_SchedulerImpl_$clinit__V();
  function callback(){
    var ret = $entry(com_google_gwt_core_client_impl_SchedulerImpl_execute__Lcom_google_gwt_core_client_Scheduler$RepeatingCommand_2Z)(cmd);
    ret && $wnd.setTimeout(callback, delayMs);
  }

  $wnd.setTimeout(callback, delayMs);
}

com_google_gwt_lang_Runtime_defineClass__Lcom_google_gwt_core_client_JavaScriptObject_2Lcom_google_gwt_core_client_JavaScriptObject_2Lcom_google_gwt_core_client_JavaScriptObject_2V(132, 208, {}, com_google_gwt_core_client_impl_SchedulerImpl_SchedulerImpl__V);
_.com_google_gwt_core_client_impl_SchedulerImpl_flushRunning = false;
_.com_google_gwt_core_client_impl_SchedulerImpl_shouldBeRunning = false;
var com_google_gwt_core_client_impl_SchedulerImpl_INSTANCE;
var com_google_gwt_lang_ClassLiteralHolder_Lcom_1google_1gwt_1core_1client_1impl_1SchedulerImpl_12_1classLit = java_lang_Class_createForClass__Ljava_lang_String_2Ljava_lang_String_2Lcom_google_gwt_core_client_JavaScriptObject_2Ljava_lang_Class_2Ljava_lang_Class_2($intern_28, 'SchedulerImpl', 132);
function com_google_gwt_core_client_impl_SchedulerImpl$Flusher_SchedulerImpl$Flusher__Lcom_google_gwt_core_client_impl_SchedulerImpl_2V(this$0){
  this.com_google_gwt_core_client_impl_SchedulerImpl$Flusher_this$01 = this$0;
}

com_google_gwt_lang_Runtime_defineClass__Lcom_google_gwt_core_client_JavaScriptObject_2Lcom_google_gwt_core_client_JavaScriptObject_2Lcom_google_gwt_core_client_JavaScriptObject_2V(133, 1, {}, com_google_gwt_core_client_impl_SchedulerImpl$Flusher_SchedulerImpl$Flusher__Lcom_google_gwt_core_client_impl_SchedulerImpl_2V);
_.execute__Z = function com_google_gwt_core_client_impl_SchedulerImpl$Flusher_execute__Z(){
  this.com_google_gwt_core_client_impl_SchedulerImpl$Flusher_this$01.com_google_gwt_core_client_impl_SchedulerImpl_flushRunning = true;
  com_google_gwt_core_client_impl_SchedulerImpl_$flushPostEventPumpCommands__Lcom_google_gwt_core_client_impl_SchedulerImpl_2V(this.com_google_gwt_core_client_impl_SchedulerImpl$Flusher_this$01);
  this.com_google_gwt_core_client_impl_SchedulerImpl$Flusher_this$01.com_google_gwt_core_client_impl_SchedulerImpl_flushRunning = false;
  return this.com_google_gwt_core_client_impl_SchedulerImpl$Flusher_this$01.com_google_gwt_core_client_impl_SchedulerImpl_shouldBeRunning = com_google_gwt_core_client_impl_SchedulerImpl_$isWorkQueued__Lcom_google_gwt_core_client_impl_SchedulerImpl_2Z(this.com_google_gwt_core_client_impl_SchedulerImpl$Flusher_this$01);
}
;
var com_google_gwt_lang_ClassLiteralHolder_Lcom_1google_1gwt_1core_1client_1impl_1SchedulerImpl$Flusher_12_1classLit = java_lang_Class_createForClass__Ljava_lang_String_2Ljava_lang_String_2Lcom_google_gwt_core_client_JavaScriptObject_2Ljava_lang_Class_2Ljava_lang_Class_2($intern_28, 'SchedulerImpl/Flusher', 133);
function com_google_gwt_core_client_impl_SchedulerImpl$Rescuer_SchedulerImpl$Rescuer__Lcom_google_gwt_core_client_impl_SchedulerImpl_2V(this$0){
  this.com_google_gwt_core_client_impl_SchedulerImpl$Rescuer_this$01 = this$0;
}

com_google_gwt_lang_Runtime_defineClass__Lcom_google_gwt_core_client_JavaScriptObject_2Lcom_google_gwt_core_client_JavaScriptObject_2Lcom_google_gwt_core_client_JavaScriptObject_2V(134, 1, {}, com_google_gwt_core_client_impl_SchedulerImpl$Rescuer_SchedulerImpl$Rescuer__Lcom_google_gwt_core_client_impl_SchedulerImpl_2V);
_.execute__Z = function com_google_gwt_core_client_impl_SchedulerImpl$Rescuer_execute__Z(){
  this.com_google_gwt_core_client_impl_SchedulerImpl$Rescuer_this$01.com_google_gwt_core_client_impl_SchedulerImpl_flushRunning && com_google_gwt_core_client_impl_SchedulerImpl_scheduleFixedDelayImpl__Lcom_google_gwt_core_client_Scheduler$RepeatingCommand_2IV(this.com_google_gwt_core_client_impl_SchedulerImpl$Rescuer_this$01.com_google_gwt_core_client_impl_SchedulerImpl_flusher, 1);
  return this.com_google_gwt_core_client_impl_SchedulerImpl$Rescuer_this$01.com_google_gwt_core_client_impl_SchedulerImpl_shouldBeRunning;
}
;
var com_google_gwt_lang_ClassLiteralHolder_Lcom_1google_1gwt_1core_1client_1impl_1SchedulerImpl$Rescuer_12_1classLit = java_lang_Class_createForClass__Ljava_lang_String_2Ljava_lang_String_2Lcom_google_gwt_core_client_JavaScriptObject_2Ljava_lang_Class_2Ljava_lang_Class_2($intern_28, 'SchedulerImpl/Rescuer', 134);
function com_google_gwt_core_client_impl_StackTraceCreator_$clinit__V(){
  com_google_gwt_core_client_impl_StackTraceCreator_$clinit__V = com_google_gwt_lang_Runtime_emptyMethod__V;
  var c, enforceLegacy;
  enforceLegacy = !com_google_gwt_core_client_impl_StackTraceCreator_supportsErrorStack__Z();
  c = new com_google_gwt_core_client_impl_StackTraceCreator$CollectorModernNoSourceMap_StackTraceCreator$CollectorModernNoSourceMap__V;
  com_google_gwt_core_client_impl_StackTraceCreator_collector = enforceLegacy?new com_google_gwt_core_client_impl_StackTraceCreator$CollectorLegacy_StackTraceCreator$CollectorLegacy__V:c;
}

function com_google_gwt_core_client_impl_StackTraceCreator_captureStackTrace__Ljava_lang_Object_2V(error){
  com_google_gwt_core_client_impl_StackTraceCreator_$clinit__V();
  com_google_gwt_core_client_impl_StackTraceCreator_collector.collect__Ljava_lang_Object_2V(error);
}

function com_google_gwt_core_client_impl_StackTraceCreator_extractFunctionName__Ljava_lang_String_2Ljava_lang_String_2(fnName){
  var fnRE = /function(?:\s+([\w$]+))?\s*\(/;
  var match_0 = fnRE.exec(fnName);
  return match_0 && match_0[1] || 'anonymous';
}

function com_google_gwt_core_client_impl_StackTraceCreator_supportsErrorStack__Z(){
  if (Error.stackTraceLimit > 0) {
    $wnd.Error.stackTraceLimit = Error.stackTraceLimit = 64;
    return true;
  }
  return 'stack' in new Error;
}

var com_google_gwt_core_client_impl_StackTraceCreator_collector;
com_google_gwt_lang_Runtime_defineClass__Lcom_google_gwt_core_client_JavaScriptObject_2Lcom_google_gwt_core_client_JavaScriptObject_2Lcom_google_gwt_core_client_JavaScriptObject_2V(219, 1, {});
var com_google_gwt_lang_ClassLiteralHolder_Lcom_1google_1gwt_1core_1client_1impl_1StackTraceCreator$Collector_12_1classLit = java_lang_Class_createForClass__Ljava_lang_String_2Ljava_lang_String_2Lcom_google_gwt_core_client_JavaScriptObject_2Ljava_lang_Class_2Ljava_lang_Class_2($intern_28, 'StackTraceCreator/Collector', 219);
function com_google_gwt_core_client_impl_StackTraceCreator$CollectorLegacy_StackTraceCreator$CollectorLegacy__V(){
}

com_google_gwt_lang_Runtime_defineClass__Lcom_google_gwt_core_client_JavaScriptObject_2Lcom_google_gwt_core_client_JavaScriptObject_2Lcom_google_gwt_core_client_JavaScriptObject_2V(113, 219, {}, com_google_gwt_core_client_impl_StackTraceCreator$CollectorLegacy_StackTraceCreator$CollectorLegacy__V);
_.collect__Ljava_lang_Object_2V = function com_google_gwt_core_client_impl_StackTraceCreator$CollectorLegacy_collect__Ljava_lang_Object_2V(error){
  var seen = {}, com_google_gwt_core_client_impl_StackTraceCreator_getFunctionName__Lcom_google_gwt_core_client_JavaScriptObject_2Ljava_lang_String_2_name_0;
  var fnStack = [];
  error['fnStack'] = fnStack;
  var callee = arguments.callee.caller;
  while (callee) {
    var name_0 = (com_google_gwt_core_client_impl_StackTraceCreator_$clinit__V() , callee.name || (callee.name = com_google_gwt_core_client_impl_StackTraceCreator_extractFunctionName__Ljava_lang_String_2Ljava_lang_String_2(callee.toString())));
    fnStack.push(name_0);
    var keyName = ':' + name_0;
    var withThisName = seen[keyName];
    if (withThisName) {
      var i, j;
      for (i = 0 , j = withThisName.length; i < j; i++) {
        if (withThisName[i] === callee) {
          return;
        }
      }
    }
    (withThisName || (seen[keyName] = [])).push(callee);
    callee = callee.caller;
  }
}
;
var com_google_gwt_lang_ClassLiteralHolder_Lcom_1google_1gwt_1core_1client_1impl_1StackTraceCreator$CollectorLegacy_12_1classLit = java_lang_Class_createForClass__Ljava_lang_String_2Ljava_lang_String_2Lcom_google_gwt_core_client_JavaScriptObject_2Ljava_lang_Class_2Ljava_lang_Class_2($intern_28, 'StackTraceCreator/CollectorLegacy', 113);
com_google_gwt_lang_Runtime_defineClass__Lcom_google_gwt_core_client_JavaScriptObject_2Lcom_google_gwt_core_client_JavaScriptObject_2Lcom_google_gwt_core_client_JavaScriptObject_2V(220, 219, {});
_.collect__Ljava_lang_Object_2V = function com_google_gwt_core_client_impl_StackTraceCreator$CollectorModern_collect__Ljava_lang_Object_2V(error){
}
;
var com_google_gwt_lang_ClassLiteralHolder_Lcom_1google_1gwt_1core_1client_1impl_1StackTraceCreator$CollectorModern_12_1classLit = java_lang_Class_createForClass__Ljava_lang_String_2Ljava_lang_String_2Lcom_google_gwt_core_client_JavaScriptObject_2Ljava_lang_Class_2Ljava_lang_Class_2($intern_28, 'StackTraceCreator/CollectorModern', 220);
function com_google_gwt_core_client_impl_StackTraceCreator$CollectorModernNoSourceMap_StackTraceCreator$CollectorModernNoSourceMap__V(){
}

com_google_gwt_lang_Runtime_defineClass__Lcom_google_gwt_core_client_JavaScriptObject_2Lcom_google_gwt_core_client_JavaScriptObject_2Lcom_google_gwt_core_client_JavaScriptObject_2V(114, 220, {}, com_google_gwt_core_client_impl_StackTraceCreator$CollectorModernNoSourceMap_StackTraceCreator$CollectorModernNoSourceMap__V);
var com_google_gwt_lang_ClassLiteralHolder_Lcom_1google_1gwt_1core_1client_1impl_1StackTraceCreator$CollectorModernNoSourceMap_12_1classLit = java_lang_Class_createForClass__Ljava_lang_String_2Ljava_lang_String_2Lcom_google_gwt_core_client_JavaScriptObject_2Ljava_lang_Class_2Ljava_lang_Class_2($intern_28, 'StackTraceCreator/CollectorModernNoSourceMap', 114);
function com_google_gwt_dom_client_Node_$appendChild__Lcom_google_gwt_dom_client_Node_2Lcom_google_gwt_dom_client_Node_2Lcom_google_gwt_dom_client_Node_2(this$static, newChild){
  return this$static.appendChild(newChild);
}

function com_google_gwt_dom_client_Node_$insertBefore__Lcom_google_gwt_dom_client_Node_2Lcom_google_gwt_dom_client_Node_2Lcom_google_gwt_dom_client_Node_2Lcom_google_gwt_dom_client_Node_2(this$static, newChild, refChild){
  return this$static.insertBefore(newChild, refChild);
}

function com_google_gwt_dom_client_Node_$removeChild__Lcom_google_gwt_dom_client_Node_2Lcom_google_gwt_dom_client_Node_2Lcom_google_gwt_dom_client_Node_2(this$static, oldChild){
  return this$static.removeChild(oldChild);
}

function com_google_gwt_dom_client_Node_$replaceChild__Lcom_google_gwt_dom_client_Node_2Lcom_google_gwt_dom_client_Node_2Lcom_google_gwt_dom_client_Node_2Lcom_google_gwt_dom_client_Node_2(this$static, newChild, oldChild){
  return this$static.replaceChild(newChild, oldChild);
}

function com_google_gwt_dom_client_Element_$addClassName__Lcom_google_gwt_dom_client_Element_2Ljava_lang_String_2Z(this$static, className){
  var idx, oldClassName;
  className = com_google_gwt_dom_client_Element_trimClassName__Ljava_lang_String_2Ljava_lang_String_2(className);
  oldClassName = this$static.className || '';
  idx = com_google_gwt_dom_client_Element_indexOfName__Ljava_lang_String_2Ljava_lang_String_2I(oldClassName, className);
  if (idx == -1) {
    oldClassName.length > 0?(this$static.className = oldClassName + ' ' + className || '' , undefined):(this$static.className = className || '' , undefined);
    return true;
  }
  return false;
}

function com_google_gwt_dom_client_Element_$removeClassName__Lcom_google_gwt_dom_client_Element_2Ljava_lang_String_2Z(this$static, className){
  var begin, end, idx, newClassName, oldStyle;
  className = com_google_gwt_dom_client_Element_trimClassName__Ljava_lang_String_2Ljava_lang_String_2(className);
  oldStyle = this$static.className || '';
  idx = com_google_gwt_dom_client_Element_indexOfName__Ljava_lang_String_2Ljava_lang_String_2I(oldStyle, className);
  if (idx != -1) {
    begin = java_lang_String_$trim__Ljava_lang_String_2Ljava_lang_String_2(oldStyle.substr(0, idx));
    end = java_lang_String_$trim__Ljava_lang_String_2Ljava_lang_String_2(java_lang_String_$substring__Ljava_lang_String_2ILjava_lang_String_2(oldStyle, idx + className.length));
    begin.length == 0?(newClassName = end):end.length == 0?(newClassName = begin):(newClassName = begin + ' ' + end);
    this$static.className = newClassName || '';
    return true;
  }
  return false;
}

function com_google_gwt_dom_client_Element_$setAttribute__Lcom_google_gwt_dom_client_Element_2Ljava_lang_String_2Ljava_lang_String_2V(this$static, name_0, value_0){
  this$static.setAttribute(name_0, value_0);
}

function com_google_gwt_dom_client_Element_$setClassName__Lcom_google_gwt_dom_client_Element_2Ljava_lang_String_2V(this$static, className){
  this$static.className = className || '';
}

function com_google_gwt_dom_client_Element_$setId__Lcom_google_gwt_dom_client_Element_2Ljava_lang_String_2V(this$static, id_0){
  this$static.id = id_0;
}

function com_google_gwt_dom_client_Element_$setInnerHTML__Lcom_google_gwt_dom_client_Element_2Ljava_lang_String_2V(this$static, html){
  this$static.innerHTML = html || '';
}

function com_google_gwt_dom_client_Element_indexOfName__Ljava_lang_String_2Ljava_lang_String_2I(nameList, name_0){
  var idx, last, lastPos;
  idx = nameList.indexOf(name_0);
  while (idx != -1) {
    if (idx == 0 || nameList.charCodeAt(idx - 1) == 32) {
      last = idx + name_0.length;
      lastPos = nameList.length;
      if (last == lastPos || last < lastPos && nameList.charCodeAt(last) == 32) {
        break;
      }
    }
    idx = nameList.indexOf(name_0, idx + 1);
  }
  return idx;
}

function com_google_gwt_dom_client_Element_trimClassName__Ljava_lang_String_2Ljava_lang_String_2(className){
  className = java_lang_String_$trim__Ljava_lang_String_2Ljava_lang_String_2(className);
  return className;
}

function com_google_gwt_dom_client_DOMImpl_$createScriptElement__Lcom_google_gwt_dom_client_DOMImpl_2Lcom_google_gwt_dom_client_Document_2Ljava_lang_String_2Lcom_google_gwt_dom_client_ScriptElement_2(doc, source){
  var elem;
  elem = com_google_gwt_dom_client_DOMImplTrident_$createElement__Lcom_google_gwt_dom_client_DOMImplTrident_2Lcom_google_gwt_dom_client_Document_2Ljava_lang_String_2Lcom_google_gwt_dom_client_Element_2(doc, 'script');
  elem.text = source;
  return elem;
}

function com_google_gwt_dom_client_DOMImpl_$getFirstChildElement__Lcom_google_gwt_dom_client_DOMImpl_2Lcom_google_gwt_dom_client_Element_2Lcom_google_gwt_dom_client_Element_2(elem){
  var child = elem.firstChild;
  while (child && child.nodeType != 1)
    child = child.nextSibling;
  return child;
}

function com_google_gwt_dom_client_DOMImpl_$getNextSiblingElement__Lcom_google_gwt_dom_client_DOMImpl_2Lcom_google_gwt_dom_client_Element_2Lcom_google_gwt_dom_client_Element_2(elem){
  var sib = elem.nextSibling;
  while (sib && sib.nodeType != 1)
    sib = sib.nextSibling;
  return sib;
}

function com_google_gwt_dom_client_DOMImpl_$getParentElement__Lcom_google_gwt_dom_client_DOMImpl_2Lcom_google_gwt_dom_client_Node_2Lcom_google_gwt_dom_client_Element_2(node){
  var parent_0 = node.parentNode;
  (!parent_0 || parent_0.nodeType != 1) && (parent_0 = null);
  return parent_0;
}

function com_google_gwt_dom_client_DOMImplTrident_$createElement__Lcom_google_gwt_dom_client_DOMImplTrident_2Lcom_google_gwt_dom_client_Document_2Ljava_lang_String_2Lcom_google_gwt_dom_client_Element_2(doc, tagName){
  var container, elem;
  if (tagName.indexOf(':') != -1) {
    container = (!doc.__gwt_container && (doc.__gwt_container = doc.createElement($intern_30)) , doc.__gwt_container);
    container.innerHTML = '<' + tagName + '/>' || '';
    elem = com_google_gwt_dom_client_DOMImpl_$getFirstChildElement__Lcom_google_gwt_dom_client_DOMImpl_2Lcom_google_gwt_dom_client_Element_2Lcom_google_gwt_dom_client_Element_2(container);
    container.removeChild(elem);
    return elem;
  }
  return doc.createElement(tagName);
}

function com_google_gwt_dom_client_DOMImplTrident_$getTagName__Lcom_google_gwt_dom_client_DOMImplTrident_2Lcom_google_gwt_dom_client_Element_2Ljava_lang_String_2(elem){
  var scopeName, tagName;
  tagName = elem.tagName;
  scopeName = elem.scopeName;
  if (scopeName == null || java_lang_String_$equalsIgnoreCase__Ljava_lang_String_2Ljava_lang_String_2Z('html', scopeName)) {
    return tagName;
  }
  return scopeName + ':' + tagName;
}

function com_google_gwt_dom_client_DOMImplTrident_$setInnerText__Lcom_google_gwt_dom_client_DOMImplTrident_2Lcom_google_gwt_dom_client_Element_2Ljava_lang_String_2V(elem, text_0){
  elem.innerText = text_0 || '';
}

function com_google_gwt_dom_client_DOMImplTrident_isOrHasChildImpl__Lcom_google_gwt_dom_client_Node_2Lcom_google_gwt_dom_client_Node_2Z(parent_0, child){
  if (parent_0.nodeType != 1 && parent_0.nodeType != 9) {
    return parent_0 == child;
  }
  if (child.nodeType != 1) {
    child = child.parentNode;
    if (!child) {
      return false;
    }
  }
  if (parent_0.nodeType == 9) {
    return parent_0 === child || parent_0.body && parent_0.body.contains(child);
  }
   else {
    return parent_0 === child || parent_0.contains(child);
  }
}

var com_google_gwt_dom_client_DOMImplTrident_currentEventTarget;
function com_google_gwt_dom_client_Document_$createTextNode__Lcom_google_gwt_dom_client_Document_2Ljava_lang_String_2Lcom_google_gwt_dom_client_Text_2(this$static, data_0){
  return this$static.createTextNode(data_0);
}

function com_google_gwt_dom_client_Document_$createUniqueId__Lcom_google_gwt_dom_client_Document_2Ljava_lang_String_2(this$static){
  !this$static.gwt_uid && (this$static.gwt_uid = 1);
  return 'gwt-uid-' + this$static.gwt_uid++;
}

function com_google_gwt_dom_client_Document_$getElementById__Lcom_google_gwt_dom_client_Document_2Ljava_lang_String_2Lcom_google_gwt_dom_client_Element_2(this$static, elementId){
  return this$static.getElementById(elementId);
}

function com_google_gwt_dom_client_Style$TextAlign_$clinit__V(){
  com_google_gwt_dom_client_Style$TextAlign_$clinit__V = com_google_gwt_lang_Runtime_emptyMethod__V;
  com_google_gwt_dom_client_Style$TextAlign_CENTER = new com_google_gwt_dom_client_Style$TextAlign$1_Style$TextAlign$1__Ljava_lang_String_2IV;
  com_google_gwt_dom_client_Style$TextAlign_JUSTIFY = new com_google_gwt_dom_client_Style$TextAlign$2_Style$TextAlign$2__Ljava_lang_String_2IV;
  com_google_gwt_dom_client_Style$TextAlign_LEFT = new com_google_gwt_dom_client_Style$TextAlign$3_Style$TextAlign$3__Ljava_lang_String_2IV;
  com_google_gwt_dom_client_Style$TextAlign_RIGHT = new com_google_gwt_dom_client_Style$TextAlign$4_Style$TextAlign$4__Ljava_lang_String_2IV;
}

function com_google_gwt_dom_client_Style$TextAlign_Style$TextAlign__Ljava_lang_String_2IV(enum$name, enum$ordinal){
  java_lang_Enum_Enum__Ljava_lang_String_2IV.call(this, enum$name, enum$ordinal);
}

function com_google_gwt_dom_client_Style$TextAlign_values___3Lcom_google_gwt_dom_client_Style$TextAlign_2(){
  com_google_gwt_dom_client_Style$TextAlign_$clinit__V();
  return com_google_gwt_lang_Array_stampJavaTypeInfo__Ljava_lang_Class_2Lcom_google_gwt_core_client_JavaScriptObject_2Lcom_google_gwt_core_client_JavaScriptObject_2ILjava_lang_Object_2Ljava_lang_Object_2(com_google_gwt_lang_Array_getClassLiteralForArray__Ljava_lang_Class_2ILjava_lang_Class_2(com_google_gwt_lang_ClassLiteralHolder_Lcom_1google_1gwt_1dom_1client_1Style$TextAlign_12_1classLit, 1), $intern_5, 43, 0, [com_google_gwt_dom_client_Style$TextAlign_CENTER, com_google_gwt_dom_client_Style$TextAlign_JUSTIFY, com_google_gwt_dom_client_Style$TextAlign_LEFT, com_google_gwt_dom_client_Style$TextAlign_RIGHT]);
}

com_google_gwt_lang_Runtime_defineClass__Lcom_google_gwt_core_client_JavaScriptObject_2Lcom_google_gwt_core_client_JavaScriptObject_2Lcom_google_gwt_core_client_JavaScriptObject_2V(43, 12, $intern_31);
var com_google_gwt_dom_client_Style$TextAlign_CENTER, com_google_gwt_dom_client_Style$TextAlign_JUSTIFY, com_google_gwt_dom_client_Style$TextAlign_LEFT, com_google_gwt_dom_client_Style$TextAlign_RIGHT;
var com_google_gwt_lang_ClassLiteralHolder_Lcom_1google_1gwt_1dom_1client_1Style$TextAlign_12_1classLit = java_lang_Class_createForEnum__Ljava_lang_String_2Ljava_lang_String_2Lcom_google_gwt_core_client_JavaScriptObject_2Ljava_lang_Class_2Lcom_google_gwt_core_client_JavaScriptObject_2Lcom_google_gwt_core_client_JavaScriptObject_2Ljava_lang_Class_2($intern_32, 'Style/TextAlign', 43, com_google_gwt_dom_client_Style$TextAlign_values___3Lcom_google_gwt_dom_client_Style$TextAlign_2);
function com_google_gwt_dom_client_Style$TextAlign$1_Style$TextAlign$1__Ljava_lang_String_2IV(){
  com_google_gwt_dom_client_Style$TextAlign_Style$TextAlign__Ljava_lang_String_2IV.call(this, 'CENTER', 0);
}

com_google_gwt_lang_Runtime_defineClass__Lcom_google_gwt_core_client_JavaScriptObject_2Lcom_google_gwt_core_client_JavaScriptObject_2Lcom_google_gwt_core_client_JavaScriptObject_2V(171, 43, $intern_31, com_google_gwt_dom_client_Style$TextAlign$1_Style$TextAlign$1__Ljava_lang_String_2IV);
_.getCssName__Ljava_lang_String_2 = function com_google_gwt_dom_client_Style$TextAlign$1_getCssName__Ljava_lang_String_2(){
  return 'center';
}
;
var com_google_gwt_lang_ClassLiteralHolder_Lcom_1google_1gwt_1dom_1client_1Style$TextAlign$1_12_1classLit = java_lang_Class_createForEnum__Ljava_lang_String_2Ljava_lang_String_2Lcom_google_gwt_core_client_JavaScriptObject_2Ljava_lang_Class_2Lcom_google_gwt_core_client_JavaScriptObject_2Lcom_google_gwt_core_client_JavaScriptObject_2Ljava_lang_Class_2($intern_32, 'Style/TextAlign/1', 171, null);
function com_google_gwt_dom_client_Style$TextAlign$2_Style$TextAlign$2__Ljava_lang_String_2IV(){
  com_google_gwt_dom_client_Style$TextAlign_Style$TextAlign__Ljava_lang_String_2IV.call(this, 'JUSTIFY', 1);
}

com_google_gwt_lang_Runtime_defineClass__Lcom_google_gwt_core_client_JavaScriptObject_2Lcom_google_gwt_core_client_JavaScriptObject_2Lcom_google_gwt_core_client_JavaScriptObject_2V(172, 43, $intern_31, com_google_gwt_dom_client_Style$TextAlign$2_Style$TextAlign$2__Ljava_lang_String_2IV);
_.getCssName__Ljava_lang_String_2 = function com_google_gwt_dom_client_Style$TextAlign$2_getCssName__Ljava_lang_String_2(){
  return 'justify';
}
;
var com_google_gwt_lang_ClassLiteralHolder_Lcom_1google_1gwt_1dom_1client_1Style$TextAlign$2_12_1classLit = java_lang_Class_createForEnum__Ljava_lang_String_2Ljava_lang_String_2Lcom_google_gwt_core_client_JavaScriptObject_2Ljava_lang_Class_2Lcom_google_gwt_core_client_JavaScriptObject_2Lcom_google_gwt_core_client_JavaScriptObject_2Ljava_lang_Class_2($intern_32, 'Style/TextAlign/2', 172, null);
function com_google_gwt_dom_client_Style$TextAlign$3_Style$TextAlign$3__Ljava_lang_String_2IV(){
  com_google_gwt_dom_client_Style$TextAlign_Style$TextAlign__Ljava_lang_String_2IV.call(this, 'LEFT', 2);
}

com_google_gwt_lang_Runtime_defineClass__Lcom_google_gwt_core_client_JavaScriptObject_2Lcom_google_gwt_core_client_JavaScriptObject_2Lcom_google_gwt_core_client_JavaScriptObject_2V(173, 43, $intern_31, com_google_gwt_dom_client_Style$TextAlign$3_Style$TextAlign$3__Ljava_lang_String_2IV);
_.getCssName__Ljava_lang_String_2 = function com_google_gwt_dom_client_Style$TextAlign$3_getCssName__Ljava_lang_String_2(){
  return 'left';
}
;
var com_google_gwt_lang_ClassLiteralHolder_Lcom_1google_1gwt_1dom_1client_1Style$TextAlign$3_12_1classLit = java_lang_Class_createForEnum__Ljava_lang_String_2Ljava_lang_String_2Lcom_google_gwt_core_client_JavaScriptObject_2Ljava_lang_Class_2Lcom_google_gwt_core_client_JavaScriptObject_2Lcom_google_gwt_core_client_JavaScriptObject_2Ljava_lang_Class_2($intern_32, 'Style/TextAlign/3', 173, null);
function com_google_gwt_dom_client_Style$TextAlign$4_Style$TextAlign$4__Ljava_lang_String_2IV(){
  com_google_gwt_dom_client_Style$TextAlign_Style$TextAlign__Ljava_lang_String_2IV.call(this, 'RIGHT', 3);
}

com_google_gwt_lang_Runtime_defineClass__Lcom_google_gwt_core_client_JavaScriptObject_2Lcom_google_gwt_core_client_JavaScriptObject_2Lcom_google_gwt_core_client_JavaScriptObject_2V(174, 43, $intern_31, com_google_gwt_dom_client_Style$TextAlign$4_Style$TextAlign$4__Ljava_lang_String_2IV);
_.getCssName__Ljava_lang_String_2 = function com_google_gwt_dom_client_Style$TextAlign$4_getCssName__Ljava_lang_String_2(){
  return 'right';
}
;
var com_google_gwt_lang_ClassLiteralHolder_Lcom_1google_1gwt_1dom_1client_1Style$TextAlign$4_12_1classLit = java_lang_Class_createForEnum__Ljava_lang_String_2Ljava_lang_String_2Lcom_google_gwt_core_client_JavaScriptObject_2Ljava_lang_Class_2Lcom_google_gwt_core_client_JavaScriptObject_2Lcom_google_gwt_core_client_JavaScriptObject_2Ljava_lang_Class_2($intern_32, 'Style/TextAlign/4', 174, null);
function com_google_gwt_dom_client_Style$Unit_$clinit__V(){
  com_google_gwt_dom_client_Style$Unit_$clinit__V = com_google_gwt_lang_Runtime_emptyMethod__V;
  com_google_gwt_dom_client_Style$Unit_PX = new com_google_gwt_dom_client_Style$Unit$1_Style$Unit$1__Ljava_lang_String_2IV;
  com_google_gwt_dom_client_Style$Unit_PCT = new com_google_gwt_dom_client_Style$Unit$2_Style$Unit$2__Ljava_lang_String_2IV;
  com_google_gwt_dom_client_Style$Unit_EM = new com_google_gwt_dom_client_Style$Unit$3_Style$Unit$3__Ljava_lang_String_2IV;
  com_google_gwt_dom_client_Style$Unit_EX = new com_google_gwt_dom_client_Style$Unit$4_Style$Unit$4__Ljava_lang_String_2IV;
  com_google_gwt_dom_client_Style$Unit_PT = new com_google_gwt_dom_client_Style$Unit$5_Style$Unit$5__Ljava_lang_String_2IV;
  com_google_gwt_dom_client_Style$Unit_PC = new com_google_gwt_dom_client_Style$Unit$6_Style$Unit$6__Ljava_lang_String_2IV;
  com_google_gwt_dom_client_Style$Unit_IN = new com_google_gwt_dom_client_Style$Unit$7_Style$Unit$7__Ljava_lang_String_2IV;
  com_google_gwt_dom_client_Style$Unit_CM = new com_google_gwt_dom_client_Style$Unit$8_Style$Unit$8__Ljava_lang_String_2IV;
  com_google_gwt_dom_client_Style$Unit_MM = new com_google_gwt_dom_client_Style$Unit$9_Style$Unit$9__Ljava_lang_String_2IV;
}

function com_google_gwt_dom_client_Style$Unit_Style$Unit__Ljava_lang_String_2IV(enum$name, enum$ordinal){
  java_lang_Enum_Enum__Ljava_lang_String_2IV.call(this, enum$name, enum$ordinal);
}

function com_google_gwt_dom_client_Style$Unit_values___3Lcom_google_gwt_dom_client_Style$Unit_2(){
  com_google_gwt_dom_client_Style$Unit_$clinit__V();
  return com_google_gwt_lang_Array_stampJavaTypeInfo__Ljava_lang_Class_2Lcom_google_gwt_core_client_JavaScriptObject_2Lcom_google_gwt_core_client_JavaScriptObject_2ILjava_lang_Object_2Ljava_lang_Object_2(com_google_gwt_lang_Array_getClassLiteralForArray__Ljava_lang_Class_2ILjava_lang_Class_2(com_google_gwt_lang_ClassLiteralHolder_Lcom_1google_1gwt_1dom_1client_1Style$Unit_12_1classLit, 1), $intern_5, 15, 0, [com_google_gwt_dom_client_Style$Unit_PX, com_google_gwt_dom_client_Style$Unit_PCT, com_google_gwt_dom_client_Style$Unit_EM, com_google_gwt_dom_client_Style$Unit_EX, com_google_gwt_dom_client_Style$Unit_PT, com_google_gwt_dom_client_Style$Unit_PC, com_google_gwt_dom_client_Style$Unit_IN, com_google_gwt_dom_client_Style$Unit_CM, com_google_gwt_dom_client_Style$Unit_MM]);
}

com_google_gwt_lang_Runtime_defineClass__Lcom_google_gwt_core_client_JavaScriptObject_2Lcom_google_gwt_core_client_JavaScriptObject_2Lcom_google_gwt_core_client_JavaScriptObject_2V(15, 12, $intern_4);
var com_google_gwt_dom_client_Style$Unit_CM, com_google_gwt_dom_client_Style$Unit_EM, com_google_gwt_dom_client_Style$Unit_EX, com_google_gwt_dom_client_Style$Unit_IN, com_google_gwt_dom_client_Style$Unit_MM, com_google_gwt_dom_client_Style$Unit_PC, com_google_gwt_dom_client_Style$Unit_PCT, com_google_gwt_dom_client_Style$Unit_PT, com_google_gwt_dom_client_Style$Unit_PX;
var com_google_gwt_lang_ClassLiteralHolder_Lcom_1google_1gwt_1dom_1client_1Style$Unit_12_1classLit = java_lang_Class_createForEnum__Ljava_lang_String_2Ljava_lang_String_2Lcom_google_gwt_core_client_JavaScriptObject_2Ljava_lang_Class_2Lcom_google_gwt_core_client_JavaScriptObject_2Lcom_google_gwt_core_client_JavaScriptObject_2Ljava_lang_Class_2($intern_32, 'Style/Unit', 15, com_google_gwt_dom_client_Style$Unit_values___3Lcom_google_gwt_dom_client_Style$Unit_2);
function com_google_gwt_dom_client_Style$Unit$1_Style$Unit$1__Ljava_lang_String_2IV(){
  com_google_gwt_dom_client_Style$Unit_Style$Unit__Ljava_lang_String_2IV.call(this, 'PX', 0);
}

com_google_gwt_lang_Runtime_defineClass__Lcom_google_gwt_core_client_JavaScriptObject_2Lcom_google_gwt_core_client_JavaScriptObject_2Lcom_google_gwt_core_client_JavaScriptObject_2V(162, 15, $intern_4, com_google_gwt_dom_client_Style$Unit$1_Style$Unit$1__Ljava_lang_String_2IV);
var com_google_gwt_lang_ClassLiteralHolder_Lcom_1google_1gwt_1dom_1client_1Style$Unit$1_12_1classLit = java_lang_Class_createForEnum__Ljava_lang_String_2Ljava_lang_String_2Lcom_google_gwt_core_client_JavaScriptObject_2Ljava_lang_Class_2Lcom_google_gwt_core_client_JavaScriptObject_2Lcom_google_gwt_core_client_JavaScriptObject_2Ljava_lang_Class_2($intern_32, 'Style/Unit/1', 162, null);
function com_google_gwt_dom_client_Style$Unit$2_Style$Unit$2__Ljava_lang_String_2IV(){
  com_google_gwt_dom_client_Style$Unit_Style$Unit__Ljava_lang_String_2IV.call(this, 'PCT', 1);
}

com_google_gwt_lang_Runtime_defineClass__Lcom_google_gwt_core_client_JavaScriptObject_2Lcom_google_gwt_core_client_JavaScriptObject_2Lcom_google_gwt_core_client_JavaScriptObject_2V(163, 15, $intern_4, com_google_gwt_dom_client_Style$Unit$2_Style$Unit$2__Ljava_lang_String_2IV);
var com_google_gwt_lang_ClassLiteralHolder_Lcom_1google_1gwt_1dom_1client_1Style$Unit$2_12_1classLit = java_lang_Class_createForEnum__Ljava_lang_String_2Ljava_lang_String_2Lcom_google_gwt_core_client_JavaScriptObject_2Ljava_lang_Class_2Lcom_google_gwt_core_client_JavaScriptObject_2Lcom_google_gwt_core_client_JavaScriptObject_2Ljava_lang_Class_2($intern_32, 'Style/Unit/2', 163, null);
function com_google_gwt_dom_client_Style$Unit$3_Style$Unit$3__Ljava_lang_String_2IV(){
  com_google_gwt_dom_client_Style$Unit_Style$Unit__Ljava_lang_String_2IV.call(this, 'EM', 2);
}

com_google_gwt_lang_Runtime_defineClass__Lcom_google_gwt_core_client_JavaScriptObject_2Lcom_google_gwt_core_client_JavaScriptObject_2Lcom_google_gwt_core_client_JavaScriptObject_2V(164, 15, $intern_4, com_google_gwt_dom_client_Style$Unit$3_Style$Unit$3__Ljava_lang_String_2IV);
var com_google_gwt_lang_ClassLiteralHolder_Lcom_1google_1gwt_1dom_1client_1Style$Unit$3_12_1classLit = java_lang_Class_createForEnum__Ljava_lang_String_2Ljava_lang_String_2Lcom_google_gwt_core_client_JavaScriptObject_2Ljava_lang_Class_2Lcom_google_gwt_core_client_JavaScriptObject_2Lcom_google_gwt_core_client_JavaScriptObject_2Ljava_lang_Class_2($intern_32, 'Style/Unit/3', 164, null);
function com_google_gwt_dom_client_Style$Unit$4_Style$Unit$4__Ljava_lang_String_2IV(){
  com_google_gwt_dom_client_Style$Unit_Style$Unit__Ljava_lang_String_2IV.call(this, 'EX', 3);
}

com_google_gwt_lang_Runtime_defineClass__Lcom_google_gwt_core_client_JavaScriptObject_2Lcom_google_gwt_core_client_JavaScriptObject_2Lcom_google_gwt_core_client_JavaScriptObject_2V(165, 15, $intern_4, com_google_gwt_dom_client_Style$Unit$4_Style$Unit$4__Ljava_lang_String_2IV);
var com_google_gwt_lang_ClassLiteralHolder_Lcom_1google_1gwt_1dom_1client_1Style$Unit$4_12_1classLit = java_lang_Class_createForEnum__Ljava_lang_String_2Ljava_lang_String_2Lcom_google_gwt_core_client_JavaScriptObject_2Ljava_lang_Class_2Lcom_google_gwt_core_client_JavaScriptObject_2Lcom_google_gwt_core_client_JavaScriptObject_2Ljava_lang_Class_2($intern_32, 'Style/Unit/4', 165, null);
function com_google_gwt_dom_client_Style$Unit$5_Style$Unit$5__Ljava_lang_String_2IV(){
  com_google_gwt_dom_client_Style$Unit_Style$Unit__Ljava_lang_String_2IV.call(this, 'PT', 4);
}

com_google_gwt_lang_Runtime_defineClass__Lcom_google_gwt_core_client_JavaScriptObject_2Lcom_google_gwt_core_client_JavaScriptObject_2Lcom_google_gwt_core_client_JavaScriptObject_2V(166, 15, $intern_4, com_google_gwt_dom_client_Style$Unit$5_Style$Unit$5__Ljava_lang_String_2IV);
var com_google_gwt_lang_ClassLiteralHolder_Lcom_1google_1gwt_1dom_1client_1Style$Unit$5_12_1classLit = java_lang_Class_createForEnum__Ljava_lang_String_2Ljava_lang_String_2Lcom_google_gwt_core_client_JavaScriptObject_2Ljava_lang_Class_2Lcom_google_gwt_core_client_JavaScriptObject_2Lcom_google_gwt_core_client_JavaScriptObject_2Ljava_lang_Class_2($intern_32, 'Style/Unit/5', 166, null);
function com_google_gwt_dom_client_Style$Unit$6_Style$Unit$6__Ljava_lang_String_2IV(){
  com_google_gwt_dom_client_Style$Unit_Style$Unit__Ljava_lang_String_2IV.call(this, 'PC', 5);
}

com_google_gwt_lang_Runtime_defineClass__Lcom_google_gwt_core_client_JavaScriptObject_2Lcom_google_gwt_core_client_JavaScriptObject_2Lcom_google_gwt_core_client_JavaScriptObject_2V(167, 15, $intern_4, com_google_gwt_dom_client_Style$Unit$6_Style$Unit$6__Ljava_lang_String_2IV);
var com_google_gwt_lang_ClassLiteralHolder_Lcom_1google_1gwt_1dom_1client_1Style$Unit$6_12_1classLit = java_lang_Class_createForEnum__Ljava_lang_String_2Ljava_lang_String_2Lcom_google_gwt_core_client_JavaScriptObject_2Ljava_lang_Class_2Lcom_google_gwt_core_client_JavaScriptObject_2Lcom_google_gwt_core_client_JavaScriptObject_2Ljava_lang_Class_2($intern_32, 'Style/Unit/6', 167, null);
function com_google_gwt_dom_client_Style$Unit$7_Style$Unit$7__Ljava_lang_String_2IV(){
  com_google_gwt_dom_client_Style$Unit_Style$Unit__Ljava_lang_String_2IV.call(this, 'IN', 6);
}

com_google_gwt_lang_Runtime_defineClass__Lcom_google_gwt_core_client_JavaScriptObject_2Lcom_google_gwt_core_client_JavaScriptObject_2Lcom_google_gwt_core_client_JavaScriptObject_2V(168, 15, $intern_4, com_google_gwt_dom_client_Style$Unit$7_Style$Unit$7__Ljava_lang_String_2IV);
var com_google_gwt_lang_ClassLiteralHolder_Lcom_1google_1gwt_1dom_1client_1Style$Unit$7_12_1classLit = java_lang_Class_createForEnum__Ljava_lang_String_2Ljava_lang_String_2Lcom_google_gwt_core_client_JavaScriptObject_2Ljava_lang_Class_2Lcom_google_gwt_core_client_JavaScriptObject_2Lcom_google_gwt_core_client_JavaScriptObject_2Ljava_lang_Class_2($intern_32, 'Style/Unit/7', 168, null);
function com_google_gwt_dom_client_Style$Unit$8_Style$Unit$8__Ljava_lang_String_2IV(){
  com_google_gwt_dom_client_Style$Unit_Style$Unit__Ljava_lang_String_2IV.call(this, 'CM', 7);
}

com_google_gwt_lang_Runtime_defineClass__Lcom_google_gwt_core_client_JavaScriptObject_2Lcom_google_gwt_core_client_JavaScriptObject_2Lcom_google_gwt_core_client_JavaScriptObject_2V(169, 15, $intern_4, com_google_gwt_dom_client_Style$Unit$8_Style$Unit$8__Ljava_lang_String_2IV);
var com_google_gwt_lang_ClassLiteralHolder_Lcom_1google_1gwt_1dom_1client_1Style$Unit$8_12_1classLit = java_lang_Class_createForEnum__Ljava_lang_String_2Ljava_lang_String_2Lcom_google_gwt_core_client_JavaScriptObject_2Ljava_lang_Class_2Lcom_google_gwt_core_client_JavaScriptObject_2Lcom_google_gwt_core_client_JavaScriptObject_2Ljava_lang_Class_2($intern_32, 'Style/Unit/8', 169, null);
function com_google_gwt_dom_client_Style$Unit$9_Style$Unit$9__Ljava_lang_String_2IV(){
  com_google_gwt_dom_client_Style$Unit_Style$Unit__Ljava_lang_String_2IV.call(this, 'MM', 8);
}

com_google_gwt_lang_Runtime_defineClass__Lcom_google_gwt_core_client_JavaScriptObject_2Lcom_google_gwt_core_client_JavaScriptObject_2Lcom_google_gwt_core_client_JavaScriptObject_2V(170, 15, $intern_4, com_google_gwt_dom_client_Style$Unit$9_Style$Unit$9__Ljava_lang_String_2IV);
var com_google_gwt_lang_ClassLiteralHolder_Lcom_1google_1gwt_1dom_1client_1Style$Unit$9_12_1classLit = java_lang_Class_createForEnum__Ljava_lang_String_2Ljava_lang_String_2Lcom_google_gwt_core_client_JavaScriptObject_2Ljava_lang_Class_2Lcom_google_gwt_core_client_JavaScriptObject_2Lcom_google_gwt_core_client_JavaScriptObject_2Ljava_lang_Class_2($intern_32, 'Style/Unit/9', 170, null);
function com_google_gwt_dom_client_Text_$setData__Lcom_google_gwt_dom_client_Text_2Ljava_lang_String_2V(this$static, data_0){
  this$static.data = data_0;
}

com_google_gwt_lang_Runtime_defineClass__Lcom_google_gwt_core_client_JavaScriptObject_2Lcom_google_gwt_core_client_JavaScriptObject_2Lcom_google_gwt_core_client_JavaScriptObject_2V(228, 1, {});
_.toString__Ljava_lang_String_2 = function com_google_web_bindery_event_shared_Event_toString__Ljava_lang_String_2(){
  return 'An event type';
}
;
var com_google_gwt_lang_ClassLiteralHolder_Lcom_1google_1web_1bindery_1event_1shared_1Event_12_1classLit = java_lang_Class_createForClass__Ljava_lang_String_2Ljava_lang_String_2Lcom_google_gwt_core_client_JavaScriptObject_2Ljava_lang_Class_2Ljava_lang_Class_2($intern_33, 'Event', 228);
function com_google_gwt_event_shared_GwtEvent_$overrideSource__Lcom_google_gwt_event_shared_GwtEvent_2Ljava_lang_Object_2V(this$static, source){
  this$static.com_google_web_bindery_event_shared_Event_source = source;
}

com_google_gwt_lang_Runtime_defineClass__Lcom_google_gwt_core_client_JavaScriptObject_2Lcom_google_gwt_core_client_JavaScriptObject_2Lcom_google_gwt_core_client_JavaScriptObject_2V(229, 228, {});
_.com_google_gwt_event_shared_GwtEvent_dead = false;
var com_google_gwt_lang_ClassLiteralHolder_Lcom_1google_1gwt_1event_1shared_1GwtEvent_12_1classLit = java_lang_Class_createForClass__Ljava_lang_String_2Ljava_lang_String_2Lcom_google_gwt_core_client_JavaScriptObject_2Ljava_lang_Class_2Ljava_lang_Class_2($intern_34, 'GwtEvent', 229);
function com_google_gwt_event_dom_client_DomEvent_$setNativeEvent__Lcom_google_gwt_event_dom_client_DomEvent_2Lcom_google_gwt_dom_client_NativeEvent_2V(this$static, nativeEvent){
  this$static.com_google_gwt_event_dom_client_DomEvent_nativeEvent = nativeEvent;
}

function com_google_gwt_event_dom_client_DomEvent_$setRelativeElement__Lcom_google_gwt_event_dom_client_DomEvent_2Lcom_google_gwt_dom_client_Element_2V(this$static, relativeElem){
  this$static.com_google_gwt_event_dom_client_DomEvent_relativeElem = relativeElem;
}

function com_google_gwt_event_dom_client_DomEvent_fireNativeEvent__Lcom_google_gwt_dom_client_NativeEvent_2Lcom_google_gwt_event_shared_HasHandlers_2Lcom_google_gwt_dom_client_Element_2V(nativeEvent, handlerSource, relativeElem){
  var currentNative, currentRelativeElem, type_0, type$iterator, types;
  if (com_google_gwt_event_dom_client_DomEvent_registered) {
    types = com_google_gwt_lang_Cast_castTo__Ljava_lang_Object_2Lcom_google_gwt_core_client_JavaScriptObject_2Ljava_lang_Object_2(com_google_gwt_event_dom_client_PrivateMap_$unsafeGet__Lcom_google_gwt_event_dom_client_PrivateMap_2Ljava_lang_String_2Ljava_lang_Object_2(com_google_gwt_event_dom_client_DomEvent_registered, nativeEvent.type), 28);
    if (types) {
      for (type$iterator = types.iterator__Ljava_util_Iterator_2(); type$iterator.hasNext__Z();) {
        type_0 = com_google_gwt_lang_Cast_castTo__Ljava_lang_Object_2Lcom_google_gwt_core_client_JavaScriptObject_2Ljava_lang_Object_2(type$iterator.next__Ljava_lang_Object_2(), 78);
        currentNative = type_0.com_google_gwt_event_dom_client_DomEvent$Type_flyweight.com_google_gwt_event_dom_client_DomEvent_nativeEvent;
        currentRelativeElem = type_0.com_google_gwt_event_dom_client_DomEvent$Type_flyweight.com_google_gwt_event_dom_client_DomEvent_relativeElem;
        com_google_gwt_event_dom_client_DomEvent_$setNativeEvent__Lcom_google_gwt_event_dom_client_DomEvent_2Lcom_google_gwt_dom_client_NativeEvent_2V(type_0.com_google_gwt_event_dom_client_DomEvent$Type_flyweight, nativeEvent);
        com_google_gwt_event_dom_client_DomEvent_$setRelativeElement__Lcom_google_gwt_event_dom_client_DomEvent_2Lcom_google_gwt_dom_client_Element_2V(type_0.com_google_gwt_event_dom_client_DomEvent$Type_flyweight, relativeElem);
        com_google_gwt_user_client_ui_Widget_$fireEvent__Lcom_google_gwt_user_client_ui_Widget_2Lcom_google_gwt_event_shared_GwtEvent_2V(handlerSource, type_0.com_google_gwt_event_dom_client_DomEvent$Type_flyweight);
        com_google_gwt_event_dom_client_DomEvent_$setNativeEvent__Lcom_google_gwt_event_dom_client_DomEvent_2Lcom_google_gwt_dom_client_NativeEvent_2V(type_0.com_google_gwt_event_dom_client_DomEvent$Type_flyweight, currentNative);
        com_google_gwt_event_dom_client_DomEvent_$setRelativeElement__Lcom_google_gwt_event_dom_client_DomEvent_2Lcom_google_gwt_dom_client_Element_2V(type_0.com_google_gwt_event_dom_client_DomEvent$Type_flyweight, currentRelativeElem);
      }
    }
  }
}

com_google_gwt_lang_Runtime_defineClass__Lcom_google_gwt_core_client_JavaScriptObject_2Lcom_google_gwt_core_client_JavaScriptObject_2Lcom_google_gwt_core_client_JavaScriptObject_2V(231, 229, {});
_.getAssociatedType__Lcom_google_gwt_event_shared_GwtEvent$Type_2 = function com_google_gwt_event_dom_client_DomEvent_getAssociatedType__Lcom_google_gwt_event_shared_GwtEvent$Type_2(){
  return com_google_gwt_event_dom_client_ClickEvent_$clinit__V() , com_google_gwt_event_dom_client_ClickEvent_TYPE;
}
;
var com_google_gwt_event_dom_client_DomEvent_registered;
var com_google_gwt_lang_ClassLiteralHolder_Lcom_1google_1gwt_1event_1dom_1client_1DomEvent_12_1classLit = java_lang_Class_createForClass__Ljava_lang_String_2Ljava_lang_String_2Lcom_google_gwt_core_client_JavaScriptObject_2Ljava_lang_Class_2Ljava_lang_Class_2($intern_35, 'DomEvent', 231);
com_google_gwt_lang_Runtime_defineClass__Lcom_google_gwt_core_client_JavaScriptObject_2Lcom_google_gwt_core_client_JavaScriptObject_2Lcom_google_gwt_core_client_JavaScriptObject_2V(232, 231, {});
var com_google_gwt_lang_ClassLiteralHolder_Lcom_1google_1gwt_1event_1dom_1client_1HumanInputEvent_12_1classLit = java_lang_Class_createForClass__Ljava_lang_String_2Ljava_lang_String_2Lcom_google_gwt_core_client_JavaScriptObject_2Ljava_lang_Class_2Ljava_lang_Class_2($intern_35, 'HumanInputEvent', 232);
com_google_gwt_lang_Runtime_defineClass__Lcom_google_gwt_core_client_JavaScriptObject_2Lcom_google_gwt_core_client_JavaScriptObject_2Lcom_google_gwt_core_client_JavaScriptObject_2V(233, 232, {});
var com_google_gwt_lang_ClassLiteralHolder_Lcom_1google_1gwt_1event_1dom_1client_1MouseEvent_12_1classLit = java_lang_Class_createForClass__Ljava_lang_String_2Ljava_lang_String_2Lcom_google_gwt_core_client_JavaScriptObject_2Ljava_lang_Class_2Ljava_lang_Class_2($intern_35, 'MouseEvent', 233);
function com_google_gwt_event_dom_client_ClickEvent_$clinit__V(){
  com_google_gwt_event_dom_client_ClickEvent_$clinit__V = com_google_gwt_lang_Runtime_emptyMethod__V;
  com_google_gwt_event_dom_client_ClickEvent_TYPE = new com_google_gwt_event_dom_client_DomEvent$Type_DomEvent$Type__Ljava_lang_String_2Lcom_google_gwt_event_dom_client_DomEvent_2V(new com_google_gwt_event_dom_client_ClickEvent_ClickEvent__V);
}

function com_google_gwt_event_dom_client_ClickEvent_ClickEvent__V(){
}

com_google_gwt_lang_Runtime_defineClass__Lcom_google_gwt_core_client_JavaScriptObject_2Lcom_google_gwt_core_client_JavaScriptObject_2Lcom_google_gwt_core_client_JavaScriptObject_2V(152, 233, {}, com_google_gwt_event_dom_client_ClickEvent_ClickEvent__V);
_.dispatch__Lcom_google_gwt_event_shared_EventHandler_2V = function com_google_gwt_event_dom_client_ClickEvent_dispatch__Lcom_google_gwt_event_shared_EventHandler_2V(handler){
  com_google_gwt_lang_Cast_castTo__Ljava_lang_Object_2Lcom_google_gwt_core_client_JavaScriptObject_2Ljava_lang_Object_2(handler, 83).onClick__Lcom_google_gwt_event_dom_client_ClickEvent_2V(this);
}
;
var com_google_gwt_event_dom_client_ClickEvent_TYPE;
var com_google_gwt_lang_ClassLiteralHolder_Lcom_1google_1gwt_1event_1dom_1client_1ClickEvent_12_1classLit = java_lang_Class_createForClass__Ljava_lang_String_2Ljava_lang_String_2Lcom_google_gwt_core_client_JavaScriptObject_2Ljava_lang_Class_2Ljava_lang_Class_2($intern_35, 'ClickEvent', 152);
com_google_gwt_lang_Runtime_defineClass__Lcom_google_gwt_core_client_JavaScriptObject_2Lcom_google_gwt_core_client_JavaScriptObject_2Lcom_google_gwt_core_client_JavaScriptObject_2V(146, 1, {});
_.hashCode__I = function com_google_web_bindery_event_shared_Event$Type_hashCode__I(){
  return this.com_google_web_bindery_event_shared_Event$Type_index;
}
;
_.toString__Ljava_lang_String_2 = function com_google_web_bindery_event_shared_Event$Type_toString__Ljava_lang_String_2(){
  return 'Event type';
}
;
_.com_google_web_bindery_event_shared_Event$Type_index = 0;
var com_google_web_bindery_event_shared_Event$Type_nextHashCode = 0;
var com_google_gwt_lang_ClassLiteralHolder_Lcom_1google_1web_1bindery_1event_1shared_1Event$Type_12_1classLit = java_lang_Class_createForClass__Ljava_lang_String_2Ljava_lang_String_2Lcom_google_gwt_core_client_JavaScriptObject_2Ljava_lang_Class_2Ljava_lang_Class_2($intern_33, 'Event/Type', 146);
function com_google_gwt_event_shared_GwtEvent$Type_GwtEvent$Type__V(){
  this.com_google_web_bindery_event_shared_Event$Type_index = ++com_google_web_bindery_event_shared_Event$Type_nextHashCode;
}

com_google_gwt_lang_Runtime_defineClass__Lcom_google_gwt_core_client_JavaScriptObject_2Lcom_google_gwt_core_client_JavaScriptObject_2Lcom_google_gwt_core_client_JavaScriptObject_2V(23, 146, {}, com_google_gwt_event_shared_GwtEvent$Type_GwtEvent$Type__V);
var com_google_gwt_lang_ClassLiteralHolder_Lcom_1google_1gwt_1event_1shared_1GwtEvent$Type_12_1classLit = java_lang_Class_createForClass__Ljava_lang_String_2Ljava_lang_String_2Lcom_google_gwt_core_client_JavaScriptObject_2Ljava_lang_Class_2Ljava_lang_Class_2($intern_34, 'GwtEvent/Type', 23);
function com_google_gwt_event_dom_client_DomEvent$Type_DomEvent$Type__Ljava_lang_String_2Lcom_google_gwt_event_dom_client_DomEvent_2V(flyweight){
  var types;
  com_google_gwt_event_shared_GwtEvent$Type_GwtEvent$Type__V.call(this);
  this.com_google_gwt_event_dom_client_DomEvent$Type_flyweight = flyweight;
  !com_google_gwt_event_dom_client_DomEvent_registered && (com_google_gwt_event_dom_client_DomEvent_registered = new com_google_gwt_event_dom_client_PrivateMap_PrivateMap__V);
  types = com_google_gwt_lang_Cast_castTo__Ljava_lang_Object_2Lcom_google_gwt_core_client_JavaScriptObject_2Ljava_lang_Object_2(com_google_gwt_event_dom_client_DomEvent_registered.com_google_gwt_event_dom_client_PrivateMap_map[$intern_36], 28);
  if (!types) {
    types = new java_util_ArrayList_ArrayList__V;
    com_google_gwt_event_dom_client_PrivateMap_$unsafePut__Lcom_google_gwt_event_dom_client_PrivateMap_2Ljava_lang_String_2Ljava_lang_Object_2V(com_google_gwt_event_dom_client_DomEvent_registered, types);
  }
  types.add__Ljava_lang_Object_2Z(this);
  this.com_google_gwt_event_dom_client_DomEvent$Type_name = $intern_36;
}

com_google_gwt_lang_Runtime_defineClass__Lcom_google_gwt_core_client_JavaScriptObject_2Lcom_google_gwt_core_client_JavaScriptObject_2Lcom_google_gwt_core_client_JavaScriptObject_2V(78, 23, {78:1}, com_google_gwt_event_dom_client_DomEvent$Type_DomEvent$Type__Ljava_lang_String_2Lcom_google_gwt_event_dom_client_DomEvent_2V);
var com_google_gwt_lang_ClassLiteralHolder_Lcom_1google_1gwt_1event_1dom_1client_1DomEvent$Type_12_1classLit = java_lang_Class_createForClass__Ljava_lang_String_2Ljava_lang_String_2Lcom_google_gwt_core_client_JavaScriptObject_2Ljava_lang_Class_2Ljava_lang_Class_2($intern_35, 'DomEvent/Type', 78);
function com_google_gwt_event_dom_client_PrivateMap_$unsafeGet__Lcom_google_gwt_event_dom_client_PrivateMap_2Ljava_lang_String_2Ljava_lang_Object_2(this$static, key){
  return this$static.com_google_gwt_event_dom_client_PrivateMap_map[key];
}

function com_google_gwt_event_dom_client_PrivateMap_$unsafePut__Lcom_google_gwt_event_dom_client_PrivateMap_2Ljava_lang_String_2Ljava_lang_Object_2V(this$static, value_0){
  this$static.com_google_gwt_event_dom_client_PrivateMap_map[$intern_36] = value_0;
}

function com_google_gwt_event_dom_client_PrivateMap_PrivateMap__V(){
  this.com_google_gwt_event_dom_client_PrivateMap_map = {};
}

com_google_gwt_lang_Runtime_defineClass__Lcom_google_gwt_core_client_JavaScriptObject_2Lcom_google_gwt_core_client_JavaScriptObject_2Lcom_google_gwt_core_client_JavaScriptObject_2V(160, 1, {}, com_google_gwt_event_dom_client_PrivateMap_PrivateMap__V);
var com_google_gwt_lang_ClassLiteralHolder_Lcom_1google_1gwt_1event_1dom_1client_1PrivateMap_12_1classLit = java_lang_Class_createForClass__Ljava_lang_String_2Ljava_lang_String_2Lcom_google_gwt_core_client_JavaScriptObject_2Ljava_lang_Class_2Ljava_lang_Class_2($intern_35, 'PrivateMap', 160);
function com_google_gwt_event_logical_shared_CloseEvent_CloseEvent__Ljava_lang_Object_2ZV(){
}

function com_google_gwt_event_logical_shared_CloseEvent_fire__Lcom_google_gwt_event_logical_shared_HasCloseHandlers_2Ljava_lang_Object_2ZV(source){
  var event_0;
  if (com_google_gwt_event_logical_shared_CloseEvent_TYPE) {
    event_0 = new com_google_gwt_event_logical_shared_CloseEvent_CloseEvent__Ljava_lang_Object_2ZV;
    com_google_gwt_event_shared_HandlerManager_$fireEvent__Lcom_google_gwt_event_shared_HandlerManager_2Lcom_google_gwt_event_shared_GwtEvent_2V(source, event_0);
  }
}

com_google_gwt_lang_Runtime_defineClass__Lcom_google_gwt_core_client_JavaScriptObject_2Lcom_google_gwt_core_client_JavaScriptObject_2Lcom_google_gwt_core_client_JavaScriptObject_2V(159, 229, {}, com_google_gwt_event_logical_shared_CloseEvent_CloseEvent__Ljava_lang_Object_2ZV);
_.dispatch__Lcom_google_gwt_event_shared_EventHandler_2V = function com_google_gwt_event_logical_shared_CloseEvent_dispatch__Lcom_google_gwt_event_shared_EventHandler_2V(handler){
  com_google_gwt_lang_Cast_castTo__Ljava_lang_Object_2Lcom_google_gwt_core_client_JavaScriptObject_2Ljava_lang_Object_2(handler, 235);
  com_google_gwt_user_client_ui_RootPanel_detachWidgets__V();
}
;
_.getAssociatedType__Lcom_google_gwt_event_shared_GwtEvent$Type_2 = function com_google_gwt_event_logical_shared_CloseEvent_getAssociatedType__Lcom_google_gwt_event_shared_GwtEvent$Type_2(){
  return com_google_gwt_event_logical_shared_CloseEvent_TYPE;
}
;
var com_google_gwt_event_logical_shared_CloseEvent_TYPE;
var com_google_gwt_lang_ClassLiteralHolder_Lcom_1google_1gwt_1event_1logical_1shared_1CloseEvent_12_1classLit = java_lang_Class_createForClass__Ljava_lang_String_2Ljava_lang_String_2Lcom_google_gwt_core_client_JavaScriptObject_2Ljava_lang_Class_2Ljava_lang_Class_2('com.google.gwt.event.logical.shared', 'CloseEvent', 159);
function com_google_gwt_event_shared_HandlerManager_$addHandler__Lcom_google_gwt_event_shared_HandlerManager_2Lcom_google_gwt_event_shared_GwtEvent$Type_2Lcom_google_gwt_event_shared_EventHandler_2Lcom_google_gwt_event_shared_HandlerRegistration_2(this$static, type_0, handler){
  return com_google_web_bindery_event_shared_SimpleEventBus_$doAdd__Lcom_google_web_bindery_event_shared_SimpleEventBus_2Lcom_google_web_bindery_event_shared_Event$Type_2Ljava_lang_Object_2Ljava_lang_Object_2Lcom_google_web_bindery_event_shared_HandlerRegistration_2(this$static.com_google_gwt_event_shared_HandlerManager_eventBus, type_0, handler) , new com_google_gwt_event_shared_LegacyHandlerWrapper_LegacyHandlerWrapper__Lcom_google_web_bindery_event_shared_HandlerRegistration_2V;
}

function com_google_gwt_event_shared_HandlerManager_$fireEvent__Lcom_google_gwt_event_shared_HandlerManager_2Lcom_google_gwt_event_shared_GwtEvent_2V(this$static, event_0){
  var e, oldSource;
  !event_0.com_google_gwt_event_shared_GwtEvent_dead || (event_0.com_google_gwt_event_shared_GwtEvent_dead = false , event_0.com_google_web_bindery_event_shared_Event_source = null);
  oldSource = event_0.com_google_web_bindery_event_shared_Event_source;
  com_google_gwt_event_shared_GwtEvent_$overrideSource__Lcom_google_gwt_event_shared_GwtEvent_2Ljava_lang_Object_2V(event_0, this$static.com_google_gwt_event_shared_HandlerManager_source);
  try {
    com_google_web_bindery_event_shared_SimpleEventBus_$doFire__Lcom_google_web_bindery_event_shared_SimpleEventBus_2Lcom_google_web_bindery_event_shared_Event_2Ljava_lang_Object_2V(this$static.com_google_gwt_event_shared_HandlerManager_eventBus, event_0);
  }
   catch ($e0) {
    $e0 = com_google_gwt_lang_Exceptions_toJava__Ljava_lang_Object_2Ljava_lang_Object_2($e0);
    if (com_google_gwt_lang_Cast_instanceOf__Ljava_lang_Object_2Lcom_google_gwt_core_client_JavaScriptObject_2Z($e0, 31)) {
      e = $e0;
      throw com_google_gwt_lang_Exceptions_toJs__Ljava_lang_Object_2Ljava_lang_Object_2(new com_google_gwt_event_shared_UmbrellaException_UmbrellaException__Ljava_util_Set_2V(e.com_google_web_bindery_event_shared_UmbrellaException_causes));
    }
     else 
      throw com_google_gwt_lang_Exceptions_toJs__Ljava_lang_Object_2Ljava_lang_Object_2($e0);
  }
   finally {
    oldSource == null?(event_0.com_google_gwt_event_shared_GwtEvent_dead = true , event_0.com_google_web_bindery_event_shared_Event_source = null):(event_0.com_google_web_bindery_event_shared_Event_source = oldSource);
  }
}

function com_google_gwt_event_shared_HandlerManager_HandlerManager__Ljava_lang_Object_2V(source){
  this.com_google_gwt_event_shared_HandlerManager_eventBus = new com_google_gwt_event_shared_HandlerManager$Bus_HandlerManager$Bus__ZV;
  this.com_google_gwt_event_shared_HandlerManager_source = source;
}

com_google_gwt_lang_Runtime_defineClass__Lcom_google_gwt_core_client_JavaScriptObject_2Lcom_google_gwt_core_client_JavaScriptObject_2Lcom_google_gwt_core_client_JavaScriptObject_2V(55, 1, {}, com_google_gwt_event_shared_HandlerManager_HandlerManager__Ljava_lang_Object_2V);
var com_google_gwt_lang_ClassLiteralHolder_Lcom_1google_1gwt_1event_1shared_1HandlerManager_12_1classLit = java_lang_Class_createForClass__Ljava_lang_String_2Ljava_lang_String_2Lcom_google_gwt_core_client_JavaScriptObject_2Ljava_lang_Class_2Ljava_lang_Class_2($intern_34, 'HandlerManager', 55);
com_google_gwt_lang_Runtime_defineClass__Lcom_google_gwt_core_client_JavaScriptObject_2Lcom_google_gwt_core_client_JavaScriptObject_2Lcom_google_gwt_core_client_JavaScriptObject_2V(230, 1, {});
var com_google_gwt_lang_ClassLiteralHolder_Lcom_1google_1web_1bindery_1event_1shared_1EventBus_12_1classLit = java_lang_Class_createForClass__Ljava_lang_String_2Ljava_lang_String_2Lcom_google_gwt_core_client_JavaScriptObject_2Ljava_lang_Class_2Ljava_lang_Class_2($intern_33, 'EventBus', 230);
function com_google_web_bindery_event_shared_SimpleEventBus_$defer__Lcom_google_web_bindery_event_shared_SimpleEventBus_2Lcom_google_web_bindery_event_shared_SimpleEventBus$Command_2V(this$static, command){
  !this$static.com_google_web_bindery_event_shared_SimpleEventBus_deferredDeltas && (this$static.com_google_web_bindery_event_shared_SimpleEventBus_deferredDeltas = new java_util_ArrayList_ArrayList__V);
  java_util_ArrayList_$add__Ljava_util_ArrayList_2Ljava_lang_Object_2Z(this$static.com_google_web_bindery_event_shared_SimpleEventBus_deferredDeltas, command);
}

function com_google_web_bindery_event_shared_SimpleEventBus_$doAdd__Lcom_google_web_bindery_event_shared_SimpleEventBus_2Lcom_google_web_bindery_event_shared_Event$Type_2Ljava_lang_Object_2Ljava_lang_Object_2Lcom_google_web_bindery_event_shared_HandlerRegistration_2(this$static, type_0, handler){
  var l;
  if (!type_0) {
    throw com_google_gwt_lang_Exceptions_toJs__Ljava_lang_Object_2Ljava_lang_Object_2(new java_lang_NullPointerException_NullPointerException__Ljava_lang_String_2V('Cannot add a handler with a null type'));
  }
  this$static.com_google_web_bindery_event_shared_SimpleEventBus_firingDepth > 0?com_google_web_bindery_event_shared_SimpleEventBus_$defer__Lcom_google_web_bindery_event_shared_SimpleEventBus_2Lcom_google_web_bindery_event_shared_SimpleEventBus$Command_2V(this$static, new com_google_web_bindery_event_shared_SimpleEventBus$2_SimpleEventBus$2__Lcom_google_web_bindery_event_shared_SimpleEventBus_2V(this$static, type_0, handler)):(l = com_google_web_bindery_event_shared_SimpleEventBus_$ensureHandlerList__Lcom_google_web_bindery_event_shared_SimpleEventBus_2Lcom_google_web_bindery_event_shared_Event$Type_2Ljava_lang_Object_2Ljava_util_List_2(this$static, type_0, null) , l.add__Ljava_lang_Object_2Z(handler));
  return new com_google_web_bindery_event_shared_SimpleEventBus$1_SimpleEventBus$1__Lcom_google_web_bindery_event_shared_SimpleEventBus_2V;
}

function com_google_web_bindery_event_shared_SimpleEventBus_$doAddNow__Lcom_google_web_bindery_event_shared_SimpleEventBus_2Lcom_google_web_bindery_event_shared_Event$Type_2Ljava_lang_Object_2Ljava_lang_Object_2V(this$static, type_0, source, handler){
  var l;
  l = com_google_web_bindery_event_shared_SimpleEventBus_$ensureHandlerList__Lcom_google_web_bindery_event_shared_SimpleEventBus_2Lcom_google_web_bindery_event_shared_Event$Type_2Ljava_lang_Object_2Ljava_util_List_2(this$static, type_0, source);
  l.add__Ljava_lang_Object_2Z(handler);
}

function com_google_web_bindery_event_shared_SimpleEventBus_$doFire__Lcom_google_web_bindery_event_shared_SimpleEventBus_2Lcom_google_web_bindery_event_shared_Event_2Ljava_lang_Object_2V(this$static, event_0){
  var causes, directHandlers, e, handler, handlers, it;
  if (!event_0) {
    throw com_google_gwt_lang_Exceptions_toJs__Ljava_lang_Object_2Ljava_lang_Object_2(new java_lang_NullPointerException_NullPointerException__Ljava_lang_String_2V('Cannot fire null event'));
  }
  try {
    ++this$static.com_google_web_bindery_event_shared_SimpleEventBus_firingDepth;
    handlers = (directHandlers = com_google_web_bindery_event_shared_SimpleEventBus_$getHandlerList__Lcom_google_web_bindery_event_shared_SimpleEventBus_2Lcom_google_web_bindery_event_shared_Event$Type_2Ljava_lang_Object_2Ljava_util_List_2(this$static, event_0.getAssociatedType__Lcom_google_gwt_event_shared_GwtEvent$Type_2()) , directHandlers);
    causes = null;
    it = this$static.com_google_web_bindery_event_shared_SimpleEventBus_isReverseOrder?handlers.listIterator__ILjava_util_ListIterator_2(handlers.size__I()):handlers.listIterator__Ljava_util_ListIterator_2();
    while (this$static.com_google_web_bindery_event_shared_SimpleEventBus_isReverseOrder?it.hasPrevious__Z():it.hasNext__Z()) {
      handler = this$static.com_google_web_bindery_event_shared_SimpleEventBus_isReverseOrder?it.previous__Ljava_lang_Object_2():it.next__Ljava_lang_Object_2();
      try {
        event_0.dispatch__Lcom_google_gwt_event_shared_EventHandler_2V(com_google_gwt_lang_Cast_castTo__Ljava_lang_Object_2Lcom_google_gwt_core_client_JavaScriptObject_2Ljava_lang_Object_2(handler, 64));
      }
       catch ($e0) {
        $e0 = com_google_gwt_lang_Exceptions_toJava__Ljava_lang_Object_2Ljava_lang_Object_2($e0);
        if (com_google_gwt_lang_Cast_instanceOf__Ljava_lang_Object_2Lcom_google_gwt_core_client_JavaScriptObject_2Z($e0, 6)) {
          e = $e0;
          !causes && (causes = new java_util_HashSet_HashSet__V);
          java_util_AbstractHashMap_$put__Ljava_util_AbstractHashMap_2Ljava_lang_Object_2Ljava_lang_Object_2Ljava_lang_Object_2(causes.java_util_HashSet_map, e, causes);
        }
         else 
          throw com_google_gwt_lang_Exceptions_toJs__Ljava_lang_Object_2Ljava_lang_Object_2($e0);
      }
    }
    if (causes) {
      throw com_google_gwt_lang_Exceptions_toJs__Ljava_lang_Object_2Ljava_lang_Object_2(new com_google_web_bindery_event_shared_UmbrellaException_UmbrellaException__Ljava_util_Set_2V(causes));
    }
  }
   finally {
    --this$static.com_google_web_bindery_event_shared_SimpleEventBus_firingDepth;
    this$static.com_google_web_bindery_event_shared_SimpleEventBus_firingDepth == 0 && com_google_web_bindery_event_shared_SimpleEventBus_$handleQueuedAddsAndRemoves__Lcom_google_web_bindery_event_shared_SimpleEventBus_2V(this$static);
  }
}

function com_google_web_bindery_event_shared_SimpleEventBus_$ensureHandlerList__Lcom_google_web_bindery_event_shared_SimpleEventBus_2Lcom_google_web_bindery_event_shared_Event$Type_2Ljava_lang_Object_2Ljava_util_List_2(this$static, type_0, source){
  var handlers, sourceMap;
  sourceMap = com_google_gwt_lang_Cast_castTo__Ljava_lang_Object_2Lcom_google_gwt_core_client_JavaScriptObject_2Ljava_lang_Object_2(java_util_AbstractHashMap_$get__Ljava_util_AbstractHashMap_2Ljava_lang_Object_2Ljava_lang_Object_2(this$static.com_google_web_bindery_event_shared_SimpleEventBus_map, type_0), 65);
  if (!sourceMap) {
    sourceMap = new java_util_HashMap_HashMap__V;
    java_util_AbstractHashMap_$put__Ljava_util_AbstractHashMap_2Ljava_lang_Object_2Ljava_lang_Object_2Ljava_lang_Object_2(this$static.com_google_web_bindery_event_shared_SimpleEventBus_map, type_0, sourceMap);
  }
  handlers = com_google_gwt_lang_Cast_castTo__Ljava_lang_Object_2Lcom_google_gwt_core_client_JavaScriptObject_2Ljava_lang_Object_2(java_util_AbstractMap_getEntryValueOrNull__Ljava_util_Map$Entry_2Ljava_lang_Object_2(java_util_InternalHashCodeMap_$getEntry__Ljava_util_InternalHashCodeMap_2Ljava_lang_Object_2Ljava_util_Map$Entry_2(sourceMap.java_util_AbstractHashMap_hashCodeMap, source)), 28);
  if (!handlers) {
    handlers = new java_util_ArrayList_ArrayList__V;
    java_util_InternalHashCodeMap_$put__Ljava_util_InternalHashCodeMap_2Ljava_lang_Object_2Ljava_lang_Object_2Ljava_lang_Object_2(sourceMap.java_util_AbstractHashMap_hashCodeMap, source, handlers);
  }
  return handlers;
}

function com_google_web_bindery_event_shared_SimpleEventBus_$getHandlerList__Lcom_google_web_bindery_event_shared_SimpleEventBus_2Lcom_google_web_bindery_event_shared_Event$Type_2Ljava_lang_Object_2Ljava_util_List_2(this$static, type_0){
  var handlers, sourceMap;
  sourceMap = com_google_gwt_lang_Cast_castTo__Ljava_lang_Object_2Lcom_google_gwt_core_client_JavaScriptObject_2Ljava_lang_Object_2(java_util_AbstractHashMap_$get__Ljava_util_AbstractHashMap_2Ljava_lang_Object_2Ljava_lang_Object_2(this$static.com_google_web_bindery_event_shared_SimpleEventBus_map, type_0), 65);
  if (!sourceMap) {
    return java_util_Collections_$clinit__V() , java_util_Collections_$clinit__V() , java_util_Collections_EMPTY_1LIST;
  }
  handlers = com_google_gwt_lang_Cast_castTo__Ljava_lang_Object_2Lcom_google_gwt_core_client_JavaScriptObject_2Ljava_lang_Object_2(java_util_AbstractMap_getEntryValueOrNull__Ljava_util_Map$Entry_2Ljava_lang_Object_2(java_util_InternalHashCodeMap_$getEntry__Ljava_util_InternalHashCodeMap_2Ljava_lang_Object_2Ljava_util_Map$Entry_2(sourceMap.java_util_AbstractHashMap_hashCodeMap, null)), 28);
  if (!handlers) {
    return java_util_Collections_$clinit__V() , java_util_Collections_$clinit__V() , java_util_Collections_EMPTY_1LIST;
  }
  return handlers;
}

function com_google_web_bindery_event_shared_SimpleEventBus_$handleQueuedAddsAndRemoves__Lcom_google_web_bindery_event_shared_SimpleEventBus_2V(this$static){
  var c, c$iterator;
  if (this$static.com_google_web_bindery_event_shared_SimpleEventBus_deferredDeltas) {
    try {
      for (c$iterator = new java_util_ArrayList$1_ArrayList$1__Ljava_util_ArrayList_2V(this$static.com_google_web_bindery_event_shared_SimpleEventBus_deferredDeltas); c$iterator.java_util_ArrayList$1_i < c$iterator.java_util_ArrayList$1_this$01.java_util_ArrayList_array.length;) {
        c = com_google_gwt_lang_Cast_castTo__Ljava_lang_Object_2Lcom_google_gwt_core_client_JavaScriptObject_2Ljava_lang_Object_2(java_util_ArrayList$1_$next__Ljava_util_ArrayList$1_2Ljava_lang_Object_2(c$iterator), 236);
        com_google_web_bindery_event_shared_SimpleEventBus_$doAddNow__Lcom_google_web_bindery_event_shared_SimpleEventBus_2Lcom_google_web_bindery_event_shared_Event$Type_2Ljava_lang_Object_2Ljava_lang_Object_2V(c.com_google_web_bindery_event_shared_SimpleEventBus$2_this$01, c.com_google_web_bindery_event_shared_SimpleEventBus$2_val$type2, c.com_google_web_bindery_event_shared_SimpleEventBus$2_val$source3, c.com_google_web_bindery_event_shared_SimpleEventBus$2_val$handler4);
      }
    }
     finally {
      this$static.com_google_web_bindery_event_shared_SimpleEventBus_deferredDeltas = null;
    }
  }
}

com_google_gwt_lang_Runtime_defineClass__Lcom_google_gwt_core_client_JavaScriptObject_2Lcom_google_gwt_core_client_JavaScriptObject_2Lcom_google_gwt_core_client_JavaScriptObject_2V(147, 230, {});
_.com_google_web_bindery_event_shared_SimpleEventBus_firingDepth = 0;
_.com_google_web_bindery_event_shared_SimpleEventBus_isReverseOrder = false;
var com_google_gwt_lang_ClassLiteralHolder_Lcom_1google_1web_1bindery_1event_1shared_1SimpleEventBus_12_1classLit = java_lang_Class_createForClass__Ljava_lang_String_2Ljava_lang_String_2Lcom_google_gwt_core_client_JavaScriptObject_2Ljava_lang_Class_2Ljava_lang_Class_2($intern_33, 'SimpleEventBus', 147);
function com_google_gwt_event_shared_HandlerManager$Bus_HandlerManager$Bus__ZV(){
  this.com_google_web_bindery_event_shared_SimpleEventBus_map = new java_util_HashMap_HashMap__V;
  this.com_google_web_bindery_event_shared_SimpleEventBus_isReverseOrder = false;
}

com_google_gwt_lang_Runtime_defineClass__Lcom_google_gwt_core_client_JavaScriptObject_2Lcom_google_gwt_core_client_JavaScriptObject_2Lcom_google_gwt_core_client_JavaScriptObject_2V(148, 147, {}, com_google_gwt_event_shared_HandlerManager$Bus_HandlerManager$Bus__ZV);
var com_google_gwt_lang_ClassLiteralHolder_Lcom_1google_1gwt_1event_1shared_1HandlerManager$Bus_12_1classLit = java_lang_Class_createForClass__Ljava_lang_String_2Ljava_lang_String_2Lcom_google_gwt_core_client_JavaScriptObject_2Ljava_lang_Class_2Ljava_lang_Class_2($intern_34, 'HandlerManager/Bus', 148);
function com_google_gwt_event_shared_LegacyHandlerWrapper_LegacyHandlerWrapper__Lcom_google_web_bindery_event_shared_HandlerRegistration_2V(){
}

com_google_gwt_lang_Runtime_defineClass__Lcom_google_gwt_core_client_JavaScriptObject_2Lcom_google_gwt_core_client_JavaScriptObject_2Lcom_google_gwt_core_client_JavaScriptObject_2V(161, 1, {}, com_google_gwt_event_shared_LegacyHandlerWrapper_LegacyHandlerWrapper__Lcom_google_web_bindery_event_shared_HandlerRegistration_2V);
var com_google_gwt_lang_ClassLiteralHolder_Lcom_1google_1gwt_1event_1shared_1LegacyHandlerWrapper_12_1classLit = java_lang_Class_createForClass__Ljava_lang_String_2Ljava_lang_String_2Lcom_google_gwt_core_client_JavaScriptObject_2Ljava_lang_Class_2Ljava_lang_Class_2($intern_34, 'LegacyHandlerWrapper', 161);
function com_google_web_bindery_event_shared_UmbrellaException_UmbrellaException__Ljava_util_Set_2V(causes){
  var cause, cause$iterator, i, lastArg;
  java_lang_RuntimeException_RuntimeException__Ljava_lang_String_2Ljava_lang_Throwable_2V.call(this, (lastArg = com_google_web_bindery_event_shared_UmbrellaException_makeMessage__Ljava_util_Set_2Ljava_lang_String_2(causes) , causes.isEmpty__Z()?null:com_google_gwt_lang_Cast_castTo__Ljava_lang_Object_2Lcom_google_gwt_core_client_JavaScriptObject_2Ljava_lang_Object_2(causes.iterator__Ljava_util_Iterator_2().next__Ljava_lang_Object_2(), 6) , lastArg));
  this.com_google_web_bindery_event_shared_UmbrellaException_causes = causes;
  i = 0;
  for (cause$iterator = causes.iterator__Ljava_util_Iterator_2(); cause$iterator.hasNext__Z();) {
    cause = com_google_gwt_lang_Cast_castTo__Ljava_lang_Object_2Lcom_google_gwt_core_client_JavaScriptObject_2Ljava_lang_Object_2(cause$iterator.next__Ljava_lang_Object_2(), 6);
    if (i++ == 0) {
      continue;
    }
    java_lang_Throwable_$addSuppressed__Ljava_lang_Throwable_2Ljava_lang_Throwable_2V(this, cause);
  }
}

function com_google_web_bindery_event_shared_UmbrellaException_makeMessage__Ljava_util_Set_2Ljava_lang_String_2(causes){
  var b, count, first, t, t$iterator;
  count = causes.size__I();
  if (count == 0) {
    return null;
  }
  b = new java_lang_StringBuilder_StringBuilder__Ljava_lang_String_2V(count == 1?'Exception caught: ':count + ' exceptions caught: ');
  first = true;
  for (t$iterator = causes.iterator__Ljava_util_Iterator_2(); t$iterator.hasNext__Z();) {
    t = com_google_gwt_lang_Cast_castTo__Ljava_lang_Object_2Lcom_google_gwt_core_client_JavaScriptObject_2Ljava_lang_Object_2(t$iterator.next__Ljava_lang_Object_2(), 6);
    first?(first = false):(b.java_lang_AbstractStringBuilder_string += '; ' , b);
    java_lang_StringBuilder_$append__Ljava_lang_StringBuilder_2Ljava_lang_String_2Ljava_lang_StringBuilder_2(b, t.getMessage__Ljava_lang_String_2());
  }
  return b.java_lang_AbstractStringBuilder_string;
}

com_google_gwt_lang_Runtime_defineClass__Lcom_google_gwt_core_client_JavaScriptObject_2Lcom_google_gwt_core_client_JavaScriptObject_2Lcom_google_gwt_core_client_JavaScriptObject_2V(31, 10, $intern_37, com_google_web_bindery_event_shared_UmbrellaException_UmbrellaException__Ljava_util_Set_2V);
var com_google_gwt_lang_ClassLiteralHolder_Lcom_1google_1web_1bindery_1event_1shared_1UmbrellaException_12_1classLit = java_lang_Class_createForClass__Ljava_lang_String_2Ljava_lang_String_2Lcom_google_gwt_core_client_JavaScriptObject_2Ljava_lang_Class_2Ljava_lang_Class_2($intern_33, $intern_38, 31);
function com_google_gwt_event_shared_UmbrellaException_UmbrellaException__Ljava_util_Set_2V(causes){
  com_google_web_bindery_event_shared_UmbrellaException_UmbrellaException__Ljava_util_Set_2V.call(this, causes);
}

com_google_gwt_lang_Runtime_defineClass__Lcom_google_gwt_core_client_JavaScriptObject_2Lcom_google_gwt_core_client_JavaScriptObject_2Lcom_google_gwt_core_client_JavaScriptObject_2V(88, 31, $intern_37, com_google_gwt_event_shared_UmbrellaException_UmbrellaException__Ljava_util_Set_2V);
var com_google_gwt_lang_ClassLiteralHolder_Lcom_1google_1gwt_1event_1shared_1UmbrellaException_12_1classLit = java_lang_Class_createForClass__Ljava_lang_String_2Ljava_lang_String_2Lcom_google_gwt_core_client_JavaScriptObject_2Ljava_lang_Class_2Ljava_lang_Class_2($intern_34, $intern_38, 88);
function com_google_gwt_i18n_client_BidiUtils_getDirectionOnElement__Lcom_google_gwt_dom_client_Element_2Lcom_google_gwt_i18n_client_HasDirection$Direction_2(elem){
  var dirPropertyValue;
  dirPropertyValue = elem['dir'] == null?null:String(elem['dir']);
  if (java_lang_String_$equalsIgnoreCase__Ljava_lang_String_2Ljava_lang_String_2Z('rtl', dirPropertyValue)) {
    return com_google_gwt_i18n_client_HasDirection$Direction_$clinit__V() , com_google_gwt_i18n_client_HasDirection$Direction_RTL;
  }
   else if (java_lang_String_$equalsIgnoreCase__Ljava_lang_String_2Ljava_lang_String_2Z('ltr', dirPropertyValue)) {
    return com_google_gwt_i18n_client_HasDirection$Direction_$clinit__V() , com_google_gwt_i18n_client_HasDirection$Direction_LTR;
  }
  return com_google_gwt_i18n_client_HasDirection$Direction_$clinit__V() , com_google_gwt_i18n_client_HasDirection$Direction_DEFAULT;
}

function com_google_gwt_i18n_client_BidiUtils_setDirectionOnElement__Lcom_google_gwt_dom_client_Element_2Lcom_google_gwt_i18n_client_HasDirection$Direction_2V(elem, direction){
  switch (direction.java_lang_Enum_ordinal) {
    case 0:
      {
        elem['dir'] = 'rtl';
        break;
      }

    case 1:
      {
        elem['dir'] = 'ltr';
        break;
      }

    case 2:
      {
        com_google_gwt_i18n_client_BidiUtils_getDirectionOnElement__Lcom_google_gwt_dom_client_Element_2Lcom_google_gwt_i18n_client_HasDirection$Direction_2(elem) != (com_google_gwt_i18n_client_HasDirection$Direction_$clinit__V() , com_google_gwt_i18n_client_HasDirection$Direction_DEFAULT) && (elem['dir'] = '' , undefined);
        break;
      }

  }
}

function com_google_gwt_i18n_client_HasDirection$Direction_$clinit__V(){
  com_google_gwt_i18n_client_HasDirection$Direction_$clinit__V = com_google_gwt_lang_Runtime_emptyMethod__V;
  com_google_gwt_i18n_client_HasDirection$Direction_RTL = new com_google_gwt_i18n_client_HasDirection$Direction_HasDirection$Direction__Ljava_lang_String_2IV('RTL', 0);
  com_google_gwt_i18n_client_HasDirection$Direction_LTR = new com_google_gwt_i18n_client_HasDirection$Direction_HasDirection$Direction__Ljava_lang_String_2IV('LTR', 1);
  com_google_gwt_i18n_client_HasDirection$Direction_DEFAULT = new com_google_gwt_i18n_client_HasDirection$Direction_HasDirection$Direction__Ljava_lang_String_2IV($intern_39, 2);
}

function com_google_gwt_i18n_client_HasDirection$Direction_HasDirection$Direction__Ljava_lang_String_2IV(enum$name, enum$ordinal){
  java_lang_Enum_Enum__Ljava_lang_String_2IV.call(this, enum$name, enum$ordinal);
}

function com_google_gwt_i18n_client_HasDirection$Direction_values___3Lcom_google_gwt_i18n_client_HasDirection$Direction_2(){
  com_google_gwt_i18n_client_HasDirection$Direction_$clinit__V();
  return com_google_gwt_lang_Array_stampJavaTypeInfo__Ljava_lang_Class_2Lcom_google_gwt_core_client_JavaScriptObject_2Lcom_google_gwt_core_client_JavaScriptObject_2ILjava_lang_Object_2Ljava_lang_Object_2(com_google_gwt_lang_Array_getClassLiteralForArray__Ljava_lang_Class_2ILjava_lang_Class_2(com_google_gwt_lang_ClassLiteralHolder_Lcom_1google_1gwt_1i18n_1client_1HasDirection$Direction_12_1classLit, 1), $intern_5, 54, 0, [com_google_gwt_i18n_client_HasDirection$Direction_RTL, com_google_gwt_i18n_client_HasDirection$Direction_LTR, com_google_gwt_i18n_client_HasDirection$Direction_DEFAULT]);
}

com_google_gwt_lang_Runtime_defineClass__Lcom_google_gwt_core_client_JavaScriptObject_2Lcom_google_gwt_core_client_JavaScriptObject_2Lcom_google_gwt_core_client_JavaScriptObject_2V(54, 12, $intern_4, com_google_gwt_i18n_client_HasDirection$Direction_HasDirection$Direction__Ljava_lang_String_2IV);
var com_google_gwt_i18n_client_HasDirection$Direction_DEFAULT, com_google_gwt_i18n_client_HasDirection$Direction_LTR, com_google_gwt_i18n_client_HasDirection$Direction_RTL;
var com_google_gwt_lang_ClassLiteralHolder_Lcom_1google_1gwt_1i18n_1client_1HasDirection$Direction_12_1classLit = java_lang_Class_createForEnum__Ljava_lang_String_2Ljava_lang_String_2Lcom_google_gwt_core_client_JavaScriptObject_2Ljava_lang_Class_2Lcom_google_gwt_core_client_JavaScriptObject_2Lcom_google_gwt_core_client_JavaScriptObject_2Ljava_lang_Class_2('com.google.gwt.i18n.client', 'HasDirection/Direction', 54, com_google_gwt_i18n_client_HasDirection$Direction_values___3Lcom_google_gwt_i18n_client_HasDirection$Direction_2);
function com_google_gwt_lang_Array_canSet__Ljava_lang_Object_2Ljava_lang_Object_2Z(array, value_0){
  var elementTypeCategory;
  switch (com_google_gwt_lang_Array_getElementTypeCategory__Ljava_lang_Object_2I(array)) {
    case 6:
      return com_google_gwt_lang_Cast_instanceOfString__Ljava_lang_Object_2Z(value_0);
    case 7:
      return com_google_gwt_lang_Cast_instanceOfDouble__Ljava_lang_Object_2Z(value_0);
    case 8:
      return com_google_gwt_lang_Cast_instanceOfBoolean__Ljava_lang_Object_2Z(value_0);
    case 3:
      return Array.isArray(value_0) && (elementTypeCategory = com_google_gwt_lang_Array_getElementTypeCategory__Ljava_lang_Object_2I(value_0) , !(elementTypeCategory >= 14 && elementTypeCategory <= 16));
    case 11:
      return value_0 != null && typeof value_0 === $intern_1;
    case 12:
      return value_0 != null && (typeof value_0 === $intern_0 || typeof value_0 == $intern_1);
    case 0:
      return com_google_gwt_lang_Cast_canCast__Ljava_lang_Object_2Lcom_google_gwt_core_client_JavaScriptObject_2Z(value_0, array.__elementTypeId$);
    case 2:
      return com_google_gwt_lang_Cast_isJsObjectOrFunction__Ljava_lang_Object_2Z(value_0) && !(value_0.java_lang_Object_typeMarker === com_google_gwt_lang_Runtime_typeMarkerFn__V);
    case 1:
      return com_google_gwt_lang_Cast_isJsObjectOrFunction__Ljava_lang_Object_2Z(value_0) && !(value_0.java_lang_Object_typeMarker === com_google_gwt_lang_Runtime_typeMarkerFn__V) || com_google_gwt_lang_Cast_canCast__Ljava_lang_Object_2Lcom_google_gwt_core_client_JavaScriptObject_2Z(value_0, array.__elementTypeId$);
    default:return true;
  }
}

function com_google_gwt_lang_Array_getClassLiteralForArray__Ljava_lang_Class_2ILjava_lang_Class_2(clazz, dimensions){
  return java_lang_Class_getClassLiteralForArray__Ljava_lang_Class_2ILjava_lang_Class_2(clazz, dimensions);
}

function com_google_gwt_lang_Array_getElementTypeCategory__Ljava_lang_Object_2I(array){
  return array.__elementTypeCategory$ == null?10:array.__elementTypeCategory$;
}

function com_google_gwt_lang_Array_initUnidimensionalArray__Ljava_lang_Class_2Lcom_google_gwt_core_client_JavaScriptObject_2Lcom_google_gwt_core_client_JavaScriptObject_2IIILjava_lang_Object_2(leafClassLiteral, castableTypeMap, elementTypeId, length_0, elementTypeCategory, dimensions){
  var result;
  result = com_google_gwt_lang_Array_initializeArrayElementsWithDefaults__IILjava_lang_Object_2(elementTypeCategory, length_0);
  elementTypeCategory != 10 && com_google_gwt_lang_Array_stampJavaTypeInfo__Ljava_lang_Class_2Lcom_google_gwt_core_client_JavaScriptObject_2Lcom_google_gwt_core_client_JavaScriptObject_2ILjava_lang_Object_2Ljava_lang_Object_2(com_google_gwt_lang_Array_getClassLiteralForArray__Ljava_lang_Class_2ILjava_lang_Class_2(leafClassLiteral, dimensions), castableTypeMap, elementTypeId, elementTypeCategory, result);
  return result;
}

function com_google_gwt_lang_Array_initializeArrayElementsWithDefaults__IILjava_lang_Object_2(elementTypeCategory, length_0){
  var array = new Array(length_0);
  var initValue;
  switch (elementTypeCategory) {
    case 14:
    case 15:
      initValue = 0;
      break;
    case 16:
      initValue = false;
      break;
    default:return array;
  }
  for (var i = 0; i < length_0; ++i) {
    array[i] = initValue;
  }
  return array;
}

function com_google_gwt_lang_Array_isJavaArray__Ljava_lang_Object_2Z(src_0){
  return Array.isArray(src_0) && src_0.java_lang_Object_typeMarker === com_google_gwt_lang_Runtime_typeMarkerFn__V;
}

function com_google_gwt_lang_Array_setCheck__Ljava_lang_Object_2ILjava_lang_Object_2Ljava_lang_Object_2(array, index_0, value_0){
  javaemul_internal_InternalPreconditions_checkCriticalArrayType__ZV(value_0 == null || com_google_gwt_lang_Array_canSet__Ljava_lang_Object_2Ljava_lang_Object_2Z(array, value_0));
  return array[index_0] = value_0;
}

function com_google_gwt_lang_Array_stampJavaTypeInfo__Ljava_lang_Class_2Lcom_google_gwt_core_client_JavaScriptObject_2Lcom_google_gwt_core_client_JavaScriptObject_2ILjava_lang_Object_2Ljava_lang_Object_2(arrayClass, castableTypeMap, elementTypeId, elementTypeCategory, array){
  array.java_lang_Object__1_1_1clazz = arrayClass;
  array.java_lang_Object_castableTypeMap = castableTypeMap;
  array.java_lang_Object_typeMarker = com_google_gwt_lang_Runtime_typeMarkerFn__V;
  array.__elementTypeId$ = elementTypeId;
  array.__elementTypeCategory$ = elementTypeCategory;
  return array;
}

function com_google_gwt_lang_Exceptions_toJava__Ljava_lang_Object_2Ljava_lang_Object_2(e){
  var javaException;
  if (com_google_gwt_lang_Cast_instanceOf__Ljava_lang_Object_2Lcom_google_gwt_core_client_JavaScriptObject_2Z(e, 6)) {
    return e;
  }
  javaException = e && e[$intern_26];
  if (!javaException) {
    javaException = new com_google_gwt_core_client_JavaScriptException_JavaScriptException__Ljava_lang_Object_2V(e);
    com_google_gwt_core_client_impl_StackTraceCreator_captureStackTrace__Ljava_lang_Object_2V(javaException);
  }
  return javaException;
}

function com_google_gwt_lang_Exceptions_toJs__Ljava_lang_Object_2Ljava_lang_Object_2(t){
  return t.java_lang_Throwable_backingJsObject;
}

function com_google_gwt_lang_com_100046flair_100046FLAIR_1_1EntryMethodHolder_init__V(){
  var org_gwtbootstrap3_client_GwtBootstrap3ClientBundle_1default_1InlineClientBundleGenerator$2_$getText__Lorg_gwtbootstrap3_client_GwtBootstrap3ClientBundle_1default_1InlineClientBundleGenerator$2_2Ljava_lang_String_2_builder_0, org_gwtbootstrap3_client_GwtBootstrap3ClientBundle_1default_1InlineClientBundleGenerator$1_$getText__Lorg_gwtbootstrap3_client_GwtBootstrap3ClientBundle_1default_1InlineClientBundleGenerator$1_2Ljava_lang_String_2_builder_0;
  $wnd.setTimeout($entry(com_google_gwt_useragent_client_UserAgentAsserter_assertCompileTimeUserAgent__V));
  com_google_gwt_user_client_DocumentModeAsserter_$onModuleLoad__Lcom_google_gwt_user_client_DocumentModeAsserter_2V();
  typeof $wnd['jQuery'] !== 'undefined' || com_google_gwt_core_client_ScriptInjector$FromString_$inject__Lcom_google_gwt_core_client_ScriptInjector$FromString_2Lcom_google_gwt_core_client_JavaScriptObject_2(com_google_gwt_core_client_ScriptInjector$FromString_$setWindow__Lcom_google_gwt_core_client_ScriptInjector$FromString_2Lcom_google_gwt_core_client_JavaScriptObject_2Lcom_google_gwt_core_client_ScriptInjector$FromString_2((com_google_gwt_core_client_ScriptInjector_$clinit__V() , new com_google_gwt_core_client_ScriptInjector$FromString_ScriptInjector$FromString__Ljava_lang_String_2V((org_gwtbootstrap3_client_GwtBootstrap3ClientBundle_1default_1InlineClientBundleGenerator$2_$getText__Lorg_gwtbootstrap3_client_GwtBootstrap3ClientBundle_1default_1InlineClientBundleGenerator$2_2Ljava_lang_String_2_builder_0 = new java_lang_StringBuilder_StringBuilder__V , org_gwtbootstrap3_client_GwtBootstrap3ClientBundle_1default_1InlineClientBundleGenerator$2_$getText__Lorg_gwtbootstrap3_client_GwtBootstrap3ClientBundle_1default_1InlineClientBundleGenerator$2_2Ljava_lang_String_2_builder_0.java_lang_AbstractStringBuilder_string += '/*! jQuery v1.12.4 | (c) jQuery Foundation | jquery.org/license */\n!function(a,b){"object"==typeof module&&"object"==typeof module.exports?module.exports=a.document?b(a,!0):function(a){if(!a.document)throw new Error("jQuery requires a window with a document");return b(a)}:b(a)}("undefined"!=typeof window?window:this,function(a,b){var c=[],d=a.document,e=c.slice,f=c.concat,g=c.push,h=c.indexOf,i={},j=i.toString,k=i.hasOwnProperty,l={},m="1.12.4",n=function(a,b){return new n.fn.init(a,b)},o=/^[\\s\\uFEFF\\xA0]+|[\\s\\uFEFF\\xA0]+$/g,p=/^-ms-/,q=/-([\\da-z])/gi,r=function(a,b){return b.toUpperCase()};n.fn=n.prototype={jquery:m,constructor:n,selector:"",length:0,toArray:function(){return e.call(this)},get:function(a){return null!=a?0>a?this[a+this.length]:this[a]:e.call(this)},pushStack:function(a){var b=n.merge(this.constructor(),a);return b.prevObject=this,b.context=this.context,b},each:function(a){return n.each(this,a)},map:function(a){return this.pushStack(n.map(this,function(b,c){return a.call(b,c,b)}))},slice:function(){return this.pushStack(e.apply(this,arguments))},first:function(){return this.eq(0)},last:function(){return this.eq(-1)},eq:function(a){var b=this.length,c=+a+(0>a?b:0);return this.pushStack(c>=0&&b>c?[this[c]]:[])},end:function(){return this.prevObject||this.constructor()},push:g,sort:c.sort,splice:c.splice},n.extend=n.fn.extend=function(){var a,b,c,d,e,f,g=arguments[0]||{},h=1,i=arguments.length,j=!1;for("boolean"==typeof g&&(j=g,g=arguments[h]||{},h++),"object"==typeof g||n.isFunction(g)||(g={}),h===i&&(g=this,h--);i>h;h++)if(null!=(e=arguments[h]))for(d in e)a=g[d],c=e[d],g!==c&&(j&&c&&(n.isPlainObject(c)||(b=n.isArray(c)))?(b?(b=!1,f=a&&n.isArray(a)?a:[]):f=a&&n.isPlainObject(a)?a:{},g[d]=n.extend(j,f,c)):void 0!==c&&(g[d]=c));return g},n.extend({expando:"jQuery"+(m+Math.random()).replace(/\\D/g,""),isReady:!0,error:function(a){throw new Error(a)},noop:function(){},isFunction:function(a){return"function"===n.type(a)},isArray:Array.isArray||function(a){return"array"===n.type(a)},isWindow:function(a){return null!=a&&a==a.window},isNumeric:function(a){var b=a&&a.toString();return!n.isArray(a)&&b-parseFloat(b)+1>=0},isEmptyObject:function(a){var b;for(b in a)return!1;return!0},isPlainObject:function(a){var b;if(!a||"object"!==n.type(a)||a.nodeType||n.isWindow(a))return!1;try{if(a.constructor&&!k.call(a,"constructor")&&!k.call(a.constructor.prototype,"isPrototypeOf"))return!1}catch(c){return!1}if(!l.ownFirst)for(b in a)return k.call(a,b);for(b in a);return void 0===b||k.call(a,b)},type:function(a){return null==a?a+"":"object"==typeof a||"function"==typeof a?i[j.call(a)]||"object":typeof a},globalEval:function(b){b&&n.trim(b)&&(a.execScript||function(b){a.eval.call(a,b)})(b)},camelCase:function(a){return a.replace(p,"ms-").replace(q,r)},nodeName:function(a,b){return a.nodeName&&a.nodeName.toLowerCase()===b.toLowerCase()},each:function(a,b){var c,d=0;if(s(a)){for(c=a.length;c>d;d++)if(b.call(a[d],d,a[d])===!1)break}else for(d in a)if(b.call(a[d],d,a[d])===!1)break;return a},trim:function(a){return null==a?"":(a+"").replace(o,"")},makeArray:function(a,b){var c=b||[];return null!=a&&(s(Object(a))?n.merge(c,"string"==typeof a?[a]:a):g.call(c,a)),c},inArray:function(a,b,c){var d;if(b){if(h)return h.call(b,a,c);for(d=b.length,c=c?0>c?Math.max(0,d+c):c:0;d>c;c++)if(c in b&&b[c]===a)return c}return-1},merge:function(a,b){var c=+b.length,d=0,e=a.length;while(c>d)a[e++]=b[d++];if(c!==c)while(void 0!==b[d])a[e++]=b[d++];return a.length=e,a},grep:function(a,b,c){for(var d,e=[],f=0,g=a.length,h=!c;g>f;f++)d=!b(a[f],f),d!==h&&e.push(a[f]);return e},map:function(a,b,c){var d,e,g=0,h=[];if(s(a))for(d=a.length;d>g;g++)e=b(a[g],g,c),null!=e&&h.push(e);else for(g in a)e=b(a[g],g,c),null!=e&&h.push(e);return f.apply([],h)},guid:1,proxy:function(a,b){var c,d,f;return"string"==typeof b&&(f=a[b],b=a,a=f),n.isFunction(a)?(c=e.call(arguments,2),d=function(){return a.apply(b||this,c.concat(e.call(arguments)))},d.guid=a.guid=a.guid||n.guid++,d):void 0},now:function(){return+new Date},support:l}),"function"==typeof Symbol&&(n.fn[Symbol.iterator]=c[Symbol.iterator]),n.each("Boolean Number String Function Array Date RegExp Object Error Symbol".split(" "),function(a,b){i["[object "+b+"]"]=b.toLowerCase()});function s(a){var b=!!a&&"length"in a&&a.length,c=n.type(a);return"function"===c||n.isWindow(a)?!1:"array"===c||0===b||"number"==typeof b&&b>0&&b-1 in a}var t=function(a){var b,c,d,e,f,g,h,i,j,k,l,m,n,o,p,q,r,s,t,u="sizzle"+1*new Date,v=a.document,w=0,x=0,y=ga(),z=ga(),A=ga(),B=function(a,b){return a===b&&(l=!0),0},C=1<<31,D={}.hasOwnProperty,E=[],F=E.pop,G=E.push,H=E.push,I=E.slice,J=function(a,b){for(var c=0,d=a.length;d>c;c++)if(a[c]===b)return c;return-1},K="checked|selected|async|autofocus|autoplay|controls|defer|disabled|hidden|ismap|loop|multiple|open|readonly|required|scoped",L="[\\\\x20\\\\t\\\\r\\\\n\\\\f]",M="(?:\\\\\\\\.|[\\\\w-]|[^\\\\x00-\\\\xa0])+",N="\\\\["+L+"*("+M+")(?:"+L+"*([*^$|!~]?=)"+L+"*(?:\'((?:\\\\\\\\.|[^\\\\\\\\\'])*)\'|\\"((?:\\\\\\\\.|[^\\\\\\\\\\"])*)\\"|("+M+"))|)"+L+"*\\\\]",O=":("+M+")(?:\\\\(((\'((?:\\\\\\\\.|[^\\\\\\\\\'])*)\'|\\"((?:\\\\\\\\.|[^\\\\\\\\\\"])*)\\")|((?:\\\\\\\\.|[^\\\\\\\\()[\\\\]]|"+N+")*)|.*)\\\\)|)",P=new RegExp(L+"+","g"),Q=new RegExp("^"+L+"+|((?:^|[^\\\\\\\\])(?:\\\\\\\\.)*)"+L+"+$","g"),R=new RegExp("^"+L+"*,"+L+"*"),S=new RegExp("^"+L+"*([>+~]|"+L+")"+L+"*"),T=new RegExp("="+L+"*([^\\\\]\'\\"]*?)"+L+"*\\\\]","g"),U=new RegExp(O),V=new RegExp("^"+M+"$"),W={ID:new RegExp("^#("+M+")"),CLASS:new RegExp("^\\\\.("+M+")"),TAG:new RegExp("^("+M+"|[*])"),ATTR:new RegExp("^"+N),PSEUDO:new RegExp("^"+O),CHILD:new RegExp("^:(only|first|last|nth|nth-last)-(child|of-type)(?:\\\\("+L+"*(even|odd|(([+-]|)(\\\\d*)n|)"+L+"*(?:([+-]|)"+L+"*(\\\\d+)|))"+L+"*\\\\)|)","i"),bool:new RegExp("^(?:"+K+")$","i"),needsContext:new RegExp("^"+L+"*[>+~]|:(even|odd|eq|gt|lt|nth|first|last)(?:\\\\("+L+"*((?:-\\\\d)?\\\\d*)"+L+"*\\\\)|)(?=[^-]|$)","i")},X=/^(?:input|select|textarea|button)$/i,Y=/^h\\d$/i,Z=/^[^{]+\\{\\s*\\[native \\w/,$=/^(?:#([\\w-]+)|(\\w+)|\\.([\\w-]+))$/,_=/[+~]/,aa=/\'|\\\\/g,ba=new RegExp("\\\\\\\\([\\\\da-f]{1,6}"+L+"?|("+L+")|.)","ig"),ca=function(a,b,c){var d="0x"+b-65536;return d!==d||c?b:0>d?String.fromCharCode(d+65536):String.fromCharCode(d>>10|55296,1023&d|56320)},da=function(){m()};try{H.apply(E=I.call(v.childNodes),v.childNodes),E[v.childNodes.length].nodeType}catch(ea){H={apply:E.length?function(a,b){G.apply(a,I.call(b))}:function(a,b){var c=a.length,d=0;while(a[c++]=b[d++]);a.length=c-1}}}function fa(a,b,d,e){var f,h,j,k,l,o,r,s,w=b&&b.ownerDocument,x=b?b.nodeType:9;if(d=d||[],"string"!=typeof a||!a||1!==x&&9!==x&&11!==x)return d;if(!e&&((b?b.ownerDocument||b:v)!==n&&m(b),b=b||n,p)){if(11!==x&&(o=$.exec(a)))if(f=o[1]){if(9===x){if(!(j=b.getElementById(f)))return d;if(j.id===f)return d.push(j),d}else if(w&&(j=w.getElementById(f))&&t(b,j)&&j.id===f)return d.push(j),d}else{if(o[2])return H.apply(d,b.getElementsByTagName(a)),d;if((f=o[3])&&c.getElementsByClassName&&b.getElementsByClassName)return H.apply(d,b.getElementsByClassName(f)),d}if(c.qsa&&!A[a+" "]&&(!q||!q.test(a))){if(1!==x)w=b,s=a;else if("object"!==b.nodeName.toLowerCase()){(k=b.getAttribute("id"))?k=k.replace(aa,"\\\\$&"):b.setAttribute("id",k=u),r=g(a),h=r.length,l=V.test(k)?"#"+k:"[id=\'"+k+"\']";while(h--)r[h]=l+" "+qa(r[h]);s=r.join(","),w=_.test(a)&&oa(b.parentNode)||b}if(s)try{return H.apply(d,w.querySelectorAll(s)),d}catch(y){}finally{k===u&&b.removeAttribute("id")}}}return i(a.replace(Q,"$1"),b,d,e)}function ga(){var a=[];function b(c,e){return a.push(c+" ")>d.cacheLength&&delete b[a.shift()],b[c+" "]=e}return b}function ha(a){return a[u]=!0,a}function ia(a){var b=n.createElement("div");try{return!!a(b)}catch(c){return!1}finally{b.parentNode&&b.parentNode.removeChild(b),b=null}}function ja(a,b){var c=a.split("|"),e=c.length;while(e--)d.attrHandle[c[e]]=b}function ka(a,b){var c=b&&a,d=c&&1===a.nodeType&&1===b.nodeType&&(~b.sourceIndex||C)-(~a.sourceIndex||C);if(d)return d;if(c)while(c=c.nextSibling)if(c===b)return-1;return a?1:-1}function la(a){return function(b){var c=b.nodeName.toLowerCase();return"input"===c&&b.type===a}}function ma(a){return function(b){var c=b.nodeName.toLowerCase();return("input"===c||"button"===c)&&b.type===a}}function na(a){return ha(function(b){return b=+b,ha(function(c,d){var e,f=a([],c.length,b),g=f.length;while(g--)c[e=f[g]]&&(c[e]=!(d[e]=c[e]))})})}function oa(a){return a&&"undefined"!=typeof a.getElementsByTagName&&a}c=fa.support={},f=fa.isXML=function(a){var b=a&&(a.ownerDocument||a).documentElement;return b?"HTML"!==b.nodeName:!1},m=fa.setDocument=function(a){var b,e,g=a?a.ownerDocument||a:v;return g!==n&&9===g.nodeType&&g.documentElement?(n=g,o=n.documentElement,p=!f(n),(e=n.defaultView)&&e.top!==e&&(e.addEventListener?e.addEventListener("unload",da,!1):e.attachEvent&&e.attachEvent("onunload",da)),c.attributes=ia(function(a){return a.className="i",!a.getAttribute("className")}),c.getElementsByTagName=ia(function(a){return a.appendChild(n.createComment("")),!a.getElementsByTagName("*").length}),c.getElementsByClassName=Z.test(n.getElementsByClassName),c.getById=ia(function(a){return o.appendChild(a).id=u,!n.getElementsByName||!n.getElementsByName(u).length}),c.getById?(d.find.ID=function(a,b){if("undefined"!=typeof b.getElementById&&p){var c=b.getElementById(a);return c?[c]:[]}},d.filter.ID=function(a){var b=a.replace(ba,ca);return function(a){return a.getAttribute("id")===b}}):(delete d.find.ID,d.filter.ID=function(a){var b=a.replace(ba,ca);return function(a){var c="undefined"!=typeof a.getAttributeNode&&a.getAttributeNode("id");return c&&c.value===b}}),d.find.TAG=c.getElementsByTagName?function(a,b){return"undefined"!=typeof b.getElementsByTagName?b.getElementsByTagName(a):c.qsa?b.querySelectorAll(a):void 0}:function(a,b){var c,d=[],e=0,f=b.getElementsByTagName(a);if("*"===a){while(c=f[e++])1===c.nodeType&&d.push(c);return d}return f},d.find.CLASS=c.getElementsByClassName&&function(a,b){return"undefined"!=typeof b.getElementsByClassName&&p?b.getElementsByClassName(a):void 0},r=[],q=[],(c.qsa=Z.test(n.querySelectorAll))&&(ia(function(a){o.appendChild(a).innerHTML="<a id=\'"+u+"\'><\/a><select id=\'"+u+"-\\r\\\\\' msallowcapture=\'\'><option selected=\'\'><\/option><\/select>",a.querySelectorAll("[msallowcapture^=\'\']").length&&q.push("[*^$]="+L+"*(?:\'\'|\\"\\")"),a.querySelectorAll("[selected]").length||q.push("\\\\["+L+"*(?:value|"+K+")"),a.querySelectorAll("[id~="+u+"-]").length||q.push("~="),a.querySelectorAll(":checked").length||q.push(":checked"),a.querySelectorAll("a#"+u+"+*").length||q.push(".#.+[+~]")}),ia(function(a){var b=n.createElement("input");b.setAttribute("type","hidden"),a.appendChild(b).setAttribute("name","D"),a.querySelectorAll("[name=d]").length&&q.push("name"+L+"*[*^$|!~]?="),a.querySelectorAll(":enabled").length||q.push(":enabled",":disabled"),a.querySelectorAll("*,:x"),q.push(",.*:")})),(c.matchesSelector=Z.test(s=o.matches||o.webkitMatchesSelector||o.mozMatchesSelector||o.oMatchesSelector||o.msMatchesSelector))&&ia(function(a){c.disconnectedMatch=s.call(a,"div"),s.call(a,"[s!=\'\']:x"),r.push("!=",O)}),q=q.length&&new RegExp(q.join("|")),r=r.length&&new RegExp(r.join("|")),b=Z.test(o.compareDocumentPosition),t=b||Z.test(o.contains)?function(a,b){var c=9===a.nodeType?a.documentElement:a,d=b&&b.parentNode;return a===d||!(!d||1!==d.nodeType||!(c.contains?c.contains(d):a.compareDocumentPosition&&16&a.compareDocumentPosition(d)))}:function(a,b){if(b)while(b=b.parentNode)if(b===a)return!0;return!1},B=b?function(a,b){if(a===b)return l=!0,0;var d=!a.compareDocumentPosition-!b.compareDocumentPosition;return d?d:(d=(a.ownerDocument||a)===(b.ownerDocument||b)?a.compareDocumentPosition(b):1,1&d||!c.sortDetached&&b.compareDocumentPosition(a)===d?a===n||a.ownerDocument===v&&t(v,a)?-1:b===n||b.ownerDocument===v&&t(v,b)?1:k?J(k,a)-J(k,b):0:4&d?-1:1)}:function(a,b){if(a===b)return l=!0,0;var c,d=0,e=a.parentNode,f=b.parentNode,g=[a],h=[b];if(!e||!f)return a===n?-1:b===n?1:e?-1:f?1:k?J(k,a)-J(k,b):0;if(e===f)return ka(a,b);c=a;while(c=c.parentNode)g.unshift(c);c=b;while(c=c.parentNode)h.unshift(c);while(g[d]===h[d])d++;return d?ka(g[d],h[d]):g[d]===v?-1:h[d]===v?1:0},n):n},fa.matches=function(a,b){return fa(a,null,null,b)},fa.matchesSelector=function(a,b){if((a.ownerDocument||a)!==n&&m(a),b=b.replace(T,"=\'$1\']"),c.matchesSelector&&p&&!A[b+" "]&&(!r||!r.test(b))&&(!q||!q.test(b)))try{var d=s.call(a,b);if(d||c.disconnectedMatch||a.document&&11!==a.document.nodeType)return d}catch(e){}return fa(b,n,null,[a]).length>0},fa.contains=function(a,b){return(a.ownerDocument||a)!==n&&m(a),t(a,b)},fa.attr=function(a,b){(a.ownerDocument||a)!==n&&m(a);var e=d.attrHandle[b.toLowerCase()],f=e&&D.call(d.attrHandle,b.toLowerCase())?e(a,b,!p):void 0;return void 0!==f?f:c.attributes||!p?a.getAttribute(b):(f=a.getAttributeNode(b))&&f.specified?f.value:null},fa.error=function(a){throw new Error("Syntax error, unrecognized expression: "+a)},fa.uniqueSort=function(a){var b,d=[],e=0,f=0;if(l=!c.detectDuplicates,k=!c.sortStable&&a.slice(0),a.sort(B),l){while(b=a[f++])b===a[f]&&(e=d.push(f));while(e--)a.splice(d[e],1)}return k=null,a},e=fa.getText=function(a){var b,c="",d=0,f=a.nodeType;if(f){if(1===f||9===f||11===f){if("string"==typeof a.textContent)return a.textContent;for(a=a.firstChild;a;a=a.nextSibling)c+=e(a)}else if(3===f||4===f)return a.nodeValue}else while(b=a[d++])c+=e(b);return c},d=fa.selectors={cacheLength:50,createPseudo:ha,match:W,attrHandle:{},find:{},relative:{">":{dir:"parentNode",first:!0}," ":{dir:"parentNode"},"+":{dir:"previousSibling",first:!0},"~":{dir:"previousSibling"}},preFilter:{ATTR:function(a){return a[1]=a[1].replace(ba,ca),a[3]=(a[3]||a[4]||a[5]||"").replace(ba,ca),"~="===a[2]&&(a[3]=" "+a[3]+" "),a.slice(0,4)},CHILD:function(a){return a[1]=a[1].toLowerCase(),"nth"===a[1].slice(0,3)?(a[3]||fa.error(a[0]),a[4]=+(a[4]?a[5]+(a[6]||1):2*("even"===a[3]||"odd"===a[3])),a[5]=+(a[7]+a[8]||"odd"===a[3])):a[3]&&fa.error(a[0]),a},PSEUDO:function(a){var b,c=!a[6]&&a[2];return W.CHILD.test(a[0])?null:(a[3]?a[2]=a[4]||a[5]||"":c&&U.test(c)&&(b=g(c,!0))&&(b=c.indexOf(")",c.length-b)-c.length)&&(a[0]=a[0].slice(0,b),a[2]=c.slice(0,b)),a.slice(0,3))}},filter:{TAG:function(a){var b=a.replace(ba,ca).toLowerCase();return"*"===a?function(){return!0}:function(a){return a.nodeName&&a.nodeName.toLowerCase()===b}},CLASS:function(a){var b=y[a+" "];return b||(b=new RegExp("(^|"+L+")"+a+"("+L+"|$)"))&&y(a,function(a){return b.test("string"==typeof a.className&&a.className||"undefined"!=typeof a.getAttribute&&a.getAttribute("class")||"")})},ATTR:function(a,b,c){return function(d){var e=fa.attr(d,a);return null==e?"!="===b:b?(e+="","="===b?e===c:"!="===b?e!==c:"^="===b?c&&0===e.indexOf(c):"*="===b?c&&e.indexOf(c)>-1:"$="===b?c&&e.slice(-c.length)===c:"~="===b?(" "+e.replace(P," ")+" ").indexOf(c)>-1:"|="===b?e===c||e.slice(0,c.length+1)===c+"-":!1):!0}},CHILD:function(a,b,c,d,e){var f="nth"!==a.slice(0,3),g="last"!==a.slice(-4),h="of-type"===b;return 1===d&&0===e?function(a){return!!a.parentNode}:function(b,c,i){var j,k,l,m,n,o,p=f!==g?"nextSibling":"previousSibling",q=b.parentNode,r=h&&b.nodeName.toLowerCase(),s=!i&&!h,t=!1;if(q){if(f){while(p){m=b;while(m=m[p])if(h?m.nodeName.toLowerCase()===r:1===m.nodeType)return!1;o=p="only"===a&&!o&&"nextSibling"}return!0}if(o=[g?q.firstChild:q.lastChild],g&&s){m=q,l=m[u]||(m[u]={}),k=l[m.uniqueID]||(l[m.uniqueID]={}),j=k[a]||[],n=j[0]===w&&j[1],t=n&&j[2],m=n&&q.childNodes[n];while(m=++n&&m&&m[p]||(t=n=0)||o.pop())if(1===m.nodeType&&++t&&m===b){k[a]=[w,n,t];break}}else if(s&&(m=b,l=m[u]||(m[u]={}),k=l[m.uniqueID]||(l[m.uniqueID]={}),j=k[a]||[],n=j[0]===w&&j[1],t=n),t===!1)while(m=++n&&m&&m[p]||(t=n=0)||o.pop())if((h?m.nodeName.toLowerCase()===r:1===m.nodeType)&&++t&&(s&&(l=m[u]||(m[u]={}),k=l[m.uniqueID]||(l[m.uniqueID]={}),k[a]=[w,t]),m===b))break;return t-=e,t===d||t%d===0&&t/d>=0}}},PSEUDO:function(a,b){var c,e=d.pseudos[a]||d.setFilters[a.toLowerCase()]||fa.error("unsupported pseudo: "+a);return e[u]?e(b):e.length>1?(c=[a,a,"",b],d.setFilters.hasOwnProperty(a.toLowerCase())?ha(function(a,c){var d,f=e(a,b),g=f.length;while(g--)d=J(a,f[g]),a[d]=!(c[d]=f[g])}):function(a){return e(a,0,c)}):e}},pseudos:{not:ha(function(a){var b=[],c=[],d=h(a.replace(Q,"$1"));return d[u]?ha(function(a,b,c,e){var f,g=d(a,null,e,[]),h=a.length;while(h--)(f=g[h])&&(a[h]=!(b[h]=f))}):function(a,e,f){return b[0]=a,d(b,null,f,c),b[0]=null,!c.pop()}}),has:ha(function(a){return function(b' , org_gwtbootstrap3_client_GwtBootstrap3ClientBundle_1default_1InlineClientBundleGenerator$2_$getText__Lorg_gwtbootstrap3_client_GwtBootstrap3ClientBundle_1default_1InlineClientBundleGenerator$2_2Ljava_lang_String_2_builder_0.java_lang_AbstractStringBuilder_string += '){return fa(a,b).length>0}}),contains:ha(function(a){return a=a.replace(ba,ca),function(b){return(b.textContent||b.innerText||e(b)).indexOf(a)>-1}}),lang:ha(function(a){return V.test(a||"")||fa.error("unsupported lang: "+a),a=a.replace(ba,ca).toLowerCase(),function(b){var c;do if(c=p?b.lang:b.getAttribute("xml:lang")||b.getAttribute("lang"))return c=c.toLowerCase(),c===a||0===c.indexOf(a+"-");while((b=b.parentNode)&&1===b.nodeType);return!1}}),target:function(b){var c=a.location&&a.location.hash;return c&&c.slice(1)===b.id},root:function(a){return a===o},focus:function(a){return a===n.activeElement&&(!n.hasFocus||n.hasFocus())&&!!(a.type||a.href||~a.tabIndex)},enabled:function(a){return a.disabled===!1},disabled:function(a){return a.disabled===!0},checked:function(a){var b=a.nodeName.toLowerCase();return"input"===b&&!!a.checked||"option"===b&&!!a.selected},selected:function(a){return a.parentNode&&a.parentNode.selectedIndex,a.selected===!0},empty:function(a){for(a=a.firstChild;a;a=a.nextSibling)if(a.nodeType<6)return!1;return!0},parent:function(a){return!d.pseudos.empty(a)},header:function(a){return Y.test(a.nodeName)},input:function(a){return X.test(a.nodeName)},button:function(a){var b=a.nodeName.toLowerCase();return"input"===b&&"button"===a.type||"button"===b},text:function(a){var b;return"input"===a.nodeName.toLowerCase()&&"text"===a.type&&(null==(b=a.getAttribute("type"))||"text"===b.toLowerCase())},first:na(function(){return[0]}),last:na(function(a,b){return[b-1]}),eq:na(function(a,b,c){return[0>c?c+b:c]}),even:na(function(a,b){for(var c=0;b>c;c+=2)a.push(c);return a}),odd:na(function(a,b){for(var c=1;b>c;c+=2)a.push(c);return a}),lt:na(function(a,b,c){for(var d=0>c?c+b:c;--d>=0;)a.push(d);return a}),gt:na(function(a,b,c){for(var d=0>c?c+b:c;++d<b;)a.push(d);return a})}},d.pseudos.nth=d.pseudos.eq;for(b in{radio:!0,checkbox:!0,file:!0,password:!0,image:!0})d.pseudos[b]=la(b);for(b in{submit:!0,reset:!0})d.pseudos[b]=ma(b);function pa(){}pa.prototype=d.filters=d.pseudos,d.setFilters=new pa,g=fa.tokenize=function(a,b){var c,e,f,g,h,i,j,k=z[a+" "];if(k)return b?0:k.slice(0);h=a,i=[],j=d.preFilter;while(h){c&&!(e=R.exec(h))||(e&&(h=h.slice(e[0].length)||h),i.push(f=[])),c=!1,(e=S.exec(h))&&(c=e.shift(),f.push({value:c,type:e[0].replace(Q," ")}),h=h.slice(c.length));for(g in d.filter)!(e=W[g].exec(h))||j[g]&&!(e=j[g](e))||(c=e.shift(),f.push({value:c,type:g,matches:e}),h=h.slice(c.length));if(!c)break}return b?h.length:h?fa.error(a):z(a,i).slice(0)};function qa(a){for(var b=0,c=a.length,d="";c>b;b++)d+=a[b].value;return d}function ra(a,b,c){var d=b.dir,e=c&&"parentNode"===d,f=x++;return b.first?function(b,c,f){while(b=b[d])if(1===b.nodeType||e)return a(b,c,f)}:function(b,c,g){var h,i,j,k=[w,f];if(g){while(b=b[d])if((1===b.nodeType||e)&&a(b,c,g))return!0}else while(b=b[d])if(1===b.nodeType||e){if(j=b[u]||(b[u]={}),i=j[b.uniqueID]||(j[b.uniqueID]={}),(h=i[d])&&h[0]===w&&h[1]===f)return k[2]=h[2];if(i[d]=k,k[2]=a(b,c,g))return!0}}}function sa(a){return a.length>1?function(b,c,d){var e=a.length;while(e--)if(!a[e](b,c,d))return!1;return!0}:a[0]}function ta(a,b,c){for(var d=0,e=b.length;e>d;d++)fa(a,b[d],c);return c}function ua(a,b,c,d,e){for(var f,g=[],h=0,i=a.length,j=null!=b;i>h;h++)(f=a[h])&&(c&&!c(f,d,e)||(g.push(f),j&&b.push(h)));return g}function va(a,b,c,d,e,f){return d&&!d[u]&&(d=va(d)),e&&!e[u]&&(e=va(e,f)),ha(function(f,g,h,i){var j,k,l,m=[],n=[],o=g.length,p=f||ta(b||"*",h.nodeType?[h]:h,[]),q=!a||!f&&b?p:ua(p,m,a,h,i),r=c?e||(f?a:o||d)?[]:g:q;if(c&&c(q,r,h,i),d){j=ua(r,n),d(j,[],h,i),k=j.length;while(k--)(l=j[k])&&(r[n[k]]=!(q[n[k]]=l))}if(f){if(e||a){if(e){j=[],k=r.length;while(k--)(l=r[k])&&j.push(q[k]=l);e(null,r=[],j,i)}k=r.length;while(k--)(l=r[k])&&(j=e?J(f,l):m[k])>-1&&(f[j]=!(g[j]=l))}}else r=ua(r===g?r.splice(o,r.length):r),e?e(null,g,r,i):H.apply(g,r)})}function wa(a){for(var b,c,e,f=a.length,g=d.relative[a[0].type],h=g||d.relative[" "],i=g?1:0,k=ra(function(a){return a===b},h,!0),l=ra(function(a){return J(b,a)>-1},h,!0),m=[function(a,c,d){var e=!g&&(d||c!==j)||((b=c).nodeType?k(a,c,d):l(a,c,d));return b=null,e}];f>i;i++)if(c=d.relative[a[i].type])m=[ra(sa(m),c)];else{if(c=d.filter[a[i].type].apply(null,a[i].matches),c[u]){for(e=++i;f>e;e++)if(d.relative[a[e].type])break;return va(i>1&&sa(m),i>1&&qa(a.slice(0,i-1).concat({value:" "===a[i-2].type?"*":""})).replace(Q,"$1"),c,e>i&&wa(a.slice(i,e)),f>e&&wa(a=a.slice(e)),f>e&&qa(a))}m.push(c)}return sa(m)}function xa(a,b){var c=b.length>0,e=a.length>0,f=function(f,g,h,i,k){var l,o,q,r=0,s="0",t=f&&[],u=[],v=j,x=f||e&&d.find.TAG("*",k),y=w+=null==v?1:Math.random()||.1,z=x.length;for(k&&(j=g===n||g||k);s!==z&&null!=(l=x[s]);s++){if(e&&l){o=0,g||l.ownerDocument===n||(m(l),h=!p);while(q=a[o++])if(q(l,g||n,h)){i.push(l);break}k&&(w=y)}c&&((l=!q&&l)&&r--,f&&t.push(l))}if(r+=s,c&&s!==r){o=0;while(q=b[o++])q(t,u,g,h);if(f){if(r>0)while(s--)t[s]||u[s]||(u[s]=F.call(i));u=ua(u)}H.apply(i,u),k&&!f&&u.length>0&&r+b.length>1&&fa.uniqueSort(i)}return k&&(w=y,j=v),t};return c?ha(f):f}return h=fa.compile=function(a,b){var c,d=[],e=[],f=A[a+" "];if(!f){b||(b=g(a)),c=b.length;while(c--)f=wa(b[c]),f[u]?d.push(f):e.push(f);f=A(a,xa(e,d)),f.selector=a}return f},i=fa.select=function(a,b,e,f){var i,j,k,l,m,n="function"==typeof a&&a,o=!f&&g(a=n.selector||a);if(e=e||[],1===o.length){if(j=o[0]=o[0].slice(0),j.length>2&&"ID"===(k=j[0]).type&&c.getById&&9===b.nodeType&&p&&d.relative[j[1].type]){if(b=(d.find.ID(k.matches[0].replace(ba,ca),b)||[])[0],!b)return e;n&&(b=b.parentNode),a=a.slice(j.shift().value.length)}i=W.needsContext.test(a)?0:j.length;while(i--){if(k=j[i],d.relative[l=k.type])break;if((m=d.find[l])&&(f=m(k.matches[0].replace(ba,ca),_.test(j[0].type)&&oa(b.parentNode)||b))){if(j.splice(i,1),a=f.length&&qa(j),!a)return H.apply(e,f),e;break}}}return(n||h(a,o))(f,b,!p,e,!b||_.test(a)&&oa(b.parentNode)||b),e},c.sortStable=u.split("").sort(B).join("")===u,c.detectDuplicates=!!l,m(),c.sortDetached=ia(function(a){return 1&a.compareDocumentPosition(n.createElement("div"))}),ia(function(a){return a.innerHTML="<a href=\'#\'><\/a>","#"===a.firstChild.getAttribute("href")})||ja("type|href|height|width",function(a,b,c){return c?void 0:a.getAttribute(b,"type"===b.toLowerCase()?1:2)}),c.attributes&&ia(function(a){return a.innerHTML="<input/>",a.firstChild.setAttribute("value",""),""===a.firstChild.getAttribute("value")})||ja("value",function(a,b,c){return c||"input"!==a.nodeName.toLowerCase()?void 0:a.defaultValue}),ia(function(a){return null==a.getAttribute("disabled")})||ja(K,function(a,b,c){var d;return c?void 0:a[b]===!0?b.toLowerCase():(d=a.getAttributeNode(b))&&d.specified?d.value:null}),fa}(a);n.find=t,n.expr=t.selectors,n.expr[":"]=n.expr.pseudos,n.uniqueSort=n.unique=t.uniqueSort,n.text=t.getText,n.isXMLDoc=t.isXML,n.contains=t.contains;var u=function(a,b,c){var d=[],e=void 0!==c;while((a=a[b])&&9!==a.nodeType)if(1===a.nodeType){if(e&&n(a).is(c))break;d.push(a)}return d},v=function(a,b){for(var c=[];a;a=a.nextSibling)1===a.nodeType&&a!==b&&c.push(a);return c},w=n.expr.match.needsContext,x=/^<([\\w-]+)\\s*\\/?>(?:<\\/\\1>|)$/,y=/^.[^:#\\[\\.,]*$/;function z(a,b,c){if(n.isFunction(b))return n.grep(a,function(a,d){return!!b.call(a,d,a)!==c});if(b.nodeType)return n.grep(a,function(a){return a===b!==c});if("string"==typeof b){if(y.test(b))return n.filter(b,a,c);b=n.filter(b,a)}return n.grep(a,function(a){return n.inArray(a,b)>-1!==c})}n.filter=function(a,b,c){var d=b[0];return c&&(a=":not("+a+")"),1===b.length&&1===d.nodeType?n.find.matchesSelector(d,a)?[d]:[]:n.find.matches(a,n.grep(b,function(a){return 1===a.nodeType}))},n.fn.extend({find:function(a){var b,c=[],d=this,e=d.length;if("string"!=typeof a)return this.pushStack(n(a).filter(function(){for(b=0;e>b;b++)if(n.contains(d[b],this))return!0}));for(b=0;e>b;b++)n.find(a,d[b],c);return c=this.pushStack(e>1?n.unique(c):c),c.selector=this.selector?this.selector+" "+a:a,c},filter:function(a){return this.pushStack(z(this,a||[],!1))},not:function(a){return this.pushStack(z(this,a||[],!0))},is:function(a){return!!z(this,"string"==typeof a&&w.test(a)?n(a):a||[],!1).length}});var A,B=/^(?:\\s*(<[\\w\\W]+>)[^>]*|#([\\w-]*))$/,C=n.fn.init=function(a,b,c){var e,f;if(!a)return this;if(c=c||A,"string"==typeof a){if(e="<"===a.charAt(0)&&">"===a.charAt(a.length-1)&&a.length>=3?[null,a,null]:B.exec(a),!e||!e[1]&&b)return!b||b.jquery?(b||c).find(a):this.constructor(b).find(a);if(e[1]){if(b=b instanceof n?b[0]:b,n.merge(this,n.parseHTML(e[1],b&&b.nodeType?b.ownerDocument||b:d,!0)),x.test(e[1])&&n.isPlainObject(b))for(e in b)n.isFunction(this[e])?this[e](b[e]):this.attr(e,b[e]);return this}if(f=d.getElementById(e[2]),f&&f.parentNode){if(f.id!==e[2])return A.find(a);this.length=1,this[0]=f}return this.context=d,this.selector=a,this}return a.nodeType?(this.context=this[0]=a,this.length=1,this):n.isFunction(a)?"undefined"!=typeof c.ready?c.ready(a):a(n):(void 0!==a.selector&&(this.selector=a.selector,this.context=a.context),n.makeArray(a,this))};C.prototype=n.fn,A=n(d);var D=/^(?:parents|prev(?:Until|All))/,E={children:!0,contents:!0,next:!0,prev:!0};n.fn.extend({has:function(a){var b,c=n(a,this),d=c.length;return this.filter(function(){for(b=0;d>b;b++)if(n.contains(this,c[b]))return!0})},closest:function(a,b){for(var c,d=0,e=this.length,f=[],g=w.test(a)||"string"!=typeof a?n(a,b||this.context):0;e>d;d++)for(c=this[d];c&&c!==b;c=c.parentNode)if(c.nodeType<11&&(g?g.index(c)>-1:1===c.nodeType&&n.find.matchesSelector(c,a))){f.push(c);break}return this.pushStack(f.length>1?n.uniqueSort(f):f)},index:function(a){return a?"string"==typeof a?n.inArray(this[0],n(a)):n.inArray(a.jquery?a[0]:a,this):this[0]&&this[0].parentNode?this.first().prevAll().length:-1},add:function(a,b){return this.pushStack(n.uniqueSort(n.merge(this.get(),n(a,b))))},addBack:function(a){return this.add(null==a?this.prevObject:this.prevObject.filter(a))}});function F(a,b){do a=a[b];while(a&&1!==a.nodeType);return a}n.each({parent:function(a){var b=a.parentNode;return b&&11!==b.nodeType?b:null},parents:function(a){return u(a,"parentNode")},parentsUntil:function(a,b,c){return u(a,"parentNode",c)},next:function(a){return F(a,"nextSibling")},prev:function(a){return F(a,"previousSibling")},nextAll:function(a){return u(a,"nextSibling")},prevAll:function(a){return u(a,"previousSibling")},nextUntil:function(a,b,c){return u(a,"nextSibling",c)},prevUntil:function(a,b,c){return u(a,"previousSibling",c)},siblings:function(a){return v((a.parentNode||{}).firstChild,a)},children:function(a){return v(a.firstChild)},contents:function(a){return n.nodeName(a,"iframe")?a.contentDocument||a.contentWindow.document:n.merge([],a.childNodes)}},function(a,b){n.fn[a]=function(c,d){var e=n.map(this,b,c);return"Until"!==a.slice(-5)&&(d=c),d&&"string"==typeof d&&(e=n.filter(d,e)),this.length>1&&(E[a]||(e=n.uniqueSort(e)),D.test(a)&&(e=e.reverse())),this.pushStack(e)}});var G=/\\S+/g;function H(a){var b={};return n.each(a.match(G)||[],function(a,c){b[c]=!0}),b}n.Callbacks=function(a){a="string"==typeof a?H(a):n.extend({},a);var b,c,d,e,f=[],g=[],h=-1,i=function(){for(e=a.once,d=b=!0;g.length;h=-1){c=g.shift();while(++h<f.length)f[h].apply(c[0],c[1])===!1&&a.stopOnFalse&&(h=f.length,c=!1)}a.memory||(c=!1),b=!1,e&&(f=c?[]:"")},j={add:function(){return f&&(c&&!b&&(h=f.length-1,g.push(c)),function d(b){n.each(b,function(b,c){n.isFunction(c)?a.unique&&j.has(c)||f.push(c):c&&c.length&&"string"!==n.type(c)&&d(c)})}(arguments),c&&!b&&i()),this},remove:function(){return n.each(arguments,function(a,b){var c;while((c=n.inArray(b,f,c))>-1)f.splice(c,1),h>=c&&h--}),this},has:function(a){return a?n.inArray(a,f)>-1:f.length>0},empty:function(){return f&&(f=[]),this},disable:function(){return e=g=[],f=c="",this},disabled:function(){return!f},lock:function(){return e=!0,c||j.disable(),this},locked:function(){return!!e},fireWith:function(a,c){return e||(c=c||[],c=[a,c.slice?c.slice():c],g.push(c),b||i()),this},fire:function(){return j.fireWith(this,arguments),this},fired:function(){return!!d}};return j},n.extend({Deferred:function(a){var b=[["resolve","done",n.Callbacks("once memory"),"resolved"],["reject","fail",n.Callbacks("once memory"),"rejected"],["notify","progress",n.Callbacks("memory")]],c="pending",d={state:function(){return c},always:function(){return e.done(arguments).fail(arguments),this},then:function(){var a=arguments;return n.Deferred(function(c){n.each(b,function(b,f){var g=n.isFunction(a[b])&&a[b];e[f[1]](function(){var a=g&&g.apply(this,arguments);a&&n.isFunction(a.promise)?a.promise().progress(c.notify).done(c.resolve).fail(c.reject):c[f[0]+"With"](this===d?c.promise():this,g?[a]:arguments)})}),a=null}).promise()},promise:function(a){return null!=a?n.extend(a,d):d}},e={};return d.pipe=d.then,n.each(b,function(a,f){var g=f[2],h=f[3];d[f[1]]=g.add,h&&g.add(function(){c=h},b[1^a][2].disable,b[2][2].lock),e[f[0]]=function(){return e[f[0]+"With"](this===e?d:this,arguments),this},e[f[0]+"With"]=g.fireWith}),d.promise(e),a&&a.call(e,e),e},when:function(a){var b=0,c=e.call(arguments),d=c.length,f=1!==d||a&&n.isFunction(a.promise)?d:0,g=1===f?a:n.Deferred(),h=function(a,b,c){return function(d){b[a]=this,c[a]=arguments.length>1?e.call(arguments):d,c===i?g.notifyWith(b,c):--f||g.resolveWith(b,c)}},i,j,k;if(d>1)for(i=new Array(d),j=new Array(d),k=new Array(d);d>b;b++)c[b]&&n.isFunction(c[b].promise)?c[b].promise().progress(h(b,j,i)).done(h(b,k,c)).fail(g.reject):--f;return f||g.resolveWith(k,c),g.promise()}});var I;n.fn.ready=function(a){return n.ready.promise().done(a),this},n.extend({isReady:!1,readyWait:1,holdReady:function(a){a?n.readyWait++:n.ready(!0)},ready:function(a){(a===!0?--n.readyWait:n.isReady)||(n.isReady=!0,a!==!0&&--n.readyWait>0||(I.resolveWith(d,[n]),n.fn.triggerHandler&&(n(d).triggerHandler("ready"),n(d).off("ready"))))}});function J(){d.addEventListener?(d.removeEventListener("DOMContentLoaded",K),a.removeEventListener("load",K)):(d.detachEvent("onreadystatechange",K),a.detachEvent("onload",K))}function K(){(d.addEventListener||"load"===a.event.type||"complete"===d.readyState)&&(J(),n.ready())}n.ready.promise=function(b){if(!I)if(I=n.Deferred(),"complete"===d.readyState||"loading"!==d.readyState&&!d.documentElement.doScroll)a.setTimeout(n.ready);else if(d.addEventListener)d.addEventListener("DOMContentLoaded",K),a.addEventListener("load",K);else{d.attachEvent("onreadystatechange",K),a.attachEvent("onload",K);var c=!1;try{c=null==a.frameElement&&d.documentElement}catch(e){}c&&c.doScroll&&!function f(){if(!n.isReady){try{c.doScroll("left")}catch(b){return a.setTimeout(f,50)}J(),n.ready()}}()}return I.promise(b)},n.ready.promise();var L;for(L in n(l))break;l.ownFirst="0"===L,l.inlineBlockNeedsLayout=!1,n(function(){var a,b,c,e;c=d.getElementsByTagName("body")[0],c&&c.style&&(b=d.createElement("div"),e=d.createElement("div"),e.style.cssText="position:absolute;border:0;width:0;height:0;top:0;left:-9999px",c.appendChild(e).appendChild(b),"undefined"!=typeof b.style.zoom&&(b.style.cssText="display:inline;margin:0;border:0;padding:1px;width:1px;zoom:1",l.inlineBlockNeedsLayout=a=3===b.offsetWidth,a&&(c.style.zoom=1)),c.removeChild(e))}),function(){var a=d.createElement("div");l.deleteExpando=!0;try{delete a.test}catch(b){l.deleteExpando=!1}a=null}();var M=function(a){var b=n.noData[(a.nodeName+" ").toLowerCase()],c=+a.nodeType||1;return 1!==c&&9!==c?!1:!b||b!==!0&&a.getAttribute("classid")===b},N=/^(?:\\{[\\w\\W]*\\}|\\[[\\w\\W]*\\])$/,O=/([A-Z])/g;function P(a,b,c){if(void 0===c&&1===a.nodeType){var d="data-"+b.replace(O,"-$1").toLowerCase();if(c=a.getAttribute(d),"string"==typeof c){try{c="true"===c?!0:"false"===c?!1:"null"===c?null:+c+""===c?+c:N.test(c)?n.parseJSON(c):c}catch(e){}n.data(a,b,c)}else c=void 0;\n}return c}function Q(a){var b;for(b in a)if(("data"!==b||!n.isEmptyObject(a[b]))&&"toJSON"!==b)return!1;return!0}function R(a,b,d,e){if(M(a)){var f,g,h=n.expando,i=a.nodeType,j=i?n.cache:a,k=i?a[h]:a[h]&&h;if(k&&j[k]&&(e||j[k].data)||void 0!==d||"string"!=typeof b)return k||(k=i?a[h]=c.pop()||n.guid++:h),j[k]||(j[k]=i?{}:{toJSON:n.noop}),"object"!=typeof b&&"function"!=typeof b||(e?j[k]=n.extend(j[k],b):j[k].data=n.extend(j[k].data,b)),g=j[k],e||(g.data||(g.data={}),g=g.data),void 0!==d&&(g[n.camelCase(b)]=d),"string"==typeof b?(f=g[b],null==f&&(f=g[n.camelCase(b)])):f=g,f}}function S(a,b,c){if(M(a)){var d,e,f=a.nodeType,g=f?n.cache:a,h=f?a[n.expando]:n.expando;if(g[h]){if(b&&(d=' , org_gwtbootstrap3_client_GwtBootstrap3ClientBundle_1default_1InlineClientBundleGenerator$2_$getText__Lorg_gwtbootstrap3_client_GwtBootstrap3ClientBundle_1default_1InlineClientBundleGenerator$2_2Ljava_lang_String_2_builder_0.java_lang_AbstractStringBuilder_string += 'c?g[h]:g[h].data)){n.isArray(b)?b=b.concat(n.map(b,n.camelCase)):b in d?b=[b]:(b=n.camelCase(b),b=b in d?[b]:b.split(" ")),e=b.length;while(e--)delete d[b[e]];if(c?!Q(d):!n.isEmptyObject(d))return}(c||(delete g[h].data,Q(g[h])))&&(f?n.cleanData([a],!0):l.deleteExpando||g!=g.window?delete g[h]:g[h]=void 0)}}}n.extend({cache:{},noData:{"applet ":!0,"embed ":!0,"object ":"clsid:D27CDB6E-AE6D-11cf-96B8-444553540000"},hasData:function(a){return a=a.nodeType?n.cache[a[n.expando]]:a[n.expando],!!a&&!Q(a)},data:function(a,b,c){return R(a,b,c)},removeData:function(a,b){return S(a,b)},_data:function(a,b,c){return R(a,b,c,!0)},_removeData:function(a,b){return S(a,b,!0)}}),n.fn.extend({data:function(a,b){var c,d,e,f=this[0],g=f&&f.attributes;if(void 0===a){if(this.length&&(e=n.data(f),1===f.nodeType&&!n._data(f,"parsedAttrs"))){c=g.length;while(c--)g[c]&&(d=g[c].name,0===d.indexOf("data-")&&(d=n.camelCase(d.slice(5)),P(f,d,e[d])));n._data(f,"parsedAttrs",!0)}return e}return"object"==typeof a?this.each(function(){n.data(this,a)}):arguments.length>1?this.each(function(){n.data(this,a,b)}):f?P(f,a,n.data(f,a)):void 0},removeData:function(a){return this.each(function(){n.removeData(this,a)})}}),n.extend({queue:function(a,b,c){var d;return a?(b=(b||"fx")+"queue",d=n._data(a,b),c&&(!d||n.isArray(c)?d=n._data(a,b,n.makeArray(c)):d.push(c)),d||[]):void 0},dequeue:function(a,b){b=b||"fx";var c=n.queue(a,b),d=c.length,e=c.shift(),f=n._queueHooks(a,b),g=function(){n.dequeue(a,b)};"inprogress"===e&&(e=c.shift(),d--),e&&("fx"===b&&c.unshift("inprogress"),delete f.stop,e.call(a,g,f)),!d&&f&&f.empty.fire()},_queueHooks:function(a,b){var c=b+"queueHooks";return n._data(a,c)||n._data(a,c,{empty:n.Callbacks("once memory").add(function(){n._removeData(a,b+"queue"),n._removeData(a,c)})})}}),n.fn.extend({queue:function(a,b){var c=2;return"string"!=typeof a&&(b=a,a="fx",c--),arguments.length<c?n.queue(this[0],a):void 0===b?this:this.each(function(){var c=n.queue(this,a,b);n._queueHooks(this,a),"fx"===a&&"inprogress"!==c[0]&&n.dequeue(this,a)})},dequeue:function(a){return this.each(function(){n.dequeue(this,a)})},clearQueue:function(a){return this.queue(a||"fx",[])},promise:function(a,b){var c,d=1,e=n.Deferred(),f=this,g=this.length,h=function(){--d||e.resolveWith(f,[f])};"string"!=typeof a&&(b=a,a=void 0),a=a||"fx";while(g--)c=n._data(f[g],a+"queueHooks"),c&&c.empty&&(d++,c.empty.add(h));return h(),e.promise(b)}}),function(){var a;l.shrinkWrapBlocks=function(){if(null!=a)return a;a=!1;var b,c,e;return c=d.getElementsByTagName("body")[0],c&&c.style?(b=d.createElement("div"),e=d.createElement("div"),e.style.cssText="position:absolute;border:0;width:0;height:0;top:0;left:-9999px",c.appendChild(e).appendChild(b),"undefined"!=typeof b.style.zoom&&(b.style.cssText="-webkit-box-sizing:content-box;-moz-box-sizing:content-box;box-sizing:content-box;display:block;margin:0;border:0;padding:1px;width:1px;zoom:1",b.appendChild(d.createElement("div")).style.width="5px",a=3!==b.offsetWidth),c.removeChild(e),a):void 0}}();var T=/[+-]?(?:\\d*\\.|)\\d+(?:[eE][+-]?\\d+|)/.source,U=new RegExp("^(?:([+-])=|)("+T+")([a-z%]*)$","i"),V=["Top","Right","Bottom","Left"],W=function(a,b){return a=b||a,"none"===n.css(a,"display")||!n.contains(a.ownerDocument,a)};function X(a,b,c,d){var e,f=1,g=20,h=d?function(){return d.cur()}:function(){return n.css(a,b,"")},i=h(),j=c&&c[3]||(n.cssNumber[b]?"":"px"),k=(n.cssNumber[b]||"px"!==j&&+i)&&U.exec(n.css(a,b));if(k&&k[3]!==j){j=j||k[3],c=c||[],k=+i||1;do f=f||".5",k/=f,n.style(a,b,k+j);while(f!==(f=h()/i)&&1!==f&&--g)}return c&&(k=+k||+i||0,e=c[1]?k+(c[1]+1)*c[2]:+c[2],d&&(d.unit=j,d.start=k,d.end=e)),e}var Y=function(a,b,c,d,e,f,g){var h=0,i=a.length,j=null==c;if("object"===n.type(c)){e=!0;for(h in c)Y(a,b,h,c[h],!0,f,g)}else if(void 0!==d&&(e=!0,n.isFunction(d)||(g=!0),j&&(g?(b.call(a,d),b=null):(j=b,b=function(a,b,c){return j.call(n(a),c)})),b))for(;i>h;h++)b(a[h],c,g?d:d.call(a[h],h,b(a[h],c)));return e?a:j?b.call(a):i?b(a[0],c):f},Z=/^(?:checkbox|radio)$/i,$=/<([\\w:-]+)/,_=/^$|\\/(?:java|ecma)script/i,aa=/^\\s+/,ba="abbr|article|aside|audio|bdi|canvas|data|datalist|details|dialog|figcaption|figure|footer|header|hgroup|main|mark|meter|nav|output|picture|progress|section|summary|template|time|video";function ca(a){var b=ba.split("|"),c=a.createDocumentFragment();if(c.createElement)while(b.length)c.createElement(b.pop());return c}!function(){var a=d.createElement("div"),b=d.createDocumentFragment(),c=d.createElement("input");a.innerHTML="  <link/><table><\/table><a href=\'/a\'>a<\/a><input type=\'checkbox\'/>",l.leadingWhitespace=3===a.firstChild.nodeType,l.tbody=!a.getElementsByTagName("tbody").length,l.htmlSerialize=!!a.getElementsByTagName("link").length,l.html5Clone="<:nav><\/:nav>"!==d.createElement("nav").cloneNode(!0).outerHTML,c.type="checkbox",c.checked=!0,b.appendChild(c),l.appendChecked=c.checked,a.innerHTML="<textarea>x<\/textarea>",l.noCloneChecked=!!a.cloneNode(!0).lastChild.defaultValue,b.appendChild(a),c=d.createElement("input"),c.setAttribute("type","radio"),c.setAttribute("checked","checked"),c.setAttribute("name","t"),a.appendChild(c),l.checkClone=a.cloneNode(!0).cloneNode(!0).lastChild.checked,l.noCloneEvent=!!a.addEventListener,a[n.expando]=1,l.attributes=!a.getAttribute(n.expando)}();var da={option:[1,"<select multiple=\'multiple\'>","<\/select>"],legend:[1,"<fieldset>","<\/fieldset>"],area:[1,"<map>","<\/map>"],param:[1,"<object>","<\/object>"],thead:[1,"<table>","<\/table>"],tr:[2,"<table><tbody>","<\/tbody><\/table>"],col:[2,"<table><tbody><\/tbody><colgroup>","<\/colgroup><\/table>"],td:[3,"<table><tbody><tr>","<\/tr><\/tbody><\/table>"],_default:l.htmlSerialize?[0,"",""]:[1,"X<div>","<\/div>"]};da.optgroup=da.option,da.tbody=da.tfoot=da.colgroup=da.caption=da.thead,da.th=da.td;function ea(a,b){var c,d,e=0,f="undefined"!=typeof a.getElementsByTagName?a.getElementsByTagName(b||"*"):"undefined"!=typeof a.querySelectorAll?a.querySelectorAll(b||"*"):void 0;if(!f)for(f=[],c=a.childNodes||a;null!=(d=c[e]);e++)!b||n.nodeName(d,b)?f.push(d):n.merge(f,ea(d,b));return void 0===b||b&&n.nodeName(a,b)?n.merge([a],f):f}function fa(a,b){for(var c,d=0;null!=(c=a[d]);d++)n._data(c,"globalEval",!b||n._data(b[d],"globalEval"))}var ga=/<|&#?\\w+;/,ha=/<tbody/i;function ia(a){Z.test(a.type)&&(a.defaultChecked=a.checked)}function ja(a,b,c,d,e){for(var f,g,h,i,j,k,m,o=a.length,p=ca(b),q=[],r=0;o>r;r++)if(g=a[r],g||0===g)if("object"===n.type(g))n.merge(q,g.nodeType?[g]:g);else if(ga.test(g)){i=i||p.appendChild(b.createElement("div")),j=($.exec(g)||["",""])[1].toLowerCase(),m=da[j]||da._default,i.innerHTML=m[1]+n.htmlPrefilter(g)+m[2],f=m[0];while(f--)i=i.lastChild;if(!l.leadingWhitespace&&aa.test(g)&&q.push(b.createTextNode(aa.exec(g)[0])),!l.tbody){g="table"!==j||ha.test(g)?"<table>"!==m[1]||ha.test(g)?0:i:i.firstChild,f=g&&g.childNodes.length;while(f--)n.nodeName(k=g.childNodes[f],"tbody")&&!k.childNodes.length&&g.removeChild(k)}n.merge(q,i.childNodes),i.textContent="";while(i.firstChild)i.removeChild(i.firstChild);i=p.lastChild}else q.push(b.createTextNode(g));i&&p.removeChild(i),l.appendChecked||n.grep(ea(q,"input"),ia),r=0;while(g=q[r++])if(d&&n.inArray(g,d)>-1)e&&e.push(g);else if(h=n.contains(g.ownerDocument,g),i=ea(p.appendChild(g),"script"),h&&fa(i),c){f=0;while(g=i[f++])_.test(g.type||"")&&c.push(g)}return i=null,p}!function(){var b,c,e=d.createElement("div");for(b in{submit:!0,change:!0,focusin:!0})c="on"+b,(l[b]=c in a)||(e.setAttribute(c,"t"),l[b]=e.attributes[c].expando===!1);e=null}();var ka=/^(?:input|select|textarea)$/i,la=/^key/,ma=/^(?:mouse|pointer|contextmenu|drag|drop)|click/,na=/^(?:focusinfocus|focusoutblur)$/,oa=/^([^.]*)(?:\\.(.+)|)/;function pa(){return!0}function qa(){return!1}function ra(){try{return d.activeElement}catch(a){}}function sa(a,b,c,d,e,f){var g,h;if("object"==typeof b){"string"!=typeof c&&(d=d||c,c=void 0);for(h in b)sa(a,h,c,d,b[h],f);return a}if(null==d&&null==e?(e=c,d=c=void 0):null==e&&("string"==typeof c?(e=d,d=void 0):(e=d,d=c,c=void 0)),e===!1)e=qa;else if(!e)return a;return 1===f&&(g=e,e=function(a){return n().off(a),g.apply(this,arguments)},e.guid=g.guid||(g.guid=n.guid++)),a.each(function(){n.event.add(this,b,e,d,c)})}n.event={global:{},add:function(a,b,c,d,e){var f,g,h,i,j,k,l,m,o,p,q,r=n._data(a);if(r){c.handler&&(i=c,c=i.handler,e=i.selector),c.guid||(c.guid=n.guid++),(g=r.events)||(g=r.events={}),(k=r.handle)||(k=r.handle=function(a){return"undefined"==typeof n||a&&n.event.triggered===a.type?void 0:n.event.dispatch.apply(k.elem,arguments)},k.elem=a),b=(b||"").match(G)||[""],h=b.length;while(h--)f=oa.exec(b[h])||[],o=q=f[1],p=(f[2]||"").split(".").sort(),o&&(j=n.event.special[o]||{},o=(e?j.delegateType:j.bindType)||o,j=n.event.special[o]||{},l=n.extend({type:o,origType:q,data:d,handler:c,guid:c.guid,selector:e,needsContext:e&&n.expr.match.needsContext.test(e),namespace:p.join(".")},i),(m=g[o])||(m=g[o]=[],m.delegateCount=0,j.setup&&j.setup.call(a,d,p,k)!==!1||(a.addEventListener?a.addEventListener(o,k,!1):a.attachEvent&&a.attachEvent("on"+o,k))),j.add&&(j.add.call(a,l),l.handler.guid||(l.handler.guid=c.guid)),e?m.splice(m.delegateCount++,0,l):m.push(l),n.event.global[o]=!0);a=null}},remove:function(a,b,c,d,e){var f,g,h,i,j,k,l,m,o,p,q,r=n.hasData(a)&&n._data(a);if(r&&(k=r.events)){b=(b||"").match(G)||[""],j=b.length;while(j--)if(h=oa.exec(b[j])||[],o=q=h[1],p=(h[2]||"").split(".").sort(),o){l=n.event.special[o]||{},o=(d?l.delegateType:l.bindType)||o,m=k[o]||[],h=h[2]&&new RegExp("(^|\\\\.)"+p.join("\\\\.(?:.*\\\\.|)")+"(\\\\.|$)"),i=f=m.length;while(f--)g=m[f],!e&&q!==g.origType||c&&c.guid!==g.guid||h&&!h.test(g.namespace)||d&&d!==g.selector&&("**"!==d||!g.selector)||(m.splice(f,1),g.selector&&m.delegateCount--,l.remove&&l.remove.call(a,g));i&&!m.length&&(l.teardown&&l.teardown.call(a,p,r.handle)!==!1||n.removeEvent(a,o,r.handle),delete k[o])}else for(o in k)n.event.remove(a,o+b[j],c,d,!0);n.isEmptyObject(k)&&(delete r.handle,n._removeData(a,"events"))}},trigger:function(b,c,e,f){var g,h,i,j,l,m,o,p=[e||d],q=k.call(b,"type")?b.type:b,r=k.call(b,"namespace")?b.namespace.split("."):[];if(i=m=e=e||d,3!==e.nodeType&&8!==e.nodeType&&!na.test(q+n.event.triggered)&&(q.indexOf(".")>-1&&(r=q.split("."),q=r.shift(),r.sort()),h=q.indexOf(":")<0&&"on"+q,b=b[n.expando]?b:new n.Event(q,"object"==typeof b&&b),b.isTrigger=f?2:3,b.namespace=r.join("."),b.rnamespace=b.namespace?new RegExp("(^|\\\\.)"+r.join("\\\\.(?:.*\\\\.|)")+"(\\\\.|$)"):null,b.result=void 0,b.target||(b.target=e),c=null==c?[b]:n.makeArray(c,[b]),l=n.event.special[q]||{},f||!l.trigger||l.trigger.apply(e,c)!==!1)){if(!f&&!l.noBubble&&!n.isWindow(e)){for(j=l.delegateType||q,na.test(j+q)||(i=i.parentNode);i;i=i.parentNode)p.push(i),m=i;m===(e.ownerDocument||d)&&p.push(m.defaultView||m.parentWindow||a)}o=0;while((i=p[o++])&&!b.isPropagationStopped())b.type=o>1?j:l.bindType||q,g=(n._data(i,"events")||{})[b.type]&&n._data(i,"handle"),g&&g.apply(i,c),g=h&&i[h],g&&g.apply&&M(i)&&(b.result=g.apply(i,c),b.result===!1&&b.preventDefault());if(b.type=q,!f&&!b.isDefaultPrevented()&&(!l._default||l._default.apply(p.pop(),c)===!1)&&M(e)&&h&&e[q]&&!n.isWindow(e)){m=e[h],m&&(e[h]=null),n.event.triggered=q;try{e[q]()}catch(s){}n.event.triggered=void 0,m&&(e[h]=m)}return b.result}},dispatch:function(a){a=n.event.fix(a);var b,c,d,f,g,h=[],i=e.call(arguments),j=(n._data(this,"events")||{})[a.type]||[],k=n.event.special[a.type]||{};if(i[0]=a,a.delegateTarget=this,!k.preDispatch||k.preDispatch.call(this,a)!==!1){h=n.event.handlers.call(this,a,j),b=0;while((f=h[b++])&&!a.isPropagationStopped()){a.currentTarget=f.elem,c=0;while((g=f.handlers[c++])&&!a.isImmediatePropagationStopped())a.rnamespace&&!a.rnamespace.test(g.namespace)||(a.handleObj=g,a.data=g.data,d=((n.event.special[g.origType]||{}).handle||g.handler).apply(f.elem,i),void 0!==d&&(a.result=d)===!1&&(a.preventDefault(),a.stopPropagation()))}return k.postDispatch&&k.postDispatch.call(this,a),a.result}},handlers:function(a,b){var c,d,e,f,g=[],h=b.delegateCount,i=a.target;if(h&&i.nodeType&&("click"!==a.type||isNaN(a.button)||a.button<1))for(;i!=this;i=i.parentNode||this)if(1===i.nodeType&&(i.disabled!==!0||"click"!==a.type)){for(d=[],c=0;h>c;c++)f=b[c],e=f.selector+" ",void 0===d[e]&&(d[e]=f.needsContext?n(e,this).index(i)>-1:n.find(e,this,null,[i]).length),d[e]&&d.push(f);d.length&&g.push({elem:i,handlers:d})}return h<b.length&&g.push({elem:this,handlers:b.slice(h)}),g},fix:function(a){if(a[n.expando])return a;var b,c,e,f=a.type,g=a,h=this.fixHooks[f];h||(this.fixHooks[f]=h=ma.test(f)?this.mouseHooks:la.test(f)?this.keyHooks:{}),e=h.props?this.props.concat(h.props):this.props,a=new n.Event(g),b=e.length;while(b--)c=e[b],a[c]=g[c];return a.target||(a.target=g.srcElement||d),3===a.target.nodeType&&(a.target=a.target.parentNode),a.metaKey=!!a.metaKey,h.filter?h.filter(a,g):a},props:"altKey bubbles cancelable ctrlKey currentTarget detail eventPhase metaKey relatedTarget shiftKey target timeStamp view which".split(" "),fixHooks:{},keyHooks:{props:"char charCode key keyCode".split(" "),filter:function(a,b){return null==a.which&&(a.which=null!=b.charCode?b.charCode:b.keyCode),a}},mouseHooks:{props:"button buttons clientX clientY fromElement offsetX offsetY pageX pageY screenX screenY toElement".split(" "),filter:function(a,b){var c,e,f,g=b.button,h=b.fromElement;return null==a.pageX&&null!=b.clientX&&(e=a.target.ownerDocument||d,f=e.documentElement,c=e.body,a.pageX=b.clientX+(f&&f.scrollLeft||c&&c.scrollLeft||0)-(f&&f.clientLeft||c&&c.clientLeft||0),a.pageY=b.clientY+(f&&f.scrollTop||c&&c.scrollTop||0)-(f&&f.clientTop||c&&c.clientTop||0)),!a.relatedTarget&&h&&(a.relatedTarget=h===a.target?b.toElement:h),a.which||void 0===g||(a.which=1&g?1:2&g?3:4&g?2:0),a}},special:{load:{noBubble:!0},focus:{trigger:function(){if(this!==ra()&&this.focus)try{return this.focus(),!1}catch(a){}},delegateType:"focusin"},blur:{trigger:function(){return this===ra()&&this.blur?(this.blur(),!1):void 0},delegateType:"focusout"},click:{trigger:function(){return n.nodeName(this,"input")&&"checkbox"===this.type&&this.click?(this.click(),!1):void 0},_default:function(a){return n.nodeName(a.target,"a")}},beforeunload:{postDispatch:function(a){void 0!==a.result&&a.originalEvent&&(a.originalEvent.returnValue=a.result)}}},simulate:function(a,b,c){var d=n.extend(new n.Event,c,{type:a,isSimulated:!0});n.event.trigger(d,null,b),d.isDefaultPrevented()&&c.preventDefault()}},n.removeEvent=d.removeEventListener?function(a,b,c){a.removeEventListener&&a.removeEventListener(b,c)}:function(a,b,c){var d="on"+b;a.detachEvent&&("undefined"==typeof a[d]&&(a[d]=null),a.detachEvent(d,c))},n.Event=function(a,b){return this instanceof n.Event?(a&&a.type?(this.originalEvent=a,this.type=a.type,this.isDefaultPrevented=a.defaultPrevented||void 0===a.defaultPrevented&&a.returnValue===!1?pa:qa):this.type=a,b&&n.extend(this,b),this.timeStamp=a&&a.timeStamp||n.now(),void(this[n.expando]=!0)):new n.Event(a,b)},n.Event.prototype={constructor:n.Event,isDefaultPrevented:qa,isPropagationStopped:qa,isImmediatePropagationStopped:qa,preventDefault:function(){var a=this.originalEvent;this.isDefaultPrevented=pa,a&&(a.preventDefault?a.preventDefault():a.returnValue=!1)},stopPropagation:function(){var a=this.originalEvent;this.isPropagationStopped=pa,a&&!this.isSimulated&&(a.stopPropagation&&a.stopPropagation(),a.cancelBubble=!0)},stopImmediatePropagation:function(){var a=this.originalEvent;this.isImmediatePropagationStopped=pa,a&&a.stopImmediatePropagation&&a.stopImmediatePropagation(),this.stopPropagation()}},n.each({mouseenter:"mouseover",mouseleave:"mouseout",pointerenter:"pointerover",pointerleave:"pointerout"},function(a,b){n.event.special[a]={delegateType:b,bindType:b,handle:function(a){var c,d=this,e=a.relatedTarget,f=a.handleObj;return e&&(e===d||n.contains(d,e))||(a.type=f.origType,c=f.handler.apply(this,arguments),a.type=b),c}}}),l.submit||(n.event.special.submit={setup:function(){return n.nodeName(this,"form")?!1:void n.event.add(this,"click._submit keypress._submit",function(a){var b=a.target,c=n.nodeName(b,"input")||n.nodeName(b,"button")?n.prop(b,"form"):void 0;c&&!n._data(c,"submit")&&(n.event.add(c,"submit._submit",function(a){a._submitBubble=!0}),n._data(c,"submit",!0))})},postDispatch:function(a){a._submitBubble&&(delete a._submitBubble,this.parentNode&&!a.isTrigger&&n.event.simulate("submit",this.parentNode,a))},teardown:function(){return n.nodeName(thi' , org_gwtbootstrap3_client_GwtBootstrap3ClientBundle_1default_1InlineClientBundleGenerator$2_$getText__Lorg_gwtbootstrap3_client_GwtBootstrap3ClientBundle_1default_1InlineClientBundleGenerator$2_2Ljava_lang_String_2_builder_0.java_lang_AbstractStringBuilder_string += 's,"form")?!1:void n.event.remove(this,"._submit")}}),l.change||(n.event.special.change={setup:function(){return ka.test(this.nodeName)?("checkbox"!==this.type&&"radio"!==this.type||(n.event.add(this,"propertychange._change",function(a){"checked"===a.originalEvent.propertyName&&(this._justChanged=!0)}),n.event.add(this,"click._change",function(a){this._justChanged&&!a.isTrigger&&(this._justChanged=!1),n.event.simulate("change",this,a)})),!1):void n.event.add(this,"beforeactivate._change",function(a){var b=a.target;ka.test(b.nodeName)&&!n._data(b,"change")&&(n.event.add(b,"change._change",function(a){!this.parentNode||a.isSimulated||a.isTrigger||n.event.simulate("change",this.parentNode,a)}),n._data(b,"change",!0))})},handle:function(a){var b=a.target;return this!==b||a.isSimulated||a.isTrigger||"radio"!==b.type&&"checkbox"!==b.type?a.handleObj.handler.apply(this,arguments):void 0},teardown:function(){return n.event.remove(this,"._change"),!ka.test(this.nodeName)}}),l.focusin||n.each({focus:"focusin",blur:"focusout"},function(a,b){var c=function(a){n.event.simulate(b,a.target,n.event.fix(a))};n.event.special[b]={setup:function(){var d=this.ownerDocument||this,e=n._data(d,b);e||d.addEventListener(a,c,!0),n._data(d,b,(e||0)+1)},teardown:function(){var d=this.ownerDocument||this,e=n._data(d,b)-1;e?n._data(d,b,e):(d.removeEventListener(a,c,!0),n._removeData(d,b))}}}),n.fn.extend({on:function(a,b,c,d){return sa(this,a,b,c,d)},one:function(a,b,c,d){return sa(this,a,b,c,d,1)},off:function(a,b,c){var d,e;if(a&&a.preventDefault&&a.handleObj)return d=a.handleObj,n(a.delegateTarget).off(d.namespace?d.origType+"."+d.namespace:d.origType,d.selector,d.handler),this;if("object"==typeof a){for(e in a)this.off(e,b,a[e]);return this}return b!==!1&&"function"!=typeof b||(c=b,b=void 0),c===!1&&(c=qa),this.each(function(){n.event.remove(this,a,c,b)})},trigger:function(a,b){return this.each(function(){n.event.trigger(a,b,this)})},triggerHandler:function(a,b){var c=this[0];return c?n.event.trigger(a,b,c,!0):void 0}});var ta=/ jQuery\\d+="(?:null|\\d+)"/g,ua=new RegExp("<(?:"+ba+")[\\\\s/>]","i"),va=/<(?!area|br|col|embed|hr|img|input|link|meta|param)(([\\w:-]+)[^>]*)\\/>/gi,wa=/<script|<style|<link/i,xa=/checked\\s*(?:[^=]|=\\s*.checked.)/i,ya=/^true\\/(.*)/,za=/^\\s*<!(?:\\[CDATA\\[|--)|(?:\\]\\]|--)>\\s*$/g,Aa=ca(d),Ba=Aa.appendChild(d.createElement("div"));function Ca(a,b){return n.nodeName(a,"table")&&n.nodeName(11!==b.nodeType?b:b.firstChild,"tr")?a.getElementsByTagName("tbody")[0]||a.appendChild(a.ownerDocument.createElement("tbody")):a}function Da(a){return a.type=(null!==n.find.attr(a,"type"))+"/"+a.type,a}function Ea(a){var b=ya.exec(a.type);return b?a.type=b[1]:a.removeAttribute("type"),a}function Fa(a,b){if(1===b.nodeType&&n.hasData(a)){var c,d,e,f=n._data(a),g=n._data(b,f),h=f.events;if(h){delete g.handle,g.events={};for(c in h)for(d=0,e=h[c].length;e>d;d++)n.event.add(b,c,h[c][d])}g.data&&(g.data=n.extend({},g.data))}}function Ga(a,b){var c,d,e;if(1===b.nodeType){if(c=b.nodeName.toLowerCase(),!l.noCloneEvent&&b[n.expando]){e=n._data(b);for(d in e.events)n.removeEvent(b,d,e.handle);b.removeAttribute(n.expando)}"script"===c&&b.text!==a.text?(Da(b).text=a.text,Ea(b)):"object"===c?(b.parentNode&&(b.outerHTML=a.outerHTML),l.html5Clone&&a.innerHTML&&!n.trim(b.innerHTML)&&(b.innerHTML=a.innerHTML)):"input"===c&&Z.test(a.type)?(b.defaultChecked=b.checked=a.checked,b.value!==a.value&&(b.value=a.value)):"option"===c?b.defaultSelected=b.selected=a.defaultSelected:"input"!==c&&"textarea"!==c||(b.defaultValue=a.defaultValue)}}function Ha(a,b,c,d){b=f.apply([],b);var e,g,h,i,j,k,m=0,o=a.length,p=o-1,q=b[0],r=n.isFunction(q);if(r||o>1&&"string"==typeof q&&!l.checkClone&&xa.test(q))return a.each(function(e){var f=a.eq(e);r&&(b[0]=q.call(this,e,f.html())),Ha(f,b,c,d)});if(o&&(k=ja(b,a[0].ownerDocument,!1,a,d),e=k.firstChild,1===k.childNodes.length&&(k=e),e||d)){for(i=n.map(ea(k,"script"),Da),h=i.length;o>m;m++)g=k,m!==p&&(g=n.clone(g,!0,!0),h&&n.merge(i,ea(g,"script"))),c.call(a[m],g,m);if(h)for(j=i[i.length-1].ownerDocument,n.map(i,Ea),m=0;h>m;m++)g=i[m],_.test(g.type||"")&&!n._data(g,"globalEval")&&n.contains(j,g)&&(g.src?n._evalUrl&&n._evalUrl(g.src):n.globalEval((g.text||g.textContent||g.innerHTML||"").replace(za,"")));k=e=null}return a}function Ia(a,b,c){for(var d,e=b?n.filter(b,a):a,f=0;null!=(d=e[f]);f++)c||1!==d.nodeType||n.cleanData(ea(d)),d.parentNode&&(c&&n.contains(d.ownerDocument,d)&&fa(ea(d,"script")),d.parentNode.removeChild(d));return a}n.extend({htmlPrefilter:function(a){return a.replace(va,"<$1><\/$2>")},clone:function(a,b,c){var d,e,f,g,h,i=n.contains(a.ownerDocument,a);if(l.html5Clone||n.isXMLDoc(a)||!ua.test("<"+a.nodeName+">")?f=a.cloneNode(!0):(Ba.innerHTML=a.outerHTML,Ba.removeChild(f=Ba.firstChild)),!(l.noCloneEvent&&l.noCloneChecked||1!==a.nodeType&&11!==a.nodeType||n.isXMLDoc(a)))for(d=ea(f),h=ea(a),g=0;null!=(e=h[g]);++g)d[g]&&Ga(e,d[g]);if(b)if(c)for(h=h||ea(a),d=d||ea(f),g=0;null!=(e=h[g]);g++)Fa(e,d[g]);else Fa(a,f);return d=ea(f,"script"),d.length>0&&fa(d,!i&&ea(a,"script")),d=h=e=null,f},cleanData:function(a,b){for(var d,e,f,g,h=0,i=n.expando,j=n.cache,k=l.attributes,m=n.event.special;null!=(d=a[h]);h++)if((b||M(d))&&(f=d[i],g=f&&j[f])){if(g.events)for(e in g.events)m[e]?n.event.remove(d,e):n.removeEvent(d,e,g.handle);j[f]&&(delete j[f],k||"undefined"==typeof d.removeAttribute?d[i]=void 0:d.removeAttribute(i),c.push(f))}}}),n.fn.extend({domManip:Ha,detach:function(a){return Ia(this,a,!0)},remove:function(a){return Ia(this,a)},text:function(a){return Y(this,function(a){return void 0===a?n.text(this):this.empty().append((this[0]&&this[0].ownerDocument||d).createTextNode(a))},null,a,arguments.length)},append:function(){return Ha(this,arguments,function(a){if(1===this.nodeType||11===this.nodeType||9===this.nodeType){var b=Ca(this,a);b.appendChild(a)}})},prepend:function(){return Ha(this,arguments,function(a){if(1===this.nodeType||11===this.nodeType||9===this.nodeType){var b=Ca(this,a);b.insertBefore(a,b.firstChild)}})},before:function(){return Ha(this,arguments,function(a){this.parentNode&&this.parentNode.insertBefore(a,this)})},after:function(){return Ha(this,arguments,function(a){this.parentNode&&this.parentNode.insertBefore(a,this.nextSibling)})},empty:function(){for(var a,b=0;null!=(a=this[b]);b++){1===a.nodeType&&n.cleanData(ea(a,!1));while(a.firstChild)a.removeChild(a.firstChild);a.options&&n.nodeName(a,"select")&&(a.options.length=0)}return this},clone:function(a,b){return a=null==a?!1:a,b=null==b?a:b,this.map(function(){return n.clone(this,a,b)})},html:function(a){return Y(this,function(a){var b=this[0]||{},c=0,d=this.length;if(void 0===a)return 1===b.nodeType?b.innerHTML.replace(ta,""):void 0;if("string"==typeof a&&!wa.test(a)&&(l.htmlSerialize||!ua.test(a))&&(l.leadingWhitespace||!aa.test(a))&&!da[($.exec(a)||["",""])[1].toLowerCase()]){a=n.htmlPrefilter(a);try{for(;d>c;c++)b=this[c]||{},1===b.nodeType&&(n.cleanData(ea(b,!1)),b.innerHTML=a);b=0}catch(e){}}b&&this.empty().append(a)},null,a,arguments.length)},replaceWith:function(){var a=[];return Ha(this,arguments,function(b){var c=this.parentNode;n.inArray(this,a)<0&&(n.cleanData(ea(this)),c&&c.replaceChild(b,this))},a)}}),n.each({appendTo:"append",prependTo:"prepend",insertBefore:"before",insertAfter:"after",replaceAll:"replaceWith"},function(a,b){n.fn[a]=function(a){for(var c,d=0,e=[],f=n(a),h=f.length-1;h>=d;d++)c=d===h?this:this.clone(!0),n(f[d])[b](c),g.apply(e,c.get());return this.pushStack(e)}});var Ja,Ka={HTML:"block",BODY:"block"};function La(a,b){var c=n(b.createElement(a)).appendTo(b.body),d=n.css(c[0],"display");return c.detach(),d}function Ma(a){var b=d,c=Ka[a];return c||(c=La(a,b),"none"!==c&&c||(Ja=(Ja||n("<iframe frameborder=\'0\' width=\'0\' height=\'0\'/>")).appendTo(b.documentElement),b=(Ja[0].contentWindow||Ja[0].contentDocument).document,b.write(),b.close(),c=La(a,b),Ja.detach()),Ka[a]=c),c}var Na=/^margin/,Oa=new RegExp("^("+T+")(?!px)[a-z%]+$","i"),Pa=function(a,b,c,d){var e,f,g={};for(f in b)g[f]=a.style[f],a.style[f]=b[f];e=c.apply(a,d||[]);for(f in b)a.style[f]=g[f];return e},Qa=d.documentElement;!function(){var b,c,e,f,g,h,i=d.createElement("div"),j=d.createElement("div");if(j.style){j.style.cssText="float:left;opacity:.5",l.opacity="0.5"===j.style.opacity,l.cssFloat=!!j.style.cssFloat,j.style.backgroundClip="content-box",j.cloneNode(!0).style.backgroundClip="",l.clearCloneStyle="content-box"===j.style.backgroundClip,i=d.createElement("div"),i.style.cssText="border:0;width:8px;height:0;top:0;left:-9999px;padding:0;margin-top:1px;position:absolute",j.innerHTML="",i.appendChild(j),l.boxSizing=""===j.style.boxSizing||""===j.style.MozBoxSizing||""===j.style.WebkitBoxSizing,n.extend(l,{reliableHiddenOffsets:function(){return null==b&&k(),f},boxSizingReliable:function(){return null==b&&k(),e},pixelMarginRight:function(){return null==b&&k(),c},pixelPosition:function(){return null==b&&k(),b},reliableMarginRight:function(){return null==b&&k(),g},reliableMarginLeft:function(){return null==b&&k(),h}});function k(){var k,l,m=d.documentElement;m.appendChild(i),j.style.cssText="-webkit-box-sizing:border-box;box-sizing:border-box;position:relative;display:block;margin:auto;border:1px;padding:1px;top:1%;width:50%",b=e=h=!1,c=g=!0,a.getComputedStyle&&(l=a.getComputedStyle(j),b="1%"!==(l||{}).top,h="2px"===(l||{}).marginLeft,e="4px"===(l||{width:"4px"}).width,j.style.marginRight="50%",c="4px"===(l||{marginRight:"4px"}).marginRight,k=j.appendChild(d.createElement("div")),k.style.cssText=j.style.cssText="-webkit-box-sizing:content-box;-moz-box-sizing:content-box;box-sizing:content-box;display:block;margin:0;border:0;padding:0",k.style.marginRight=k.style.width="0",j.style.width="1px",g=!parseFloat((a.getComputedStyle(k)||{}).marginRight),j.removeChild(k)),j.style.display="none",f=0===j.getClientRects().length,f&&(j.style.display="",j.innerHTML="<table><tr><td><\/td><td>t<\/td><\/tr><\/table>",j.childNodes[0].style.borderCollapse="separate",k=j.getElementsByTagName("td"),k[0].style.cssText="margin:0;border:0;padding:0;display:none",f=0===k[0].offsetHeight,f&&(k[0].style.display="",k[1].style.display="none",f=0===k[0].offsetHeight)),m.removeChild(i)}}}();var Ra,Sa,Ta=/^(top|right|bottom|left)$/;a.getComputedStyle?(Ra=function(b){var c=b.ownerDocument.defaultView;return c&&c.opener||(c=a),c.getComputedStyle(b)},Sa=function(a,b,c){var d,e,f,g,h=a.style;return c=c||Ra(a),g=c?c.getPropertyValue(b)||c[b]:void 0,""!==g&&void 0!==g||n.contains(a.ownerDocument,a)||(g=n.style(a,b)),c&&!l.pixelMarginRight()&&Oa.test(g)&&Na.test(b)&&(d=h.width,e=h.minWidth,f=h.maxWidth,h.minWidth=h.maxWidth=h.width=g,g=c.width,h.width=d,h.minWidth=e,h.maxWidth=f),void 0===g?g:g+""}):Qa.currentStyle&&(Ra=function(a){return a.currentStyle},Sa=function(a,b,c){var d,e,f,g,h=a.style;return c=c||Ra(a),g=c?c[b]:void 0,null==g&&h&&h[b]&&(g=h[b]),Oa.test(g)&&!Ta.test(b)&&(d=h.left,e=a.runtimeStyle,f=e&&e.left,f&&(e.left=a.currentStyle.left),h.left="fontSize"===b?"1em":g,g=h.pixelLeft+"px",h.left=d,f&&(e.left=f)),void 0===g?g:g+""||"auto"});function Ua(a,b){return{get:function(){return a()?void delete this.get:(this.get=b).apply(this,arguments)}}}var Va=/alpha\\([^)]*\\)/i,Wa=/opacity\\s*=\\s*([^)]*)/i,Xa=/^(none|table(?!-c[ea]).+)/,Ya=new RegExp("^("+T+")(.*)$","i"),Za={position:"absolute",visibility:"hidden",display:"block"},$a={letterSpacing:"0",fontWeight:"400"},_a=["Webkit","O","Moz","ms"],ab=d.createElement("div").style;function bb(a){if(a in ab)return a;var b=a.charAt(0).toUpperCase()+a.slice(1),c=_a.length;while(c--)if(a=_a[c]+b,a in ab)return a}function cb(a,b){for(var c,d,e,f=[],g=0,h=a.length;h>g;g++)d=a[g],d.style&&(f[g]=n._data(d,"olddisplay"),c=d.style.display,b?(f[g]||"none"!==c||(d.style.display=""),""===d.style.display&&W(d)&&(f[g]=n._data(d,"olddisplay",Ma(d.nodeName)))):(e=W(d),(c&&"none"!==c||!e)&&n._data(d,"olddisplay",e?c:n.css(d,"display"))));for(g=0;h>g;g++)d=a[g],d.style&&(b&&"none"!==d.style.display&&""!==d.style.display||(d.style.display=b?f[g]||"":"none"));return a}function db(a,b,c){var d=Ya.exec(b);return d?Math.max(0,d[1]-(c||0))+(d[2]||"px"):b}function eb(a,b,c,d,e){for(var f=c===(d?"border":"content")?4:"width"===b?1:0,g=0;4>f;f+=2)"margin"===c&&(g+=n.css(a,c+V[f],!0,e)),d?("content"===c&&(g-=n.css(a,"padding"+V[f],!0,e)),"margin"!==c&&(g-=n.css(a,"border"+V[f]+"Width",!0,e))):(g+=n.css(a,"padding"+V[f],!0,e),"padding"!==c&&(g+=n.css(a,"border"+V[f]+"Width",!0,e)));return g}function fb(a,b,c){var d=!0,e="width"===b?a.offsetWidth:a.offsetHeight,f=Ra(a),g=l.boxSizing&&"border-box"===n.css(a,"boxSizing",!1,f);if(0>=e||null==e){if(e=Sa(a,b,f),(0>e||null==e)&&(e=a.style[b]),Oa.test(e))return e;d=g&&(l.boxSizingReliable()||e===a.style[b]),e=parseFloat(e)||0}return e+eb(a,b,c||(g?"border":"content"),d,f)+"px"}n.extend({cssHooks:{opacity:{get:function(a,b){if(b){var c=Sa(a,"opacity");return""===c?"1":c}}}},cssNumber:{animationIterationCount:!0,columnCount:!0,fillOpacity:!0,flexGrow:!0,flexShrink:!0,fontWeight:!0,lineHeight:!0,opacity:!0,order:!0,orphans:!0,widows:!0,zIndex:!0,zoom:!0},cssProps:{"float":l.cssFloat?"cssFloat":"styleFloat"},style:function(a,b,c,d){if(a&&3!==a.nodeType&&8!==a.nodeType&&a.style){var e,f,g,h=n.camelCase(b),i=a.style;if(b=n.cssProps[h]||(n.cssProps[h]=bb(h)||h),g=n.cssHooks[b]||n.cssHooks[h],void 0===c)return g&&"get"in g&&void 0!==(e=g.get(a,!1,d))?e:i[b];if(f=typeof c,"string"===f&&(e=U.exec(c))&&e[1]&&(c=X(a,b,e),f="number"),null!=c&&c===c&&("number"===f&&(c+=e&&e[3]||(n.cssNumber[h]?"":"px")),l.clearCloneStyle||""!==c||0!==b.indexOf("background")||(i[b]="inherit"),!(g&&"set"in g&&void 0===(c=g.set(a,c,d)))))try{i[b]=c}catch(j){}}},css:function(a,b,c,d){var e,f,g,h=n.camelCase(b);return b=n.cssProps[h]||(n.cssProps[h]=bb(h)||h),g=n.cssHooks[b]||n.cssHooks[h],g&&"get"in g&&(f=g.get(a,!0,c)),void 0===f&&(f=Sa(a,b,d)),"normal"===f&&b in $a&&(f=$a[b]),""===c||c?(e=parseFloat(f),c===!0||isFinite(e)?e||0:f):f}}),n.each(["height","width"],function(a,b){n.cssHooks[b]={get:function(a,c,d){return c?Xa.test(n.css(a,"display"))&&0===a.offsetWidth?Pa(a,Za,function(){return fb(a,b,d)}):fb(a,b,d):void 0},set:function(a,c,d){var e=d&&Ra(a);return db(a,c,d?eb(a,b,d,l.boxSizing&&"border-box"===n.css(a,"boxSizing",!1,e),e):0)}}}),l.opacity||(n.cssHooks.opacity={get:function(a,b){return Wa.test((b&&a.currentStyle?a.currentStyle.filter:a.style.filter)||"")?.01*parseFloat(RegExp.$1)+"":b?"1":""},set:function(a,b){var c=a.style,d=a.currentStyle,e=n.isNumeric(b)?"alpha(opacity="+100*b+")":"",f=d&&d.filter||c.filter||"";c.zoom=1,(b>=1||""===b)&&""===n.trim(f.replace(Va,""))&&c.removeAttribute&&(c.removeAttribute("filter"),""===b||d&&!d.filter)||(c.filter=Va.test(f)?f.replace(Va,e):f+" "+e)}}),n.cssHooks.marginRight=Ua(l.reliableMarginRight,function(a,b){return b?Pa(a,{display:"inline-block"},Sa,[a,"marginRight"]):void 0}),n.cssHooks.marginLeft=Ua(l.reliableMarginLeft,function(a,b){return b?(parseFloat(Sa(a,"marginLeft"))||(n.contains(a.ownerDocument,a)?a.getBoundingClientRect().left-Pa(a,{\nmarginLeft:0},function(){return a.getBoundingClientRect().left}):0))+"px":void 0}),n.each({margin:"",padding:"",border:"Width"},function(a,b){n.cssHooks[a+b]={expand:function(c){for(var d=0,e={},f="string"==typeof c?c.split(" "):[c];4>d;d++)e[a+V[d]+b]=f[d]||f[d-2]||f[0];return e}},Na.test(a)||(n.cssHooks[a+b].set=db)}),n.fn.extend({css:function(a,b){return Y(this,function(a,b,c){var d,e,f={},g=0;if(n.isArray(b)){for(d=Ra(a),e=b.length;e>g;g++)f[b[g]]=n.css(a,b[g],!1,d);return f}return void 0!==c?n.style(a,b,c):n.css(a,b)},a,b,arguments.length>1)},show:function(){return cb(this,!0)},hide:function(){return cb(this)},toggle:function(a){return"boolean"==typeof a?a?this.show():this.hide():this.each(function(){W(this)?n(this).show():n(this).hide()})}});function gb(a,b,c,d,e){return new gb.prototype.init(a,b,c,d,e)}n.Tween=gb,gb.prototype={constructor:gb,init:function(a,b,c,d,e,f){this.elem=a,this.prop=c,this.easing=e||n.easing._default,this.options=b,this.start=this.now=this.cur(),this.end=d,this.unit=f||(n.cssNumber[c]?"":"px")},cur:function(){var a=gb.propHooks[this.prop];return a&&a.get?a.get(this):gb.propHooks._default.get(this)},run:function(a){var b,c=gb.propHooks[this.prop];return this.options.duration?this.pos=b=n.easing[this.easing](a,this.options.duration*a,0,1,this.options.duration):this.pos=b=a,this.now=(this.end-this.start)*b+this.start,this.opti' , org_gwtbootstrap3_client_GwtBootstrap3ClientBundle_1default_1InlineClientBundleGenerator$2_$getText__Lorg_gwtbootstrap3_client_GwtBootstrap3ClientBundle_1default_1InlineClientBundleGenerator$2_2Ljava_lang_String_2_builder_0.java_lang_AbstractStringBuilder_string += 'ons.step&&this.options.step.call(this.elem,this.now,this),c&&c.set?c.set(this):gb.propHooks._default.set(this),this}},gb.prototype.init.prototype=gb.prototype,gb.propHooks={_default:{get:function(a){var b;return 1!==a.elem.nodeType||null!=a.elem[a.prop]&&null==a.elem.style[a.prop]?a.elem[a.prop]:(b=n.css(a.elem,a.prop,""),b&&"auto"!==b?b:0)},set:function(a){n.fx.step[a.prop]?n.fx.step[a.prop](a):1!==a.elem.nodeType||null==a.elem.style[n.cssProps[a.prop]]&&!n.cssHooks[a.prop]?a.elem[a.prop]=a.now:n.style(a.elem,a.prop,a.now+a.unit)}}},gb.propHooks.scrollTop=gb.propHooks.scrollLeft={set:function(a){a.elem.nodeType&&a.elem.parentNode&&(a.elem[a.prop]=a.now)}},n.easing={linear:function(a){return a},swing:function(a){return.5-Math.cos(a*Math.PI)/2},_default:"swing"},n.fx=gb.prototype.init,n.fx.step={};var hb,ib,jb=/^(?:toggle|show|hide)$/,kb=/queueHooks$/;function lb(){return a.setTimeout(function(){hb=void 0}),hb=n.now()}function mb(a,b){var c,d={height:a},e=0;for(b=b?1:0;4>e;e+=2-b)c=V[e],d["margin"+c]=d["padding"+c]=a;return b&&(d.opacity=d.width=a),d}function nb(a,b,c){for(var d,e=(qb.tweeners[b]||[]).concat(qb.tweeners["*"]),f=0,g=e.length;g>f;f++)if(d=e[f].call(c,b,a))return d}function ob(a,b,c){var d,e,f,g,h,i,j,k,m=this,o={},p=a.style,q=a.nodeType&&W(a),r=n._data(a,"fxshow");c.queue||(h=n._queueHooks(a,"fx"),null==h.unqueued&&(h.unqueued=0,i=h.empty.fire,h.empty.fire=function(){h.unqueued||i()}),h.unqueued++,m.always(function(){m.always(function(){h.unqueued--,n.queue(a,"fx").length||h.empty.fire()})})),1===a.nodeType&&("height"in b||"width"in b)&&(c.overflow=[p.overflow,p.overflowX,p.overflowY],j=n.css(a,"display"),k="none"===j?n._data(a,"olddisplay")||Ma(a.nodeName):j,"inline"===k&&"none"===n.css(a,"float")&&(l.inlineBlockNeedsLayout&&"inline"!==Ma(a.nodeName)?p.zoom=1:p.display="inline-block")),c.overflow&&(p.overflow="hidden",l.shrinkWrapBlocks()||m.always(function(){p.overflow=c.overflow[0],p.overflowX=c.overflow[1],p.overflowY=c.overflow[2]}));for(d in b)if(e=b[d],jb.exec(e)){if(delete b[d],f=f||"toggle"===e,e===(q?"hide":"show")){if("show"!==e||!r||void 0===r[d])continue;q=!0}o[d]=r&&r[d]||n.style(a,d)}else j=void 0;if(n.isEmptyObject(o))"inline"===("none"===j?Ma(a.nodeName):j)&&(p.display=j);else{r?"hidden"in r&&(q=r.hidden):r=n._data(a,"fxshow",{}),f&&(r.hidden=!q),q?n(a).show():m.done(function(){n(a).hide()}),m.done(function(){var b;n._removeData(a,"fxshow");for(b in o)n.style(a,b,o[b])});for(d in o)g=nb(q?r[d]:0,d,m),d in r||(r[d]=g.start,q&&(g.end=g.start,g.start="width"===d||"height"===d?1:0))}}function pb(a,b){var c,d,e,f,g;for(c in a)if(d=n.camelCase(c),e=b[d],f=a[c],n.isArray(f)&&(e=f[1],f=a[c]=f[0]),c!==d&&(a[d]=f,delete a[c]),g=n.cssHooks[d],g&&"expand"in g){f=g.expand(f),delete a[d];for(c in f)c in a||(a[c]=f[c],b[c]=e)}else b[d]=e}function qb(a,b,c){var d,e,f=0,g=qb.prefilters.length,h=n.Deferred().always(function(){delete i.elem}),i=function(){if(e)return!1;for(var b=hb||lb(),c=Math.max(0,j.startTime+j.duration-b),d=c/j.duration||0,f=1-d,g=0,i=j.tweens.length;i>g;g++)j.tweens[g].run(f);return h.notifyWith(a,[j,f,c]),1>f&&i?c:(h.resolveWith(a,[j]),!1)},j=h.promise({elem:a,props:n.extend({},b),opts:n.extend(!0,{specialEasing:{},easing:n.easing._default},c),originalProperties:b,originalOptions:c,startTime:hb||lb(),duration:c.duration,tweens:[],createTween:function(b,c){var d=n.Tween(a,j.opts,b,c,j.opts.specialEasing[b]||j.opts.easing);return j.tweens.push(d),d},stop:function(b){var c=0,d=b?j.tweens.length:0;if(e)return this;for(e=!0;d>c;c++)j.tweens[c].run(1);return b?(h.notifyWith(a,[j,1,0]),h.resolveWith(a,[j,b])):h.rejectWith(a,[j,b]),this}}),k=j.props;for(pb(k,j.opts.specialEasing);g>f;f++)if(d=qb.prefilters[f].call(j,a,k,j.opts))return n.isFunction(d.stop)&&(n._queueHooks(j.elem,j.opts.queue).stop=n.proxy(d.stop,d)),d;return n.map(k,nb,j),n.isFunction(j.opts.start)&&j.opts.start.call(a,j),n.fx.timer(n.extend(i,{elem:a,anim:j,queue:j.opts.queue})),j.progress(j.opts.progress).done(j.opts.done,j.opts.complete).fail(j.opts.fail).always(j.opts.always)}n.Animation=n.extend(qb,{tweeners:{"*":[function(a,b){var c=this.createTween(a,b);return X(c.elem,a,U.exec(b),c),c}]},tweener:function(a,b){n.isFunction(a)?(b=a,a=["*"]):a=a.match(G);for(var c,d=0,e=a.length;e>d;d++)c=a[d],qb.tweeners[c]=qb.tweeners[c]||[],qb.tweeners[c].unshift(b)},prefilters:[ob],prefilter:function(a,b){b?qb.prefilters.unshift(a):qb.prefilters.push(a)}}),n.speed=function(a,b,c){var d=a&&"object"==typeof a?n.extend({},a):{complete:c||!c&&b||n.isFunction(a)&&a,duration:a,easing:c&&b||b&&!n.isFunction(b)&&b};return d.duration=n.fx.off?0:"number"==typeof d.duration?d.duration:d.duration in n.fx.speeds?n.fx.speeds[d.duration]:n.fx.speeds._default,null!=d.queue&&d.queue!==!0||(d.queue="fx"),d.old=d.complete,d.complete=function(){n.isFunction(d.old)&&d.old.call(this),d.queue&&n.dequeue(this,d.queue)},d},n.fn.extend({fadeTo:function(a,b,c,d){return this.filter(W).css("opacity",0).show().end().animate({opacity:b},a,c,d)},animate:function(a,b,c,d){var e=n.isEmptyObject(a),f=n.speed(b,c,d),g=function(){var b=qb(this,n.extend({},a),f);(e||n._data(this,"finish"))&&b.stop(!0)};return g.finish=g,e||f.queue===!1?this.each(g):this.queue(f.queue,g)},stop:function(a,b,c){var d=function(a){var b=a.stop;delete a.stop,b(c)};return"string"!=typeof a&&(c=b,b=a,a=void 0),b&&a!==!1&&this.queue(a||"fx",[]),this.each(function(){var b=!0,e=null!=a&&a+"queueHooks",f=n.timers,g=n._data(this);if(e)g[e]&&g[e].stop&&d(g[e]);else for(e in g)g[e]&&g[e].stop&&kb.test(e)&&d(g[e]);for(e=f.length;e--;)f[e].elem!==this||null!=a&&f[e].queue!==a||(f[e].anim.stop(c),b=!1,f.splice(e,1));!b&&c||n.dequeue(this,a)})},finish:function(a){return a!==!1&&(a=a||"fx"),this.each(function(){var b,c=n._data(this),d=c[a+"queue"],e=c[a+"queueHooks"],f=n.timers,g=d?d.length:0;for(c.finish=!0,n.queue(this,a,[]),e&&e.stop&&e.stop.call(this,!0),b=f.length;b--;)f[b].elem===this&&f[b].queue===a&&(f[b].anim.stop(!0),f.splice(b,1));for(b=0;g>b;b++)d[b]&&d[b].finish&&d[b].finish.call(this);delete c.finish})}}),n.each(["toggle","show","hide"],function(a,b){var c=n.fn[b];n.fn[b]=function(a,d,e){return null==a||"boolean"==typeof a?c.apply(this,arguments):this.animate(mb(b,!0),a,d,e)}}),n.each({slideDown:mb("show"),slideUp:mb("hide"),slideToggle:mb("toggle"),fadeIn:{opacity:"show"},fadeOut:{opacity:"hide"},fadeToggle:{opacity:"toggle"}},function(a,b){n.fn[a]=function(a,c,d){return this.animate(b,a,c,d)}}),n.timers=[],n.fx.tick=function(){var a,b=n.timers,c=0;for(hb=n.now();c<b.length;c++)a=b[c],a()||b[c]!==a||b.splice(c--,1);b.length||n.fx.stop(),hb=void 0},n.fx.timer=function(a){n.timers.push(a),a()?n.fx.start():n.timers.pop()},n.fx.interval=13,n.fx.start=function(){ib||(ib=a.setInterval(n.fx.tick,n.fx.interval))},n.fx.stop=function(){a.clearInterval(ib),ib=null},n.fx.speeds={slow:600,fast:200,_default:400},n.fn.delay=function(b,c){return b=n.fx?n.fx.speeds[b]||b:b,c=c||"fx",this.queue(c,function(c,d){var e=a.setTimeout(c,b);d.stop=function(){a.clearTimeout(e)}})},function(){var a,b=d.createElement("input"),c=d.createElement("div"),e=d.createElement("select"),f=e.appendChild(d.createElement("option"));c=d.createElement("div"),c.setAttribute("className","t"),c.innerHTML="  <link/><table><\/table><a href=\'/a\'>a<\/a><input type=\'checkbox\'/>",a=c.getElementsByTagName("a")[0],b.setAttribute("type","checkbox"),c.appendChild(b),a=c.getElementsByTagName("a")[0],a.style.cssText="top:1px",l.getSetAttribute="t"!==c.className,l.style=/top/.test(a.getAttribute("style")),l.hrefNormalized="/a"===a.getAttribute("href"),l.checkOn=!!b.value,l.optSelected=f.selected,l.enctype=!!d.createElement("form").enctype,e.disabled=!0,l.optDisabled=!f.disabled,b=d.createElement("input"),b.setAttribute("value",""),l.input=""===b.getAttribute("value"),b.value="t",b.setAttribute("type","radio"),l.radioValue="t"===b.value}();var rb=/\\r/g,sb=/[\\x20\\t\\r\\n\\f]+/g;n.fn.extend({val:function(a){var b,c,d,e=this[0];{if(arguments.length)return d=n.isFunction(a),this.each(function(c){var e;1===this.nodeType&&(e=d?a.call(this,c,n(this).val()):a,null==e?e="":"number"==typeof e?e+="":n.isArray(e)&&(e=n.map(e,function(a){return null==a?"":a+""})),b=n.valHooks[this.type]||n.valHooks[this.nodeName.toLowerCase()],b&&"set"in b&&void 0!==b.set(this,e,"value")||(this.value=e))});if(e)return b=n.valHooks[e.type]||n.valHooks[e.nodeName.toLowerCase()],b&&"get"in b&&void 0!==(c=b.get(e,"value"))?c:(c=e.value,"string"==typeof c?c.replace(rb,""):null==c?"":c)}}}),n.extend({valHooks:{option:{get:function(a){var b=n.find.attr(a,"value");return null!=b?b:n.trim(n.text(a)).replace(sb," ")}},select:{get:function(a){for(var b,c,d=a.options,e=a.selectedIndex,f="select-one"===a.type||0>e,g=f?null:[],h=f?e+1:d.length,i=0>e?h:f?e:0;h>i;i++)if(c=d[i],(c.selected||i===e)&&(l.optDisabled?!c.disabled:null===c.getAttribute("disabled"))&&(!c.parentNode.disabled||!n.nodeName(c.parentNode,"optgroup"))){if(b=n(c).val(),f)return b;g.push(b)}return g},set:function(a,b){var c,d,e=a.options,f=n.makeArray(b),g=e.length;while(g--)if(d=e[g],n.inArray(n.valHooks.option.get(d),f)>-1)try{d.selected=c=!0}catch(h){d.scrollHeight}else d.selected=!1;return c||(a.selectedIndex=-1),e}}}}),n.each(["radio","checkbox"],function(){n.valHooks[this]={set:function(a,b){return n.isArray(b)?a.checked=n.inArray(n(a).val(),b)>-1:void 0}},l.checkOn||(n.valHooks[this].get=function(a){return null===a.getAttribute("value")?"on":a.value})});var tb,ub,vb=n.expr.attrHandle,wb=/^(?:checked|selected)$/i,xb=l.getSetAttribute,yb=l.input;n.fn.extend({attr:function(a,b){return Y(this,n.attr,a,b,arguments.length>1)},removeAttr:function(a){return this.each(function(){n.removeAttr(this,a)})}}),n.extend({attr:function(a,b,c){var d,e,f=a.nodeType;if(3!==f&&8!==f&&2!==f)return"undefined"==typeof a.getAttribute?n.prop(a,b,c):(1===f&&n.isXMLDoc(a)||(b=b.toLowerCase(),e=n.attrHooks[b]||(n.expr.match.bool.test(b)?ub:tb)),void 0!==c?null===c?void n.removeAttr(a,b):e&&"set"in e&&void 0!==(d=e.set(a,c,b))?d:(a.setAttribute(b,c+""),c):e&&"get"in e&&null!==(d=e.get(a,b))?d:(d=n.find.attr(a,b),null==d?void 0:d))},attrHooks:{type:{set:function(a,b){if(!l.radioValue&&"radio"===b&&n.nodeName(a,"input")){var c=a.value;return a.setAttribute("type",b),c&&(a.value=c),b}}}},removeAttr:function(a,b){var c,d,e=0,f=b&&b.match(G);if(f&&1===a.nodeType)while(c=f[e++])d=n.propFix[c]||c,n.expr.match.bool.test(c)?yb&&xb||!wb.test(c)?a[d]=!1:a[n.camelCase("default-"+c)]=a[d]=!1:n.attr(a,c,""),a.removeAttribute(xb?c:d)}}),ub={set:function(a,b,c){return b===!1?n.removeAttr(a,c):yb&&xb||!wb.test(c)?a.setAttribute(!xb&&n.propFix[c]||c,c):a[n.camelCase("default-"+c)]=a[c]=!0,c}},n.each(n.expr.match.bool.source.match(/\\w+/g),function(a,b){var c=vb[b]||n.find.attr;yb&&xb||!wb.test(b)?vb[b]=function(a,b,d){var e,f;return d||(f=vb[b],vb[b]=e,e=null!=c(a,b,d)?b.toLowerCase():null,vb[b]=f),e}:vb[b]=function(a,b,c){return c?void 0:a[n.camelCase("default-"+b)]?b.toLowerCase():null}}),yb&&xb||(n.attrHooks.value={set:function(a,b,c){return n.nodeName(a,"input")?void(a.defaultValue=b):tb&&tb.set(a,b,c)}}),xb||(tb={set:function(a,b,c){var d=a.getAttributeNode(c);return d||a.setAttributeNode(d=a.ownerDocument.createAttribute(c)),d.value=b+="","value"===c||b===a.getAttribute(c)?b:void 0}},vb.id=vb.name=vb.coords=function(a,b,c){var d;return c?void 0:(d=a.getAttributeNode(b))&&""!==d.value?d.value:null},n.valHooks.button={get:function(a,b){var c=a.getAttributeNode(b);return c&&c.specified?c.value:void 0},set:tb.set},n.attrHooks.contenteditable={set:function(a,b,c){tb.set(a,""===b?!1:b,c)}},n.each(["width","height"],function(a,b){n.attrHooks[b]={set:function(a,c){return""===c?(a.setAttribute(b,"auto"),c):void 0}}})),l.style||(n.attrHooks.style={get:function(a){return a.style.cssText||void 0},set:function(a,b){return a.style.cssText=b+""}});var zb=/^(?:input|select|textarea|button|object)$/i,Ab=/^(?:a|area)$/i;n.fn.extend({prop:function(a,b){return Y(this,n.prop,a,b,arguments.length>1)},removeProp:function(a){return a=n.propFix[a]||a,this.each(function(){try{this[a]=void 0,delete this[a]}catch(b){}})}}),n.extend({prop:function(a,b,c){var d,e,f=a.nodeType;if(3!==f&&8!==f&&2!==f)return 1===f&&n.isXMLDoc(a)||(b=n.propFix[b]||b,e=n.propHooks[b]),void 0!==c?e&&"set"in e&&void 0!==(d=e.set(a,c,b))?d:a[b]=c:e&&"get"in e&&null!==(d=e.get(a,b))?d:a[b]},propHooks:{tabIndex:{get:function(a){var b=n.find.attr(a,"tabindex");return b?parseInt(b,10):zb.test(a.nodeName)||Ab.test(a.nodeName)&&a.href?0:-1}}},propFix:{"for":"htmlFor","class":"className"}}),l.hrefNormalized||n.each(["href","src"],function(a,b){n.propHooks[b]={get:function(a){return a.getAttribute(b,4)}}}),l.optSelected||(n.propHooks.selected={get:function(a){var b=a.parentNode;return b&&(b.selectedIndex,b.parentNode&&b.parentNode.selectedIndex),null},set:function(a){var b=a.parentNode;b&&(b.selectedIndex,b.parentNode&&b.parentNode.selectedIndex)}}),n.each(["tabIndex","readOnly","maxLength","cellSpacing","cellPadding","rowSpan","colSpan","useMap","frameBorder","contentEditable"],function(){n.propFix[this.toLowerCase()]=this}),l.enctype||(n.propFix.enctype="encoding");var Bb=/[\\t\\r\\n\\f]/g;function Cb(a){return n.attr(a,"class")||""}n.fn.extend({addClass:function(a){var b,c,d,e,f,g,h,i=0;if(n.isFunction(a))return this.each(function(b){n(this).addClass(a.call(this,b,Cb(this)))});if("string"==typeof a&&a){b=a.match(G)||[];while(c=this[i++])if(e=Cb(c),d=1===c.nodeType&&(" "+e+" ").replace(Bb," ")){g=0;while(f=b[g++])d.indexOf(" "+f+" ")<0&&(d+=f+" ");h=n.trim(d),e!==h&&n.attr(c,"class",h)}}return this},removeClass:function(a){var b,c,d,e,f,g,h,i=0;if(n.isFunction(a))return this.each(function(b){n(this).removeClass(a.call(this,b,Cb(this)))});if(!arguments.length)return this.attr("class","");if("string"==typeof a&&a){b=a.match(G)||[];while(c=this[i++])if(e=Cb(c),d=1===c.nodeType&&(" "+e+" ").replace(Bb," ")){g=0;while(f=b[g++])while(d.indexOf(" "+f+" ")>-1)d=d.replace(" "+f+" "," ");h=n.trim(d),e!==h&&n.attr(c,"class",h)}}return this},toggleClass:function(a,b){var c=typeof a;return"boolean"==typeof b&&"string"===c?b?this.addClass(a):this.removeClass(a):n.isFunction(a)?this.each(function(c){n(this).toggleClass(a.call(this,c,Cb(this),b),b)}):this.each(function(){var b,d,e,f;if("string"===c){d=0,e=n(this),f=a.match(G)||[];while(b=f[d++])e.hasClass(b)?e.removeClass(b):e.addClass(b)}else void 0!==a&&"boolean"!==c||(b=Cb(this),b&&n._data(this,"__className__",b),n.attr(this,"class",b||a===!1?"":n._data(this,"__className__")||""))})},hasClass:function(a){var b,c,d=0;b=" "+a+" ";while(c=this[d++])if(1===c.nodeType&&(" "+Cb(c)+" ").replace(Bb," ").indexOf(b)>-1)return!0;return!1}}),n.each("blur focus focusin focusout load resize scroll unload click dblclick mousedown mouseup mousemove mouseover mouseout mouseenter mouseleave change select submit keydown keypress keyup error contextmenu".split(" "),function(a,b){n.fn[b]=function(a,c){return arguments.length>0?this.on(b,null,a,c):this.trigger(b)}}),n.fn.extend({hover:function(a,b){return this.mouseenter(a).mouseleave(b||a)}});var Db=a.location,Eb=n.now(),Fb=/\\?/,Gb=/(,)|(\\[|{)|(}|])|"(?:[^"\\\\\\r\\n]|\\\\["\\\\\\/bfnrt]|\\\\u[\\da-fA-F]{4})*"\\s*:?|true|false|null|-?(?!0\\d)\\d+(?:\\.\\d+|)(?:[eE][+-]?\\d+|)/g;n.parseJSON=function(b){if(a.JSON&&a.JSON.parse)return a.JSON.parse(b+"");var c,d=null,e=n.trim(b+"");return e&&!n.trim(e.replace(Gb,function(a,b,e,f){return c&&b&&(d=0),0===d?a:(c=e||b,d+=!f-!e,"")}))?Function("return "+e)():n.error("Invalid JSON: "+b)},n.parseXML=function(b){var c,d;if(!b||"string"!=typeof b)return null;try{a.DOMParser?(d=new a.DOMParser,c=d.parseFromString(b,"text/xml")):(c=new a.ActiveXObject("Microsoft.XMLDOM"),c.async="false",c.loadXML(b))}catch(e){c=void 0}return c&&c.documentElement&&!c.getElementsByTagName("parsererror").length||n.error("Invalid XML: "+b),c};var Hb=/#.*$/,Ib=/([?&])_=[^&]*/,Jb=/^(.*?):[ \\t]*([^\\r\\n]*)\\r?$/gm,Kb=/^(?:about|app|app-storage|.+-extension|file|res|widget):$/,Lb=/^(?:GET|HEAD)$/,Mb=/^\\/\\//,Nb=/^([\\w.+-]+:)(?:\\/\\/(?:[^\\/?#]*@|)([^\\/?#:]*)(?::(\\d+)|)|)/,Ob={},Pb={},Qb="*/".concat("*"),Rb=Db.href,Sb=Nb.exec(Rb.toLowerCase())||[];function Tb(a){return function(b,c){"string"!=typeof b&&(c=b,b="*");var d,e=0,f=b.toLowerCase().match(G)||[];if(n.isFunction(c))while(d=f[e++])"+"===d.charAt(0)?(d=d.slice(1)||"*",(a[d]=a[d]||[]).unshift(c)):(a[d]=a[d]||[]).push(c)}}function Ub(a,b,c,d){var e={},f=a===Pb;function g(h' , org_gwtbootstrap3_client_GwtBootstrap3ClientBundle_1default_1InlineClientBundleGenerator$2_$getText__Lorg_gwtbootstrap3_client_GwtBootstrap3ClientBundle_1default_1InlineClientBundleGenerator$2_2Ljava_lang_String_2_builder_0.java_lang_AbstractStringBuilder_string += '){var i;return e[h]=!0,n.each(a[h]||[],function(a,h){var j=h(b,c,d);return"string"!=typeof j||f||e[j]?f?!(i=j):void 0:(b.dataTypes.unshift(j),g(j),!1)}),i}return g(b.dataTypes[0])||!e["*"]&&g("*")}function Vb(a,b){var c,d,e=n.ajaxSettings.flatOptions||{};for(d in b)void 0!==b[d]&&((e[d]?a:c||(c={}))[d]=b[d]);return c&&n.extend(!0,a,c),a}function Wb(a,b,c){var d,e,f,g,h=a.contents,i=a.dataTypes;while("*"===i[0])i.shift(),void 0===e&&(e=a.mimeType||b.getResponseHeader("Content-Type"));if(e)for(g in h)if(h[g]&&h[g].test(e)){i.unshift(g);break}if(i[0]in c)f=i[0];else{for(g in c){if(!i[0]||a.converters[g+" "+i[0]]){f=g;break}d||(d=g)}f=f||d}return f?(f!==i[0]&&i.unshift(f),c[f]):void 0}function Xb(a,b,c,d){var e,f,g,h,i,j={},k=a.dataTypes.slice();if(k[1])for(g in a.converters)j[g.toLowerCase()]=a.converters[g];f=k.shift();while(f)if(a.responseFields[f]&&(c[a.responseFields[f]]=b),!i&&d&&a.dataFilter&&(b=a.dataFilter(b,a.dataType)),i=f,f=k.shift())if("*"===f)f=i;else if("*"!==i&&i!==f){if(g=j[i+" "+f]||j["* "+f],!g)for(e in j)if(h=e.split(" "),h[1]===f&&(g=j[i+" "+h[0]]||j["* "+h[0]])){g===!0?g=j[e]:j[e]!==!0&&(f=h[0],k.unshift(h[1]));break}if(g!==!0)if(g&&a["throws"])b=g(b);else try{b=g(b)}catch(l){return{state:"parsererror",error:g?l:"No conversion from "+i+" to "+f}}}return{state:"success",data:b}}n.extend({active:0,lastModified:{},etag:{},ajaxSettings:{url:Rb,type:"GET",isLocal:Kb.test(Sb[1]),global:!0,processData:!0,async:!0,contentType:"application/x-www-form-urlencoded; charset=UTF-8",accepts:{"*":Qb,text:"text/plain",html:"text/html",xml:"application/xml, text/xml",json:"application/json, text/javascript"},contents:{xml:/\\bxml\\b/,html:/\\bhtml/,json:/\\bjson\\b/},responseFields:{xml:"responseXML",text:"responseText",json:"responseJSON"},converters:{"* text":String,"text html":!0,"text json":n.parseJSON,"text xml":n.parseXML},flatOptions:{url:!0,context:!0}},ajaxSetup:function(a,b){return b?Vb(Vb(a,n.ajaxSettings),b):Vb(n.ajaxSettings,a)},ajaxPrefilter:Tb(Ob),ajaxTransport:Tb(Pb),ajax:function(b,c){"object"==typeof b&&(c=b,b=void 0),c=c||{};var d,e,f,g,h,i,j,k,l=n.ajaxSetup({},c),m=l.context||l,o=l.context&&(m.nodeType||m.jquery)?n(m):n.event,p=n.Deferred(),q=n.Callbacks("once memory"),r=l.statusCode||{},s={},t={},u=0,v="canceled",w={readyState:0,getResponseHeader:function(a){var b;if(2===u){if(!k){k={};while(b=Jb.exec(g))k[b[1].toLowerCase()]=b[2]}b=k[a.toLowerCase()]}return null==b?null:b},getAllResponseHeaders:function(){return 2===u?g:null},setRequestHeader:function(a,b){var c=a.toLowerCase();return u||(a=t[c]=t[c]||a,s[a]=b),this},overrideMimeType:function(a){return u||(l.mimeType=a),this},statusCode:function(a){var b;if(a)if(2>u)for(b in a)r[b]=[r[b],a[b]];else w.always(a[w.status]);return this},abort:function(a){var b=a||v;return j&&j.abort(b),y(0,b),this}};if(p.promise(w).complete=q.add,w.success=w.done,w.error=w.fail,l.url=((b||l.url||Rb)+"").replace(Hb,"").replace(Mb,Sb[1]+"//"),l.type=c.method||c.type||l.method||l.type,l.dataTypes=n.trim(l.dataType||"*").toLowerCase().match(G)||[""],null==l.crossDomain&&(d=Nb.exec(l.url.toLowerCase()),l.crossDomain=!(!d||d[1]===Sb[1]&&d[2]===Sb[2]&&(d[3]||("http:"===d[1]?"80":"443"))===(Sb[3]||("http:"===Sb[1]?"80":"443")))),l.data&&l.processData&&"string"!=typeof l.data&&(l.data=n.param(l.data,l.traditional)),Ub(Ob,l,c,w),2===u)return w;i=n.event&&l.global,i&&0===n.active++&&n.event.trigger("ajaxStart"),l.type=l.type.toUpperCase(),l.hasContent=!Lb.test(l.type),f=l.url,l.hasContent||(l.data&&(f=l.url+=(Fb.test(f)?"&":"?")+l.data,delete l.data),l.cache===!1&&(l.url=Ib.test(f)?f.replace(Ib,"$1_="+Eb++):f+(Fb.test(f)?"&":"?")+"_="+Eb++)),l.ifModified&&(n.lastModified[f]&&w.setRequestHeader("If-Modified-Since",n.lastModified[f]),n.etag[f]&&w.setRequestHeader("If-None-Match",n.etag[f])),(l.data&&l.hasContent&&l.contentType!==!1||c.contentType)&&w.setRequestHeader("Content-Type",l.contentType),w.setRequestHeader("Accept",l.dataTypes[0]&&l.accepts[l.dataTypes[0]]?l.accepts[l.dataTypes[0]]+("*"!==l.dataTypes[0]?", "+Qb+"; q=0.01":""):l.accepts["*"]);for(e in l.headers)w.setRequestHeader(e,l.headers[e]);if(l.beforeSend&&(l.beforeSend.call(m,w,l)===!1||2===u))return w.abort();v="abort";for(e in{success:1,error:1,complete:1})w[e](l[e]);if(j=Ub(Pb,l,c,w)){if(w.readyState=1,i&&o.trigger("ajaxSend",[w,l]),2===u)return w;l.async&&l.timeout>0&&(h=a.setTimeout(function(){w.abort("timeout")},l.timeout));try{u=1,j.send(s,y)}catch(x){if(!(2>u))throw x;y(-1,x)}}else y(-1,"No Transport");function y(b,c,d,e){var k,s,t,v,x,y=c;2!==u&&(u=2,h&&a.clearTimeout(h),j=void 0,g=e||"",w.readyState=b>0?4:0,k=b>=200&&300>b||304===b,d&&(v=Wb(l,w,d)),v=Xb(l,v,w,k),k?(l.ifModified&&(x=w.getResponseHeader("Last-Modified"),x&&(n.lastModified[f]=x),x=w.getResponseHeader("etag"),x&&(n.etag[f]=x)),204===b||"HEAD"===l.type?y="nocontent":304===b?y="notmodified":(y=v.state,s=v.data,t=v.error,k=!t)):(t=y,!b&&y||(y="error",0>b&&(b=0))),w.status=b,w.statusText=(c||y)+"",k?p.resolveWith(m,[s,y,w]):p.rejectWith(m,[w,y,t]),w.statusCode(r),r=void 0,i&&o.trigger(k?"ajaxSuccess":"ajaxError",[w,l,k?s:t]),q.fireWith(m,[w,y]),i&&(o.trigger("ajaxComplete",[w,l]),--n.active||n.event.trigger("ajaxStop")))}return w},getJSON:function(a,b,c){return n.get(a,b,c,"json")},getScript:function(a,b){return n.get(a,void 0,b,"script")}}),n.each(["get","post"],function(a,b){n[b]=function(a,c,d,e){return n.isFunction(c)&&(e=e||d,d=c,c=void 0),n.ajax(n.extend({url:a,type:b,dataType:e,data:c,success:d},n.isPlainObject(a)&&a))}}),n._evalUrl=function(a){return n.ajax({url:a,type:"GET",dataType:"script",cache:!0,async:!1,global:!1,"throws":!0})},n.fn.extend({wrapAll:function(a){if(n.isFunction(a))return this.each(function(b){n(this).wrapAll(a.call(this,b))});if(this[0]){var b=n(a,this[0].ownerDocument).eq(0).clone(!0);this[0].parentNode&&b.insertBefore(this[0]),b.map(function(){var a=this;while(a.firstChild&&1===a.firstChild.nodeType)a=a.firstChild;return a}).append(this)}return this},wrapInner:function(a){return n.isFunction(a)?this.each(function(b){n(this).wrapInner(a.call(this,b))}):this.each(function(){var b=n(this),c=b.contents();c.length?c.wrapAll(a):b.append(a)})},wrap:function(a){var b=n.isFunction(a);return this.each(function(c){n(this).wrapAll(b?a.call(this,c):a)})},unwrap:function(){return this.parent().each(function(){n.nodeName(this,"body")||n(this).replaceWith(this.childNodes)}).end()}});function Yb(a){return a.style&&a.style.display||n.css(a,"display")}function Zb(a){if(!n.contains(a.ownerDocument||d,a))return!0;while(a&&1===a.nodeType){if("none"===Yb(a)||"hidden"===a.type)return!0;a=a.parentNode}return!1}n.expr.filters.hidden=function(a){return l.reliableHiddenOffsets()?a.offsetWidth<=0&&a.offsetHeight<=0&&!a.getClientRects().length:Zb(a)},n.expr.filters.visible=function(a){return!n.expr.filters.hidden(a)};var $b=/%20/g,_b=/\\[\\]$/,ac=/\\r?\\n/g,bc=/^(?:submit|button|image|reset|file)$/i,cc=/^(?:input|select|textarea|keygen)/i;function dc(a,b,c,d){var e;if(n.isArray(b))n.each(b,function(b,e){c||_b.test(a)?d(a,e):dc(a+"["+("object"==typeof e&&null!=e?b:"")+"]",e,c,d)});else if(c||"object"!==n.type(b))d(a,b);else for(e in b)dc(a+"["+e+"]",b[e],c,d)}n.param=function(a,b){var c,d=[],e=function(a,b){b=n.isFunction(b)?b():null==b?"":b,d[d.length]=encodeURIComponent(a)+"="+encodeURIComponent(b)};if(void 0===b&&(b=n.ajaxSettings&&n.ajaxSettings.traditional),n.isArray(a)||a.jquery&&!n.isPlainObject(a))n.each(a,function(){e(this.name,this.value)});else for(c in a)dc(c,a[c],b,e);return d.join("&").replace($b,"+")},n.fn.extend({serialize:function(){return n.param(this.serializeArray())},serializeArray:function(){return this.map(function(){var a=n.prop(this,"elements");return a?n.makeArray(a):this}).filter(function(){var a=this.type;return this.name&&!n(this).is(":disabled")&&cc.test(this.nodeName)&&!bc.test(a)&&(this.checked||!Z.test(a))}).map(function(a,b){var c=n(this).val();return null==c?null:n.isArray(c)?n.map(c,function(a){return{name:b.name,value:a.replace(ac,"\\r\\n")}}):{name:b.name,value:c.replace(ac,"\\r\\n")}}).get()}}),n.ajaxSettings.xhr=void 0!==a.ActiveXObject?function(){return this.isLocal?ic():d.documentMode>8?hc():/^(get|post|head|put|delete|options)$/i.test(this.type)&&hc()||ic()}:hc;var ec=0,fc={},gc=n.ajaxSettings.xhr();a.attachEvent&&a.attachEvent("onunload",function(){for(var a in fc)fc[a](void 0,!0)}),l.cors=!!gc&&"withCredentials"in gc,gc=l.ajax=!!gc,gc&&n.ajaxTransport(function(b){if(!b.crossDomain||l.cors){var c;return{send:function(d,e){var f,g=b.xhr(),h=++ec;if(g.open(b.type,b.url,b.async,b.username,b.password),b.xhrFields)for(f in b.xhrFields)g[f]=b.xhrFields[f];b.mimeType&&g.overrideMimeType&&g.overrideMimeType(b.mimeType),b.crossDomain||d["X-Requested-With"]||(d["X-Requested-With"]="XMLHttpRequest");for(f in d)void 0!==d[f]&&g.setRequestHeader(f,d[f]+"");g.send(b.hasContent&&b.data||null),c=function(a,d){var f,i,j;if(c&&(d||4===g.readyState))if(delete fc[h],c=void 0,g.onreadystatechange=n.noop,d)4!==g.readyState&&g.abort();else{j={},f=g.status,"string"==typeof g.responseText&&(j.text=g.responseText);try{i=g.statusText}catch(k){i=""}f||!b.isLocal||b.crossDomain?1223===f&&(f=204):f=j.text?200:404}j&&e(f,i,j,g.getAllResponseHeaders())},b.async?4===g.readyState?a.setTimeout(c):g.onreadystatechange=fc[h]=c:c()},abort:function(){c&&c(void 0,!0)}}}});function hc(){try{return new a.XMLHttpRequest}catch(b){}}function ic(){try{return new a.ActiveXObject("Microsoft.XMLHTTP")}catch(b){}}n.ajaxSetup({accepts:{script:"text/javascript, application/javascript, application/ecmascript, application/x-ecmascript"},contents:{script:/\\b(?:java|ecma)script\\b/},converters:{"text script":function(a){return n.globalEval(a),a}}}),n.ajaxPrefilter("script",function(a){void 0===a.cache&&(a.cache=!1),a.crossDomain&&(a.type="GET",a.global=!1)}),n.ajaxTransport("script",function(a){if(a.crossDomain){var b,c=d.head||n("head")[0]||d.documentElement;return{send:function(e,f){b=d.createElement("script"),b.async=!0,a.scriptCharset&&(b.charset=a.scriptCharset),b.src=a.url,b.onload=b.onreadystatechange=function(a,c){(c||!b.readyState||/loaded|complete/.test(b.readyState))&&(b.onload=b.onreadystatechange=null,b.parentNode&&b.parentNode.removeChild(b),b=null,c||f(200,"success"))},c.insertBefore(b,c.firstChild)},abort:function(){b&&b.onload(void 0,!0)}}}});var jc=[],kc=/(=)\\?(?=&|$)|\\?\\?/;n.ajaxSetup({jsonp:"callback",jsonpCallback:function(){var a=jc.pop()||n.expando+"_"+Eb++;return this[a]=!0,a}}),n.ajaxPrefilter("json jsonp",function(b,c,d){var e,f,g,h=b.jsonp!==!1&&(kc.test(b.url)?"url":"string"==typeof b.data&&0===(b.contentType||"").indexOf("application/x-www-form-urlencoded")&&kc.test(b.data)&&"data");return h||"jsonp"===b.dataTypes[0]?(e=b.jsonpCallback=n.isFunction(b.jsonpCallback)?b.jsonpCallback():b.jsonpCallback,h?b[h]=b[h].replace(kc,"$1"+e):b.jsonp!==!1&&(b.url+=(Fb.test(b.url)?"&":"?")+b.jsonp+"="+e),b.converters["script json"]=function(){return g||n.error(e+" was not called"),g[0]},b.dataTypes[0]="json",f=a[e],a[e]=function(){g=arguments},d.always(function(){void 0===f?n(a).removeProp(e):a[e]=f,b[e]&&(b.jsonpCallback=c.jsonpCallback,jc.push(e)),g&&n.isFunction(f)&&f(g[0]),g=f=void 0}),"script"):void 0}),n.parseHTML=function(a,b,c){if(!a||"string"!=typeof a)return null;"boolean"==typeof b&&(c=b,b=!1),b=b||d;var e=x.exec(a),f=!c&&[];return e?[b.createElement(e[1])]:(e=ja([a],b,f),f&&f.length&&n(f).remove(),n.merge([],e.childNodes))};var lc=n.fn.load;n.fn.load=function(a,b,c){if("string"!=typeof a&&lc)return lc.apply(this,arguments);var d,e,f,g=this,h=a.indexOf(" ");return h>-1&&(d=n.trim(a.slice(h,a.length)),a=a.slice(0,h)),n.isFunction(b)?(c=b,b=void 0):b&&"object"==typeof b&&(e="POST"),g.length>0&&n.ajax({url:a,type:e||"GET",dataType:"html",data:b}).done(function(a){f=arguments,g.html(d?n("<div>").append(n.parseHTML(a)).find(d):a)}).always(c&&function(a,b){g.each(function(){c.apply(this,f||[a.responseText,b,a])})}),this},n.each(["ajaxStart","ajaxStop","ajaxComplete","ajaxError","ajaxSuccess","ajaxSend"],function(a,b){n.fn[b]=function(a){return this.on(b,a)}}),n.expr.filters.animated=function(a){return n.grep(n.timers,function(b){return a===b.elem}).length};function mc(a){return n.isWindow(a)?a:9===a.nodeType?a.defaultView||a.parentWindow:!1}n.offset={setOffset:function(a,b,c){var d,e,f,g,h,i,j,k=n.css(a,"position"),l=n(a),m={};"static"===k&&(a.style.position="relative"),h=l.offset(),f=n.css(a,"top"),i=n.css(a,"left"),j=("absolute"===k||"fixed"===k)&&n.inArray("auto",[f,i])>-1,j?(d=l.position(),g=d.top,e=d.left):(g=parseFloat(f)||0,e=parseFloat(i)||0),n.isFunction(b)&&(b=b.call(a,c,n.extend({},h))),null!=b.top&&(m.top=b.top-h.top+g),null!=b.left&&(m.left=b.left-h.left+e),"using"in b?b.using.call(a,m):l.css(m)}},n.fn.extend({offset:function(a){if(arguments.length)return void 0===a?this:this.each(function(b){n.offset.setOffset(this,a,b)});var b,c,d={top:0,left:0},e=this[0],f=e&&e.ownerDocument;if(f)return b=f.documentElement,n.contains(b,e)?("undefined"!=typeof e.getBoundingClientRect&&(d=e.getBoundingClientRect()),c=mc(f),{top:d.top+(c.pageYOffset||b.scrollTop)-(b.clientTop||0),left:d.left+(c.pageXOffset||b.scrollLeft)-(b.clientLeft||0)}):d},position:function(){if(this[0]){var a,b,c={top:0,left:0},d=this[0];return"fixed"===n.css(d,"position")?b=d.getBoundingClientRect():(a=this.offsetParent(),b=this.offset(),n.nodeName(a[0],"html")||(c=a.offset()),c.top+=n.css(a[0],"borderTopWidth",!0),c.left+=n.css(a[0],"borderLeftWidth",!0)),{top:b.top-c.top-n.css(d,"marginTop",!0),left:b.left-c.left-n.css(d,"marginLeft",!0)}}},offsetParent:function(){return this.map(function(){var a=this.offsetParent;while(a&&!n.nodeName(a,"html")&&"static"===n.css(a,"position"))a=a.offsetParent;return a||Qa})}}),n.each({scrollLeft:"pageXOffset",scrollTop:"pageYOffset"},function(a,b){var c=/Y/.test(b);n.fn[a]=function(d){return Y(this,function(a,d,e){var f=mc(a);return void 0===e?f?b in f?f[b]:f.document.documentElement[d]:a[d]:void(f?f.scrollTo(c?n(f).scrollLeft():e,c?e:n(f).scrollTop()):a[d]=e)},a,d,arguments.length,null)}}),n.each(["top","left"],function(a,b){n.cssHooks[b]=Ua(l.pixelPosition,function(a,c){return c?(c=Sa(a,b),Oa.test(c)?n(a).position()[b]+"px":c):void 0})}),n.each({Height:"height",Width:"width"},function(a,b){n.each({\npadding:"inner"+a,content:b,"":"outer"+a},function(c,d){n.fn[d]=function(d,e){var f=arguments.length&&(c||"boolean"!=typeof d),g=c||(d===!0||e===!0?"margin":"border");return Y(this,function(b,c,d){var e;return n.isWindow(b)?b.document.documentElement["client"+a]:9===b.nodeType?(e=b.documentElement,Math.max(b.body["scroll"+a],e["scroll"+a],b.body["offset"+a],e["offset"+a],e["client"+a])):void 0===d?n.css(b,c,g):n.style(b,c,d,g)},b,f?d:void 0,f,null)}})}),n.fn.extend({bind:function(a,b,c){return this.on(a,null,b,c)},unbind:function(a,b){return this.off(a,null,b)},delegate:function(a,b,c,d){return this.on(b,a,c,d)},undelegate:function(a,b,c){return 1===arguments.length?this.off(a,"**"):this.off(b,a||"**",c)}}),n.fn.size=function(){return this.length},n.fn.andSelf=n.fn.addBack,"function"==typeof define&&define.amd&&define("jquery",[],function(){return n});var nc=a.jQuery,oc=a.$;return n.noConflict=function(b){return a.$===n&&(a.$=oc),b&&a.jQuery===n&&(a.jQuery=nc),n},b||(a.jQuery=a.$=n),n});\n' , org_gwtbootstrap3_client_GwtBootstrap3ClientBundle_1default_1InlineClientBundleGenerator$2_$getText__Lorg_gwtbootstrap3_client_GwtBootstrap3ClientBundle_1default_1InlineClientBundleGenerator$2_2Ljava_lang_String_2_builder_0.java_lang_AbstractStringBuilder_string))), com_google_gwt_core_client_ScriptInjector_TOP_1WINDOW));
  typeof $wnd['jQuery'].fn.emulateTransitionEnd !== 'undefined' || com_google_gwt_core_client_ScriptInjector$FromString_$inject__Lcom_google_gwt_core_client_ScriptInjector$FromString_2Lcom_google_gwt_core_client_JavaScriptObject_2(com_google_gwt_core_client_ScriptInjector$FromString_$setWindow__Lcom_google_gwt_core_client_ScriptInjector$FromString_2Lcom_google_gwt_core_client_JavaScriptObject_2Lcom_google_gwt_core_client_ScriptInjector$FromString_2((com_google_gwt_core_client_ScriptInjector_$clinit__V() , new com_google_gwt_core_client_ScriptInjector$FromString_ScriptInjector$FromString__Ljava_lang_String_2V((org_gwtbootstrap3_client_GwtBootstrap3ClientBundle_1default_1InlineClientBundleGenerator$1_$getText__Lorg_gwtbootstrap3_client_GwtBootstrap3ClientBundle_1default_1InlineClientBundleGenerator$1_2Ljava_lang_String_2_builder_0 = new java_lang_StringBuilder_StringBuilder__V , org_gwtbootstrap3_client_GwtBootstrap3ClientBundle_1default_1InlineClientBundleGenerator$1_$getText__Lorg_gwtbootstrap3_client_GwtBootstrap3ClientBundle_1default_1InlineClientBundleGenerator$1_2Ljava_lang_String_2_builder_0.java_lang_AbstractStringBuilder_string += '/*!\n * Bootstrap v3.3.7 (http://getbootstrap.com)\n * Copyright 2011-2016 Twitter, Inc.\n * Licensed under the MIT license\n */\nif("undefined"==typeof jQuery)throw new Error("Bootstrap\'s JavaScript requires jQuery");+function(a){"use strict";var b=a.fn.jquery.split(" ")[0].split(".");if(b[0]<2&&b[1]<9||1==b[0]&&9==b[1]&&b[2]<1||b[0]>3)throw new Error("Bootstrap\'s JavaScript requires jQuery version 1.9.1 or higher, but lower than version 4")}(jQuery),+function(a){"use strict";function b(){var a=document.createElement("bootstrap"),b={WebkitTransition:"webkitTransitionEnd",MozTransition:"transitionend",OTransition:"oTransitionEnd otransitionend",transition:"transitionend"};for(var c in b)if(void 0!==a.style[c])return{end:b[c]};return!1}a.fn.emulateTransitionEnd=function(b){var c=!1,d=this;a(this).one("bsTransitionEnd",function(){c=!0});var e=function(){c||a(d).trigger(a.support.transition.end)};return setTimeout(e,b),this},a(function(){a.support.transition=b(),a.support.transition&&(a.event.special.bsTransitionEnd={bindType:a.support.transition.end,delegateType:a.support.transition.end,handle:function(b){if(a(b.target).is(this))return b.handleObj.handler.apply(this,arguments)}})})}(jQuery),+function(a){"use strict";function b(b){return this.each(function(){var c=a(this),e=c.data("bs.alert");e||c.data("bs.alert",e=new d(this)),"string"==typeof b&&e[b].call(c)})}var c=\'[data-dismiss="alert"]\',d=function(b){a(b).on("click",c,this.close)};d.VERSION="3.3.7",d.TRANSITION_DURATION=150,d.prototype.close=function(b){function c(){g.detach().trigger("closed.bs.alert").remove()}var e=a(this),f=e.attr("data-target");f||(f=e.attr("href"),f=f&&f.replace(/.*(?=#[^\\s]*$)/,""));var g=a("#"===f?[]:f);b&&b.preventDefault(),g.length||(g=e.closest(".alert")),g.trigger(b=a.Event("close.bs.alert")),b.isDefaultPrevented()||(g.removeClass("in"),a.support.transition&&g.hasClass("fade")?g.one("bsTransitionEnd",c).emulateTransitionEnd(d.TRANSITION_DURATION):c())};var e=a.fn.alert;a.fn.alert=b,a.fn.alert.Constructor=d,a.fn.alert.noConflict=function(){return a.fn.alert=e,this},a(document).on("click.bs.alert.data-api",c,d.prototype.close)}(jQuery),+function(a){"use strict";function b(b){return this.each(function(){var d=a(this),e=d.data("bs.button"),f="object"==typeof b&&b;e||d.data("bs.button",e=new c(this,f)),"toggle"==b?e.toggle():b&&e.setState(b)})}var c=function(b,d){this.$element=a(b),this.options=a.extend({},c.DEFAULTS,d),this.isLoading=!1};c.VERSION="3.3.7",c.DEFAULTS={loadingText:"loading..."},c.prototype.setState=function(b){var c="disabled",d=this.$element,e=d.is("input")?"val":"html",f=d.data();b+="Text",null==f.resetText&&d.data("resetText",d[e]()),setTimeout(a.proxy(function(){d[e](null==f[b]?this.options[b]:f[b]),"loadingText"==b?(this.isLoading=!0,d.addClass(c).attr(c,c).prop(c,!0)):this.isLoading&&(this.isLoading=!1,d.removeClass(c).removeAttr(c).prop(c,!1))},this),0)},c.prototype.toggle=function(){var a=!0,b=this.$element.closest(\'[data-toggle="buttons"]\');if(b.length){var c=this.$element.find("input");"radio"==c.prop("type")?(c.prop("checked")&&(a=!1),b.find(".active").removeClass("active"),this.$element.addClass("active")):"checkbox"==c.prop("type")&&(c.prop("checked")!==this.$element.hasClass("active")&&(a=!1),this.$element.toggleClass("active")),c.prop("checked",this.$element.hasClass("active")),a&&c.trigger("change")}else this.$element.attr("aria-pressed",!this.$element.hasClass("active")),this.$element.toggleClass("active")};var d=a.fn.button;a.fn.button=b,a.fn.button.Constructor=c,a.fn.button.noConflict=function(){return a.fn.button=d,this},a(document).on("click.bs.button.data-api",\'[data-toggle^="button"]\',function(c){var d=a(c.target).closest(".btn");b.call(d,"toggle"),a(c.target).is(\'input[type="radio"], input[type="checkbox"]\')||(c.preventDefault(),d.is("input,button")?d.trigger("focus"):d.find("input:visible,button:visible").first().trigger("focus"))}).on("focus.bs.button.data-api blur.bs.button.data-api",\'[data-toggle^="button"]\',function(b){a(b.target).closest(".btn").toggleClass("focus",/^focus(in)?$/.test(b.type))})}(jQuery),+function(a){"use strict";function b(b){return this.each(function(){var d=a(this),e=d.data("bs.carousel"),f=a.extend({},c.DEFAULTS,d.data(),"object"==typeof b&&b),g="string"==typeof b?b:f.slide;e||d.data("bs.carousel",e=new c(this,f)),"number"==typeof b?e.to(b):g?e[g]():f.interval&&e.pause().cycle()})}var c=function(b,c){this.$element=a(b),this.$indicators=this.$element.find(".carousel-indicators"),this.options=c,this.paused=null,this.sliding=null,this.interval=null,this.$active=null,this.$items=null,this.options.keyboard&&this.$element.on("keydown.bs.carousel",a.proxy(this.keydown,this)),"hover"==this.options.pause&&!("ontouchstart"in document.documentElement)&&this.$element.on("mouseenter.bs.carousel",a.proxy(this.pause,this)).on("mouseleave.bs.carousel",a.proxy(this.cycle,this))};c.VERSION="3.3.7",c.TRANSITION_DURATION=600,c.DEFAULTS={interval:5e3,pause:"hover",wrap:!0,keyboard:!0},c.prototype.keydown=function(a){if(!/input|textarea/i.test(a.target.tagName)){switch(a.which){case 37:this.prev();break;case 39:this.next();break;default:return}a.preventDefault()}},c.prototype.cycle=function(b){return b||(this.paused=!1),this.interval&&clearInterval(this.interval),this.options.interval&&!this.paused&&(this.interval=setInterval(a.proxy(this.next,this),this.options.interval)),this},c.prototype.getItemIndex=function(a){return this.$items=a.parent().children(".item"),this.$items.index(a||this.$active)},c.prototype.getItemForDirection=function(a,b){var c=this.getItemIndex(b),d="prev"==a&&0===c||"next"==a&&c==this.$items.length-1;if(d&&!this.options.wrap)return b;var e="prev"==a?-1:1,f=(c+e)%this.$items.length;return this.$items.eq(f)},c.prototype.to=function(a){var b=this,c=this.getItemIndex(this.$active=this.$element.find(".item.active"));if(!(a>this.$items.length-1||a<0))return this.sliding?this.$element.one("slid.bs.carousel",function(){b.to(a)}):c==a?this.pause().cycle():this.slide(a>c?"next":"prev",this.$items.eq(a))},c.prototype.pause=function(b){return b||(this.paused=!0),this.$element.find(".next, .prev").length&&a.support.transition&&(this.$element.trigger(a.support.transition.end),this.cycle(!0)),this.interval=clearInterval(this.interval),this},c.prototype.next=function(){if(!this.sliding)return this.slide("next")},c.prototype.prev=function(){if(!this.sliding)return this.slide("prev")},c.prototype.slide=function(b,d){var e=this.$element.find(".item.active"),f=d||this.getItemForDirection(b,e),g=this.interval,h="next"==b?"left":"right",i=this;if(f.hasClass("active"))return this.sliding=!1;var j=f[0],k=a.Event("slide.bs.carousel",{relatedTarget:j,direction:h});if(this.$element.trigger(k),!k.isDefaultPrevented()){if(this.sliding=!0,g&&this.pause(),this.$indicators.length){this.$indicators.find(".active").removeClass("active");var l=a(this.$indicators.children()[this.getItemIndex(f)]);l&&l.addClass("active")}var m=a.Event("slid.bs.carousel",{relatedTarget:j,direction:h});return a.support.transition&&this.$element.hasClass("slide")?(f.addClass(b),f[0].offsetWidth,e.addClass(h),f.addClass(h),e.one("bsTransitionEnd",function(){f.removeClass([b,h].join(" ")).addClass("active"),e.removeClass(["active",h].join(" ")),i.sliding=!1,setTimeout(function(){i.$element.trigger(m)},0)}).emulateTransitionEnd(c.TRANSITION_DURATION)):(e.removeClass("active"),f.addClass("active"),this.sliding=!1,this.$element.trigger(m)),g&&this.cycle(),this}};var d=a.fn.carousel;a.fn.carousel=b,a.fn.carousel.Constructor=c,a.fn.carousel.noConflict=function(){return a.fn.carousel=d,this};var e=function(c){var d,e=a(this),f=a(e.attr("data-target")||(d=e.attr("href"))&&d.replace(/.*(?=#[^\\s]+$)/,""));if(f.hasClass("carousel")){var g=a.extend({},f.data(),e.data()),h=e.attr("data-slide-to");h&&(g.interval=!1),b.call(f,g),h&&f.data("bs.carousel").to(h),c.preventDefault()}};a(document).on("click.bs.carousel.data-api","[data-slide]",e).on("click.bs.carousel.data-api","[data-slide-to]",e),a(window).on("load",function(){a(\'[data-ride="carousel"]\').each(function(){var c=a(this);b.call(c,c.data())})})}(jQuery),+function(a){"use strict";function b(b){var c,d=b.attr("data-target")||(c=b.attr("href"))&&c.replace(/.*(?=#[^\\s]+$)/,"");return a(d)}function c(b){return this.each(function(){var c=a(this),e=c.data("bs.collapse"),f=a.extend({},d.DEFAULTS,c.data(),"object"==typeof b&&b);!e&&f.toggle&&/show|hide/.test(b)&&(f.toggle=!1),e||c.data("bs.collapse",e=new d(this,f)),"string"==typeof b&&e[b]()})}var d=function(b,c){this.$element=a(b),this.options=a.extend({},d.DEFAULTS,c),this.$trigger=a(\'[data-toggle="collapse"][href="#\'+b.id+\'"],[data-toggle="collapse"][data-target="#\'+b.id+\'"]\'),this.transitioning=null,this.options.parent?this.$parent=this.getParent():this.addAriaAndCollapsedClass(this.$element,this.$trigger),this.options.toggle&&this.toggle()};d.VERSION="3.3.7",d.TRANSITION_DURATION=350,d.DEFAULTS={toggle:!0},d.prototype.dimension=function(){var a=this.$element.hasClass("width");return a?"width":"height"},d.prototype.show=function(){if(!this.transitioning&&!this.$element.hasClass("in")){var b,e=this.$parent&&this.$parent.children(".panel").children(".in, .collapsing");if(!(e&&e.length&&(b=e.data("bs.collapse"),b&&b.transitioning))){var f=a.Event("show.bs.collapse");if(this.$element.trigger(f),!f.isDefaultPrevented()){e&&e.length&&(c.call(e,"hide"),b||e.data("bs.collapse",null));var g=this.dimension();this.$element.removeClass("collapse").addClass("collapsing")[g](0).attr("aria-expanded",!0),this.$trigger.removeClass("collapsed").attr("aria-expanded",!0),this.transitioning=1;var h=function(){this.$element.removeClass("collapsing").addClass("collapse in")[g](""),this.transitioning=0,this.$element.trigger("shown.bs.collapse")};if(!a.support.transition)return h.call(this);var i=a.camelCase(["scroll",g].join("-"));this.$element.one("bsTransitionEnd",a.proxy(h,this)).emulateTransitionEnd(d.TRANSITION_DURATION)[g](this.$element[0][i])}}}},d.prototype.hide=function(){if(!this.transitioning&&this.$element.hasClass("in")){var b=a.Event("hide.bs.collapse");if(this.$element.trigger(b),!b.isDefaultPrevented()){var c=this.dimension();this.$element[c](this.$element[c]())[0].offsetHeight,this.$element.addClass("collapsing").removeClass("collapse in").attr("aria-expanded",!1),this.$trigger.addClass("collapsed").attr("aria-expanded",!1),this.transitioning=1;var e=function(){this.transitioning=0,this.$element.removeClass("collapsing").addClass("collapse").trigger("hidden.bs.collapse")};return a.support.transition?void this.$element[c](0).one("bsTransitionEnd",a.proxy(e,this)).emulateTransitionEnd(d.TRANSITION_DURATION):e.call(this)}}},d.prototype.toggle=function(){this[this.$element.hasClass("in")?"hide":"show"]()},d.prototype.getParent=function(){return a(this.options.parent).find(\'[data-toggle="collapse"][data-parent="\'+this.options.parent+\'"]\').each(a.proxy(function(c,d){var e=a(d);this.addAriaAndCollapsedClass(b(e),e)},this)).end()},d.prototype.addAriaAndCollapsedClass=function(a,b){var c=a.hasClass("in");a.attr("aria-expanded",c),b.toggleClass("collapsed",!c).attr("aria-expanded",c)};var e=a.fn.collapse;a.fn.collapse=c,a.fn.collapse.Constructor=d,a.fn.collapse.noConflict=function(){return a.fn.collapse=e,this},a(document).on("click.bs.collapse.data-api",\'[data-toggle="collapse"]\',function(d){var e=a(this);e.attr("data-target")||d.preventDefault();var f=b(e),g=f.data("bs.collapse"),h=g?"toggle":e.data();c.call(f,h)})}(jQuery),+function(a){"use strict";function b(b){var c=b.attr("data-target");c||(c=b.attr("href"),c=c&&/#[A-Za-z]/.test(c)&&c.replace(/.*(?=#[^\\s]*$)/,""));var d=c&&a(c);return d&&d.length?d:b.parent()}function c(c){c&&3===c.which||(a(e).remove(),a(f).each(function(){var d=a(this),e=b(d),f={relatedTarget:this};e.hasClass("open")&&(c&&"click"==c.type&&/input|textarea/i.test(c.target.tagName)&&a.contains(e[0],c.target)||(e.trigger(c=a.Event("hide.bs.dropdown",f)),c.isDefaultPrevented()||(d.attr("aria-expanded","false"),e.removeClass("open").trigger(a.Event("hidden.bs.dropdown",f)))))}))}function d(b){return this.each(function(){var c=a(this),d=c.data("bs.dropdown");d||c.data("bs.dropdown",d=new g(this)),"string"==typeof b&&d[b].call(c)})}var e=".dropdown-backdrop",f=\'[data-toggle="dropdown"]\',g=function(b){a(b).on("click.bs.dropdown",this.toggle)};g.VERSION="3.3.7",g.prototype.toggle=function(d){var e=a(this);if(!e.is(".disabled, :disabled")){var f=b(e),g=f.hasClass("open");if(c(),!g){"ontouchstart"in document.documentElement&&!f.closest(".navbar-nav").length&&a(document.createElement("div")).addClass("dropdown-backdrop").insertAfter(a(this)).on("click",c);var h={relatedTarget:this};if(f.trigger(d=a.Event("show.bs.dropdown",h)),d.isDefaultPrevented())return;e.trigger("focus").attr("aria-expanded","true"),f.toggleClass("open").trigger(a.Event("shown.bs.dropdown",h))}return!1}},g.prototype.keydown=function(c){if(/(38|40|27|32)/.test(c.which)&&!/input|textarea/i.test(c.target.tagName)){var d=a(this);if(c.preventDefault(),c.stopPropagation(),!d.is(".disabled, :disabled")){var e=b(d),g=e.hasClass("open");if(!g&&27!=c.which||g&&27==c.which)return 27==c.which&&e.find(f).trigger("focus"),d.trigger("click");var h=" li:not(.disabled):visible a",i=e.find(".dropdown-menu"+h);if(i.length){var j=i.index(c.target);38==c.which&&j>0&&j--,40==c.which&&j<i.length-1&&j++,~j||(j=0),i.eq(j).trigger("focus")}}}};var h=a.fn.dropdown;a.fn.dropdown=d,a.fn.dropdown.Constructor=g,a.fn.dropdown.noConflict=function(){return a.fn.dropdown=h,this},a(document).on("click.bs.dropdown.data-api",c).on("click.bs.dropdown.data-api",".dropdown form",function(a){a.stopPropagation()}).on("click.bs.dropdown.data-api",f,g.prototype.toggle).on("keydown.bs.dropdown.data-api",f,g.prototype.keydown).on("keydown.bs.dropdown.data-api",".dropdown-menu",g.prototype.keydown)}(jQuery),+function(a){"use strict";function b(b,d){return this.each(function(){var e=a(this),f=e.data("bs.modal"),g=a.extend({},c.DEFAULTS,e.data(),"object"==typeof b&&b);f||e.data("bs.modal",f=new c(this,g)),"string"==typeof b?f[b](d):g.show&&f.show(d)})}var c=function(b,c){this.options=c,this.$body=a(document.body),this.$element=a(b),this.$dialog=this.$element.find(".modal-dialog"),this.$backdrop=null,this.isShown=null,this.originalBodyPad=null,this.scrollbarWidth=0,this.ignoreBackdropClick=!1,this.options.remote&&this.$element.find(".modal-content").load(this.options.remote,a.proxy(function(){this.$element.trigger("loaded.bs.modal")},this))};c.VERSION="3.3.7",c.TRANSITION_DURATION=300,c.BACKDROP_TRANSITION_DURATION=150,c.DEFAULTS={backdrop:!0,keyboard:!0,show:!0},c.prototype.toggle=function(a){return this.isShown?this.hide():this.show(a)},c.prototype.show=function(b){var d=this,e=a.Event("show.bs.modal",{relatedTarget:b});this.$element.trigger(e),this.isShown||e.isDefaultPrevented()||(this.isShown=!0,this.checkScrollbar(),this.setScrollbar(),this.$body.addClass("modal-open"),this.escape(),this.resize(),this.$element.on("click.dismiss.bs.modal",\'[data-dismiss="modal"]\',a.proxy(this.hide,this)),this.$dialog.on("mousedown.dismiss.bs.modal",function(){d.$element.one("mouseup.dismiss.bs.modal",function(b){a(b.target).is(d.$element)&&(d.ignoreBackdropClick=!0)})}),this.backdrop(function(){var e=a.support.transition&&d.$element.hasClass("fade");d.$element.parent().length||d.$element.appendTo(d.$body),d.$element.show().scrollTop(0),d.adjustDialog(),e&&d.$element[0].offsetWidth,d.$element.addClass("in"),d.enforceFocus();var f=a.Event("shown.bs.modal",{relatedTarget:b});e?d.$dialog.one("bsTransitionEnd",function(){d.$element.trigger("focus").trigger(f)}).emulateTransitionEnd(c.TRANSITION_DURATION):d.$element.trigger("focus").trigger(f)}))},c.prototype.hide=function(b){b&&b.preventDefault(),b=a.Event("hide.bs.modal"),this.$element.trigger(b),this.isShown&&!b.isDefaultPrevented()&&(this.isShown=!1,this.escape(),this.resize(),a(document).off("focusin.bs.modal"),this.$element.removeClass("in").off("click.dismiss.bs.modal").off("mouseup.dismiss.bs.modal"),this.$dialog.off("mousedown.dismiss.bs.modal"),a.support.transition&&this.$element.hasClass("fade")?this.$element.one("bsTransitionEnd",a.proxy(this.hideModal,this)).emulateTransitionEnd(c.TRANSITION_DURATION):this.hideModal())},c.prototype.enforceFocus=function(){a(document).off("focusin.bs.modal").on("focusin.bs.modal",a.proxy(function(a){doc' , org_gwtbootstrap3_client_GwtBootstrap3ClientBundle_1default_1InlineClientBundleGenerator$1_$getText__Lorg_gwtbootstrap3_client_GwtBootstrap3ClientBundle_1default_1InlineClientBundleGenerator$1_2Ljava_lang_String_2_builder_0.java_lang_AbstractStringBuilder_string += 'ument===a.target||this.$element[0]===a.target||this.$element.has(a.target).length||this.$element.trigger("focus")},this))},c.prototype.escape=function(){this.isShown&&this.options.keyboard?this.$element.on("keydown.dismiss.bs.modal",a.proxy(function(a){27==a.which&&this.hide()},this)):this.isShown||this.$element.off("keydown.dismiss.bs.modal")},c.prototype.resize=function(){this.isShown?a(window).on("resize.bs.modal",a.proxy(this.handleUpdate,this)):a(window).off("resize.bs.modal")},c.prototype.hideModal=function(){var a=this;this.$element.hide(),this.backdrop(function(){a.$body.removeClass("modal-open"),a.resetAdjustments(),a.resetScrollbar(),a.$element.trigger("hidden.bs.modal")})},c.prototype.removeBackdrop=function(){this.$backdrop&&this.$backdrop.remove(),this.$backdrop=null},c.prototype.backdrop=function(b){var d=this,e=this.$element.hasClass("fade")?"fade":"";if(this.isShown&&this.options.backdrop){var f=a.support.transition&&e;if(this.$backdrop=a(document.createElement("div")).addClass("modal-backdrop "+e).appendTo(this.$body),this.$element.on("click.dismiss.bs.modal",a.proxy(function(a){return this.ignoreBackdropClick?void(this.ignoreBackdropClick=!1):void(a.target===a.currentTarget&&("static"==this.options.backdrop?this.$element[0].focus():this.hide()))},this)),f&&this.$backdrop[0].offsetWidth,this.$backdrop.addClass("in"),!b)return;f?this.$backdrop.one("bsTransitionEnd",b).emulateTransitionEnd(c.BACKDROP_TRANSITION_DURATION):b()}else if(!this.isShown&&this.$backdrop){this.$backdrop.removeClass("in");var g=function(){d.removeBackdrop(),b&&b()};a.support.transition&&this.$element.hasClass("fade")?this.$backdrop.one("bsTransitionEnd",g).emulateTransitionEnd(c.BACKDROP_TRANSITION_DURATION):g()}else b&&b()},c.prototype.handleUpdate=function(){this.adjustDialog()},c.prototype.adjustDialog=function(){var a=this.$element[0].scrollHeight>document.documentElement.clientHeight;this.$element.css({paddingLeft:!this.bodyIsOverflowing&&a?this.scrollbarWidth:"",paddingRight:this.bodyIsOverflowing&&!a?this.scrollbarWidth:""})},c.prototype.resetAdjustments=function(){this.$element.css({paddingLeft:"",paddingRight:""})},c.prototype.checkScrollbar=function(){var a=window.innerWidth;if(!a){var b=document.documentElement.getBoundingClientRect();a=b.right-Math.abs(b.left)}this.bodyIsOverflowing=document.body.clientWidth<a,this.scrollbarWidth=this.measureScrollbar()},c.prototype.setScrollbar=function(){var a=parseInt(this.$body.css("padding-right")||0,10);this.originalBodyPad=document.body.style.paddingRight||"",this.bodyIsOverflowing&&this.$body.css("padding-right",a+this.scrollbarWidth)},c.prototype.resetScrollbar=function(){this.$body.css("padding-right",this.originalBodyPad)},c.prototype.measureScrollbar=function(){var a=document.createElement("div");a.className="modal-scrollbar-measure",this.$body.append(a);var b=a.offsetWidth-a.clientWidth;return this.$body[0].removeChild(a),b};var d=a.fn.modal;a.fn.modal=b,a.fn.modal.Constructor=c,a.fn.modal.noConflict=function(){return a.fn.modal=d,this},a(document).on("click.bs.modal.data-api",\'[data-toggle="modal"]\',function(c){var d=a(this),e=d.attr("href"),f=a(d.attr("data-target")||e&&e.replace(/.*(?=#[^\\s]+$)/,"")),g=f.data("bs.modal")?"toggle":a.extend({remote:!/#/.test(e)&&e},f.data(),d.data());d.is("a")&&c.preventDefault(),f.one("show.bs.modal",function(a){a.isDefaultPrevented()||f.one("hidden.bs.modal",function(){d.is(":visible")&&d.trigger("focus")})}),b.call(f,g,this)})}(jQuery),+function(a){"use strict";function b(b){return this.each(function(){var d=a(this),e=d.data("bs.tooltip"),f="object"==typeof b&&b;!e&&/destroy|hide/.test(b)||(e||d.data("bs.tooltip",e=new c(this,f)),"string"==typeof b&&e[b]())})}var c=function(a,b){this.type=null,this.options=null,this.enabled=null,this.timeout=null,this.hoverState=null,this.$element=null,this.inState=null,this.init("tooltip",a,b)};c.VERSION="3.3.7",c.TRANSITION_DURATION=150,c.DEFAULTS={animation:!0,placement:"top",selector:!1,template:\'<div class="tooltip" role="tooltip"><div class="tooltip-arrow"><\/div><div class="tooltip-inner"><\/div><\/div>\',trigger:"hover focus",title:"",delay:0,html:!1,container:!1,viewport:{selector:"body",padding:0}},c.prototype.init=function(b,c,d){if(this.enabled=!0,this.type=b,this.$element=a(c),this.options=this.getOptions(d),this.$viewport=this.options.viewport&&a(a.isFunction(this.options.viewport)?this.options.viewport.call(this,this.$element):this.options.viewport.selector||this.options.viewport),this.inState={click:!1,hover:!1,focus:!1},this.$element[0]instanceof document.constructor&&!this.options.selector)throw new Error("`selector` option must be specified when initializing "+this.type+" on the window.document object!");for(var e=this.options.trigger.split(" "),f=e.length;f--;){var g=e[f];if("click"==g)this.$element.on("click."+this.type,this.options.selector,a.proxy(this.toggle,this));else if("manual"!=g){var h="hover"==g?"mouseenter":"focusin",i="hover"==g?"mouseleave":"focusout";this.$element.on(h+"."+this.type,this.options.selector,a.proxy(this.enter,this)),this.$element.on(i+"."+this.type,this.options.selector,a.proxy(this.leave,this))}}this.options.selector?this._options=a.extend({},this.options,{trigger:"manual",selector:""}):this.fixTitle()},c.prototype.getDefaults=function(){return c.DEFAULTS},c.prototype.getOptions=function(b){return b=a.extend({},this.getDefaults(),this.$element.data(),b),b.delay&&"number"==typeof b.delay&&(b.delay={show:b.delay,hide:b.delay}),b},c.prototype.getDelegateOptions=function(){var b={},c=this.getDefaults();return this._options&&a.each(this._options,function(a,d){c[a]!=d&&(b[a]=d)}),b},c.prototype.enter=function(b){var c=b instanceof this.constructor?b:a(b.currentTarget).data("bs."+this.type);return c||(c=new this.constructor(b.currentTarget,this.getDelegateOptions()),a(b.currentTarget).data("bs."+this.type,c)),b instanceof a.Event&&(c.inState["focusin"==b.type?"focus":"hover"]=!0),c.tip().hasClass("in")||"in"==c.hoverState?void(c.hoverState="in"):(clearTimeout(c.timeout),c.hoverState="in",c.options.delay&&c.options.delay.show?void(c.timeout=setTimeout(function(){"in"==c.hoverState&&c.show()},c.options.delay.show)):c.show())},c.prototype.isInStateTrue=function(){for(var a in this.inState)if(this.inState[a])return!0;return!1},c.prototype.leave=function(b){var c=b instanceof this.constructor?b:a(b.currentTarget).data("bs."+this.type);if(c||(c=new this.constructor(b.currentTarget,this.getDelegateOptions()),a(b.currentTarget).data("bs."+this.type,c)),b instanceof a.Event&&(c.inState["focusout"==b.type?"focus":"hover"]=!1),!c.isInStateTrue())return clearTimeout(c.timeout),c.hoverState="out",c.options.delay&&c.options.delay.hide?void(c.timeout=setTimeout(function(){"out"==c.hoverState&&c.hide()},c.options.delay.hide)):c.hide()},c.prototype.show=function(){var b=a.Event("show.bs."+this.type);if(this.hasContent()&&this.enabled){this.$element.trigger(b);var d=a.contains(this.$element[0].ownerDocument.documentElement,this.$element[0]);if(b.isDefaultPrevented()||!d)return;var e=this,f=this.tip(),g=this.getUID(this.type);this.setContent(),f.attr("id",g),this.$element.attr("aria-describedby",g),this.options.animation&&f.addClass("fade");var h="function"==typeof this.options.placement?this.options.placement.call(this,f[0],this.$element[0]):this.options.placement,i=/\\s?auto?\\s?/i,j=i.test(h);j&&(h=h.replace(i,"")||"top"),f.detach().css({top:0,left:0,display:"block"}).addClass(h).data("bs."+this.type,this),this.options.container?f.appendTo(this.options.container):f.insertAfter(this.$element),this.$element.trigger("inserted.bs."+this.type);var k=this.getPosition(),l=f[0].offsetWidth,m=f[0].offsetHeight;if(j){var n=h,o=this.getPosition(this.$viewport);h="bottom"==h&&k.bottom+m>o.bottom?"top":"top"==h&&k.top-m<o.top?"bottom":"right"==h&&k.right+l>o.width?"left":"left"==h&&k.left-l<o.left?"right":h,f.removeClass(n).addClass(h)}var p=this.getCalculatedOffset(h,k,l,m);this.applyPlacement(p,h);var q=function(){var a=e.hoverState;e.$element.trigger("shown.bs."+e.type),e.hoverState=null,"out"==a&&e.leave(e)};a.support.transition&&this.$tip.hasClass("fade")?f.one("bsTransitionEnd",q).emulateTransitionEnd(c.TRANSITION_DURATION):q()}},c.prototype.applyPlacement=function(b,c){var d=this.tip(),e=d[0].offsetWidth,f=d[0].offsetHeight,g=parseInt(d.css("margin-top"),10),h=parseInt(d.css("margin-left"),10);isNaN(g)&&(g=0),isNaN(h)&&(h=0),b.top+=g,b.left+=h,a.offset.setOffset(d[0],a.extend({using:function(a){d.css({top:Math.round(a.top),left:Math.round(a.left)})}},b),0),d.addClass("in");var i=d[0].offsetWidth,j=d[0].offsetHeight;"top"==c&&j!=f&&(b.top=b.top+f-j);var k=this.getViewportAdjustedDelta(c,b,i,j);k.left?b.left+=k.left:b.top+=k.top;var l=/top|bottom/.test(c),m=l?2*k.left-e+i:2*k.top-f+j,n=l?"offsetWidth":"offsetHeight";d.offset(b),this.replaceArrow(m,d[0][n],l)},c.prototype.replaceArrow=function(a,b,c){this.arrow().css(c?"left":"top",50*(1-a/b)+"%").css(c?"top":"left","")},c.prototype.setContent=function(){var a=this.tip(),b=this.getTitle();a.find(".tooltip-inner")[this.options.html?"html":"text"](b),a.removeClass("fade in top bottom left right")},c.prototype.hide=function(b){function d(){"in"!=e.hoverState&&f.detach(),e.$element&&e.$element.removeAttr("aria-describedby").trigger("hidden.bs."+e.type),b&&b()}var e=this,f=a(this.$tip),g=a.Event("hide.bs."+this.type);if(this.$element.trigger(g),!g.isDefaultPrevented())return f.removeClass("in"),a.support.transition&&f.hasClass("fade")?f.one("bsTransitionEnd",d).emulateTransitionEnd(c.TRANSITION_DURATION):d(),this.hoverState=null,this},c.prototype.fixTitle=function(){var a=this.$element;(a.attr("title")||"string"!=typeof a.attr("data-original-title"))&&a.attr("data-original-title",a.attr("title")||"").attr("title","")},c.prototype.hasContent=function(){return this.getTitle()},c.prototype.getPosition=function(b){b=b||this.$element;var c=b[0],d="BODY"==c.tagName,e=c.getBoundingClientRect();null==e.width&&(e=a.extend({},e,{width:e.right-e.left,height:e.bottom-e.top}));var f=window.SVGElement&&c instanceof window.SVGElement,g=d?{top:0,left:0}:f?null:b.offset(),h={scroll:d?document.documentElement.scrollTop||document.body.scrollTop:b.scrollTop()},i=d?{width:a(window).width(),height:a(window).height()}:null;return a.extend({},e,h,i,g)},c.prototype.getCalculatedOffset=function(a,b,c,d){return"bottom"==a?{top:b.top+b.height,left:b.left+b.width/2-c/2}:"top"==a?{top:b.top-d,left:b.left+b.width/2-c/2}:"left"==a?{top:b.top+b.height/2-d/2,left:b.left-c}:{top:b.top+b.height/2-d/2,left:b.left+b.width}},c.prototype.getViewportAdjustedDelta=function(a,b,c,d){var e={top:0,left:0};if(!this.$viewport)return e;var f=this.options.viewport&&this.options.viewport.padding||0,g=this.getPosition(this.$viewport);if(/right|left/.test(a)){var h=b.top-f-g.scroll,i=b.top+f-g.scroll+d;h<g.top?e.top=g.top-h:i>g.top+g.height&&(e.top=g.top+g.height-i)}else{var j=b.left-f,k=b.left+f+c;j<g.left?e.left=g.left-j:k>g.right&&(e.left=g.left+g.width-k)}return e},c.prototype.getTitle=function(){var a,b=this.$element,c=this.options;return a=b.attr("data-original-title")||("function"==typeof c.title?c.title.call(b[0]):c.title)},c.prototype.getUID=function(a){do a+=~~(1e6*Math.random());while(document.getElementById(a));return a},c.prototype.tip=function(){if(!this.$tip&&(this.$tip=a(this.options.template),1!=this.$tip.length))throw new Error(this.type+" `template` option must consist of exactly 1 top-level element!");return this.$tip},c.prototype.arrow=function(){return this.$arrow=this.$arrow||this.tip().find(".tooltip-arrow")},c.prototype.enable=function(){this.enabled=!0},c.prototype.disable=function(){this.enabled=!1},c.prototype.toggleEnabled=function(){this.enabled=!this.enabled},c.prototype.toggle=function(b){var c=this;b&&(c=a(b.currentTarget).data("bs."+this.type),c||(c=new this.constructor(b.currentTarget,this.getDelegateOptions()),a(b.currentTarget).data("bs."+this.type,c))),b?(c.inState.click=!c.inState.click,c.isInStateTrue()?c.enter(c):c.leave(c)):c.tip().hasClass("in")?c.leave(c):c.enter(c)},c.prototype.destroy=function(){var a=this;clearTimeout(this.timeout),this.hide(function(){a.$element.off("."+a.type).removeData("bs."+a.type),a.$tip&&a.$tip.detach(),a.$tip=null,a.$arrow=null,a.$viewport=null,a.$element=null})};var d=a.fn.tooltip;a.fn.tooltip=b,a.fn.tooltip.Constructor=c,a.fn.tooltip.noConflict=function(){return a.fn.tooltip=d,this}}(jQuery),+function(a){"use strict";function b(b){return this.each(function(){var d=a(this),e=d.data("bs.popover"),f="object"==typeof b&&b;!e&&/destroy|hide/.test(b)||(e||d.data("bs.popover",e=new c(this,f)),"string"==typeof b&&e[b]())})}var c=function(a,b){this.init("popover",a,b)};if(!a.fn.tooltip)throw new Error("Popover requires tooltip.js");c.VERSION="3.3.7",c.DEFAULTS=a.extend({},a.fn.tooltip.Constructor.DEFAULTS,{placement:"right",trigger:"click",content:"",template:\'<div class="popover" role="tooltip"><div class="arrow"><\/div><h3 class="popover-title"><\/h3><div class="popover-content"><\/div><\/div>\'}),c.prototype=a.extend({},a.fn.tooltip.Constructor.prototype),c.prototype.constructor=c,c.prototype.getDefaults=function(){return c.DEFAULTS},c.prototype.setContent=function(){var a=this.tip(),b=this.getTitle(),c=this.getContent();a.find(".popover-title")[this.options.html?"html":"text"](b),a.find(".popover-content").children().detach().end()[this.options.html?"string"==typeof c?"html":"append":"text"](c),a.removeClass("fade top bottom left right in"),a.find(".popover-title").html()||a.find(".popover-title").hide()},c.prototype.hasContent=function(){return this.getTitle()||this.getContent()},c.prototype.getContent=function(){var a=this.$element,b=this.options;return a.attr("data-content")||("function"==typeof b.content?b.content.call(a[0]):b.content)},c.prototype.arrow=function(){return this.$arrow=this.$arrow||this.tip().find(".arrow")};var d=a.fn.popover;a.fn.popover=b,a.fn.popover.Constructor=c,a.fn.popover.noConflict=function(){return a.fn.popover=d,this}}(jQuery),+function(a){"use strict";function b(c,d){this.$body=a(document.body),this.$scrollElement=a(a(c).is(document.body)?window:c),this.options=a.extend({},b.DEFAULTS,d),this.selector=(this.options.target||"")+" .nav li > a",this.offsets=[],this.targets=[],this.activeTarget=null,this.scrollHeight=0,this.$scrollElement.on("scroll.bs.scrollspy",a.proxy(this.process,this)),this.refresh(),this.process()}function c(c){return this.each(function(){var d=a(this),e=d.data("bs.scrollspy"),f="object"==typeof c&&c;e||d.data("bs.scrollspy",e=new b(this,f)),"string"==typeof c&&e[c]()})}b.VERSION="3.3.7",b.DEFAULTS={offset:10},b.prototype.getScrollHeight=function(){return this.$scrollElement[0].scrollHeight||Math.max(this.$body[0].scrollHeight,document.documentElement.scrollHeight)},b.prototype.refresh=function(){var b=this,c="offset",d=0;this.offsets=[],this.targets=[],this.scrollHeight=this.getScrollHeight(),a.isWindow(this.$scrollElement[0])||(c="position",d=this.$scrollElement.scrollTop()),this.$body.find(this.selector).map(function(){var b=a(this),e=b.data("target")||b.attr("href"),f=/^#./.test(e)&&a(e);return f&&f.length&&f.is(":visible")&&[[f[c]().top+d,e]]||null}).sort(function(a,b){return a[0]-b[0]}).each(function(){b.offsets.push(this[0]),b.targets.push(this[1])})},b.prototype.process=function(){var a,b=this.$scrollElement.scrollTop()+this.options.offset,c=this.getScrollHeight(),d=this.options.offset+c-this.$scrollElement.height(),e=this.offsets,f=this.targets,g=this.activeTarget;if(this.scrollHeight!=c&&this.refresh(),b>=d)return g!=(a=f[f.length-1])&&this.activate(a);if(g&&b<e[0])return this.activeTarget=null,this.clear();for(a=e.length;a--;)g!=f[a]&&b>=e[a]&&(void 0===e[a+1]||b<e[a+1])&&this.activate(f[a])},b.prototype.activate=function(b){\nthis.activeTarget=b,this.clear();var c=this.selector+\'[data-target="\'+b+\'"],\'+this.selector+\'[href="\'+b+\'"]\',d=a(c).parents("li").addClass("active");d.parent(".dropdown-menu").length&&(d=d.closest("li.dropdown").addClass("active")),d.trigger("activate.bs.scrollspy")},b.prototype.clear=function(){a(this.selector).parentsUntil(this.options.target,".active").removeClass("active")};var d=a.fn.scrollspy;a.fn.scrollspy=c,a.fn.scrollspy.Constructor=b,a.fn.scrollspy.noConflict=function(){return a.fn.scrollspy=d,this},a(window).on("load.bs.scrollspy.data-api",function(){a(\'[data-spy="scroll"]\').each(function(' , org_gwtbootstrap3_client_GwtBootstrap3ClientBundle_1default_1InlineClientBundleGenerator$1_$getText__Lorg_gwtbootstrap3_client_GwtBootstrap3ClientBundle_1default_1InlineClientBundleGenerator$1_2Ljava_lang_String_2_builder_0.java_lang_AbstractStringBuilder_string += '){var b=a(this);c.call(b,b.data())})})}(jQuery),+function(a){"use strict";function b(b){return this.each(function(){var d=a(this),e=d.data("bs.tab");e||d.data("bs.tab",e=new c(this)),"string"==typeof b&&e[b]()})}var c=function(b){this.element=a(b)};c.VERSION="3.3.7",c.TRANSITION_DURATION=150,c.prototype.show=function(){var b=this.element,c=b.closest("ul:not(.dropdown-menu)"),d=b.data("target");if(d||(d=b.attr("href"),d=d&&d.replace(/.*(?=#[^\\s]*$)/,"")),!b.parent("li").hasClass("active")){var e=c.find(".active:last a"),f=a.Event("hide.bs.tab",{relatedTarget:b[0]}),g=a.Event("show.bs.tab",{relatedTarget:e[0]});if(e.trigger(f),b.trigger(g),!g.isDefaultPrevented()&&!f.isDefaultPrevented()){var h=a(d);this.activate(b.closest("li"),c),this.activate(h,h.parent(),function(){e.trigger({type:"hidden.bs.tab",relatedTarget:b[0]}),b.trigger({type:"shown.bs.tab",relatedTarget:e[0]})})}}},c.prototype.activate=function(b,d,e){function f(){g.removeClass("active").find("> .dropdown-menu > .active").removeClass("active").end().find(\'[data-toggle="tab"]\').attr("aria-expanded",!1),b.addClass("active").find(\'[data-toggle="tab"]\').attr("aria-expanded",!0),h?(b[0].offsetWidth,b.addClass("in")):b.removeClass("fade"),b.parent(".dropdown-menu").length&&b.closest("li.dropdown").addClass("active").end().find(\'[data-toggle="tab"]\').attr("aria-expanded",!0),e&&e()}var g=d.find("> .active"),h=e&&a.support.transition&&(g.length&&g.hasClass("fade")||!!d.find("> .fade").length);g.length&&h?g.one("bsTransitionEnd",f).emulateTransitionEnd(c.TRANSITION_DURATION):f(),g.removeClass("in")};var d=a.fn.tab;a.fn.tab=b,a.fn.tab.Constructor=c,a.fn.tab.noConflict=function(){return a.fn.tab=d,this};var e=function(c){c.preventDefault(),b.call(a(this),"show")};a(document).on("click.bs.tab.data-api",\'[data-toggle="tab"]\',e).on("click.bs.tab.data-api",\'[data-toggle="pill"]\',e)}(jQuery),+function(a){"use strict";function b(b){return this.each(function(){var d=a(this),e=d.data("bs.affix"),f="object"==typeof b&&b;e||d.data("bs.affix",e=new c(this,f)),"string"==typeof b&&e[b]()})}var c=function(b,d){this.options=a.extend({},c.DEFAULTS,d),this.$target=a(this.options.target).on("scroll.bs.affix.data-api",a.proxy(this.checkPosition,this)).on("click.bs.affix.data-api",a.proxy(this.checkPositionWithEventLoop,this)),this.$element=a(b),this.affixed=null,this.unpin=null,this.pinnedOffset=null,this.checkPosition()};c.VERSION="3.3.7",c.RESET="affix affix-top affix-bottom",c.DEFAULTS={offset:0,target:window},c.prototype.getState=function(a,b,c,d){var e=this.$target.scrollTop(),f=this.$element.offset(),g=this.$target.height();if(null!=c&&"top"==this.affixed)return e<c&&"top";if("bottom"==this.affixed)return null!=c?!(e+this.unpin<=f.top)&&"bottom":!(e+g<=a-d)&&"bottom";var h=null==this.affixed,i=h?e:f.top,j=h?g:b;return null!=c&&e<=c?"top":null!=d&&i+j>=a-d&&"bottom"},c.prototype.getPinnedOffset=function(){if(this.pinnedOffset)return this.pinnedOffset;this.$element.removeClass(c.RESET).addClass("affix");var a=this.$target.scrollTop(),b=this.$element.offset();return this.pinnedOffset=b.top-a},c.prototype.checkPositionWithEventLoop=function(){setTimeout(a.proxy(this.checkPosition,this),1)},c.prototype.checkPosition=function(){if(this.$element.is(":visible")){var b=this.$element.height(),d=this.options.offset,e=d.top,f=d.bottom,g=Math.max(a(document).height(),a(document.body).height());"object"!=typeof d&&(f=e=d),"function"==typeof e&&(e=d.top(this.$element)),"function"==typeof f&&(f=d.bottom(this.$element));var h=this.getState(g,b,e,f);if(this.affixed!=h){null!=this.unpin&&this.$element.css("top","");var i="affix"+(h?"-"+h:""),j=a.Event(i+".bs.affix");if(this.$element.trigger(j),j.isDefaultPrevented())return;this.affixed=h,this.unpin="bottom"==h?this.getPinnedOffset():null,this.$element.removeClass(c.RESET).addClass(i).trigger(i.replace("affix","affixed")+".bs.affix")}"bottom"==h&&this.$element.offset({top:g-b-f})}};var d=a.fn.affix;a.fn.affix=b,a.fn.affix.Constructor=c,a.fn.affix.noConflict=function(){return a.fn.affix=d,this},a(window).on("load",function(){a(\'[data-spy="affix"]\').each(function(){var c=a(this),d=c.data();d.offset=d.offset||{},null!=d.offsetBottom&&(d.offset.bottom=d.offsetBottom),null!=d.offsetTop&&(d.offset.top=d.offsetTop),b.call(c,d)})})}(jQuery);' , org_gwtbootstrap3_client_GwtBootstrap3ClientBundle_1default_1InlineClientBundleGenerator$1_$getText__Lorg_gwtbootstrap3_client_GwtBootstrap3ClientBundle_1default_1InlineClientBundleGenerator$1_2Ljava_lang_String_2_builder_0.java_lang_AbstractStringBuilder_string))), com_google_gwt_core_client_ScriptInjector_TOP_1WINDOW));
  com_flair_client_ClientEndPoint_$init__Lcom_flair_client_ClientEndPoint_2V((com_flair_client_ClientEndPoint_$clinit__V() , com_flair_client_ClientEndPoint_$clinit__V() , com_flair_client_ClientEndPoint_INSTANCE));
}

function com_google_gwt_regexp_shared_RegExp_$replace__Lcom_google_gwt_regexp_shared_RegExp_2Ljava_lang_String_2Ljava_lang_String_2Ljava_lang_String_2(this$static, input_0, replacement){
  return input_0.replace(this$static, replacement);
}

function com_google_gwt_regexp_shared_RegExp_$test__Lcom_google_gwt_regexp_shared_RegExp_2Ljava_lang_String_2Z(this$static, input_0){
  return this$static.test(input_0);
}

function com_google_gwt_safehtml_shared_OnlyToBeUsedInGeneratedCodeStringBlessedAsSafeHtml_OnlyToBeUsedInGeneratedCodeStringBlessedAsSafeHtml__Ljava_lang_String_2V(html){
  if (html == null) {
    throw com_google_gwt_lang_Exceptions_toJs__Ljava_lang_Object_2Ljava_lang_Object_2(new java_lang_NullPointerException_NullPointerException__Ljava_lang_String_2V('html is null'));
  }
  this.com_google_gwt_safehtml_shared_OnlyToBeUsedInGeneratedCodeStringBlessedAsSafeHtml_html = html;
}

com_google_gwt_lang_Runtime_defineClass__Lcom_google_gwt_core_client_JavaScriptObject_2Lcom_google_gwt_core_client_JavaScriptObject_2Lcom_google_gwt_core_client_JavaScriptObject_2V(37, 1, {67:1, 3:1}, com_google_gwt_safehtml_shared_OnlyToBeUsedInGeneratedCodeStringBlessedAsSafeHtml_OnlyToBeUsedInGeneratedCodeStringBlessedAsSafeHtml__Ljava_lang_String_2V);
_.asString__Ljava_lang_String_2 = function com_google_gwt_safehtml_shared_OnlyToBeUsedInGeneratedCodeStringBlessedAsSafeHtml_asString__Ljava_lang_String_2(){
  return this.com_google_gwt_safehtml_shared_OnlyToBeUsedInGeneratedCodeStringBlessedAsSafeHtml_html;
}
;
_.equals__Ljava_lang_Object_2Z = function com_google_gwt_safehtml_shared_OnlyToBeUsedInGeneratedCodeStringBlessedAsSafeHtml_equals__Ljava_lang_Object_2Z(obj){
  if (!com_google_gwt_lang_Cast_instanceOf__Ljava_lang_Object_2Lcom_google_gwt_core_client_JavaScriptObject_2Z(obj, 67)) {
    return false;
  }
  return java_lang_String_$equals__Ljava_lang_String_2Ljava_lang_Object_2Z(this.com_google_gwt_safehtml_shared_OnlyToBeUsedInGeneratedCodeStringBlessedAsSafeHtml_html, com_google_gwt_lang_Cast_castTo__Ljava_lang_Object_2Lcom_google_gwt_core_client_JavaScriptObject_2Ljava_lang_Object_2(obj, 67).asString__Ljava_lang_String_2());
}
;
_.hashCode__I = function com_google_gwt_safehtml_shared_OnlyToBeUsedInGeneratedCodeStringBlessedAsSafeHtml_hashCode__I(){
  return javaemul_internal_StringHashCache_getHashCode__Ljava_lang_String_2I(this.com_google_gwt_safehtml_shared_OnlyToBeUsedInGeneratedCodeStringBlessedAsSafeHtml_html);
}
;
var com_google_gwt_lang_ClassLiteralHolder_Lcom_1google_1gwt_1safehtml_1shared_1OnlyToBeUsedInGeneratedCodeStringBlessedAsSafeHtml_12_1classLit = java_lang_Class_createForClass__Ljava_lang_String_2Ljava_lang_String_2Lcom_google_gwt_core_client_JavaScriptObject_2Ljava_lang_Class_2Ljava_lang_Class_2($intern_40, 'OnlyToBeUsedInGeneratedCodeStringBlessedAsSafeHtml', 37);
function com_google_gwt_safehtml_shared_SafeHtmlString_SafeHtmlString__Ljava_lang_String_2V(html){
  this.com_google_gwt_safehtml_shared_SafeHtmlString_html = html;
}

com_google_gwt_lang_Runtime_defineClass__Lcom_google_gwt_core_client_JavaScriptObject_2Lcom_google_gwt_core_client_JavaScriptObject_2Lcom_google_gwt_core_client_JavaScriptObject_2V(45, 1, {67:1, 3:1}, com_google_gwt_safehtml_shared_SafeHtmlString_SafeHtmlString__Ljava_lang_String_2V);
_.asString__Ljava_lang_String_2 = function com_google_gwt_safehtml_shared_SafeHtmlString_asString__Ljava_lang_String_2(){
  return this.com_google_gwt_safehtml_shared_SafeHtmlString_html;
}
;
_.equals__Ljava_lang_Object_2Z = function com_google_gwt_safehtml_shared_SafeHtmlString_equals__Ljava_lang_Object_2Z(obj){
  if (!com_google_gwt_lang_Cast_instanceOf__Ljava_lang_Object_2Lcom_google_gwt_core_client_JavaScriptObject_2Z(obj, 67)) {
    return false;
  }
  return java_lang_String_$equals__Ljava_lang_String_2Ljava_lang_Object_2Z(this.com_google_gwt_safehtml_shared_SafeHtmlString_html, com_google_gwt_lang_Cast_castTo__Ljava_lang_Object_2Lcom_google_gwt_core_client_JavaScriptObject_2Ljava_lang_Object_2(obj, 67).asString__Ljava_lang_String_2());
}
;
_.hashCode__I = function com_google_gwt_safehtml_shared_SafeHtmlString_hashCode__I(){
  return javaemul_internal_StringHashCache_getHashCode__Ljava_lang_String_2I(this.com_google_gwt_safehtml_shared_SafeHtmlString_html);
}
;
_.toString__Ljava_lang_String_2 = function com_google_gwt_safehtml_shared_SafeHtmlString_toString__Ljava_lang_String_2(){
  return 'safe: "' + this.com_google_gwt_safehtml_shared_SafeHtmlString_html + '"';
}
;
var com_google_gwt_lang_ClassLiteralHolder_Lcom_1google_1gwt_1safehtml_1shared_1SafeHtmlString_12_1classLit = java_lang_Class_createForClass__Ljava_lang_String_2Ljava_lang_String_2Lcom_google_gwt_core_client_JavaScriptObject_2Ljava_lang_Class_2Ljava_lang_Class_2($intern_40, 'SafeHtmlString', 45);
function com_google_gwt_safehtml_shared_SafeHtmlUtils_$clinit__V(){
  com_google_gwt_safehtml_shared_SafeHtmlUtils_$clinit__V = com_google_gwt_lang_Runtime_emptyMethod__V;
  new com_google_gwt_safehtml_shared_SafeHtmlString_SafeHtmlString__Ljava_lang_String_2V('');
  com_google_gwt_safehtml_shared_SafeHtmlUtils_HTML_1CHARS_1RE = new RegExp('[&<>\'"]');
  com_google_gwt_safehtml_shared_SafeHtmlUtils_AMP_1RE = new RegExp('&', 'g');
  com_google_gwt_safehtml_shared_SafeHtmlUtils_GT_1RE = new RegExp('>', 'g');
  com_google_gwt_safehtml_shared_SafeHtmlUtils_LT_1RE = new RegExp('<', 'g');
  com_google_gwt_safehtml_shared_SafeHtmlUtils_SQUOT_1RE = new RegExp("'", 'g');
  com_google_gwt_safehtml_shared_SafeHtmlUtils_QUOT_1RE = new RegExp('"', 'g');
}

function com_google_gwt_safehtml_shared_SafeHtmlUtils_htmlEscape__Ljava_lang_String_2Ljava_lang_String_2(s){
  com_google_gwt_safehtml_shared_SafeHtmlUtils_$clinit__V();
  if (!com_google_gwt_regexp_shared_RegExp_$test__Lcom_google_gwt_regexp_shared_RegExp_2Ljava_lang_String_2Z(com_google_gwt_safehtml_shared_SafeHtmlUtils_HTML_1CHARS_1RE, s)) {
    return s;
  }
  s.indexOf('&') != -1 && (s = com_google_gwt_regexp_shared_RegExp_$replace__Lcom_google_gwt_regexp_shared_RegExp_2Ljava_lang_String_2Ljava_lang_String_2Ljava_lang_String_2(com_google_gwt_safehtml_shared_SafeHtmlUtils_AMP_1RE, s, '&amp;'));
  s.indexOf('<') != -1 && (s = com_google_gwt_regexp_shared_RegExp_$replace__Lcom_google_gwt_regexp_shared_RegExp_2Ljava_lang_String_2Ljava_lang_String_2Ljava_lang_String_2(com_google_gwt_safehtml_shared_SafeHtmlUtils_LT_1RE, s, '&lt;'));
  s.indexOf('>') != -1 && (s = com_google_gwt_regexp_shared_RegExp_$replace__Lcom_google_gwt_regexp_shared_RegExp_2Ljava_lang_String_2Ljava_lang_String_2Ljava_lang_String_2(com_google_gwt_safehtml_shared_SafeHtmlUtils_GT_1RE, s, '&gt;'));
  s.indexOf('"') != -1 && (s = com_google_gwt_regexp_shared_RegExp_$replace__Lcom_google_gwt_regexp_shared_RegExp_2Ljava_lang_String_2Ljava_lang_String_2Ljava_lang_String_2(com_google_gwt_safehtml_shared_SafeHtmlUtils_QUOT_1RE, s, '&quot;'));
  s.indexOf("'") != -1 && (s = com_google_gwt_regexp_shared_RegExp_$replace__Lcom_google_gwt_regexp_shared_RegExp_2Ljava_lang_String_2Ljava_lang_String_2Ljava_lang_String_2(com_google_gwt_safehtml_shared_SafeHtmlUtils_SQUOT_1RE, s, '&#39;'));
  return s;
}

var com_google_gwt_safehtml_shared_SafeHtmlUtils_AMP_1RE, com_google_gwt_safehtml_shared_SafeHtmlUtils_GT_1RE, com_google_gwt_safehtml_shared_SafeHtmlUtils_HTML_1CHARS_1RE, com_google_gwt_safehtml_shared_SafeHtmlUtils_LT_1RE, com_google_gwt_safehtml_shared_SafeHtmlUtils_QUOT_1RE, com_google_gwt_safehtml_shared_SafeHtmlUtils_SQUOT_1RE;
function com_google_gwt_uibinder_client_LazyDomElement_$get__Lcom_google_gwt_uibinder_client_LazyDomElement_2Lcom_google_gwt_dom_client_Element_2(this$static){
  if (!this$static.com_google_gwt_uibinder_client_LazyDomElement_element) {
    this$static.com_google_gwt_uibinder_client_LazyDomElement_element = com_google_gwt_dom_client_Document_$getElementById__Lcom_google_gwt_dom_client_Document_2Ljava_lang_String_2Lcom_google_gwt_dom_client_Element_2($doc, this$static.com_google_gwt_uibinder_client_LazyDomElement_domId);
    if (!this$static.com_google_gwt_uibinder_client_LazyDomElement_element) {
      throw com_google_gwt_lang_Exceptions_toJs__Ljava_lang_Object_2Ljava_lang_Object_2(new java_lang_RuntimeException_RuntimeException__Ljava_lang_String_2V('Cannot find element with id "' + this$static.com_google_gwt_uibinder_client_LazyDomElement_domId + '". Perhaps it is not attached to the document body.'));
    }
    this$static.com_google_gwt_uibinder_client_LazyDomElement_element.removeAttribute('id');
  }
  return this$static.com_google_gwt_uibinder_client_LazyDomElement_element;
}

function com_google_gwt_uibinder_client_LazyDomElement_LazyDomElement__Ljava_lang_String_2V(domId){
  this.com_google_gwt_uibinder_client_LazyDomElement_domId = domId;
}

com_google_gwt_lang_Runtime_defineClass__Lcom_google_gwt_core_client_JavaScriptObject_2Lcom_google_gwt_core_client_JavaScriptObject_2Lcom_google_gwt_core_client_JavaScriptObject_2V(56, 1, {}, com_google_gwt_uibinder_client_LazyDomElement_LazyDomElement__Ljava_lang_String_2V);
var com_google_gwt_lang_ClassLiteralHolder_Lcom_1google_1gwt_1uibinder_1client_1LazyDomElement_12_1classLit = java_lang_Class_createForClass__Ljava_lang_String_2Ljava_lang_String_2Lcom_google_gwt_core_client_JavaScriptObject_2Ljava_lang_Class_2Ljava_lang_Class_2($intern_41, 'LazyDomElement', 56);
function com_google_gwt_uibinder_client_UiBinderUtil_attachToDom__Lcom_google_gwt_dom_client_Element_2Lcom_google_gwt_uibinder_client_UiBinderUtil$TempAttachment_2(element){
  var origParent, origSibling;
  com_google_gwt_uibinder_client_UiBinderUtil_ensureHiddenDiv__V();
  origParent = com_google_gwt_dom_client_DOMImpl_$getParentElement__Lcom_google_gwt_dom_client_DOMImpl_2Lcom_google_gwt_dom_client_Node_2Lcom_google_gwt_dom_client_Element_2(element);
  origSibling = com_google_gwt_dom_client_DOMImpl_$getNextSiblingElement__Lcom_google_gwt_dom_client_DOMImpl_2Lcom_google_gwt_dom_client_Element_2Lcom_google_gwt_dom_client_Element_2(element);
  com_google_gwt_dom_client_Node_$appendChild__Lcom_google_gwt_dom_client_Node_2Lcom_google_gwt_dom_client_Node_2Lcom_google_gwt_dom_client_Node_2(com_google_gwt_uibinder_client_UiBinderUtil_hiddenDiv, element);
  return new com_google_gwt_uibinder_client_UiBinderUtil$TempAttachment_UiBinderUtil$TempAttachment__Lcom_google_gwt_dom_client_Element_2Lcom_google_gwt_dom_client_Element_2Lcom_google_gwt_dom_client_Element_2V(origParent, origSibling, element);
}

function com_google_gwt_uibinder_client_UiBinderUtil_ensureHiddenDiv__V(){
  if (!com_google_gwt_uibinder_client_UiBinderUtil_hiddenDiv) {
    com_google_gwt_uibinder_client_UiBinderUtil_hiddenDiv = com_google_gwt_dom_client_DOMImplTrident_$createElement__Lcom_google_gwt_dom_client_DOMImplTrident_2Lcom_google_gwt_dom_client_Document_2Ljava_lang_String_2Lcom_google_gwt_dom_client_Element_2($doc, $intern_30);
    com_google_gwt_user_client_ui_UIObject_setVisible__Lcom_google_gwt_dom_client_Element_2ZV(com_google_gwt_uibinder_client_UiBinderUtil_hiddenDiv, false);
    com_google_gwt_dom_client_Node_$appendChild__Lcom_google_gwt_dom_client_Node_2Lcom_google_gwt_dom_client_Node_2Lcom_google_gwt_dom_client_Node_2((com_google_gwt_user_client_ui_RootPanel_$clinit__V() , $doc.body), com_google_gwt_uibinder_client_UiBinderUtil_hiddenDiv);
  }
}

function com_google_gwt_uibinder_client_UiBinderUtil_orphan__Lcom_google_gwt_dom_client_Node_2V(node){
  com_google_gwt_dom_client_Node_$removeChild__Lcom_google_gwt_dom_client_Node_2Lcom_google_gwt_dom_client_Node_2Lcom_google_gwt_dom_client_Node_2(node.parentNode, node);
}

var com_google_gwt_uibinder_client_UiBinderUtil_hiddenDiv;
function com_google_gwt_uibinder_client_UiBinderUtil$TempAttachment_UiBinderUtil$TempAttachment__Lcom_google_gwt_dom_client_Element_2Lcom_google_gwt_dom_client_Element_2Lcom_google_gwt_dom_client_Element_2V(origParent, origSibling, element){
  this.com_google_gwt_uibinder_client_UiBinderUtil$TempAttachment_origParent = origParent;
  this.com_google_gwt_uibinder_client_UiBinderUtil$TempAttachment_origSibling = origSibling;
  this.com_google_gwt_uibinder_client_UiBinderUtil$TempAttachment_element = element;
}

com_google_gwt_lang_Runtime_defineClass__Lcom_google_gwt_core_client_JavaScriptObject_2Lcom_google_gwt_core_client_JavaScriptObject_2Lcom_google_gwt_core_client_JavaScriptObject_2V(175, 1, {}, com_google_gwt_uibinder_client_UiBinderUtil$TempAttachment_UiBinderUtil$TempAttachment__Lcom_google_gwt_dom_client_Element_2Lcom_google_gwt_dom_client_Element_2Lcom_google_gwt_dom_client_Element_2V);
var com_google_gwt_lang_ClassLiteralHolder_Lcom_1google_1gwt_1uibinder_1client_1UiBinderUtil$TempAttachment_12_1classLit = java_lang_Class_createForClass__Ljava_lang_String_2Ljava_lang_String_2Lcom_google_gwt_core_client_JavaScriptObject_2Ljava_lang_Class_2Ljava_lang_Class_2($intern_41, 'UiBinderUtil/TempAttachment', 175);
function com_google_gwt_user_client_DOM_dispatchEvent__Lcom_google_gwt_user_client_Event_2Lcom_google_gwt_dom_client_Element_2Lcom_google_gwt_user_client_EventListener_2V(evt, elem, listener){
  var prevCurrentEvent;
  prevCurrentEvent = com_google_gwt_user_client_DOM_currentEvent;
  com_google_gwt_user_client_DOM_currentEvent = evt;
  elem == com_google_gwt_user_client_DOM_sCaptureElem && com_google_gwt_user_client_impl_DOMImpl_$eventGetTypeInt__Lcom_google_gwt_user_client_impl_DOMImpl_2Ljava_lang_String_2I(evt.type) == 8192 && (com_google_gwt_user_client_DOM_sCaptureElem = null);
  listener.onBrowserEvent__Lcom_google_gwt_user_client_Event_2V(evt);
  com_google_gwt_user_client_DOM_currentEvent = prevCurrentEvent;
}

function com_google_gwt_user_client_DOM_insertChild__Lcom_google_gwt_dom_client_Element_2Lcom_google_gwt_dom_client_Element_2IV(parent_0, child, index_0){
  com_google_gwt_user_client_impl_DOMImplTrident_$insertChild__Lcom_google_gwt_user_client_impl_DOMImplTrident_2Lcom_google_gwt_dom_client_Element_2Lcom_google_gwt_dom_client_Element_2IV(parent_0, com_google_gwt_user_client_DOM_resolve__Lcom_google_gwt_dom_client_Element_2Lcom_google_gwt_dom_client_Element_2(child), index_0);
}

function com_google_gwt_user_client_DOM_isPotential__Lcom_google_gwt_core_client_JavaScriptObject_2Z(o){
  try {
    return !!o && !!o.__gwt_resolve;
  }
   catch (e) {
    return false;
  }
}

function com_google_gwt_user_client_DOM_previewEvent__Lcom_google_gwt_user_client_Event_2Z(evt){
  return true;
}

function com_google_gwt_user_client_DOM_resolve__Lcom_google_gwt_dom_client_Element_2Lcom_google_gwt_dom_client_Element_2(maybePotential){
  return maybePotential.__gwt_resolve?maybePotential.__gwt_resolve():maybePotential;
}

function com_google_gwt_user_client_DOM_sinkEvents__Lcom_google_gwt_dom_client_Element_2IV(elem, eventBits){
  com_google_gwt_user_client_impl_DOMImpl_$maybeInitializeEventSystem__Lcom_google_gwt_user_client_impl_DOMImpl_2V();
  com_google_gwt_user_client_impl_DOMImplTrident_$sinkEventsImpl__Lcom_google_gwt_user_client_impl_DOMImplTrident_2Lcom_google_gwt_dom_client_Element_2IV(elem, eventBits);
}

var com_google_gwt_user_client_DOM_currentEvent = null, com_google_gwt_user_client_DOM_sCaptureElem;
function com_google_gwt_user_client_DocumentModeAsserter_$onModuleLoad__Lcom_google_gwt_user_client_DocumentModeAsserter_2V(){
  var allowedModes, currentMode, i;
  currentMode = $doc.compatMode;
  allowedModes = com_google_gwt_lang_Array_stampJavaTypeInfo__Ljava_lang_Class_2Lcom_google_gwt_core_client_JavaScriptObject_2Lcom_google_gwt_core_client_JavaScriptObject_2ILjava_lang_Object_2Ljava_lang_Object_2(com_google_gwt_lang_Array_getClassLiteralForArray__Ljava_lang_Class_2ILjava_lang_Class_2(com_google_gwt_lang_ClassLiteralHolder_Ljava_1lang_1String_12_1classLit, 1), {3:1}, 2, 6, [$intern_42]);
  for (i = 0; i < allowedModes.length; i++) {
    if (java_lang_String_$equals__Ljava_lang_String_2Ljava_lang_Object_2Z(allowedModes[i], currentMode)) {
      return;
    }
  }
  allowedModes.length == 1 && java_lang_String_$equals__Ljava_lang_String_2Ljava_lang_Object_2Z($intern_42, allowedModes[0]) && java_lang_String_$equals__Ljava_lang_String_2Ljava_lang_Object_2Z('BackCompat', currentMode)?"GWT no longer supports Quirks Mode (document.compatMode=' BackCompat').<br>Make sure your application's host HTML page has a Standards Mode (document.compatMode=' CSS1Compat') doctype,<br>e.g. by using &lt;!doctype html&gt; at the start of your application's HTML page.<br><br>To continue using this unsupported rendering mode and risk layout problems, suppress this message by adding<br>the following line to your*.gwt.xml module file:<br>&nbsp;&nbsp;&lt;extend-configuration-property name=\"document.compatMode\" value=\"" + currentMode + '"/&gt;':"Your *.gwt.xml module configuration prohibits the use of the current document rendering mode (document.compatMode=' " + currentMode + "').<br>Modify your application's host HTML page doctype, or update your custom " + "'document.compatMode' configuration property settings.";
}

function com_google_gwt_user_client_Window_addCloseHandler__Lcom_google_gwt_event_logical_shared_CloseHandler_2Lcom_google_gwt_event_shared_HandlerRegistration_2(handler){
  com_google_gwt_user_client_Window_maybeInitializeCloseHandlers__V();
  return com_google_gwt_user_client_Window_addHandler__Lcom_google_gwt_event_shared_GwtEvent$Type_2Lcom_google_gwt_event_shared_EventHandler_2Lcom_google_gwt_event_shared_HandlerRegistration_2(com_google_gwt_event_logical_shared_CloseEvent_TYPE?com_google_gwt_event_logical_shared_CloseEvent_TYPE:(com_google_gwt_event_logical_shared_CloseEvent_TYPE = new com_google_gwt_event_shared_GwtEvent$Type_GwtEvent$Type__V), handler);
}

function com_google_gwt_user_client_Window_addHandler__Lcom_google_gwt_event_shared_GwtEvent$Type_2Lcom_google_gwt_event_shared_EventHandler_2Lcom_google_gwt_event_shared_HandlerRegistration_2(type_0, handler){
  return com_google_gwt_event_shared_HandlerManager_$addHandler__Lcom_google_gwt_event_shared_HandlerManager_2Lcom_google_gwt_event_shared_GwtEvent$Type_2Lcom_google_gwt_event_shared_EventHandler_2Lcom_google_gwt_event_shared_HandlerRegistration_2((!com_google_gwt_user_client_Window_handlers && (com_google_gwt_user_client_Window_handlers = new com_google_gwt_user_client_Window$WindowHandlers_Window$WindowHandlers__V) , com_google_gwt_user_client_Window_handlers), type_0, handler);
}

function com_google_gwt_user_client_Window_maybeInitializeCloseHandlers__V(){
  var com_google_gwt_user_client_impl_WindowImplIE_$initHandler__Lcom_google_gwt_user_client_impl_WindowImplIE_2Ljava_lang_String_2Lcom_google_gwt_core_client_Scheduler$ScheduledCommand_2V_scriptElem_0;
  if (!com_google_gwt_user_client_Window_closeHandlersInitialized) {
    com_google_gwt_user_client_impl_WindowImplIE_$initHandler__Lcom_google_gwt_user_client_impl_WindowImplIE_2Ljava_lang_String_2Lcom_google_gwt_core_client_Scheduler$ScheduledCommand_2V_scriptElem_0 = com_google_gwt_dom_client_DOMImpl_$createScriptElement__Lcom_google_gwt_dom_client_DOMImpl_2Lcom_google_gwt_dom_client_Document_2Ljava_lang_String_2Lcom_google_gwt_dom_client_ScriptElement_2($doc, 'function __gwt_initWindowCloseHandler(beforeunload, unload) {\n  var wnd = window\n  , oldOnBeforeUnload = wnd.onbeforeunload\n  , oldOnUnload = wnd.onunload;\n  \n  wnd.onbeforeunload = function(evt) {\n    var ret, oldRet;\n    try {\n      ret = beforeunload();\n    } finally {\n      oldRet = oldOnBeforeUnload && oldOnBeforeUnload(evt);\n    }\n    // Avoid returning null as IE6 will coerce it into a string.\n    // Ensure that "" gets returned properly.\n    if (ret != null) {\n      return ret;\n    }\n    if (oldRet != null) {\n      return oldRet;\n    }\n    // returns undefined.\n  };\n  \n  wnd.onunload = function(evt) {\n    try {\n      unload();\n    } finally {\n      oldOnUnload && oldOnUnload(evt);\n      wnd.onresize = null;\n      wnd.onscroll = null;\n      wnd.onbeforeunload = null;\n      wnd.onunload = null;\n    }\n  };\n  \n  // Remove the reference once we\'ve initialize the handler\n  wnd.__gwt_initWindowCloseHandler = undefined;\n}\n');
    com_google_gwt_dom_client_Node_$appendChild__Lcom_google_gwt_dom_client_Node_2Lcom_google_gwt_dom_client_Node_2Lcom_google_gwt_dom_client_Node_2($doc.body, com_google_gwt_user_client_impl_WindowImplIE_$initHandler__Lcom_google_gwt_user_client_impl_WindowImplIE_2Ljava_lang_String_2Lcom_google_gwt_core_client_Scheduler$ScheduledCommand_2V_scriptElem_0);
    $wnd.__gwt_initWindowCloseHandler($entry(com_google_gwt_user_client_Window_onClosing__Ljava_lang_String_2), $entry(com_google_gwt_user_client_Window_onClosed__V));
    com_google_gwt_dom_client_Node_$removeChild__Lcom_google_gwt_dom_client_Node_2Lcom_google_gwt_dom_client_Node_2Lcom_google_gwt_dom_client_Node_2($doc.body, com_google_gwt_user_client_impl_WindowImplIE_$initHandler__Lcom_google_gwt_user_client_impl_WindowImplIE_2Ljava_lang_String_2Lcom_google_gwt_core_client_Scheduler$ScheduledCommand_2V_scriptElem_0);
    com_google_gwt_user_client_Window_closeHandlersInitialized = true;
  }
}

function com_google_gwt_user_client_Window_onClosed__V(){
  com_google_gwt_user_client_Window_closeHandlersInitialized && com_google_gwt_event_logical_shared_CloseEvent_fire__Lcom_google_gwt_event_logical_shared_HasCloseHandlers_2Ljava_lang_Object_2ZV((!com_google_gwt_user_client_Window_handlers && (com_google_gwt_user_client_Window_handlers = new com_google_gwt_user_client_Window$WindowHandlers_Window$WindowHandlers__V) , com_google_gwt_user_client_Window_handlers));
}

function com_google_gwt_user_client_Window_onClosing__Ljava_lang_String_2(){
  var event_0;
  if (com_google_gwt_user_client_Window_closeHandlersInitialized) {
    event_0 = new com_google_gwt_user_client_Window$ClosingEvent_Window$ClosingEvent__V;
    !!com_google_gwt_user_client_Window_handlers && com_google_gwt_event_shared_HandlerManager_$fireEvent__Lcom_google_gwt_event_shared_HandlerManager_2Lcom_google_gwt_event_shared_GwtEvent_2V(com_google_gwt_user_client_Window_handlers, event_0);
    return null;
  }
  return null;
}

var com_google_gwt_user_client_Window_closeHandlersInitialized = false, com_google_gwt_user_client_Window_handlers;
function com_google_gwt_user_client_Window$ClosingEvent_$clinit__V(){
  com_google_gwt_user_client_Window$ClosingEvent_$clinit__V = com_google_gwt_lang_Runtime_emptyMethod__V;
  com_google_gwt_user_client_Window$ClosingEvent_TYPE = new com_google_gwt_event_shared_GwtEvent$Type_GwtEvent$Type__V;
}

function com_google_gwt_user_client_Window$ClosingEvent_Window$ClosingEvent__V(){
  com_google_gwt_user_client_Window$ClosingEvent_$clinit__V();
}

com_google_gwt_lang_Runtime_defineClass__Lcom_google_gwt_core_client_JavaScriptObject_2Lcom_google_gwt_core_client_JavaScriptObject_2Lcom_google_gwt_core_client_JavaScriptObject_2V(145, 229, {}, com_google_gwt_user_client_Window$ClosingEvent_Window$ClosingEvent__V);
_.dispatch__Lcom_google_gwt_event_shared_EventHandler_2V = function com_google_gwt_user_client_Window$ClosingEvent_dispatch__Lcom_google_gwt_event_shared_EventHandler_2V(handler){
  com_google_gwt_lang_Cast_throwClassCastExceptionUnlessNull__Ljava_lang_Object_2Ljava_lang_Object_2(handler);
  null.$_nullMethod();
}
;
_.getAssociatedType__Lcom_google_gwt_event_shared_GwtEvent$Type_2 = function com_google_gwt_user_client_Window$ClosingEvent_getAssociatedType__Lcom_google_gwt_event_shared_GwtEvent$Type_2(){
  return com_google_gwt_user_client_Window$ClosingEvent_TYPE;
}
;
var com_google_gwt_user_client_Window$ClosingEvent_TYPE;
var com_google_gwt_lang_ClassLiteralHolder_Lcom_1google_1gwt_1user_1client_1Window$ClosingEvent_12_1classLit = java_lang_Class_createForClass__Ljava_lang_String_2Ljava_lang_String_2Lcom_google_gwt_core_client_JavaScriptObject_2Ljava_lang_Class_2Ljava_lang_Class_2($intern_43, 'Window/ClosingEvent', 145);
function com_google_gwt_user_client_Window$WindowHandlers_Window$WindowHandlers__V(){
  com_google_gwt_event_shared_HandlerManager_HandlerManager__Ljava_lang_Object_2V.call(this, null);
}

com_google_gwt_lang_Runtime_defineClass__Lcom_google_gwt_core_client_JavaScriptObject_2Lcom_google_gwt_core_client_JavaScriptObject_2Lcom_google_gwt_core_client_JavaScriptObject_2V(93, 55, {}, com_google_gwt_user_client_Window$WindowHandlers_Window$WindowHandlers__V);
var com_google_gwt_lang_ClassLiteralHolder_Lcom_1google_1gwt_1user_1client_1Window$WindowHandlers_12_1classLit = java_lang_Class_createForClass__Ljava_lang_String_2Ljava_lang_String_2Lcom_google_gwt_core_client_JavaScriptObject_2Ljava_lang_Class_2Ljava_lang_Class_2($intern_43, 'Window/WindowHandlers', 93);
function com_google_gwt_user_client_impl_DOMImpl_$eventGetTypeInt__Lcom_google_gwt_user_client_impl_DOMImpl_2Ljava_lang_String_2I(eventType){
  switch (eventType) {
    case 'blur':
      return 4096;
    case 'change':
      return 1024;
    case $intern_36:
      return 1;
    case 'dblclick':
      return 2;
    case 'focus':
      return 2048;
    case 'keydown':
      return 128;
    case 'keypress':
      return 256;
    case 'keyup':
      return 512;
    case 'load':
      return 32768;
    case 'losecapture':
      return 8192;
    case 'mousedown':
      return 4;
    case 'mousemove':
      return 64;
    case 'mouseout':
      return 32;
    case 'mouseover':
      return 16;
    case 'mouseup':
      return 8;
    case 'scroll':
      return 16384;
    case 'error':
      return 65536;
    case 'DOMMouseScroll':
    case 'mousewheel':
      return 131072;
    case 'contextmenu':
      return 262144;
    case 'paste':
      return 524288;
    case 'touchstart':
      return 1048576;
    case 'touchmove':
      return 2097152;
    case 'touchend':
      return 4194304;
    case 'touchcancel':
      return 8388608;
    case 'gesturestart':
      return 16777216;
    case 'gesturechange':
      return 33554432;
    case 'gestureend':
      return 67108864;
    default:return -1;
  }
}

function com_google_gwt_user_client_impl_DOMImpl_$maybeInitializeEventSystem__Lcom_google_gwt_user_client_impl_DOMImpl_2V(){
  if (!com_google_gwt_user_client_impl_DOMImpl_eventSystemIsInitialized) {
    com_google_gwt_user_client_impl_DOMImplTrident_$initEventSystem__Lcom_google_gwt_user_client_impl_DOMImplTrident_2V();
    com_google_gwt_user_client_impl_DOMImpl_eventSystemIsInitialized = true;
  }
}

function com_google_gwt_user_client_impl_DOMImpl_getEventListener__Lcom_google_gwt_dom_client_Element_2Lcom_google_gwt_user_client_EventListener_2(elem){
  var maybeListener = elem.__listener;
  return !com_google_gwt_lang_Cast_instanceOfJso__Ljava_lang_Object_2Z(maybeListener) && com_google_gwt_lang_Cast_instanceOf__Ljava_lang_Object_2Lcom_google_gwt_core_client_JavaScriptObject_2Z(maybeListener, 5)?maybeListener:null;
}

function com_google_gwt_user_client_impl_DOMImpl_setEventListener__Lcom_google_gwt_dom_client_Element_2Lcom_google_gwt_user_client_EventListener_2V(elem, listener){
  elem.__listener = listener;
}

var com_google_gwt_user_client_impl_DOMImpl_eventSystemIsInitialized = false;
function com_google_gwt_user_client_impl_DOMImplTrident_$initEventSystem__Lcom_google_gwt_user_client_impl_DOMImplTrident_2V(){
  $wnd.__gwt_globalEventArray == null && ($wnd.__gwt_globalEventArray = new Array);
  $wnd.__gwt_globalEventArray[$wnd.__gwt_globalEventArray.length] = $entry(function(){
    return com_google_gwt_user_client_DOM_previewEvent__Lcom_google_gwt_user_client_Event_2Z($wnd.event);
  }
  );
  var dispatchEvent = $entry(function(){
    var oldEventTarget = com_google_gwt_dom_client_DOMImplTrident_currentEventTarget;
    com_google_gwt_dom_client_DOMImplTrident_currentEventTarget = this;
    if ($wnd.event.returnValue == null) {
      $wnd.event.returnValue = true;
      if (!com_google_gwt_user_client_impl_DOMImplTrident_previewEventImpl__Z()) {
        com_google_gwt_dom_client_DOMImplTrident_currentEventTarget = oldEventTarget;
        return;
      }
    }
    var getEventListener = com_google_gwt_user_client_impl_DOMImpl_getEventListener__Lcom_google_gwt_dom_client_Element_2Lcom_google_gwt_user_client_EventListener_2;
    var listener, curElem = this;
    while (curElem && !(listener = getEventListener(curElem))) {
      curElem = curElem.parentElement;
    }
    listener && com_google_gwt_user_client_DOM_dispatchEvent__Lcom_google_gwt_user_client_Event_2Lcom_google_gwt_dom_client_Element_2Lcom_google_gwt_user_client_EventListener_2V($wnd.event, curElem, listener);
    com_google_gwt_dom_client_DOMImplTrident_currentEventTarget = oldEventTarget;
  }
  );
  var dispatchDblClickEvent = $entry(function(){
    var newEvent = $doc.createEventObject();
    $wnd.event.returnValue == null && $wnd.event.srcElement.fireEvent && $wnd.event.srcElement.fireEvent('onclick', newEvent);
    if (this.__eventBits & 2) {
      dispatchEvent.call(this);
    }
     else if ($wnd.event.returnValue == null) {
      $wnd.event.returnValue = true;
      com_google_gwt_user_client_impl_DOMImplTrident_previewEventImpl__Z();
    }
  }
  );
  var dispatchUnhandledEvent = $entry(function(){
    this.__gwtLastUnhandledEvent = $wnd.event.type;
    dispatchEvent.call(this);
  }
  );
  var moduleName = (com_google_gwt_core_client_impl_Impl_$clinit__V() , $moduleName).replace(/\./g, '_');
  $wnd['__gwt_dispatchEvent_' + moduleName] = dispatchEvent;
  com_google_gwt_user_client_impl_DOMImplTrident_callDispatchEvent = (new Function('w', 'return function() { w.__gwt_dispatchEvent_' + moduleName + '.call(this) }'))($wnd);
  $wnd['__gwt_dispatchDblClickEvent_' + moduleName] = dispatchDblClickEvent;
  com_google_gwt_user_client_impl_DOMImplTrident_callDispatchDblClickEvent = (new Function('w', 'return function() { w.__gwt_dispatchDblClickEvent_' + moduleName + $intern_44))($wnd);
  $wnd['__gwt_dispatchUnhandledEvent_' + moduleName] = dispatchUnhandledEvent;
  com_google_gwt_user_client_impl_DOMImplTrident_callDispatchUnhandledEvent = (new Function('w', $intern_45 + moduleName + $intern_44))($wnd);
  com_google_gwt_user_client_impl_DOMImplTrident_callDispatchOnLoadEvent = (new Function('w', $intern_45 + moduleName + '.call(w.event.srcElement)}'))($wnd);
  var bodyDispatcher = $entry(function(){
    dispatchEvent.call($doc.body);
  }
  );
  var bodyDblClickDispatcher = $entry(function(){
    dispatchDblClickEvent.call($doc.body);
  }
  );
  $doc.body.attachEvent('onclick', bodyDispatcher);
  $doc.body.attachEvent('onmousedown', bodyDispatcher);
  $doc.body.attachEvent('onmouseup', bodyDispatcher);
  $doc.body.attachEvent('onmousemove', bodyDispatcher);
  $doc.body.attachEvent('onmousewheel', bodyDispatcher);
  $doc.body.attachEvent('onkeydown', bodyDispatcher);
  $doc.body.attachEvent('onkeypress', bodyDispatcher);
  $doc.body.attachEvent('onkeyup', bodyDispatcher);
  $doc.body.attachEvent('onfocus', bodyDispatcher);
  $doc.body.attachEvent('onblur', bodyDispatcher);
  $doc.body.attachEvent('ondblclick', bodyDblClickDispatcher);
  $doc.body.attachEvent('oncontextmenu', bodyDispatcher);
}

function com_google_gwt_user_client_impl_DOMImplTrident_$insertChild__Lcom_google_gwt_user_client_impl_DOMImplTrident_2Lcom_google_gwt_dom_client_Element_2Lcom_google_gwt_dom_client_Element_2IV(parent_0, child, index_0){
  index_0 >= parent_0.children.length?parent_0.appendChild(child):parent_0.insertBefore(child, parent_0.children[index_0]);
}

function com_google_gwt_user_client_impl_DOMImplTrident_$sinkEventsImpl__Lcom_google_gwt_user_client_impl_DOMImplTrident_2Lcom_google_gwt_dom_client_Element_2IV(elem, bits){
  var chMask = (elem.__eventBits || 0) ^ bits;
  elem.__eventBits = bits;
  if (!chMask)
    return;
  chMask & 1 && (elem.onclick = bits & 1?com_google_gwt_user_client_impl_DOMImplTrident_callDispatchEvent:null);
  chMask & 3 && (elem.ondblclick = bits & 3?com_google_gwt_user_client_impl_DOMImplTrident_callDispatchDblClickEvent:null);
  chMask & 4 && (elem.onmousedown = bits & 4?com_google_gwt_user_client_impl_DOMImplTrident_callDispatchEvent:null);
  chMask & 8 && (elem.onmouseup = bits & 8?com_google_gwt_user_client_impl_DOMImplTrident_callDispatchEvent:null);
  chMask & 16 && (elem.onmouseover = bits & 16?com_google_gwt_user_client_impl_DOMImplTrident_callDispatchEvent:null);
  chMask & 32 && (elem.onmouseout = bits & 32?com_google_gwt_user_client_impl_DOMImplTrident_callDispatchEvent:null);
  chMask & 64 && (elem.onmousemove = bits & 64?com_google_gwt_user_client_impl_DOMImplTrident_callDispatchEvent:null);
  chMask & 128 && (elem.onkeydown = bits & 128?com_google_gwt_user_client_impl_DOMImplTrident_callDispatchEvent:null);
  chMask & 256 && (elem.onkeypress = bits & 256?com_google_gwt_user_client_impl_DOMImplTrident_callDispatchEvent:null);
  chMask & 512 && (elem.onkeyup = bits & 512?com_google_gwt_user_client_impl_DOMImplTrident_callDispatchEvent:null);
  chMask & 1024 && (elem.onchange = bits & 1024?com_google_gwt_user_client_impl_DOMImplTrident_callDispatchEvent:null);
  chMask & 2048 && (elem.onfocus = bits & 2048?com_google_gwt_user_client_impl_DOMImplTrident_callDispatchEvent:null);
  chMask & 4096 && (elem.onblur = bits & 4096?com_google_gwt_user_client_impl_DOMImplTrident_callDispatchEvent:null);
  chMask & 8192 && (elem.onlosecapture = bits & 8192?com_google_gwt_user_client_impl_DOMImplTrident_callDispatchEvent:null);
  chMask & 16384 && (elem.onscroll = bits & 16384?com_google_gwt_user_client_impl_DOMImplTrident_callDispatchEvent:null);
  chMask & 32768 && (elem.nodeName == 'IFRAME'?bits & 32768?elem.attachEvent('onload', com_google_gwt_user_client_impl_DOMImplTrident_callDispatchOnLoadEvent):elem.detachEvent('onload', com_google_gwt_user_client_impl_DOMImplTrident_callDispatchOnLoadEvent):(elem.onload = bits & 32768?com_google_gwt_user_client_impl_DOMImplTrident_callDispatchUnhandledEvent:null));
  chMask & 65536 && (elem.onerror = bits & 65536?com_google_gwt_user_client_impl_DOMImplTrident_callDispatchEvent:null);
  chMask & 131072 && (elem.onmousewheel = bits & 131072?com_google_gwt_user_client_impl_DOMImplTrident_callDispatchEvent:null);
  chMask & 262144 && (elem.oncontextmenu = bits & 262144?com_google_gwt_user_client_impl_DOMImplTrident_callDispatchEvent:null);
  chMask & 524288 && (elem.onpaste = bits & 524288?com_google_gwt_user_client_impl_DOMImplTrident_callDispatchEvent:null);
}

function com_google_gwt_user_client_impl_DOMImplTrident_previewEventImpl__Z(){
  var isCancelled = false;
  for (var i = 0; i < $wnd.__gwt_globalEventArray.length; i++) {
    !$wnd.__gwt_globalEventArray[i]() && (isCancelled = true);
  }
  return !isCancelled;
}

var com_google_gwt_user_client_impl_DOMImplTrident_callDispatchDblClickEvent, com_google_gwt_user_client_impl_DOMImplTrident_callDispatchEvent, com_google_gwt_user_client_impl_DOMImplTrident_callDispatchOnLoadEvent, com_google_gwt_user_client_impl_DOMImplTrident_callDispatchUnhandledEvent;
com_google_gwt_lang_Runtime_defineClass__Lcom_google_gwt_core_client_JavaScriptObject_2Lcom_google_gwt_core_client_JavaScriptObject_2Lcom_google_gwt_core_client_JavaScriptObject_2V(223, 4, $intern_46);
_.doAttachChildren__V = function com_google_gwt_user_client_ui_Panel_doAttachChildren__V(){
  com_google_gwt_user_client_ui_AttachDetachException_tryCommand__Ljava_lang_Iterable_2Lcom_google_gwt_user_client_ui_AttachDetachException$Command_2V(this, (com_google_gwt_user_client_ui_AttachDetachException_$clinit__V() , com_google_gwt_user_client_ui_AttachDetachException_attachCommand));
}
;
_.doDetachChildren__V = function com_google_gwt_user_client_ui_Panel_doDetachChildren__V(){
  com_google_gwt_user_client_ui_AttachDetachException_tryCommand__Ljava_lang_Iterable_2Lcom_google_gwt_user_client_ui_AttachDetachException$Command_2V(this, (com_google_gwt_user_client_ui_AttachDetachException_$clinit__V() , com_google_gwt_user_client_ui_AttachDetachException_detachCommand));
}
;
var com_google_gwt_lang_ClassLiteralHolder_Lcom_1google_1gwt_1user_1client_1ui_1Panel_12_1classLit = java_lang_Class_createForClass__Ljava_lang_String_2Ljava_lang_String_2Lcom_google_gwt_core_client_JavaScriptObject_2Ljava_lang_Class_2Ljava_lang_Class_2($intern_8, 'Panel', 223);
function com_google_gwt_user_client_ui_ComplexPanel_$add__Lcom_google_gwt_user_client_ui_ComplexPanel_2Lcom_google_gwt_user_client_ui_Widget_2Lcom_google_gwt_user_client_Element_2V(this$static, child, container){
  com_google_gwt_user_client_ui_Widget_$removeFromParent__Lcom_google_gwt_user_client_ui_Widget_2V(child);
  com_google_gwt_user_client_ui_WidgetCollection_$add__Lcom_google_gwt_user_client_ui_WidgetCollection_2Lcom_google_gwt_user_client_ui_Widget_2V(this$static.com_google_gwt_user_client_ui_ComplexPanel_children, child);
  com_google_gwt_dom_client_Node_$appendChild__Lcom_google_gwt_dom_client_Node_2Lcom_google_gwt_dom_client_Node_2Lcom_google_gwt_dom_client_Node_2(container, com_google_gwt_user_client_DOM_resolve__Lcom_google_gwt_dom_client_Element_2Lcom_google_gwt_dom_client_Element_2(child.com_google_gwt_user_client_ui_UIObject_element));
  com_google_gwt_user_client_ui_Widget_$setParent__Lcom_google_gwt_user_client_ui_Widget_2Lcom_google_gwt_user_client_ui_Widget_2V(child, this$static);
}

function com_google_gwt_user_client_ui_ComplexPanel_$adjustIndex__Lcom_google_gwt_user_client_ui_ComplexPanel_2Lcom_google_gwt_user_client_ui_Widget_2II(this$static, child, beforeIndex){
  var idx;
  com_google_gwt_user_client_ui_ComplexPanel_$checkIndexBoundsForInsertion__Lcom_google_gwt_user_client_ui_ComplexPanel_2IV(this$static, beforeIndex);
  if (child.com_google_gwt_user_client_ui_Widget_parent == this$static) {
    idx = com_google_gwt_user_client_ui_WidgetCollection_$indexOf__Lcom_google_gwt_user_client_ui_WidgetCollection_2Lcom_google_gwt_user_client_ui_Widget_2I(this$static.com_google_gwt_user_client_ui_ComplexPanel_children, child);
    idx < beforeIndex && --beforeIndex;
  }
  return beforeIndex;
}

function com_google_gwt_user_client_ui_ComplexPanel_$checkIndexBoundsForInsertion__Lcom_google_gwt_user_client_ui_ComplexPanel_2IV(this$static, index_0){
  if (index_0 < 0 || index_0 > this$static.com_google_gwt_user_client_ui_ComplexPanel_children.com_google_gwt_user_client_ui_WidgetCollection_size) {
    throw com_google_gwt_lang_Exceptions_toJs__Ljava_lang_Object_2Ljava_lang_Object_2(new java_lang_IndexOutOfBoundsException_IndexOutOfBoundsException__V);
  }
}

function com_google_gwt_user_client_ui_ComplexPanel_$insert__Lcom_google_gwt_user_client_ui_ComplexPanel_2Lcom_google_gwt_user_client_ui_Widget_2Lcom_google_gwt_user_client_Element_2IZV(this$static, child, container, beforeIndex){
  beforeIndex = com_google_gwt_user_client_ui_ComplexPanel_$adjustIndex__Lcom_google_gwt_user_client_ui_ComplexPanel_2Lcom_google_gwt_user_client_ui_Widget_2II(this$static, child, beforeIndex);
  com_google_gwt_user_client_ui_Widget_$removeFromParent__Lcom_google_gwt_user_client_ui_Widget_2V(child);
  com_google_gwt_user_client_ui_WidgetCollection_$insert__Lcom_google_gwt_user_client_ui_WidgetCollection_2Lcom_google_gwt_user_client_ui_Widget_2IV(this$static.com_google_gwt_user_client_ui_ComplexPanel_children, child, beforeIndex);
  com_google_gwt_user_client_DOM_insertChild__Lcom_google_gwt_dom_client_Element_2Lcom_google_gwt_dom_client_Element_2IV(container, child.com_google_gwt_user_client_ui_UIObject_element, beforeIndex);
  com_google_gwt_user_client_ui_Widget_$setParent__Lcom_google_gwt_user_client_ui_Widget_2Lcom_google_gwt_user_client_ui_Widget_2V(child, this$static);
}

function com_google_gwt_user_client_ui_ComplexPanel_$remove__Lcom_google_gwt_user_client_ui_ComplexPanel_2Lcom_google_gwt_user_client_ui_Widget_2Z(this$static, w){
  var elem;
  if (w.com_google_gwt_user_client_ui_Widget_parent != this$static) {
    return false;
  }
  try {
    com_google_gwt_user_client_ui_Widget_$setParent__Lcom_google_gwt_user_client_ui_Widget_2Lcom_google_gwt_user_client_ui_Widget_2V(w, null);
  }
   finally {
    elem = w.com_google_gwt_user_client_ui_UIObject_element;
    com_google_gwt_dom_client_Node_$removeChild__Lcom_google_gwt_dom_client_Node_2Lcom_google_gwt_dom_client_Node_2Lcom_google_gwt_dom_client_Node_2(com_google_gwt_dom_client_DOMImpl_$getParentElement__Lcom_google_gwt_dom_client_DOMImpl_2Lcom_google_gwt_dom_client_Node_2Lcom_google_gwt_dom_client_Element_2(elem), elem);
    com_google_gwt_user_client_ui_WidgetCollection_$remove__Lcom_google_gwt_user_client_ui_WidgetCollection_2Lcom_google_gwt_user_client_ui_Widget_2V(this$static.com_google_gwt_user_client_ui_ComplexPanel_children, w);
  }
  return true;
}

function com_google_gwt_user_client_ui_ComplexPanel_ComplexPanel__V(){
  this.com_google_gwt_user_client_ui_ComplexPanel_children = new com_google_gwt_user_client_ui_WidgetCollection_WidgetCollection__Lcom_google_gwt_user_client_ui_HasWidgets_2V(this);
}

com_google_gwt_lang_Runtime_defineClass__Lcom_google_gwt_core_client_JavaScriptObject_2Lcom_google_gwt_core_client_JavaScriptObject_2Lcom_google_gwt_core_client_JavaScriptObject_2V(41, 223, $intern_46);
_.iterator__Ljava_util_Iterator_2 = function com_google_gwt_user_client_ui_ComplexPanel_iterator__Ljava_util_Iterator_2(){
  return new com_google_gwt_user_client_ui_WidgetCollection$WidgetIterator_WidgetCollection$WidgetIterator__Lcom_google_gwt_user_client_ui_WidgetCollection_2V(this.com_google_gwt_user_client_ui_ComplexPanel_children);
}
;
_.remove__Lcom_google_gwt_user_client_ui_Widget_2Z = function com_google_gwt_user_client_ui_ComplexPanel_remove__Lcom_google_gwt_user_client_ui_Widget_2Z(w){
  return com_google_gwt_user_client_ui_ComplexPanel_$remove__Lcom_google_gwt_user_client_ui_ComplexPanel_2Lcom_google_gwt_user_client_ui_Widget_2Z(this, w);
}
;
var com_google_gwt_lang_ClassLiteralHolder_Lcom_1google_1gwt_1user_1client_1ui_1ComplexPanel_12_1classLit = java_lang_Class_createForClass__Ljava_lang_String_2Ljava_lang_String_2Lcom_google_gwt_core_client_JavaScriptObject_2Ljava_lang_Class_2Ljava_lang_Class_2($intern_8, 'ComplexPanel', 41);
function com_google_gwt_user_client_ui_AbsolutePanel_$add__Lcom_google_gwt_user_client_ui_AbsolutePanel_2Lcom_google_gwt_user_client_ui_Widget_2V(this$static, w){
  com_google_gwt_user_client_ui_ComplexPanel_$add__Lcom_google_gwt_user_client_ui_ComplexPanel_2Lcom_google_gwt_user_client_ui_Widget_2Lcom_google_gwt_user_client_Element_2V(this$static, w, this$static.com_google_gwt_user_client_ui_UIObject_element);
}

function com_google_gwt_user_client_ui_AbsolutePanel_changeToStaticPositioning__Lcom_google_gwt_dom_client_Element_2V(elem){
  elem.style['left'] = '';
  elem.style['top'] = '';
  elem.style['position'] = '';
}

com_google_gwt_lang_Runtime_defineClass__Lcom_google_gwt_core_client_JavaScriptObject_2Lcom_google_gwt_core_client_JavaScriptObject_2Lcom_google_gwt_core_client_JavaScriptObject_2V(125, 41, $intern_46);
_.remove__Lcom_google_gwt_user_client_ui_Widget_2Z = function com_google_gwt_user_client_ui_AbsolutePanel_remove__Lcom_google_gwt_user_client_ui_Widget_2Z(w){
  var removed;
  removed = com_google_gwt_user_client_ui_ComplexPanel_$remove__Lcom_google_gwt_user_client_ui_ComplexPanel_2Lcom_google_gwt_user_client_ui_Widget_2Z(this, w);
  removed && com_google_gwt_user_client_ui_AbsolutePanel_changeToStaticPositioning__Lcom_google_gwt_dom_client_Element_2V(w.com_google_gwt_user_client_ui_UIObject_element);
  return removed;
}
;
var com_google_gwt_lang_ClassLiteralHolder_Lcom_1google_1gwt_1user_1client_1ui_1AbsolutePanel_12_1classLit = java_lang_Class_createForClass__Ljava_lang_String_2Ljava_lang_String_2Lcom_google_gwt_core_client_JavaScriptObject_2Ljava_lang_Class_2Ljava_lang_Class_2($intern_8, 'AbsolutePanel', 125);
function com_google_gwt_user_client_ui_AttachDetachException_$clinit__V(){
  com_google_gwt_user_client_ui_AttachDetachException_$clinit__V = com_google_gwt_lang_Runtime_emptyMethod__V;
  com_google_gwt_user_client_ui_AttachDetachException_attachCommand = new com_google_gwt_user_client_ui_AttachDetachException$1_AttachDetachException$1__V;
  com_google_gwt_user_client_ui_AttachDetachException_detachCommand = new com_google_gwt_user_client_ui_AttachDetachException$2_AttachDetachException$2__V;
}

function com_google_gwt_user_client_ui_AttachDetachException_AttachDetachException__Ljava_util_Set_2V(causes){
  com_google_gwt_event_shared_UmbrellaException_UmbrellaException__Ljava_util_Set_2V.call(this, causes);
}

function com_google_gwt_user_client_ui_AttachDetachException_tryCommand__Ljava_lang_Iterable_2Lcom_google_gwt_user_client_ui_AttachDetachException$Command_2V(hasWidgets, c){
  com_google_gwt_user_client_ui_AttachDetachException_$clinit__V();
  var caught, e, w, w$iterator;
  caught = null;
  for (w$iterator = hasWidgets.iterator__Ljava_util_Iterator_2(); w$iterator.hasNext__Z();) {
    w = com_google_gwt_lang_Cast_castTo__Ljava_lang_Object_2Lcom_google_gwt_core_client_JavaScriptObject_2Ljava_lang_Object_2(w$iterator.next__Ljava_lang_Object_2(), 4);
    try {
      c.execute__Lcom_google_gwt_user_client_ui_Widget_2V(w);
    }
     catch ($e0) {
      $e0 = com_google_gwt_lang_Exceptions_toJava__Ljava_lang_Object_2Ljava_lang_Object_2($e0);
      if (com_google_gwt_lang_Cast_instanceOf__Ljava_lang_Object_2Lcom_google_gwt_core_client_JavaScriptObject_2Z($e0, 6)) {
        e = $e0;
        !caught && (caught = new java_util_HashSet_HashSet__V);
        java_util_AbstractHashMap_$put__Ljava_util_AbstractHashMap_2Ljava_lang_Object_2Ljava_lang_Object_2Ljava_lang_Object_2(caught.java_util_HashSet_map, e, caught);
      }
       else 
        throw com_google_gwt_lang_Exceptions_toJs__Ljava_lang_Object_2Ljava_lang_Object_2($e0);
    }
  }
  if (caught) {
    throw com_google_gwt_lang_Exceptions_toJs__Ljava_lang_Object_2Ljava_lang_Object_2(new com_google_gwt_user_client_ui_AttachDetachException_AttachDetachException__Ljava_util_Set_2V(caught));
  }
}

com_google_gwt_lang_Runtime_defineClass__Lcom_google_gwt_core_client_JavaScriptObject_2Lcom_google_gwt_core_client_JavaScriptObject_2Lcom_google_gwt_core_client_JavaScriptObject_2V(126, 88, $intern_37, com_google_gwt_user_client_ui_AttachDetachException_AttachDetachException__Ljava_util_Set_2V);
var com_google_gwt_user_client_ui_AttachDetachException_attachCommand, com_google_gwt_user_client_ui_AttachDetachException_detachCommand;
var com_google_gwt_lang_ClassLiteralHolder_Lcom_1google_1gwt_1user_1client_1ui_1AttachDetachException_12_1classLit = java_lang_Class_createForClass__Ljava_lang_String_2Ljava_lang_String_2Lcom_google_gwt_core_client_JavaScriptObject_2Ljava_lang_Class_2Ljava_lang_Class_2($intern_8, 'AttachDetachException', 126);
function com_google_gwt_user_client_ui_AttachDetachException$1_AttachDetachException$1__V(){
}

com_google_gwt_lang_Runtime_defineClass__Lcom_google_gwt_core_client_JavaScriptObject_2Lcom_google_gwt_core_client_JavaScriptObject_2Lcom_google_gwt_core_client_JavaScriptObject_2V(127, 1, {}, com_google_gwt_user_client_ui_AttachDetachException$1_AttachDetachException$1__V);
_.execute__Lcom_google_gwt_user_client_ui_Widget_2V = function com_google_gwt_user_client_ui_AttachDetachException$1_execute__Lcom_google_gwt_user_client_ui_Widget_2V(w){
  w.onAttach__V();
}
;
var com_google_gwt_lang_ClassLiteralHolder_Lcom_1google_1gwt_1user_1client_1ui_1AttachDetachException$1_12_1classLit = java_lang_Class_createForClass__Ljava_lang_String_2Ljava_lang_String_2Lcom_google_gwt_core_client_JavaScriptObject_2Ljava_lang_Class_2Ljava_lang_Class_2($intern_8, 'AttachDetachException/1', 127);
function com_google_gwt_user_client_ui_AttachDetachException$2_AttachDetachException$2__V(){
}

com_google_gwt_lang_Runtime_defineClass__Lcom_google_gwt_core_client_JavaScriptObject_2Lcom_google_gwt_core_client_JavaScriptObject_2Lcom_google_gwt_core_client_JavaScriptObject_2V(128, 1, {}, com_google_gwt_user_client_ui_AttachDetachException$2_AttachDetachException$2__V);
_.execute__Lcom_google_gwt_user_client_ui_Widget_2V = function com_google_gwt_user_client_ui_AttachDetachException$2_execute__Lcom_google_gwt_user_client_ui_Widget_2V(w){
  w.onDetach__V();
}
;
var com_google_gwt_lang_ClassLiteralHolder_Lcom_1google_1gwt_1user_1client_1ui_1AttachDetachException$2_12_1classLit = java_lang_Class_createForClass__Ljava_lang_String_2Ljava_lang_String_2Lcom_google_gwt_core_client_JavaScriptObject_2Ljava_lang_Class_2Ljava_lang_Class_2($intern_8, 'AttachDetachException/2', 128);
function com_google_gwt_user_client_ui_DirectionalTextHelper_$setTextOrHtml__Lcom_google_gwt_user_client_ui_DirectionalTextHelper_2Ljava_lang_String_2ZV(this$static, content, isHtml){
  isHtml?com_google_gwt_dom_client_Element_$setInnerHTML__Lcom_google_gwt_dom_client_Element_2Ljava_lang_String_2V(this$static.com_google_gwt_user_client_ui_DirectionalTextHelper_element, content):com_google_gwt_dom_client_DOMImplTrident_$setInnerText__Lcom_google_gwt_dom_client_DOMImplTrident_2Lcom_google_gwt_dom_client_Element_2Ljava_lang_String_2V(this$static.com_google_gwt_user_client_ui_DirectionalTextHelper_element, content);
  if (this$static.com_google_gwt_user_client_ui_DirectionalTextHelper_textDir != this$static.com_google_gwt_user_client_ui_DirectionalTextHelper_initialElementDir) {
    this$static.com_google_gwt_user_client_ui_DirectionalTextHelper_textDir = this$static.com_google_gwt_user_client_ui_DirectionalTextHelper_initialElementDir;
    com_google_gwt_i18n_client_BidiUtils_setDirectionOnElement__Lcom_google_gwt_dom_client_Element_2Lcom_google_gwt_i18n_client_HasDirection$Direction_2V(this$static.com_google_gwt_user_client_ui_DirectionalTextHelper_element, this$static.com_google_gwt_user_client_ui_DirectionalTextHelper_initialElementDir);
  }
}

function com_google_gwt_user_client_ui_DirectionalTextHelper_DirectionalTextHelper__Lcom_google_gwt_dom_client_Element_2ZV(element){
  this.com_google_gwt_user_client_ui_DirectionalTextHelper_element = element;
  this.com_google_gwt_user_client_ui_DirectionalTextHelper_initialElementDir = com_google_gwt_i18n_client_BidiUtils_getDirectionOnElement__Lcom_google_gwt_dom_client_Element_2Lcom_google_gwt_i18n_client_HasDirection$Direction_2(element);
  this.com_google_gwt_user_client_ui_DirectionalTextHelper_textDir = this.com_google_gwt_user_client_ui_DirectionalTextHelper_initialElementDir;
}

com_google_gwt_lang_Runtime_defineClass__Lcom_google_gwt_core_client_JavaScriptObject_2Lcom_google_gwt_core_client_JavaScriptObject_2Lcom_google_gwt_core_client_JavaScriptObject_2V(196, 1, {}, com_google_gwt_user_client_ui_DirectionalTextHelper_DirectionalTextHelper__Lcom_google_gwt_dom_client_Element_2ZV);
var com_google_gwt_lang_ClassLiteralHolder_Lcom_1google_1gwt_1user_1client_1ui_1DirectionalTextHelper_12_1classLit = java_lang_Class_createForClass__Ljava_lang_String_2Ljava_lang_String_2Lcom_google_gwt_core_client_JavaScriptObject_2Ljava_lang_Class_2Ljava_lang_Class_2($intern_8, 'DirectionalTextHelper', 196);
function com_google_gwt_user_client_ui_FlowPanel_$add__Lcom_google_gwt_user_client_ui_FlowPanel_2Lcom_google_gwt_user_client_ui_Widget_2V(this$static, w){
  com_google_gwt_user_client_ui_ComplexPanel_$add__Lcom_google_gwt_user_client_ui_ComplexPanel_2Lcom_google_gwt_user_client_ui_Widget_2Lcom_google_gwt_user_client_Element_2V(this$static, w, this$static.com_google_gwt_user_client_ui_UIObject_element);
}

function com_google_gwt_user_client_ui_FlowPanel_FlowPanel__V(){
  com_google_gwt_user_client_ui_ComplexPanel_ComplexPanel__V.call(this);
  com_google_gwt_user_client_ui_UIObject_$setElement__Lcom_google_gwt_user_client_ui_UIObject_2Lcom_google_gwt_user_client_Element_2V(this, com_google_gwt_dom_client_DOMImplTrident_$createElement__Lcom_google_gwt_dom_client_DOMImplTrident_2Lcom_google_gwt_dom_client_Document_2Ljava_lang_String_2Lcom_google_gwt_dom_client_Element_2($doc, $intern_30));
}

com_google_gwt_lang_Runtime_defineClass__Lcom_google_gwt_core_client_JavaScriptObject_2Lcom_google_gwt_core_client_JavaScriptObject_2Lcom_google_gwt_core_client_JavaScriptObject_2V(57, 41, $intern_46);
var com_google_gwt_lang_ClassLiteralHolder_Lcom_1google_1gwt_1user_1client_1ui_1FlowPanel_12_1classLit = java_lang_Class_createForClass__Ljava_lang_String_2Ljava_lang_String_2Lcom_google_gwt_core_client_JavaScriptObject_2Ljava_lang_Class_2Ljava_lang_Class_2($intern_8, 'FlowPanel', 57);
function com_google_gwt_user_client_ui_HasHorizontalAlignment_$clinit__V(){
  com_google_gwt_user_client_ui_HasHorizontalAlignment_$clinit__V = com_google_gwt_lang_Runtime_emptyMethod__V;
  com_google_gwt_dom_client_Style$TextAlign_$clinit__V();
}

function com_google_gwt_user_client_ui_LabelBase_LabelBase__Lcom_google_gwt_dom_client_Element_2ZV(element){
  this.com_google_gwt_user_client_ui_UIObject_element = element;
  this.com_google_gwt_user_client_ui_LabelBase_directionalTextHelper = new com_google_gwt_user_client_ui_DirectionalTextHelper_DirectionalTextHelper__Lcom_google_gwt_dom_client_Element_2ZV(this.com_google_gwt_user_client_ui_UIObject_element);
}

com_google_gwt_lang_Runtime_defineClass__Lcom_google_gwt_core_client_JavaScriptObject_2Lcom_google_gwt_core_client_JavaScriptObject_2Lcom_google_gwt_core_client_JavaScriptObject_2V(103, 4, $intern_9);
var com_google_gwt_lang_ClassLiteralHolder_Lcom_1google_1gwt_1user_1client_1ui_1LabelBase_12_1classLit = java_lang_Class_createForClass__Ljava_lang_String_2Ljava_lang_String_2Lcom_google_gwt_core_client_JavaScriptObject_2Ljava_lang_Class_2Ljava_lang_Class_2($intern_8, 'LabelBase', 103);
function com_google_gwt_user_client_ui_Label_Label__Lcom_google_gwt_dom_client_Element_2V(element){
  com_google_gwt_user_client_ui_LabelBase_LabelBase__Lcom_google_gwt_dom_client_Element_2ZV.call(this, (java_lang_String_$equalsIgnoreCase__Ljava_lang_String_2Ljava_lang_String_2Z($intern_47, com_google_gwt_dom_client_DOMImplTrident_$getTagName__Lcom_google_gwt_dom_client_DOMImplTrident_2Lcom_google_gwt_dom_client_Element_2Ljava_lang_String_2(element)) , element));
}

com_google_gwt_lang_Runtime_defineClass__Lcom_google_gwt_core_client_JavaScriptObject_2Lcom_google_gwt_core_client_JavaScriptObject_2Lcom_google_gwt_core_client_JavaScriptObject_2V(186, 103, $intern_9);
_.setText__Ljava_lang_String_2V = function com_google_gwt_user_client_ui_Label_setText__Ljava_lang_String_2V(text_0){
  com_google_gwt_user_client_ui_DirectionalTextHelper_$setTextOrHtml__Lcom_google_gwt_user_client_ui_DirectionalTextHelper_2Ljava_lang_String_2ZV(this.com_google_gwt_user_client_ui_LabelBase_directionalTextHelper, text_0, false);
}
;
var com_google_gwt_lang_ClassLiteralHolder_Lcom_1google_1gwt_1user_1client_1ui_1Label_12_1classLit = java_lang_Class_createForClass__Ljava_lang_String_2Ljava_lang_String_2Lcom_google_gwt_core_client_JavaScriptObject_2Ljava_lang_Class_2Ljava_lang_Class_2($intern_8, 'Label', 186);
function com_google_gwt_user_client_ui_HTML_$setHTML__Lcom_google_gwt_user_client_ui_HTML_2Ljava_lang_String_2V(this$static, html){
  com_google_gwt_user_client_ui_DirectionalTextHelper_$setTextOrHtml__Lcom_google_gwt_user_client_ui_DirectionalTextHelper_2Ljava_lang_String_2ZV(this$static.com_google_gwt_user_client_ui_LabelBase_directionalTextHelper, html, true);
}

function com_google_gwt_user_client_ui_HTML_HTML__V(){
  com_google_gwt_user_client_ui_Label_Label__Lcom_google_gwt_dom_client_Element_2V.call(this, com_google_gwt_dom_client_DOMImplTrident_$createElement__Lcom_google_gwt_dom_client_DOMImplTrident_2Lcom_google_gwt_dom_client_Document_2Ljava_lang_String_2Lcom_google_gwt_dom_client_Element_2($doc, $intern_30));
  this.com_google_gwt_user_client_ui_UIObject_element.className = 'gwt-HTML';
}

com_google_gwt_lang_Runtime_defineClass__Lcom_google_gwt_core_client_JavaScriptObject_2Lcom_google_gwt_core_client_JavaScriptObject_2Lcom_google_gwt_core_client_JavaScriptObject_2V(60, 186, $intern_9, com_google_gwt_user_client_ui_HTML_HTML__V);
var com_google_gwt_lang_ClassLiteralHolder_Lcom_1google_1gwt_1user_1client_1ui_1HTML_12_1classLit = java_lang_Class_createForClass__Ljava_lang_String_2Ljava_lang_String_2Lcom_google_gwt_core_client_JavaScriptObject_2Ljava_lang_Class_2Ljava_lang_Class_2($intern_8, 'HTML', 60);
function com_google_gwt_user_client_ui_HTMLPanel_$addAndReplaceElement__Lcom_google_gwt_user_client_ui_HTMLPanel_2Lcom_google_gwt_user_client_ui_Widget_2Lcom_google_gwt_user_client_Element_2V(this$static, widget, toReplace){
  var children, next, toRemove;
  if (toReplace == widget.com_google_gwt_user_client_ui_UIObject_element) {
    return;
  }
  com_google_gwt_user_client_ui_Widget_$removeFromParent__Lcom_google_gwt_user_client_ui_Widget_2V(widget);
  toRemove = null;
  children = new com_google_gwt_user_client_ui_WidgetCollection$WidgetIterator_WidgetCollection$WidgetIterator__Lcom_google_gwt_user_client_ui_WidgetCollection_2V(this$static.com_google_gwt_user_client_ui_ComplexPanel_children);
  while (children.com_google_gwt_user_client_ui_WidgetCollection$WidgetIterator_index < children.com_google_gwt_user_client_ui_WidgetCollection$WidgetIterator_this$01.com_google_gwt_user_client_ui_WidgetCollection_size) {
    next = com_google_gwt_user_client_ui_WidgetCollection$WidgetIterator_$next__Lcom_google_gwt_user_client_ui_WidgetCollection$WidgetIterator_2Lcom_google_gwt_user_client_ui_Widget_2(children);
    if (com_google_gwt_dom_client_DOMImplTrident_isOrHasChildImpl__Lcom_google_gwt_dom_client_Node_2Lcom_google_gwt_dom_client_Node_2Z(toReplace, next.com_google_gwt_user_client_ui_UIObject_element)) {
      if (next.com_google_gwt_user_client_ui_UIObject_element == toReplace) {
        toRemove = next;
        break;
      }
      com_google_gwt_user_client_ui_WidgetCollection$WidgetIterator_$remove__Lcom_google_gwt_user_client_ui_WidgetCollection$WidgetIterator_2V(children);
    }
  }
  com_google_gwt_user_client_ui_WidgetCollection_$add__Lcom_google_gwt_user_client_ui_WidgetCollection_2Lcom_google_gwt_user_client_ui_Widget_2V(this$static.com_google_gwt_user_client_ui_ComplexPanel_children, widget);
  if (!toRemove) {
    com_google_gwt_dom_client_Node_$replaceChild__Lcom_google_gwt_dom_client_Node_2Lcom_google_gwt_dom_client_Node_2Lcom_google_gwt_dom_client_Node_2Lcom_google_gwt_dom_client_Node_2(toReplace.parentNode, widget.com_google_gwt_user_client_ui_UIObject_element, toReplace);
  }
   else {
    com_google_gwt_dom_client_Node_$insertBefore__Lcom_google_gwt_dom_client_Node_2Lcom_google_gwt_dom_client_Node_2Lcom_google_gwt_dom_client_Node_2Lcom_google_gwt_dom_client_Node_2(toReplace.parentNode, widget.com_google_gwt_user_client_ui_UIObject_element, toReplace);
    com_google_gwt_user_client_ui_ComplexPanel_$remove__Lcom_google_gwt_user_client_ui_ComplexPanel_2Lcom_google_gwt_user_client_ui_Widget_2Z(this$static, toRemove);
  }
  com_google_gwt_user_client_ui_Widget_$setParent__Lcom_google_gwt_user_client_ui_Widget_2Lcom_google_gwt_user_client_ui_Widget_2V(widget, this$static);
}

function com_google_gwt_user_client_ui_HTMLPanel_HTMLPanel__Ljava_lang_String_2V(html){
  com_google_gwt_user_client_ui_ComplexPanel_ComplexPanel__V.call(this);
  com_google_gwt_user_client_ui_UIObject_$setElement__Lcom_google_gwt_user_client_ui_UIObject_2Lcom_google_gwt_user_client_Element_2V(this, com_google_gwt_dom_client_DOMImplTrident_$createElement__Lcom_google_gwt_dom_client_DOMImplTrident_2Lcom_google_gwt_dom_client_Document_2Ljava_lang_String_2Lcom_google_gwt_dom_client_Element_2($doc, $intern_30));
  com_google_gwt_dom_client_Element_$setInnerHTML__Lcom_google_gwt_dom_client_Element_2Ljava_lang_String_2V(this.com_google_gwt_user_client_ui_UIObject_element, html);
}

com_google_gwt_lang_Runtime_defineClass__Lcom_google_gwt_core_client_JavaScriptObject_2Lcom_google_gwt_core_client_JavaScriptObject_2Lcom_google_gwt_core_client_JavaScriptObject_2V(96, 41, $intern_46, com_google_gwt_user_client_ui_HTMLPanel_HTMLPanel__Ljava_lang_String_2V);
var com_google_gwt_lang_ClassLiteralHolder_Lcom_1google_1gwt_1user_1client_1ui_1HTMLPanel_12_1classLit = java_lang_Class_createForClass__Ljava_lang_String_2Ljava_lang_String_2Lcom_google_gwt_core_client_JavaScriptObject_2Ljava_lang_Class_2Ljava_lang_Class_2($intern_8, 'HTMLPanel', 96);
function com_google_gwt_user_client_ui_PotentialElement_$clinit__V(){
  com_google_gwt_user_client_ui_PotentialElement_$clinit__V = com_google_gwt_lang_Runtime_emptyMethod__V;
  com_google_gwt_user_client_ui_PotentialElement_declareShim__V();
}

function com_google_gwt_user_client_ui_PotentialElement_$setResolver__Lcom_google_gwt_user_client_ui_PotentialElement_2Lcom_google_gwt_user_client_ui_UIObject_2Lcom_google_gwt_dom_client_Element_2(this$static, resolver){
  com_google_gwt_user_client_ui_PotentialElement_$clinit__V();
  this$static.__gwt_resolve = com_google_gwt_user_client_ui_PotentialElement_buildResolveCallback__Lcom_google_gwt_user_client_ui_UIObject_2Lcom_google_gwt_core_client_JavaScriptObject_2(resolver);
}

function com_google_gwt_user_client_ui_PotentialElement_buildResolveCallback__Lcom_google_gwt_user_client_ui_UIObject_2Lcom_google_gwt_core_client_JavaScriptObject_2(resolver){
  return function(){
    this.__gwt_resolve = com_google_gwt_user_client_ui_PotentialElement_cannotResolveTwice__V;
    return resolver.resolvePotentialElement__Lcom_google_gwt_dom_client_Element_2();
  }
  ;
}

function com_google_gwt_user_client_ui_PotentialElement_cannotResolveTwice__V(){
  throw 'A PotentialElement cannot be resolved twice.';
}

function com_google_gwt_user_client_ui_PotentialElement_declareShim__V(){
  var shim = function(){
  }
  ;
  shim.prototype = {className:'', clientHeight:0, clientWidth:0, dir:'', getAttribute:function(name_0, value_0){
    return this[name_0];
  }
  , href:'', id:'', lang:'', nodeType:1, removeAttribute:function(name_0, value_0){
    this[name_0] = undefined;
  }
  , setAttribute:function(name_0, value_0){
    this[name_0] = value_0;
  }
  , src:'', style:{}, title:''};
  $wnd.GwtPotentialElementShim = shim;
}

function com_google_gwt_user_client_ui_RootPanel_$clinit__V(){
  com_google_gwt_user_client_ui_RootPanel_$clinit__V = com_google_gwt_lang_Runtime_emptyMethod__V;
  com_google_gwt_user_client_ui_RootPanel_maybeDetachCommand = new com_google_gwt_user_client_ui_RootPanel$1_RootPanel$1__V;
  com_google_gwt_user_client_ui_RootPanel_rootPanels = new java_util_HashMap_HashMap__V;
  com_google_gwt_user_client_ui_RootPanel_widgetsToDetach = new java_util_HashSet_HashSet__V;
}

function com_google_gwt_user_client_ui_RootPanel_RootPanel__Lcom_google_gwt_dom_client_Element_2V(elem){
  com_google_gwt_user_client_ui_ComplexPanel_ComplexPanel__V.call(this);
  this.com_google_gwt_user_client_ui_UIObject_element = elem;
  com_google_gwt_user_client_ui_Widget_$onAttach__Lcom_google_gwt_user_client_ui_Widget_2V(this);
}

function com_google_gwt_user_client_ui_RootPanel_detachNow__Lcom_google_gwt_user_client_ui_Widget_2V(widget){
  com_google_gwt_user_client_ui_RootPanel_$clinit__V();
  try {
    widget.onDetach__V();
  }
   finally {
    java_util_HashSet_$remove__Ljava_util_HashSet_2Ljava_lang_Object_2Z(com_google_gwt_user_client_ui_RootPanel_widgetsToDetach, widget);
  }
}

function com_google_gwt_user_client_ui_RootPanel_detachWidgets__V(){
  com_google_gwt_user_client_ui_RootPanel_$clinit__V();
  try {
    com_google_gwt_user_client_ui_AttachDetachException_tryCommand__Ljava_lang_Iterable_2Lcom_google_gwt_user_client_ui_AttachDetachException$Command_2V(com_google_gwt_user_client_ui_RootPanel_widgetsToDetach, com_google_gwt_user_client_ui_RootPanel_maybeDetachCommand);
  }
   finally {
    java_util_AbstractHashMap_$reset__Ljava_util_AbstractHashMap_2V(com_google_gwt_user_client_ui_RootPanel_widgetsToDetach.java_util_HashSet_map);
    java_util_AbstractHashMap_$reset__Ljava_util_AbstractHashMap_2V(com_google_gwt_user_client_ui_RootPanel_rootPanels);
  }
}

function com_google_gwt_user_client_ui_RootPanel_get__Ljava_lang_String_2Lcom_google_gwt_user_client_ui_RootPanel_2(){
  com_google_gwt_user_client_ui_RootPanel_$clinit__V();
  var rp;
  rp = com_google_gwt_lang_Cast_castTo__Ljava_lang_Object_2Lcom_google_gwt_core_client_JavaScriptObject_2Ljava_lang_Object_2(java_util_AbstractHashMap_$get__Ljava_util_AbstractHashMap_2Ljava_lang_Object_2Ljava_lang_Object_2(com_google_gwt_user_client_ui_RootPanel_rootPanels, null), 49);
  if (rp) {
    return rp;
  }
  java_util_AbstractHashMap_$size__Ljava_util_AbstractHashMap_2I(com_google_gwt_user_client_ui_RootPanel_rootPanels) == 0 && com_google_gwt_user_client_Window_addCloseHandler__Lcom_google_gwt_event_logical_shared_CloseHandler_2Lcom_google_gwt_event_shared_HandlerRegistration_2(new com_google_gwt_user_client_ui_RootPanel$2_RootPanel$2__V);
  rp = new com_google_gwt_user_client_ui_RootPanel$DefaultRootPanel_RootPanel$DefaultRootPanel__V;
  java_util_AbstractHashMap_$put__Ljava_util_AbstractHashMap_2Ljava_lang_Object_2Ljava_lang_Object_2Ljava_lang_Object_2(com_google_gwt_user_client_ui_RootPanel_rootPanels, null, rp);
  java_util_HashSet_$add__Ljava_util_HashSet_2Ljava_lang_Object_2Z(com_google_gwt_user_client_ui_RootPanel_widgetsToDetach, rp);
  return rp;
}

com_google_gwt_lang_Runtime_defineClass__Lcom_google_gwt_core_client_JavaScriptObject_2Lcom_google_gwt_core_client_JavaScriptObject_2Lcom_google_gwt_core_client_JavaScriptObject_2V(49, 125, $intern_48);
var com_google_gwt_user_client_ui_RootPanel_maybeDetachCommand, com_google_gwt_user_client_ui_RootPanel_rootPanels, com_google_gwt_user_client_ui_RootPanel_widgetsToDetach;
var com_google_gwt_lang_ClassLiteralHolder_Lcom_1google_1gwt_1user_1client_1ui_1RootPanel_12_1classLit = java_lang_Class_createForClass__Ljava_lang_String_2Ljava_lang_String_2Lcom_google_gwt_core_client_JavaScriptObject_2Ljava_lang_Class_2Ljava_lang_Class_2($intern_8, 'RootPanel', 49);
function com_google_gwt_user_client_ui_RootPanel$1_RootPanel$1__V(){
}

com_google_gwt_lang_Runtime_defineClass__Lcom_google_gwt_core_client_JavaScriptObject_2Lcom_google_gwt_core_client_JavaScriptObject_2Lcom_google_gwt_core_client_JavaScriptObject_2V(130, 1, {}, com_google_gwt_user_client_ui_RootPanel$1_RootPanel$1__V);
_.execute__Lcom_google_gwt_user_client_ui_Widget_2V = function com_google_gwt_user_client_ui_RootPanel$1_execute__Lcom_google_gwt_user_client_ui_Widget_2V(w){
  w.isAttached__Z() && w.onDetach__V();
}
;
var com_google_gwt_lang_ClassLiteralHolder_Lcom_1google_1gwt_1user_1client_1ui_1RootPanel$1_12_1classLit = java_lang_Class_createForClass__Ljava_lang_String_2Ljava_lang_String_2Lcom_google_gwt_core_client_JavaScriptObject_2Ljava_lang_Class_2Ljava_lang_Class_2($intern_8, 'RootPanel/1', 130);
function com_google_gwt_user_client_ui_RootPanel$2_RootPanel$2__V(){
}

com_google_gwt_lang_Runtime_defineClass__Lcom_google_gwt_core_client_JavaScriptObject_2Lcom_google_gwt_core_client_JavaScriptObject_2Lcom_google_gwt_core_client_JavaScriptObject_2V(131, 1, {235:1, 64:1}, com_google_gwt_user_client_ui_RootPanel$2_RootPanel$2__V);
var com_google_gwt_lang_ClassLiteralHolder_Lcom_1google_1gwt_1user_1client_1ui_1RootPanel$2_12_1classLit = java_lang_Class_createForClass__Ljava_lang_String_2Ljava_lang_String_2Lcom_google_gwt_core_client_JavaScriptObject_2Ljava_lang_Class_2Ljava_lang_Class_2($intern_8, 'RootPanel/2', 131);
function com_google_gwt_user_client_ui_RootPanel$DefaultRootPanel_RootPanel$DefaultRootPanel__V(){
  com_google_gwt_user_client_ui_RootPanel_RootPanel__Lcom_google_gwt_dom_client_Element_2V.call(this, (com_google_gwt_user_client_ui_RootPanel_$clinit__V() , $doc.body));
}

com_google_gwt_lang_Runtime_defineClass__Lcom_google_gwt_core_client_JavaScriptObject_2Lcom_google_gwt_core_client_JavaScriptObject_2Lcom_google_gwt_core_client_JavaScriptObject_2V(129, 49, $intern_48, com_google_gwt_user_client_ui_RootPanel$DefaultRootPanel_RootPanel$DefaultRootPanel__V);
var com_google_gwt_lang_ClassLiteralHolder_Lcom_1google_1gwt_1user_1client_1ui_1RootPanel$DefaultRootPanel_12_1classLit = java_lang_Class_createForClass__Ljava_lang_String_2Ljava_lang_String_2Lcom_google_gwt_core_client_JavaScriptObject_2Ljava_lang_Class_2Ljava_lang_Class_2($intern_8, 'RootPanel/DefaultRootPanel', 129);
function com_google_gwt_user_client_ui_SimplePanel_SimplePanel__V(){
  com_google_gwt_user_client_ui_SimplePanel_SimplePanel__Lcom_google_gwt_dom_client_Element_2V.call(this, com_google_gwt_dom_client_DOMImplTrident_$createElement__Lcom_google_gwt_dom_client_DOMImplTrident_2Lcom_google_gwt_dom_client_Document_2Ljava_lang_String_2Lcom_google_gwt_dom_client_Element_2($doc, $intern_30));
}

function com_google_gwt_user_client_ui_SimplePanel_SimplePanel__Lcom_google_gwt_dom_client_Element_2V(elem){
  this.com_google_gwt_user_client_ui_UIObject_element = elem;
}

com_google_gwt_lang_Runtime_defineClass__Lcom_google_gwt_core_client_JavaScriptObject_2Lcom_google_gwt_core_client_JavaScriptObject_2Lcom_google_gwt_core_client_JavaScriptObject_2V(97, 223, $intern_46, com_google_gwt_user_client_ui_SimplePanel_SimplePanel__V);
_.iterator__Ljava_util_Iterator_2 = function com_google_gwt_user_client_ui_SimplePanel_iterator__Ljava_util_Iterator_2(){
  return new com_google_gwt_user_client_ui_SimplePanel$1_SimplePanel$1__Lcom_google_gwt_user_client_ui_SimplePanel_2V;
}
;
_.remove__Lcom_google_gwt_user_client_ui_Widget_2Z = function com_google_gwt_user_client_ui_SimplePanel_remove__Lcom_google_gwt_user_client_ui_Widget_2Z(w){
  return false;
}
;
var com_google_gwt_lang_ClassLiteralHolder_Lcom_1google_1gwt_1user_1client_1ui_1SimplePanel_12_1classLit = java_lang_Class_createForClass__Ljava_lang_String_2Ljava_lang_String_2Lcom_google_gwt_core_client_JavaScriptObject_2Ljava_lang_Class_2Ljava_lang_Class_2($intern_8, 'SimplePanel', 97);
function com_google_gwt_user_client_ui_SimplePanel$1_$next__Lcom_google_gwt_user_client_ui_SimplePanel$1_2Lcom_google_gwt_user_client_ui_Widget_2(){
  throw com_google_gwt_lang_Exceptions_toJs__Ljava_lang_Object_2Ljava_lang_Object_2(new java_util_NoSuchElementException_NoSuchElementException__V);
}

function com_google_gwt_user_client_ui_SimplePanel$1_SimplePanel$1__Lcom_google_gwt_user_client_ui_SimplePanel_2V(){
}

com_google_gwt_lang_Runtime_defineClass__Lcom_google_gwt_core_client_JavaScriptObject_2Lcom_google_gwt_core_client_JavaScriptObject_2Lcom_google_gwt_core_client_JavaScriptObject_2V(177, 1, {}, com_google_gwt_user_client_ui_SimplePanel$1_SimplePanel$1__Lcom_google_gwt_user_client_ui_SimplePanel_2V);
_.next__Ljava_lang_Object_2 = function com_google_gwt_user_client_ui_SimplePanel$1_next__Ljava_lang_Object_2(){
  return com_google_gwt_user_client_ui_SimplePanel$1_$next__Lcom_google_gwt_user_client_ui_SimplePanel$1_2Lcom_google_gwt_user_client_ui_Widget_2();
}
;
_.hasNext__Z = function com_google_gwt_user_client_ui_SimplePanel$1_hasNext__Z(){
  return false;
}
;
var com_google_gwt_lang_ClassLiteralHolder_Lcom_1google_1gwt_1user_1client_1ui_1SimplePanel$1_12_1classLit = java_lang_Class_createForClass__Ljava_lang_String_2Ljava_lang_String_2Lcom_google_gwt_core_client_JavaScriptObject_2Ljava_lang_Class_2Ljava_lang_Class_2($intern_8, 'SimplePanel/1', 177);
function com_google_gwt_user_client_ui_WidgetCollection_$add__Lcom_google_gwt_user_client_ui_WidgetCollection_2Lcom_google_gwt_user_client_ui_Widget_2V(this$static, w){
  com_google_gwt_user_client_ui_WidgetCollection_$insert__Lcom_google_gwt_user_client_ui_WidgetCollection_2Lcom_google_gwt_user_client_ui_Widget_2IV(this$static, w, this$static.com_google_gwt_user_client_ui_WidgetCollection_size);
}

function com_google_gwt_user_client_ui_WidgetCollection_$get__Lcom_google_gwt_user_client_ui_WidgetCollection_2ILcom_google_gwt_user_client_ui_Widget_2(this$static, index_0){
  if (index_0 < 0 || index_0 >= this$static.com_google_gwt_user_client_ui_WidgetCollection_size) {
    throw com_google_gwt_lang_Exceptions_toJs__Ljava_lang_Object_2Ljava_lang_Object_2(new java_lang_IndexOutOfBoundsException_IndexOutOfBoundsException__V);
  }
  return this$static.com_google_gwt_user_client_ui_WidgetCollection_array[index_0];
}

function com_google_gwt_user_client_ui_WidgetCollection_$indexOf__Lcom_google_gwt_user_client_ui_WidgetCollection_2Lcom_google_gwt_user_client_ui_Widget_2I(this$static, w){
  var i;
  for (i = 0; i < this$static.com_google_gwt_user_client_ui_WidgetCollection_size; ++i) {
    if (this$static.com_google_gwt_user_client_ui_WidgetCollection_array[i] == w) {
      return i;
    }
  }
  return -1;
}

function com_google_gwt_user_client_ui_WidgetCollection_$insert__Lcom_google_gwt_user_client_ui_WidgetCollection_2Lcom_google_gwt_user_client_ui_Widget_2IV(this$static, w, beforeIndex){
  var i, i0, newArray;
  if (beforeIndex < 0 || beforeIndex > this$static.com_google_gwt_user_client_ui_WidgetCollection_size) {
    throw com_google_gwt_lang_Exceptions_toJs__Ljava_lang_Object_2Ljava_lang_Object_2(new java_lang_IndexOutOfBoundsException_IndexOutOfBoundsException__V);
  }
  if (this$static.com_google_gwt_user_client_ui_WidgetCollection_size == this$static.com_google_gwt_user_client_ui_WidgetCollection_array.length) {
    newArray = com_google_gwt_lang_Array_initUnidimensionalArray__Ljava_lang_Class_2Lcom_google_gwt_core_client_JavaScriptObject_2Lcom_google_gwt_core_client_JavaScriptObject_2IIILjava_lang_Object_2(com_google_gwt_lang_ClassLiteralHolder_Lcom_1google_1gwt_1user_1client_1ui_1Widget_12_1classLit, {3:1}, 4, this$static.com_google_gwt_user_client_ui_WidgetCollection_array.length * 2, 0, 1);
    for (i0 = 0; i0 < this$static.com_google_gwt_user_client_ui_WidgetCollection_array.length; ++i0) {
      newArray[i0] = this$static.com_google_gwt_user_client_ui_WidgetCollection_array[i0];
    }
    this$static.com_google_gwt_user_client_ui_WidgetCollection_array = newArray;
  }
  ++this$static.com_google_gwt_user_client_ui_WidgetCollection_size;
  for (i = this$static.com_google_gwt_user_client_ui_WidgetCollection_size - 1; i > beforeIndex; --i) {
    this$static.com_google_gwt_user_client_ui_WidgetCollection_array[i] = this$static.com_google_gwt_user_client_ui_WidgetCollection_array[i - 1];
  }
  this$static.com_google_gwt_user_client_ui_WidgetCollection_array[beforeIndex] = w;
}

function com_google_gwt_user_client_ui_WidgetCollection_$remove__Lcom_google_gwt_user_client_ui_WidgetCollection_2IV(this$static, index_0){
  var i;
  if (index_0 < 0 || index_0 >= this$static.com_google_gwt_user_client_ui_WidgetCollection_size) {
    throw com_google_gwt_lang_Exceptions_toJs__Ljava_lang_Object_2Ljava_lang_Object_2(new java_lang_IndexOutOfBoundsException_IndexOutOfBoundsException__V);
  }
  --this$static.com_google_gwt_user_client_ui_WidgetCollection_size;
  for (i = index_0; i < this$static.com_google_gwt_user_client_ui_WidgetCollection_size; ++i) {
    this$static.com_google_gwt_user_client_ui_WidgetCollection_array[i] = this$static.com_google_gwt_user_client_ui_WidgetCollection_array[i + 1];
  }
  this$static.com_google_gwt_user_client_ui_WidgetCollection_array[this$static.com_google_gwt_user_client_ui_WidgetCollection_size] = null;
}

function com_google_gwt_user_client_ui_WidgetCollection_$remove__Lcom_google_gwt_user_client_ui_WidgetCollection_2Lcom_google_gwt_user_client_ui_Widget_2V(this$static, w){
  var index_0;
  index_0 = com_google_gwt_user_client_ui_WidgetCollection_$indexOf__Lcom_google_gwt_user_client_ui_WidgetCollection_2Lcom_google_gwt_user_client_ui_Widget_2I(this$static, w);
  if (index_0 == -1) {
    throw com_google_gwt_lang_Exceptions_toJs__Ljava_lang_Object_2Ljava_lang_Object_2(new java_util_NoSuchElementException_NoSuchElementException__V);
  }
  com_google_gwt_user_client_ui_WidgetCollection_$remove__Lcom_google_gwt_user_client_ui_WidgetCollection_2IV(this$static, index_0);
}

function com_google_gwt_user_client_ui_WidgetCollection_WidgetCollection__Lcom_google_gwt_user_client_ui_HasWidgets_2V(parent_0){
  this.com_google_gwt_user_client_ui_WidgetCollection_parent = parent_0;
  this.com_google_gwt_user_client_ui_WidgetCollection_array = com_google_gwt_lang_Array_initUnidimensionalArray__Ljava_lang_Class_2Lcom_google_gwt_core_client_JavaScriptObject_2Lcom_google_gwt_core_client_JavaScriptObject_2IIILjava_lang_Object_2(com_google_gwt_lang_ClassLiteralHolder_Lcom_1google_1gwt_1user_1client_1ui_1Widget_12_1classLit, {3:1}, 4, 4, 0, 1);
}

com_google_gwt_lang_Runtime_defineClass__Lcom_google_gwt_core_client_JavaScriptObject_2Lcom_google_gwt_core_client_JavaScriptObject_2Lcom_google_gwt_core_client_JavaScriptObject_2V(151, 1, {}, com_google_gwt_user_client_ui_WidgetCollection_WidgetCollection__Lcom_google_gwt_user_client_ui_HasWidgets_2V);
_.iterator__Ljava_util_Iterator_2 = function com_google_gwt_user_client_ui_WidgetCollection_iterator__Ljava_util_Iterator_2(){
  return new com_google_gwt_user_client_ui_WidgetCollection$WidgetIterator_WidgetCollection$WidgetIterator__Lcom_google_gwt_user_client_ui_WidgetCollection_2V(this);
}
;
_.com_google_gwt_user_client_ui_WidgetCollection_size = 0;
var com_google_gwt_lang_ClassLiteralHolder_Lcom_1google_1gwt_1user_1client_1ui_1WidgetCollection_12_1classLit = java_lang_Class_createForClass__Ljava_lang_String_2Ljava_lang_String_2Lcom_google_gwt_core_client_JavaScriptObject_2Ljava_lang_Class_2Ljava_lang_Class_2($intern_8, 'WidgetCollection', 151);
function com_google_gwt_user_client_ui_WidgetCollection$WidgetIterator_$next__Lcom_google_gwt_user_client_ui_WidgetCollection$WidgetIterator_2Lcom_google_gwt_user_client_ui_Widget_2(this$static){
  if (this$static.com_google_gwt_user_client_ui_WidgetCollection$WidgetIterator_index >= this$static.com_google_gwt_user_client_ui_WidgetCollection$WidgetIterator_this$01.com_google_gwt_user_client_ui_WidgetCollection_size) {
    throw com_google_gwt_lang_Exceptions_toJs__Ljava_lang_Object_2Ljava_lang_Object_2(new java_util_NoSuchElementException_NoSuchElementException__V);
  }
  this$static.com_google_gwt_user_client_ui_WidgetCollection$WidgetIterator_currentWidget = this$static.com_google_gwt_user_client_ui_WidgetCollection$WidgetIterator_this$01.com_google_gwt_user_client_ui_WidgetCollection_array[this$static.com_google_gwt_user_client_ui_WidgetCollection$WidgetIterator_index];
  ++this$static.com_google_gwt_user_client_ui_WidgetCollection$WidgetIterator_index;
  return this$static.com_google_gwt_user_client_ui_WidgetCollection$WidgetIterator_currentWidget;
}

function com_google_gwt_user_client_ui_WidgetCollection$WidgetIterator_$remove__Lcom_google_gwt_user_client_ui_WidgetCollection$WidgetIterator_2V(this$static){
  if (!this$static.com_google_gwt_user_client_ui_WidgetCollection$WidgetIterator_currentWidget) {
    throw com_google_gwt_lang_Exceptions_toJs__Ljava_lang_Object_2Ljava_lang_Object_2(new java_lang_IllegalStateException_IllegalStateException__V);
  }
  this$static.com_google_gwt_user_client_ui_WidgetCollection$WidgetIterator_this$01.com_google_gwt_user_client_ui_WidgetCollection_parent.remove__Lcom_google_gwt_user_client_ui_Widget_2Z(this$static.com_google_gwt_user_client_ui_WidgetCollection$WidgetIterator_currentWidget);
  --this$static.com_google_gwt_user_client_ui_WidgetCollection$WidgetIterator_index;
  this$static.com_google_gwt_user_client_ui_WidgetCollection$WidgetIterator_currentWidget = null;
}

function com_google_gwt_user_client_ui_WidgetCollection$WidgetIterator_WidgetCollection$WidgetIterator__Lcom_google_gwt_user_client_ui_WidgetCollection_2V(this$0){
  this.com_google_gwt_user_client_ui_WidgetCollection$WidgetIterator_this$01 = this$0;
}

com_google_gwt_lang_Runtime_defineClass__Lcom_google_gwt_core_client_JavaScriptObject_2Lcom_google_gwt_core_client_JavaScriptObject_2Lcom_google_gwt_core_client_JavaScriptObject_2V(75, 1, {}, com_google_gwt_user_client_ui_WidgetCollection$WidgetIterator_WidgetCollection$WidgetIterator__Lcom_google_gwt_user_client_ui_WidgetCollection_2V);
_.next__Ljava_lang_Object_2 = function com_google_gwt_user_client_ui_WidgetCollection$WidgetIterator_next__Ljava_lang_Object_2(){
  return com_google_gwt_user_client_ui_WidgetCollection$WidgetIterator_$next__Lcom_google_gwt_user_client_ui_WidgetCollection$WidgetIterator_2Lcom_google_gwt_user_client_ui_Widget_2(this);
}
;
_.hasNext__Z = function com_google_gwt_user_client_ui_WidgetCollection$WidgetIterator_hasNext__Z(){
  return this.com_google_gwt_user_client_ui_WidgetCollection$WidgetIterator_index < this.com_google_gwt_user_client_ui_WidgetCollection$WidgetIterator_this$01.com_google_gwt_user_client_ui_WidgetCollection_size;
}
;
_.com_google_gwt_user_client_ui_WidgetCollection$WidgetIterator_index = 0;
var com_google_gwt_lang_ClassLiteralHolder_Lcom_1google_1gwt_1user_1client_1ui_1WidgetCollection$WidgetIterator_12_1classLit = java_lang_Class_createForClass__Ljava_lang_String_2Ljava_lang_String_2Lcom_google_gwt_core_client_JavaScriptObject_2Ljava_lang_Class_2Ljava_lang_Class_2($intern_8, 'WidgetCollection/WidgetIterator', 75);
function com_google_gwt_user_client_ui_impl_HyperlinkImplIE_$clinit__V(){
  com_google_gwt_user_client_ui_impl_HyperlinkImplIE_$clinit__V = com_google_gwt_lang_Runtime_emptyMethod__V;
  com_google_gwt_user_client_ui_impl_HyperlinkImplIE_getInternetExplorerVersion__I() >= 7;
}

function com_google_gwt_user_client_ui_impl_HyperlinkImplIE_getInternetExplorerVersion__I(){
  var rv = -1;
  if (navigator.appName == 'Microsoft Internet Explorer') {
    var ua = navigator.userAgent;
    var re = new RegExp('MSIE ([0-9]{1,}[.0-9]{0,})');
    re.exec(ua) != null && (rv = parseFloat(RegExp.$1));
  }
  return rv;
}

function com_google_gwt_useragent_client_UserAgentAsserter_assertCompileTimeUserAgent__V(){
  var runtimeValue;
  runtimeValue = com_google_gwt_useragent_client_UserAgentImplIe8_$getRuntimeValue__Lcom_google_gwt_useragent_client_UserAgentImplIe8_2Ljava_lang_String_2();
  if (!java_lang_String_$equals__Ljava_lang_String_2Ljava_lang_Object_2Z('ie8', runtimeValue)) {
    throw com_google_gwt_lang_Exceptions_toJs__Ljava_lang_Object_2Ljava_lang_Object_2(new com_google_gwt_useragent_client_UserAgentAsserter$UserAgentAssertionError_UserAgentAsserter$UserAgentAssertionError__Ljava_lang_String_2Ljava_lang_String_2V(runtimeValue));
  }
}

function java_lang_Error_Error__Ljava_lang_String_2Ljava_lang_Throwable_2V(message){
  java_lang_Throwable_Throwable__Ljava_lang_String_2Ljava_lang_Throwable_2V.call(this, message);
}

com_google_gwt_lang_Runtime_defineClass__Lcom_google_gwt_core_client_JavaScriptObject_2Lcom_google_gwt_core_client_JavaScriptObject_2Lcom_google_gwt_core_client_JavaScriptObject_2V(68, 6, $intern_27);
var com_google_gwt_lang_ClassLiteralHolder_Ljava_1lang_1Error_12_1classLit = java_lang_Class_createForClass__Ljava_lang_String_2Ljava_lang_String_2Lcom_google_gwt_core_client_JavaScriptObject_2Ljava_lang_Class_2Ljava_lang_Class_2($intern_2, 'Error', 68);
com_google_gwt_lang_Runtime_defineClass__Lcom_google_gwt_core_client_JavaScriptObject_2Lcom_google_gwt_core_client_JavaScriptObject_2Lcom_google_gwt_core_client_JavaScriptObject_2V(11, 68, $intern_27);
var com_google_gwt_lang_ClassLiteralHolder_Ljava_1lang_1AssertionError_12_1classLit = java_lang_Class_createForClass__Ljava_lang_String_2Ljava_lang_String_2Lcom_google_gwt_core_client_JavaScriptObject_2Ljava_lang_Class_2Ljava_lang_Class_2($intern_2, 'AssertionError', 11);
function com_google_gwt_useragent_client_UserAgentAsserter$UserAgentAssertionError_UserAgentAsserter$UserAgentAssertionError__Ljava_lang_String_2Ljava_lang_String_2V(runtimeValue){
  var java_lang_AssertionError_AssertionError__Ljava_lang_Object_2V_lastArg_0;
  java_lang_Error_Error__Ljava_lang_String_2Ljava_lang_Throwable_2V.call(this, (java_lang_AssertionError_AssertionError__Ljava_lang_Object_2V_lastArg_0 = $intern_49 + runtimeValue + $intern_50 + $intern_51 == null?$intern_29:com_google_gwt_lang_Runtime_toString__Ljava_lang_Object_2Ljava_lang_String_2($intern_49 + runtimeValue + $intern_50 + $intern_51) , com_google_gwt_lang_Cast_instanceOf__Ljava_lang_Object_2Lcom_google_gwt_core_client_JavaScriptObject_2Z($intern_49 + runtimeValue + $intern_50 + $intern_51, 6)?com_google_gwt_lang_Cast_castTo__Ljava_lang_Object_2Lcom_google_gwt_core_client_JavaScriptObject_2Ljava_lang_Object_2($intern_49 + runtimeValue + $intern_50 + $intern_51, 6):null , java_lang_AssertionError_AssertionError__Ljava_lang_Object_2V_lastArg_0));
}

com_google_gwt_lang_Runtime_defineClass__Lcom_google_gwt_core_client_JavaScriptObject_2Lcom_google_gwt_core_client_JavaScriptObject_2Lcom_google_gwt_core_client_JavaScriptObject_2V(109, 11, $intern_27, com_google_gwt_useragent_client_UserAgentAsserter$UserAgentAssertionError_UserAgentAsserter$UserAgentAssertionError__Ljava_lang_String_2Ljava_lang_String_2V);
var com_google_gwt_lang_ClassLiteralHolder_Lcom_1google_1gwt_1useragent_1client_1UserAgentAsserter$UserAgentAssertionError_12_1classLit = java_lang_Class_createForClass__Ljava_lang_String_2Ljava_lang_String_2Lcom_google_gwt_core_client_JavaScriptObject_2Ljava_lang_Class_2Ljava_lang_Class_2('com.google.gwt.useragent.client', 'UserAgentAsserter/UserAgentAssertionError', 109);
function com_google_gwt_useragent_client_UserAgentImplIe8_$getRuntimeValue__Lcom_google_gwt_useragent_client_UserAgentImplIe8_2Ljava_lang_String_2(){
  var ua = navigator.userAgent.toLowerCase();
  var docMode = $doc.documentMode;
  if (function(){
    return ua.indexOf('webkit') != -1;
  }
  ())
    return 'safari';
  if (function(){
    return ua.indexOf('msie') != -1 && docMode >= 10 && docMode < 11;
  }
  ())
    return 'ie10';
  if (function(){
    return ua.indexOf('msie') != -1 && docMode >= 9 && docMode < 11;
  }
  ())
    return 'ie9';
  if (function(){
    return ua.indexOf('msie') != -1 && docMode >= 8 && docMode < 11;
  }
  ())
    return 'ie8';
  if (function(){
    return ua.indexOf('gecko') != -1 || docMode >= 11;
  }
  ())
    return 'gecko1_8';
  return 'unknown';
}

function com_google_web_bindery_event_shared_SimpleEventBus$1_SimpleEventBus$1__Lcom_google_web_bindery_event_shared_SimpleEventBus_2V(){
}

com_google_gwt_lang_Runtime_defineClass__Lcom_google_gwt_core_client_JavaScriptObject_2Lcom_google_gwt_core_client_JavaScriptObject_2Lcom_google_gwt_core_client_JavaScriptObject_2V(149, 1, {}, com_google_web_bindery_event_shared_SimpleEventBus$1_SimpleEventBus$1__Lcom_google_web_bindery_event_shared_SimpleEventBus_2V);
var com_google_gwt_lang_ClassLiteralHolder_Lcom_1google_1web_1bindery_1event_1shared_1SimpleEventBus$1_12_1classLit = java_lang_Class_createForClass__Ljava_lang_String_2Ljava_lang_String_2Lcom_google_gwt_core_client_JavaScriptObject_2Ljava_lang_Class_2Ljava_lang_Class_2($intern_33, 'SimpleEventBus/1', 149);
function com_google_web_bindery_event_shared_SimpleEventBus$2_SimpleEventBus$2__Lcom_google_web_bindery_event_shared_SimpleEventBus_2V(this$0, val$type, val$handler){
  this.com_google_web_bindery_event_shared_SimpleEventBus$2_this$01 = this$0;
  this.com_google_web_bindery_event_shared_SimpleEventBus$2_val$type2 = val$type;
  this.com_google_web_bindery_event_shared_SimpleEventBus$2_val$source3 = null;
  this.com_google_web_bindery_event_shared_SimpleEventBus$2_val$handler4 = val$handler;
}

com_google_gwt_lang_Runtime_defineClass__Lcom_google_gwt_core_client_JavaScriptObject_2Lcom_google_gwt_core_client_JavaScriptObject_2Lcom_google_gwt_core_client_JavaScriptObject_2V(150, 1, {236:1}, com_google_web_bindery_event_shared_SimpleEventBus$2_SimpleEventBus$2__Lcom_google_web_bindery_event_shared_SimpleEventBus_2V);
var com_google_gwt_lang_ClassLiteralHolder_Lcom_1google_1web_1bindery_1event_1shared_1SimpleEventBus$2_12_1classLit = java_lang_Class_createForClass__Ljava_lang_String_2Ljava_lang_String_2Lcom_google_gwt_core_client_JavaScriptObject_2Ljava_lang_Class_2Ljava_lang_Class_2($intern_33, 'SimpleEventBus/2', 150);
function java_lang_AbstractStringBuilder_AbstractStringBuilder__Ljava_lang_String_2V(string){
  this.java_lang_AbstractStringBuilder_string = string;
}

com_google_gwt_lang_Runtime_defineClass__Lcom_google_gwt_core_client_JavaScriptObject_2Lcom_google_gwt_core_client_JavaScriptObject_2Lcom_google_gwt_core_client_JavaScriptObject_2V(86, 1, {110:1});
_.toString__Ljava_lang_String_2 = function java_lang_AbstractStringBuilder_toString__Ljava_lang_String_2(){
  return this.java_lang_AbstractStringBuilder_string;
}
;
var com_google_gwt_lang_ClassLiteralHolder_Ljava_1lang_1AbstractStringBuilder_12_1classLit = java_lang_Class_createForClass__Ljava_lang_String_2Ljava_lang_String_2Lcom_google_gwt_core_client_JavaScriptObject_2Ljava_lang_Class_2Ljava_lang_Class_2($intern_2, 'AbstractStringBuilder', 86);
function java_lang_ArrayStoreException_ArrayStoreException__V(){
  java_lang_RuntimeException_RuntimeException__V.call(this);
}

com_google_gwt_lang_Runtime_defineClass__Lcom_google_gwt_core_client_JavaScriptObject_2Lcom_google_gwt_core_client_JavaScriptObject_2Lcom_google_gwt_core_client_JavaScriptObject_2V(116, 10, $intern_27, java_lang_ArrayStoreException_ArrayStoreException__V);
var com_google_gwt_lang_ClassLiteralHolder_Ljava_1lang_1ArrayStoreException_12_1classLit = java_lang_Class_createForClass__Ljava_lang_String_2Ljava_lang_String_2Lcom_google_gwt_core_client_JavaScriptObject_2Ljava_lang_Class_2Ljava_lang_Class_2($intern_2, 'ArrayStoreException', 116);
function java_lang_Boolean_$clinit__V(){
  java_lang_Boolean_$clinit__V = com_google_gwt_lang_Runtime_emptyMethod__V;
}

com_google_gwt_lang_Cast_booleanCastMap = $intern_4;
var com_google_gwt_lang_ClassLiteralHolder_Ljava_1lang_1Boolean_12_1classLit = java_lang_Class_createForClass__Ljava_lang_String_2Ljava_lang_String_2Lcom_google_gwt_core_client_JavaScriptObject_2Ljava_lang_Class_2Ljava_lang_Class_2($intern_2, 'Boolean', 218);
function java_lang_ClassCastException_ClassCastException__V(){
  java_lang_RuntimeException_RuntimeException__V.call(this);
}

com_google_gwt_lang_Runtime_defineClass__Lcom_google_gwt_core_client_JavaScriptObject_2Lcom_google_gwt_core_client_JavaScriptObject_2Lcom_google_gwt_core_client_JavaScriptObject_2V(111, 10, $intern_27, java_lang_ClassCastException_ClassCastException__V);
var com_google_gwt_lang_ClassLiteralHolder_Ljava_1lang_1ClassCastException_12_1classLit = java_lang_Class_createForClass__Ljava_lang_String_2Ljava_lang_String_2Lcom_google_gwt_core_client_JavaScriptObject_2Ljava_lang_Class_2Ljava_lang_Class_2($intern_2, 'ClassCastException', 111);
function java_lang_IllegalArgumentException_IllegalArgumentException__Ljava_lang_String_2V(message){
  java_lang_RuntimeException_RuntimeException__Ljava_lang_String_2V.call(this, message);
}

com_google_gwt_lang_Runtime_defineClass__Lcom_google_gwt_core_client_JavaScriptObject_2Lcom_google_gwt_core_client_JavaScriptObject_2Lcom_google_gwt_core_client_JavaScriptObject_2V(69, 10, $intern_27, java_lang_IllegalArgumentException_IllegalArgumentException__Ljava_lang_String_2V);
var com_google_gwt_lang_ClassLiteralHolder_Ljava_1lang_1IllegalArgumentException_12_1classLit = java_lang_Class_createForClass__Ljava_lang_String_2Ljava_lang_String_2Lcom_google_gwt_core_client_JavaScriptObject_2Ljava_lang_Class_2Ljava_lang_Class_2($intern_2, 'IllegalArgumentException', 69);
function java_lang_IllegalStateException_IllegalStateException__V(){
  java_lang_RuntimeException_RuntimeException__V.call(this);
}

function java_lang_IllegalStateException_IllegalStateException__Ljava_lang_String_2V(s){
  java_lang_RuntimeException_RuntimeException__Ljava_lang_String_2V.call(this, s);
}

com_google_gwt_lang_Runtime_defineClass__Lcom_google_gwt_core_client_JavaScriptObject_2Lcom_google_gwt_core_client_JavaScriptObject_2Lcom_google_gwt_core_client_JavaScriptObject_2V(21, 10, $intern_27, java_lang_IllegalStateException_IllegalStateException__V, java_lang_IllegalStateException_IllegalStateException__Ljava_lang_String_2V);
var com_google_gwt_lang_ClassLiteralHolder_Ljava_1lang_1IllegalStateException_12_1classLit = java_lang_Class_createForClass__Ljava_lang_String_2Ljava_lang_String_2Lcom_google_gwt_core_client_JavaScriptObject_2Ljava_lang_Class_2Ljava_lang_Class_2($intern_2, 'IllegalStateException', 21);
function java_lang_IndexOutOfBoundsException_IndexOutOfBoundsException__V(){
  java_lang_RuntimeException_RuntimeException__V.call(this);
}

function java_lang_IndexOutOfBoundsException_IndexOutOfBoundsException__Ljava_lang_String_2V(message){
  java_lang_RuntimeException_RuntimeException__Ljava_lang_String_2V.call(this, message);
}

com_google_gwt_lang_Runtime_defineClass__Lcom_google_gwt_core_client_JavaScriptObject_2Lcom_google_gwt_core_client_JavaScriptObject_2Lcom_google_gwt_core_client_JavaScriptObject_2V(29, 10, $intern_27, java_lang_IndexOutOfBoundsException_IndexOutOfBoundsException__V, java_lang_IndexOutOfBoundsException_IndexOutOfBoundsException__Ljava_lang_String_2V);
var com_google_gwt_lang_ClassLiteralHolder_Ljava_1lang_1IndexOutOfBoundsException_12_1classLit = java_lang_Class_createForClass__Ljava_lang_String_2Ljava_lang_String_2Lcom_google_gwt_core_client_JavaScriptObject_2Ljava_lang_Class_2Ljava_lang_Class_2($intern_2, 'IndexOutOfBoundsException', 29);
function java_lang_NullPointerException_NullPointerException__V(){
  java_lang_RuntimeException_RuntimeException__V.call(this);
}

function java_lang_NullPointerException_NullPointerException__Ljava_lang_String_2V(message){
  java_lang_RuntimeException_RuntimeException__Ljava_lang_String_2V.call(this, message);
}

com_google_gwt_lang_Runtime_defineClass__Lcom_google_gwt_core_client_JavaScriptObject_2Lcom_google_gwt_core_client_JavaScriptObject_2Lcom_google_gwt_core_client_JavaScriptObject_2V(30, 70, $intern_27, java_lang_NullPointerException_NullPointerException__V, java_lang_NullPointerException_NullPointerException__Ljava_lang_String_2V);
_.package_private$java_lang$createError__Ljava_lang_String_2Ljava_lang_Object_2 = function java_lang_NullPointerException_createError__Ljava_lang_String_2Ljava_lang_Object_2(msg){
  return new $wnd.TypeError(msg);
}
;
var com_google_gwt_lang_ClassLiteralHolder_Ljava_1lang_1NullPointerException_12_1classLit = java_lang_Class_createForClass__Ljava_lang_String_2Ljava_lang_String_2Lcom_google_gwt_core_client_JavaScriptObject_2Ljava_lang_Class_2Ljava_lang_Class_2($intern_2, 'NullPointerException', 30);
function java_lang_String_$charAt__Ljava_lang_String_2IC(this$static, index_0){
  return this$static.charCodeAt(index_0);
}

function java_lang_String_$equals__Ljava_lang_String_2Ljava_lang_Object_2Z(this$static, other){
  return javaemul_internal_InternalPreconditions_checkCriticalNotNull__Ljava_lang_Object_2Ljava_lang_Object_2(this$static) , this$static === other;
}

function java_lang_String_$equalsIgnoreCase__Ljava_lang_String_2Ljava_lang_String_2Z(this$static, other){
  javaemul_internal_InternalPreconditions_checkCriticalNotNull__Ljava_lang_Object_2Ljava_lang_Object_2(this$static);
  if (other == null) {
    return false;
  }
  if (java_lang_String_$equals__Ljava_lang_String_2Ljava_lang_Object_2Z(this$static, other)) {
    return true;
  }
  return this$static.length == other.length && java_lang_String_$equals__Ljava_lang_String_2Ljava_lang_Object_2Z(this$static.toLowerCase(), other.toLowerCase());
}

function java_lang_String_$substring__Ljava_lang_String_2ILjava_lang_String_2(this$static, beginIndex){
  return this$static.substr(beginIndex, this$static.length - beginIndex);
}

function java_lang_String_$trim__Ljava_lang_String_2Ljava_lang_String_2(this$static){
  var end, length_0, start_0;
  length_0 = this$static.length;
  start_0 = 0;
  while (start_0 < length_0 && this$static.charCodeAt(start_0) <= 32) {
    ++start_0;
  }
  end = length_0;
  while (end > start_0 && this$static.charCodeAt(end - 1) <= 32) {
    --end;
  }
  return start_0 > 0 || end < length_0?this$static.substr(start_0, end - start_0):this$static;
}

com_google_gwt_lang_Cast_stringCastMap = {3:1, 110:1, 8:1, 2:1};
var com_google_gwt_lang_ClassLiteralHolder_Ljava_1lang_1String_12_1classLit = java_lang_Class_createForClass__Ljava_lang_String_2Ljava_lang_String_2Lcom_google_gwt_core_client_JavaScriptObject_2Ljava_lang_Class_2Ljava_lang_Class_2($intern_2, 'String', 2);
function java_lang_StringBuilder_$append__Ljava_lang_StringBuilder_2Ljava_lang_CharSequence_2Ljava_lang_StringBuilder_2(this$static, x_0){
  this$static.java_lang_AbstractStringBuilder_string += '' + x_0;
  return this$static;
}

function java_lang_StringBuilder_$append__Ljava_lang_StringBuilder_2Ljava_lang_String_2Ljava_lang_StringBuilder_2(this$static, x_0){
  this$static.java_lang_AbstractStringBuilder_string += '' + x_0;
  return this$static;
}

function java_lang_StringBuilder_StringBuilder__V(){
  java_lang_AbstractStringBuilder_AbstractStringBuilder__Ljava_lang_String_2V.call(this, '');
}

function java_lang_StringBuilder_StringBuilder__Ljava_lang_String_2V(s){
  java_lang_AbstractStringBuilder_AbstractStringBuilder__Ljava_lang_String_2V.call(this, (javaemul_internal_InternalPreconditions_checkCriticalNotNull__Ljava_lang_Object_2Ljava_lang_Object_2(s) , s));
}

com_google_gwt_lang_Runtime_defineClass__Lcom_google_gwt_core_client_JavaScriptObject_2Lcom_google_gwt_core_client_JavaScriptObject_2Lcom_google_gwt_core_client_JavaScriptObject_2V(14, 86, {110:1}, java_lang_StringBuilder_StringBuilder__V, java_lang_StringBuilder_StringBuilder__Ljava_lang_String_2V);
var com_google_gwt_lang_ClassLiteralHolder_Ljava_1lang_1StringBuilder_12_1classLit = java_lang_Class_createForClass__Ljava_lang_String_2Ljava_lang_String_2Lcom_google_gwt_core_client_JavaScriptObject_2Ljava_lang_Class_2Ljava_lang_Class_2($intern_2, 'StringBuilder', 14);
com_google_gwt_lang_Runtime_defineClass__Lcom_google_gwt_core_client_JavaScriptObject_2Lcom_google_gwt_core_client_JavaScriptObject_2Lcom_google_gwt_core_client_JavaScriptObject_2V(252, 1, {});
function java_lang_UnsupportedOperationException_UnsupportedOperationException__V(){
  java_lang_RuntimeException_RuntimeException__V.call(this);
}

function java_lang_UnsupportedOperationException_UnsupportedOperationException__Ljava_lang_String_2V(message){
  java_lang_RuntimeException_RuntimeException__Ljava_lang_String_2V.call(this, message);
}

com_google_gwt_lang_Runtime_defineClass__Lcom_google_gwt_core_client_JavaScriptObject_2Lcom_google_gwt_core_client_JavaScriptObject_2Lcom_google_gwt_core_client_JavaScriptObject_2V(71, 10, $intern_27, java_lang_UnsupportedOperationException_UnsupportedOperationException__V, java_lang_UnsupportedOperationException_UnsupportedOperationException__Ljava_lang_String_2V);
var com_google_gwt_lang_ClassLiteralHolder_Ljava_1lang_1UnsupportedOperationException_12_1classLit = java_lang_Class_createForClass__Ljava_lang_String_2Ljava_lang_String_2Lcom_google_gwt_core_client_JavaScriptObject_2Ljava_lang_Class_2Ljava_lang_Class_2($intern_2, 'UnsupportedOperationException', 71);
function java_util_AbstractCollection_$advanceToFind__Ljava_util_AbstractCollection_2Ljava_lang_Object_2ZZ(this$static, o){
  var e, iter;
  for (iter = this$static.iterator__Ljava_util_Iterator_2(); iter.hasNext__Z();) {
    e = iter.next__Ljava_lang_Object_2();
    if (com_google_gwt_lang_Cast_maskUndefined__Ljava_lang_Object_2Ljava_lang_Object_2(o) === com_google_gwt_lang_Cast_maskUndefined__Ljava_lang_Object_2Ljava_lang_Object_2(e) || o != null && java_lang_Object_equals_1Ljava_1lang_1Object_1_1Z_1_1devirtual$__Ljava_lang_Object_2Ljava_lang_Object_2Z(o, e)) {
      return true;
    }
  }
  return false;
}

function java_util_AbstractCollection_$containsAll__Ljava_util_AbstractCollection_2Ljava_util_Collection_2Z(this$static, c){
  var e, e$iterator;
  javaemul_internal_InternalPreconditions_checkCriticalNotNull__Ljava_lang_Object_2Ljava_lang_Object_2(c);
  for (e$iterator = c.iterator__Ljava_util_Iterator_2(); e$iterator.hasNext__Z();) {
    e = e$iterator.next__Ljava_lang_Object_2();
    if (!this$static.contains__Ljava_lang_Object_2Z(e)) {
      return false;
    }
  }
  return true;
}

com_google_gwt_lang_Runtime_defineClass__Lcom_google_gwt_core_client_JavaScriptObject_2Lcom_google_gwt_core_client_JavaScriptObject_2Lcom_google_gwt_core_client_JavaScriptObject_2V(225, 1, {});
_.add__Ljava_lang_Object_2Z = function java_util_AbstractCollection_add__Ljava_lang_Object_2Z(o){
  throw com_google_gwt_lang_Exceptions_toJs__Ljava_lang_Object_2Ljava_lang_Object_2(new java_lang_UnsupportedOperationException_UnsupportedOperationException__Ljava_lang_String_2V('Add not supported on this collection'));
}
;
_.contains__Ljava_lang_Object_2Z = function java_util_AbstractCollection_contains__Ljava_lang_Object_2Z(o){
  return java_util_AbstractCollection_$advanceToFind__Ljava_util_AbstractCollection_2Ljava_lang_Object_2ZZ(this, o);
}
;
_.isEmpty__Z = function java_util_AbstractCollection_isEmpty__Z(){
  return this.size__I() == 0;
}
;
_.toString__Ljava_lang_String_2 = function java_util_AbstractCollection_toString__Ljava_lang_String_2(){
  var e, e$iterator, joiner;
  joiner = new java_util_StringJoiner_StringJoiner__Ljava_lang_CharSequence_2Ljava_lang_CharSequence_2Ljava_lang_CharSequence_2V('[', ']');
  for (e$iterator = this.iterator__Ljava_util_Iterator_2(); e$iterator.hasNext__Z();) {
    e = e$iterator.next__Ljava_lang_Object_2();
    java_util_StringJoiner_$add__Ljava_util_StringJoiner_2Ljava_lang_CharSequence_2Ljava_util_StringJoiner_2(joiner, e === this?'(this Collection)':e == null?$intern_29:com_google_gwt_lang_Runtime_toString__Ljava_lang_Object_2Ljava_lang_String_2(e));
  }
  return !joiner.java_util_StringJoiner_builder?joiner.java_util_StringJoiner_emptyValue:joiner.java_util_StringJoiner_suffix.length == 0?joiner.java_util_StringJoiner_builder.java_lang_AbstractStringBuilder_string:joiner.java_util_StringJoiner_builder.java_lang_AbstractStringBuilder_string + ('' + joiner.java_util_StringJoiner_suffix);
}
;
var com_google_gwt_lang_ClassLiteralHolder_Ljava_1util_1AbstractCollection_12_1classLit = java_lang_Class_createForClass__Ljava_lang_String_2Ljava_lang_String_2Lcom_google_gwt_core_client_JavaScriptObject_2Ljava_lang_Class_2Ljava_lang_Class_2($intern_52, 'AbstractCollection', 225);
function java_util_AbstractMap_$containsEntry__Ljava_util_AbstractMap_2Ljava_util_Map$Entry_2Z(this$static, entry){
  var key, ourValue, value_0;
  key = entry.getKey__Ljava_lang_Object_2();
  value_0 = entry.getValue__Ljava_lang_Object_2();
  ourValue = com_google_gwt_lang_Cast_instanceOfString__Ljava_lang_Object_2Z(key)?java_util_AbstractHashMap_$getStringValue__Ljava_util_AbstractHashMap_2Ljava_lang_String_2Ljava_lang_Object_2(this$static, key):java_util_AbstractMap_getEntryValueOrNull__Ljava_util_Map$Entry_2Ljava_lang_Object_2(java_util_InternalHashCodeMap_$getEntry__Ljava_util_InternalHashCodeMap_2Ljava_lang_Object_2Ljava_util_Map$Entry_2(this$static.java_util_AbstractHashMap_hashCodeMap, key));
  if (!(com_google_gwt_lang_Cast_maskUndefined__Ljava_lang_Object_2Ljava_lang_Object_2(value_0) === com_google_gwt_lang_Cast_maskUndefined__Ljava_lang_Object_2Ljava_lang_Object_2(ourValue) || value_0 != null && java_lang_Object_equals_1Ljava_1lang_1Object_1_1Z_1_1devirtual$__Ljava_lang_Object_2Ljava_lang_Object_2Z(value_0, ourValue))) {
    return false;
  }
  if (ourValue == null && !(com_google_gwt_lang_Cast_instanceOfString__Ljava_lang_Object_2Z(key)?java_util_AbstractHashMap_$hasStringValue__Ljava_util_AbstractHashMap_2Ljava_lang_String_2Z(this$static, key):!!java_util_InternalHashCodeMap_$getEntry__Ljava_util_InternalHashCodeMap_2Ljava_lang_Object_2Ljava_util_Map$Entry_2(this$static.java_util_AbstractHashMap_hashCodeMap, key))) {
    return false;
  }
  return true;
}

function java_util_AbstractMap_$toString__Ljava_util_AbstractMap_2Ljava_lang_Object_2Ljava_lang_String_2(this$static, o){
  return o === this$static?'(this Map)':o == null?$intern_29:com_google_gwt_lang_Runtime_toString__Ljava_lang_Object_2Ljava_lang_String_2(o);
}

function java_util_AbstractMap_getEntryValueOrNull__Ljava_util_Map$Entry_2Ljava_lang_Object_2(entry){
  return !entry?null:entry.getValue__Ljava_lang_Object_2();
}

com_google_gwt_lang_Runtime_defineClass__Lcom_google_gwt_core_client_JavaScriptObject_2Lcom_google_gwt_core_client_JavaScriptObject_2Lcom_google_gwt_core_client_JavaScriptObject_2V(224, 1, {65:1});
_.equals__Ljava_lang_Object_2Z = function java_util_AbstractMap_equals__Ljava_lang_Object_2Z(obj){
  var entry, entry$iterator, otherMap;
  if (obj === this) {
    return true;
  }
  if (!com_google_gwt_lang_Cast_instanceOf__Ljava_lang_Object_2Lcom_google_gwt_core_client_JavaScriptObject_2Z(obj, 18)) {
    return false;
  }
  otherMap = com_google_gwt_lang_Cast_castTo__Ljava_lang_Object_2Lcom_google_gwt_core_client_JavaScriptObject_2Ljava_lang_Object_2(obj, 65);
  if (this.java_util_AbstractHashMap_hashCodeMap.java_util_InternalHashCodeMap_size + this.java_util_AbstractHashMap_stringMap.java_util_InternalStringMap_size != otherMap.java_util_AbstractHashMap_hashCodeMap.java_util_InternalHashCodeMap_size + otherMap.java_util_AbstractHashMap_stringMap.java_util_InternalStringMap_size) {
    return false;
  }
  for (entry$iterator = new java_util_AbstractHashMap$EntrySetIterator_AbstractHashMap$EntrySetIterator__Ljava_util_AbstractHashMap_2V((new java_util_AbstractHashMap$EntrySet_AbstractHashMap$EntrySet__Ljava_util_AbstractHashMap_2V(otherMap)).java_util_AbstractHashMap$EntrySet_this$01); entry$iterator.java_util_AbstractHashMap$EntrySetIterator_hasNext;) {
    entry = java_util_AbstractHashMap$EntrySetIterator_$next__Ljava_util_AbstractHashMap$EntrySetIterator_2Ljava_util_Map$Entry_2(entry$iterator);
    if (!java_util_AbstractMap_$containsEntry__Ljava_util_AbstractMap_2Ljava_util_Map$Entry_2Z(this, entry)) {
      return false;
    }
  }
  return true;
}
;
_.hashCode__I = function java_util_AbstractMap_hashCode__I(){
  return java_util_Collections_hashCode__Ljava_lang_Iterable_2I(new java_util_AbstractHashMap$EntrySet_AbstractHashMap$EntrySet__Ljava_util_AbstractHashMap_2V(this));
}
;
_.toString__Ljava_lang_String_2 = function java_util_AbstractMap_toString__Ljava_lang_String_2(){
  var entry, entry$iterator, joiner;
  joiner = new java_util_StringJoiner_StringJoiner__Ljava_lang_CharSequence_2Ljava_lang_CharSequence_2Ljava_lang_CharSequence_2V('{', '}');
  for (entry$iterator = new java_util_AbstractHashMap$EntrySetIterator_AbstractHashMap$EntrySetIterator__Ljava_util_AbstractHashMap_2V((new java_util_AbstractHashMap$EntrySet_AbstractHashMap$EntrySet__Ljava_util_AbstractHashMap_2V(this)).java_util_AbstractHashMap$EntrySet_this$01); entry$iterator.java_util_AbstractHashMap$EntrySetIterator_hasNext;) {
    entry = java_util_AbstractHashMap$EntrySetIterator_$next__Ljava_util_AbstractHashMap$EntrySetIterator_2Ljava_util_Map$Entry_2(entry$iterator);
    java_util_StringJoiner_$add__Ljava_util_StringJoiner_2Ljava_lang_CharSequence_2Ljava_util_StringJoiner_2(joiner, java_util_AbstractMap_$toString__Ljava_util_AbstractMap_2Ljava_lang_Object_2Ljava_lang_String_2(this, entry.getKey__Ljava_lang_Object_2()) + '=' + java_util_AbstractMap_$toString__Ljava_util_AbstractMap_2Ljava_lang_Object_2Ljava_lang_String_2(this, entry.getValue__Ljava_lang_Object_2()));
  }
  return !joiner.java_util_StringJoiner_builder?joiner.java_util_StringJoiner_emptyValue:joiner.java_util_StringJoiner_suffix.length == 0?joiner.java_util_StringJoiner_builder.java_lang_AbstractStringBuilder_string:joiner.java_util_StringJoiner_builder.java_lang_AbstractStringBuilder_string + ('' + joiner.java_util_StringJoiner_suffix);
}
;
var com_google_gwt_lang_ClassLiteralHolder_Ljava_1util_1AbstractMap_12_1classLit = java_lang_Class_createForClass__Ljava_lang_String_2Ljava_lang_String_2Lcom_google_gwt_core_client_JavaScriptObject_2Ljava_lang_Class_2Ljava_lang_Class_2($intern_52, 'AbstractMap', 224);
function java_util_AbstractHashMap_$containsKey__Ljava_util_AbstractHashMap_2Ljava_lang_Object_2Z(this$static, key){
  return com_google_gwt_lang_Cast_instanceOfString__Ljava_lang_Object_2Z(key)?java_util_AbstractHashMap_$hasStringValue__Ljava_util_AbstractHashMap_2Ljava_lang_String_2Z(this$static, key):!!java_util_InternalHashCodeMap_$getEntry__Ljava_util_InternalHashCodeMap_2Ljava_lang_Object_2Ljava_util_Map$Entry_2(this$static.java_util_AbstractHashMap_hashCodeMap, key);
}

function java_util_AbstractHashMap_$get__Ljava_util_AbstractHashMap_2Ljava_lang_Object_2Ljava_lang_Object_2(this$static, key){
  return com_google_gwt_lang_Cast_instanceOfString__Ljava_lang_Object_2Z(key)?java_util_AbstractHashMap_$getStringValue__Ljava_util_AbstractHashMap_2Ljava_lang_String_2Ljava_lang_Object_2(this$static, key):java_util_AbstractMap_getEntryValueOrNull__Ljava_util_Map$Entry_2Ljava_lang_Object_2(java_util_InternalHashCodeMap_$getEntry__Ljava_util_InternalHashCodeMap_2Ljava_lang_Object_2Ljava_util_Map$Entry_2(this$static.java_util_AbstractHashMap_hashCodeMap, key));
}

function java_util_AbstractHashMap_$getStringValue__Ljava_util_AbstractHashMap_2Ljava_lang_String_2Ljava_lang_Object_2(this$static, key){
  return key == null?java_util_AbstractMap_getEntryValueOrNull__Ljava_util_Map$Entry_2Ljava_lang_Object_2(java_util_InternalHashCodeMap_$getEntry__Ljava_util_InternalHashCodeMap_2Ljava_lang_Object_2Ljava_util_Map$Entry_2(this$static.java_util_AbstractHashMap_hashCodeMap, null)):java_util_InternalStringMap_$get__Ljava_util_InternalStringMap_2Ljava_lang_String_2Ljava_lang_Object_2(this$static.java_util_AbstractHashMap_stringMap, key);
}

function java_util_AbstractHashMap_$hasStringValue__Ljava_util_AbstractHashMap_2Ljava_lang_String_2Z(this$static, key){
  return key == null?!!java_util_InternalHashCodeMap_$getEntry__Ljava_util_InternalHashCodeMap_2Ljava_lang_Object_2Ljava_util_Map$Entry_2(this$static.java_util_AbstractHashMap_hashCodeMap, null):java_util_InternalStringMap_$contains__Ljava_util_InternalStringMap_2Ljava_lang_String_2Z(this$static.java_util_AbstractHashMap_stringMap, key);
}

function java_util_AbstractHashMap_$put__Ljava_util_AbstractHashMap_2Ljava_lang_Object_2Ljava_lang_Object_2Ljava_lang_Object_2(this$static, key, value_0){
  return com_google_gwt_lang_Cast_instanceOfString__Ljava_lang_Object_2Z(key)?java_util_AbstractHashMap_$putStringValue__Ljava_util_AbstractHashMap_2Ljava_lang_String_2Ljava_lang_Object_2Ljava_lang_Object_2(this$static, key, value_0):java_util_InternalHashCodeMap_$put__Ljava_util_InternalHashCodeMap_2Ljava_lang_Object_2Ljava_lang_Object_2Ljava_lang_Object_2(this$static.java_util_AbstractHashMap_hashCodeMap, key, value_0);
}

function java_util_AbstractHashMap_$putStringValue__Ljava_util_AbstractHashMap_2Ljava_lang_String_2Ljava_lang_Object_2Ljava_lang_Object_2(this$static, key, value_0){
  return key == null?java_util_InternalHashCodeMap_$put__Ljava_util_InternalHashCodeMap_2Ljava_lang_Object_2Ljava_lang_Object_2Ljava_lang_Object_2(this$static.java_util_AbstractHashMap_hashCodeMap, null, value_0):java_util_InternalStringMap_$put__Ljava_util_InternalStringMap_2Ljava_lang_String_2Ljava_lang_Object_2Ljava_lang_Object_2(this$static.java_util_AbstractHashMap_stringMap, key, value_0);
}

function java_util_AbstractHashMap_$remove__Ljava_util_AbstractHashMap_2Ljava_lang_Object_2Ljava_lang_Object_2(this$static, key){
  return java_util_InternalHashCodeMap_$remove__Ljava_util_InternalHashCodeMap_2Ljava_lang_Object_2Ljava_lang_Object_2(this$static.java_util_AbstractHashMap_hashCodeMap, key);
}

function java_util_AbstractHashMap_$reset__Ljava_util_AbstractHashMap_2V(this$static){
  var modCount;
  this$static.java_util_AbstractHashMap_hashCodeMap = new java_util_InternalHashCodeMap_InternalHashCodeMap__Ljava_util_AbstractHashMap_2V(this$static);
  this$static.java_util_AbstractHashMap_stringMap = new java_util_InternalStringMap_InternalStringMap__Ljava_util_AbstractHashMap_2V(this$static);
  modCount = this$static[$intern_53] | 0;
  this$static[$intern_53] = modCount + 1;
}

function java_util_AbstractHashMap_$size__Ljava_util_AbstractHashMap_2I(this$static){
  return this$static.java_util_AbstractHashMap_hashCodeMap.java_util_InternalHashCodeMap_size + this$static.java_util_AbstractHashMap_stringMap.java_util_InternalStringMap_size;
}

com_google_gwt_lang_Runtime_defineClass__Lcom_google_gwt_core_client_JavaScriptObject_2Lcom_google_gwt_core_client_JavaScriptObject_2Lcom_google_gwt_core_client_JavaScriptObject_2V(135, 224, {65:1});
var com_google_gwt_lang_ClassLiteralHolder_Ljava_1util_1AbstractHashMap_12_1classLit = java_lang_Class_createForClass__Ljava_lang_String_2Ljava_lang_String_2Lcom_google_gwt_core_client_JavaScriptObject_2Ljava_lang_Class_2Ljava_lang_Class_2($intern_52, 'AbstractHashMap', 135);
com_google_gwt_lang_Runtime_defineClass__Lcom_google_gwt_core_client_JavaScriptObject_2Lcom_google_gwt_core_client_JavaScriptObject_2Lcom_google_gwt_core_client_JavaScriptObject_2V(226, 225, {66:1});
_.equals__Ljava_lang_Object_2Z = function java_util_AbstractSet_equals__Ljava_lang_Object_2Z(o){
  var other;
  if (o === this) {
    return true;
  }
  if (!com_google_gwt_lang_Cast_instanceOf__Ljava_lang_Object_2Lcom_google_gwt_core_client_JavaScriptObject_2Z(o, 66)) {
    return false;
  }
  other = com_google_gwt_lang_Cast_castTo__Ljava_lang_Object_2Lcom_google_gwt_core_client_JavaScriptObject_2Ljava_lang_Object_2(o, 66);
  if (other.size__I() != this.size__I()) {
    return false;
  }
  return java_util_AbstractCollection_$containsAll__Ljava_util_AbstractCollection_2Ljava_util_Collection_2Z(this, other);
}
;
_.hashCode__I = function java_util_AbstractSet_hashCode__I(){
  return java_util_Collections_hashCode__Ljava_lang_Iterable_2I(this);
}
;
var com_google_gwt_lang_ClassLiteralHolder_Ljava_1util_1AbstractSet_12_1classLit = java_lang_Class_createForClass__Ljava_lang_String_2Ljava_lang_String_2Lcom_google_gwt_core_client_JavaScriptObject_2Ljava_lang_Class_2Ljava_lang_Class_2($intern_52, 'AbstractSet', 226);
function java_util_AbstractHashMap$EntrySet_$contains__Ljava_util_AbstractHashMap$EntrySet_2Ljava_lang_Object_2Z(this$static, o){
  if (com_google_gwt_lang_Cast_instanceOf__Ljava_lang_Object_2Lcom_google_gwt_core_client_JavaScriptObject_2Z(o, 17)) {
    return java_util_AbstractMap_$containsEntry__Ljava_util_AbstractMap_2Ljava_util_Map$Entry_2Z(this$static.java_util_AbstractHashMap$EntrySet_this$01, com_google_gwt_lang_Cast_castTo__Ljava_lang_Object_2Lcom_google_gwt_core_client_JavaScriptObject_2Ljava_lang_Object_2(o, 17));
  }
  return false;
}

function java_util_AbstractHashMap$EntrySet_AbstractHashMap$EntrySet__Ljava_util_AbstractHashMap_2V(this$0){
  this.java_util_AbstractHashMap$EntrySet_this$01 = this$0;
}

com_google_gwt_lang_Runtime_defineClass__Lcom_google_gwt_core_client_JavaScriptObject_2Lcom_google_gwt_core_client_JavaScriptObject_2Lcom_google_gwt_core_client_JavaScriptObject_2V(32, 226, {66:1}, java_util_AbstractHashMap$EntrySet_AbstractHashMap$EntrySet__Ljava_util_AbstractHashMap_2V);
_.contains__Ljava_lang_Object_2Z = function java_util_AbstractHashMap$EntrySet_contains__Ljava_lang_Object_2Z(o){
  return java_util_AbstractHashMap$EntrySet_$contains__Ljava_util_AbstractHashMap$EntrySet_2Ljava_lang_Object_2Z(this, o);
}
;
_.iterator__Ljava_util_Iterator_2 = function java_util_AbstractHashMap$EntrySet_iterator__Ljava_util_Iterator_2(){
  return new java_util_AbstractHashMap$EntrySetIterator_AbstractHashMap$EntrySetIterator__Ljava_util_AbstractHashMap_2V(this.java_util_AbstractHashMap$EntrySet_this$01);
}
;
_.size__I = function java_util_AbstractHashMap$EntrySet_size__I(){
  return java_util_AbstractHashMap_$size__Ljava_util_AbstractHashMap_2I(this.java_util_AbstractHashMap$EntrySet_this$01);
}
;
var com_google_gwt_lang_ClassLiteralHolder_Ljava_1util_1AbstractHashMap$EntrySet_12_1classLit = java_lang_Class_createForClass__Ljava_lang_String_2Ljava_lang_String_2Lcom_google_gwt_core_client_JavaScriptObject_2Ljava_lang_Class_2Ljava_lang_Class_2($intern_52, 'AbstractHashMap/EntrySet', 32);
function java_util_AbstractHashMap$EntrySetIterator_$computeHasNext__Ljava_util_AbstractHashMap$EntrySetIterator_2Z(this$static){
  if (this$static.java_util_AbstractHashMap$EntrySetIterator_current.hasNext__Z()) {
    return true;
  }
  if (this$static.java_util_AbstractHashMap$EntrySetIterator_current != this$static.java_util_AbstractHashMap$EntrySetIterator_stringMapEntries) {
    return false;
  }
  this$static.java_util_AbstractHashMap$EntrySetIterator_current = new java_util_InternalHashCodeMap$1_InternalHashCodeMap$1__Ljava_util_InternalHashCodeMap_2V(this$static.java_util_AbstractHashMap$EntrySetIterator_this$01.java_util_AbstractHashMap_hashCodeMap);
  return this$static.java_util_AbstractHashMap$EntrySetIterator_current.hasNext__Z();
}

function java_util_AbstractHashMap$EntrySetIterator_$next__Ljava_util_AbstractHashMap$EntrySetIterator_2Ljava_util_Map$Entry_2(this$static){
  var rv;
  java_util_ConcurrentModificationDetector_checkStructuralChange__Ljava_lang_Object_2Ljava_util_Iterator_2V(this$static.java_util_AbstractHashMap$EntrySetIterator_this$01, this$static);
  javaemul_internal_InternalPreconditions_checkCriticalElement__ZV(this$static.java_util_AbstractHashMap$EntrySetIterator_hasNext);
  rv = com_google_gwt_lang_Cast_castTo__Ljava_lang_Object_2Lcom_google_gwt_core_client_JavaScriptObject_2Ljava_lang_Object_2(this$static.java_util_AbstractHashMap$EntrySetIterator_current.next__Ljava_lang_Object_2(), 17);
  this$static.java_util_AbstractHashMap$EntrySetIterator_hasNext = java_util_AbstractHashMap$EntrySetIterator_$computeHasNext__Ljava_util_AbstractHashMap$EntrySetIterator_2Z(this$static);
  return rv;
}

function java_util_AbstractHashMap$EntrySetIterator_AbstractHashMap$EntrySetIterator__Ljava_util_AbstractHashMap_2V(this$0){
  var modCount;
  this.java_util_AbstractHashMap$EntrySetIterator_this$01 = this$0;
  this.java_util_AbstractHashMap$EntrySetIterator_stringMapEntries = new java_util_InternalStringMap$1_InternalStringMap$1__Ljava_util_InternalStringMap_2V(this.java_util_AbstractHashMap$EntrySetIterator_this$01.java_util_AbstractHashMap_stringMap);
  this.java_util_AbstractHashMap$EntrySetIterator_current = this.java_util_AbstractHashMap$EntrySetIterator_stringMapEntries;
  this.java_util_AbstractHashMap$EntrySetIterator_hasNext = java_util_AbstractHashMap$EntrySetIterator_$computeHasNext__Ljava_util_AbstractHashMap$EntrySetIterator_2Z(this);
  modCount = this$0[$intern_53];
  this[$intern_53] = modCount;
}

com_google_gwt_lang_Runtime_defineClass__Lcom_google_gwt_core_client_JavaScriptObject_2Lcom_google_gwt_core_client_JavaScriptObject_2Lcom_google_gwt_core_client_JavaScriptObject_2V(33, 1, {}, java_util_AbstractHashMap$EntrySetIterator_AbstractHashMap$EntrySetIterator__Ljava_util_AbstractHashMap_2V);
_.next__Ljava_lang_Object_2 = function java_util_AbstractHashMap$EntrySetIterator_next__Ljava_lang_Object_2(){
  return java_util_AbstractHashMap$EntrySetIterator_$next__Ljava_util_AbstractHashMap$EntrySetIterator_2Ljava_util_Map$Entry_2(this);
}
;
_.hasNext__Z = function java_util_AbstractHashMap$EntrySetIterator_hasNext__Z(){
  return this.java_util_AbstractHashMap$EntrySetIterator_hasNext;
}
;
_.java_util_AbstractHashMap$EntrySetIterator_hasNext = false;
var com_google_gwt_lang_ClassLiteralHolder_Ljava_1util_1AbstractHashMap$EntrySetIterator_12_1classLit = java_lang_Class_createForClass__Ljava_lang_String_2Ljava_lang_String_2Lcom_google_gwt_core_client_JavaScriptObject_2Ljava_lang_Class_2Ljava_lang_Class_2($intern_52, 'AbstractHashMap/EntrySetIterator', 33);
com_google_gwt_lang_Runtime_defineClass__Lcom_google_gwt_core_client_JavaScriptObject_2Lcom_google_gwt_core_client_JavaScriptObject_2Lcom_google_gwt_core_client_JavaScriptObject_2V(227, 225, {28:1});
_.add__ILjava_lang_Object_2V = function java_util_AbstractList_add__ILjava_lang_Object_2V(index_0, element){
  throw com_google_gwt_lang_Exceptions_toJs__Ljava_lang_Object_2Ljava_lang_Object_2(new java_lang_UnsupportedOperationException_UnsupportedOperationException__Ljava_lang_String_2V('Add not supported on this list'));
}
;
_.add__Ljava_lang_Object_2Z = function java_util_AbstractList_add__Ljava_lang_Object_2Z(obj){
  this.add__ILjava_lang_Object_2V(this.size__I(), obj);
  return true;
}
;
_.equals__Ljava_lang_Object_2Z = function java_util_AbstractList_equals__Ljava_lang_Object_2Z(o){
  var elem, elem$iterator, elemOther, iterOther, other;
  if (o === this) {
    return true;
  }
  if (!com_google_gwt_lang_Cast_instanceOf__Ljava_lang_Object_2Lcom_google_gwt_core_client_JavaScriptObject_2Z(o, 28)) {
    return false;
  }
  other = com_google_gwt_lang_Cast_castTo__Ljava_lang_Object_2Lcom_google_gwt_core_client_JavaScriptObject_2Ljava_lang_Object_2(o, 28);
  if (this.size__I() != other.size__I()) {
    return false;
  }
  iterOther = other.iterator__Ljava_util_Iterator_2();
  for (elem$iterator = this.iterator__Ljava_util_Iterator_2(); elem$iterator.hasNext__Z();) {
    elem = elem$iterator.next__Ljava_lang_Object_2();
    elemOther = iterOther.next__Ljava_lang_Object_2();
    if (!(com_google_gwt_lang_Cast_maskUndefined__Ljava_lang_Object_2Ljava_lang_Object_2(elem) === com_google_gwt_lang_Cast_maskUndefined__Ljava_lang_Object_2Ljava_lang_Object_2(elemOther) || elem != null && java_lang_Object_equals_1Ljava_1lang_1Object_1_1Z_1_1devirtual$__Ljava_lang_Object_2Ljava_lang_Object_2Z(elem, elemOther))) {
      return false;
    }
  }
  return true;
}
;
_.hashCode__I = function java_util_AbstractList_hashCode__I(){
  return java_util_Collections_hashCode__Ljava_util_List_2I(this);
}
;
_.iterator__Ljava_util_Iterator_2 = function java_util_AbstractList_iterator__Ljava_util_Iterator_2(){
  return new java_util_AbstractList$IteratorImpl_AbstractList$IteratorImpl__Ljava_util_AbstractList_2V(this);
}
;
_.listIterator__Ljava_util_ListIterator_2 = function java_util_AbstractList_listIterator__Ljava_util_ListIterator_2(){
  return new java_util_AbstractList$ListIteratorImpl_AbstractList$ListIteratorImpl__Ljava_util_AbstractList_2IV(this, 0);
}
;
_.listIterator__ILjava_util_ListIterator_2 = function java_util_AbstractList_listIterator__ILjava_util_ListIterator_2(from){
  return new java_util_AbstractList$ListIteratorImpl_AbstractList$ListIteratorImpl__Ljava_util_AbstractList_2IV(this, from);
}
;
var com_google_gwt_lang_ClassLiteralHolder_Ljava_1util_1AbstractList_12_1classLit = java_lang_Class_createForClass__Ljava_lang_String_2Ljava_lang_String_2Lcom_google_gwt_core_client_JavaScriptObject_2Ljava_lang_Class_2Ljava_lang_Class_2($intern_52, 'AbstractList', 227);
function java_util_AbstractList$IteratorImpl_AbstractList$IteratorImpl__Ljava_util_AbstractList_2V(this$0){
  this.java_util_AbstractList$IteratorImpl_this$01 = this$0;
}

com_google_gwt_lang_Runtime_defineClass__Lcom_google_gwt_core_client_JavaScriptObject_2Lcom_google_gwt_core_client_JavaScriptObject_2Lcom_google_gwt_core_client_JavaScriptObject_2V(90, 1, {}, java_util_AbstractList$IteratorImpl_AbstractList$IteratorImpl__Ljava_util_AbstractList_2V);
_.hasNext__Z = function java_util_AbstractList$IteratorImpl_hasNext__Z(){
  return this.java_util_AbstractList$IteratorImpl_i < this.java_util_AbstractList$IteratorImpl_this$01.size__I();
}
;
_.next__Ljava_lang_Object_2 = function java_util_AbstractList$IteratorImpl_next__Ljava_lang_Object_2(){
  javaemul_internal_InternalPreconditions_checkCriticalElement__ZV(this.java_util_AbstractList$IteratorImpl_i < this.java_util_AbstractList$IteratorImpl_this$01.size__I());
  return this.java_util_AbstractList$IteratorImpl_this$01.get__ILjava_lang_Object_2(this.java_util_AbstractList$IteratorImpl_i++);
}
;
_.java_util_AbstractList$IteratorImpl_i = 0;
var com_google_gwt_lang_ClassLiteralHolder_Ljava_1util_1AbstractList$IteratorImpl_12_1classLit = java_lang_Class_createForClass__Ljava_lang_String_2Ljava_lang_String_2Lcom_google_gwt_core_client_JavaScriptObject_2Ljava_lang_Class_2Ljava_lang_Class_2($intern_52, 'AbstractList/IteratorImpl', 90);
function java_util_AbstractList$ListIteratorImpl_AbstractList$ListIteratorImpl__Ljava_util_AbstractList_2IV(this$0, start_0){
  this.java_util_AbstractList$ListIteratorImpl_this$01 = this$0;
  java_util_AbstractList$IteratorImpl_AbstractList$IteratorImpl__Ljava_util_AbstractList_2V.call(this, this$0);
  javaemul_internal_InternalPreconditions_checkCriticalPositionIndex__IIV(start_0, this$0.size__I());
  this.java_util_AbstractList$IteratorImpl_i = start_0;
}

com_google_gwt_lang_Runtime_defineClass__Lcom_google_gwt_core_client_JavaScriptObject_2Lcom_google_gwt_core_client_JavaScriptObject_2Lcom_google_gwt_core_client_JavaScriptObject_2V(91, 90, {}, java_util_AbstractList$ListIteratorImpl_AbstractList$ListIteratorImpl__Ljava_util_AbstractList_2IV);
_.hasPrevious__Z = function java_util_AbstractList$ListIteratorImpl_hasPrevious__Z(){
  return this.java_util_AbstractList$IteratorImpl_i > 0;
}
;
_.previous__Ljava_lang_Object_2 = function java_util_AbstractList$ListIteratorImpl_previous__Ljava_lang_Object_2(){
  javaemul_internal_InternalPreconditions_checkCriticalElement__ZV(this.java_util_AbstractList$IteratorImpl_i > 0);
  return this.java_util_AbstractList$ListIteratorImpl_this$01.get__ILjava_lang_Object_2(--this.java_util_AbstractList$IteratorImpl_i);
}
;
var com_google_gwt_lang_ClassLiteralHolder_Ljava_1util_1AbstractList$ListIteratorImpl_12_1classLit = java_lang_Class_createForClass__Ljava_lang_String_2Ljava_lang_String_2Lcom_google_gwt_core_client_JavaScriptObject_2Ljava_lang_Class_2Ljava_lang_Class_2($intern_52, 'AbstractList/ListIteratorImpl', 91);
function java_util_AbstractMap$1_AbstractMap$1__Ljava_util_AbstractMap_2V(this$0){
  this.java_util_AbstractMap$1_this$01 = this$0;
}

com_google_gwt_lang_Runtime_defineClass__Lcom_google_gwt_core_client_JavaScriptObject_2Lcom_google_gwt_core_client_JavaScriptObject_2Lcom_google_gwt_core_client_JavaScriptObject_2V(89, 226, {66:1}, java_util_AbstractMap$1_AbstractMap$1__Ljava_util_AbstractMap_2V);
_.contains__Ljava_lang_Object_2Z = function java_util_AbstractMap$1_contains__Ljava_lang_Object_2Z(key){
  return java_util_AbstractHashMap_$containsKey__Ljava_util_AbstractHashMap_2Ljava_lang_Object_2Z(this.java_util_AbstractMap$1_this$01, key);
}
;
_.iterator__Ljava_util_Iterator_2 = function java_util_AbstractMap$1_iterator__Ljava_util_Iterator_2(){
  var java_util_AbstractMap$1_$iterator__Ljava_util_AbstractMap$1_2Ljava_util_Iterator_2_outerIter_0;
  return java_util_AbstractMap$1_$iterator__Ljava_util_AbstractMap$1_2Ljava_util_Iterator_2_outerIter_0 = new java_util_AbstractHashMap$EntrySetIterator_AbstractHashMap$EntrySetIterator__Ljava_util_AbstractHashMap_2V((new java_util_AbstractHashMap$EntrySet_AbstractHashMap$EntrySet__Ljava_util_AbstractHashMap_2V(this.java_util_AbstractMap$1_this$01)).java_util_AbstractHashMap$EntrySet_this$01) , new java_util_AbstractMap$1$1_AbstractMap$1$1__Ljava_util_AbstractMap$1_2V(java_util_AbstractMap$1_$iterator__Ljava_util_AbstractMap$1_2Ljava_util_Iterator_2_outerIter_0);
}
;
_.size__I = function java_util_AbstractMap$1_size__I(){
  return java_util_AbstractHashMap_$size__Ljava_util_AbstractHashMap_2I(this.java_util_AbstractMap$1_this$01);
}
;
var com_google_gwt_lang_ClassLiteralHolder_Ljava_1util_1AbstractMap$1_12_1classLit = java_lang_Class_createForClass__Ljava_lang_String_2Ljava_lang_String_2Lcom_google_gwt_core_client_JavaScriptObject_2Ljava_lang_Class_2Ljava_lang_Class_2($intern_52, 'AbstractMap/1', 89);
function java_util_AbstractMap$1$1_AbstractMap$1$1__Ljava_util_AbstractMap$1_2V(val$outerIter){
  this.java_util_AbstractMap$1$1_val$outerIter2 = val$outerIter;
}

com_google_gwt_lang_Runtime_defineClass__Lcom_google_gwt_core_client_JavaScriptObject_2Lcom_google_gwt_core_client_JavaScriptObject_2Lcom_google_gwt_core_client_JavaScriptObject_2V(72, 1, {}, java_util_AbstractMap$1$1_AbstractMap$1$1__Ljava_util_AbstractMap$1_2V);
_.hasNext__Z = function java_util_AbstractMap$1$1_hasNext__Z(){
  return this.java_util_AbstractMap$1$1_val$outerIter2.java_util_AbstractHashMap$EntrySetIterator_hasNext;
}
;
_.next__Ljava_lang_Object_2 = function java_util_AbstractMap$1$1_next__Ljava_lang_Object_2(){
  var java_util_AbstractMap$1$1_$next__Ljava_util_AbstractMap$1$1_2Ljava_lang_Object_2_entry_0;
  return java_util_AbstractMap$1$1_$next__Ljava_util_AbstractMap$1$1_2Ljava_lang_Object_2_entry_0 = java_util_AbstractHashMap$EntrySetIterator_$next__Ljava_util_AbstractHashMap$EntrySetIterator_2Ljava_util_Map$Entry_2(this.java_util_AbstractMap$1$1_val$outerIter2) , java_util_AbstractMap$1$1_$next__Ljava_util_AbstractMap$1$1_2Ljava_lang_Object_2_entry_0.getKey__Ljava_lang_Object_2();
}
;
var com_google_gwt_lang_ClassLiteralHolder_Ljava_1util_1AbstractMap$1$1_12_1classLit = java_lang_Class_createForClass__Ljava_lang_String_2Ljava_lang_String_2Lcom_google_gwt_core_client_JavaScriptObject_2Ljava_lang_Class_2Ljava_lang_Class_2($intern_52, 'AbstractMap/1/1', 72);
com_google_gwt_lang_Runtime_defineClass__Lcom_google_gwt_core_client_JavaScriptObject_2Lcom_google_gwt_core_client_JavaScriptObject_2Lcom_google_gwt_core_client_JavaScriptObject_2V(136, 1, $intern_54);
_.equals__Ljava_lang_Object_2Z = function java_util_AbstractMap$AbstractEntry_equals__Ljava_lang_Object_2Z(other){
  var entry;
  if (!com_google_gwt_lang_Cast_instanceOf__Ljava_lang_Object_2Lcom_google_gwt_core_client_JavaScriptObject_2Z(other, 17)) {
    return false;
  }
  entry = com_google_gwt_lang_Cast_castTo__Ljava_lang_Object_2Lcom_google_gwt_core_client_JavaScriptObject_2Ljava_lang_Object_2(other, 17);
  return java_util_Objects_equals__Ljava_lang_Object_2Ljava_lang_Object_2Z(this.java_util_AbstractMap$AbstractEntry_key, entry.getKey__Ljava_lang_Object_2()) && java_util_Objects_equals__Ljava_lang_Object_2Ljava_lang_Object_2Z(this.java_util_AbstractMap$AbstractEntry_value, entry.getValue__Ljava_lang_Object_2());
}
;
_.getKey__Ljava_lang_Object_2 = function java_util_AbstractMap$AbstractEntry_getKey__Ljava_lang_Object_2(){
  return this.java_util_AbstractMap$AbstractEntry_key;
}
;
_.getValue__Ljava_lang_Object_2 = function java_util_AbstractMap$AbstractEntry_getValue__Ljava_lang_Object_2(){
  return this.java_util_AbstractMap$AbstractEntry_value;
}
;
_.hashCode__I = function java_util_AbstractMap$AbstractEntry_hashCode__I(){
  return java_util_Objects_hashCode__Ljava_lang_Object_2I(this.java_util_AbstractMap$AbstractEntry_key) ^ java_util_Objects_hashCode__Ljava_lang_Object_2I(this.java_util_AbstractMap$AbstractEntry_value);
}
;
_.setValue__Ljava_lang_Object_2Ljava_lang_Object_2 = function java_util_AbstractMap$AbstractEntry_setValue__Ljava_lang_Object_2Ljava_lang_Object_2(value_0){
  var oldValue;
  oldValue = this.java_util_AbstractMap$AbstractEntry_value;
  this.java_util_AbstractMap$AbstractEntry_value = value_0;
  return oldValue;
}
;
_.toString__Ljava_lang_String_2 = function java_util_AbstractMap$AbstractEntry_toString__Ljava_lang_String_2(){
  return this.java_util_AbstractMap$AbstractEntry_key + '=' + this.java_util_AbstractMap$AbstractEntry_value;
}
;
var com_google_gwt_lang_ClassLiteralHolder_Ljava_1util_1AbstractMap$AbstractEntry_12_1classLit = java_lang_Class_createForClass__Ljava_lang_String_2Ljava_lang_String_2Lcom_google_gwt_core_client_JavaScriptObject_2Ljava_lang_Class_2Ljava_lang_Class_2($intern_52, 'AbstractMap/AbstractEntry', 136);
function java_util_AbstractMap$SimpleEntry_AbstractMap$SimpleEntry__Ljava_lang_Object_2Ljava_lang_Object_2V(key, value_0){
  this.java_util_AbstractMap$AbstractEntry_key = key;
  this.java_util_AbstractMap$AbstractEntry_value = value_0;
}

com_google_gwt_lang_Runtime_defineClass__Lcom_google_gwt_core_client_JavaScriptObject_2Lcom_google_gwt_core_client_JavaScriptObject_2Lcom_google_gwt_core_client_JavaScriptObject_2V(137, 136, $intern_54, java_util_AbstractMap$SimpleEntry_AbstractMap$SimpleEntry__Ljava_lang_Object_2Ljava_lang_Object_2V);
var com_google_gwt_lang_ClassLiteralHolder_Ljava_1util_1AbstractMap$SimpleEntry_12_1classLit = java_lang_Class_createForClass__Ljava_lang_String_2Ljava_lang_String_2Lcom_google_gwt_core_client_JavaScriptObject_2Ljava_lang_Class_2Ljava_lang_Class_2($intern_52, 'AbstractMap/SimpleEntry', 137);
com_google_gwt_lang_Runtime_defineClass__Lcom_google_gwt_core_client_JavaScriptObject_2Lcom_google_gwt_core_client_JavaScriptObject_2Lcom_google_gwt_core_client_JavaScriptObject_2V(234, 1, $intern_54);
_.equals__Ljava_lang_Object_2Z = function java_util_AbstractMapEntry_equals__Ljava_lang_Object_2Z(other){
  var entry;
  if (!com_google_gwt_lang_Cast_instanceOf__Ljava_lang_Object_2Lcom_google_gwt_core_client_JavaScriptObject_2Z(other, 17)) {
    return false;
  }
  entry = com_google_gwt_lang_Cast_castTo__Ljava_lang_Object_2Lcom_google_gwt_core_client_JavaScriptObject_2Ljava_lang_Object_2(other, 17);
  return java_util_Objects_equals__Ljava_lang_Object_2Ljava_lang_Object_2Z(this.java_util_InternalStringMap$2_val$entry2.value[0], entry.getKey__Ljava_lang_Object_2()) && java_util_Objects_equals__Ljava_lang_Object_2Ljava_lang_Object_2Z(java_util_InternalStringMap$2_$getValue__Ljava_util_InternalStringMap$2_2Ljava_lang_Object_2(this), entry.getValue__Ljava_lang_Object_2());
}
;
_.hashCode__I = function java_util_AbstractMapEntry_hashCode__I(){
  return java_util_Objects_hashCode__Ljava_lang_Object_2I(this.java_util_InternalStringMap$2_val$entry2.value[0]) ^ java_util_Objects_hashCode__Ljava_lang_Object_2I(java_util_InternalStringMap$2_$getValue__Ljava_util_InternalStringMap$2_2Ljava_lang_Object_2(this));
}
;
_.toString__Ljava_lang_String_2 = function java_util_AbstractMapEntry_toString__Ljava_lang_String_2(){
  return this.java_util_InternalStringMap$2_val$entry2.value[0] + '=' + java_util_InternalStringMap$2_$getValue__Ljava_util_InternalStringMap$2_2Ljava_lang_Object_2(this);
}
;
var com_google_gwt_lang_ClassLiteralHolder_Ljava_1util_1AbstractMapEntry_12_1classLit = java_lang_Class_createForClass__Ljava_lang_String_2Ljava_lang_String_2Lcom_google_gwt_core_client_JavaScriptObject_2Ljava_lang_Class_2Ljava_lang_Class_2($intern_52, 'AbstractMapEntry', 234);
function java_util_ArrayList_$add__Ljava_util_ArrayList_2Ljava_lang_Object_2Z(this$static, o){
  this$static.java_util_ArrayList_array[this$static.java_util_ArrayList_array.length] = o;
  return true;
}

function java_util_ArrayList_$indexOf__Ljava_util_ArrayList_2Ljava_lang_Object_2II(this$static, o, index_0){
  for (; index_0 < this$static.java_util_ArrayList_array.length; ++index_0) {
    if (java_util_Objects_equals__Ljava_lang_Object_2Ljava_lang_Object_2Z(o, this$static.java_util_ArrayList_array[index_0])) {
      return index_0;
    }
  }
  return -1;
}

function java_util_ArrayList_ArrayList__V(){
  this.java_util_ArrayList_array = com_google_gwt_lang_Array_initUnidimensionalArray__Ljava_lang_Class_2Lcom_google_gwt_core_client_JavaScriptObject_2Lcom_google_gwt_core_client_JavaScriptObject_2IIILjava_lang_Object_2(com_google_gwt_lang_ClassLiteralHolder_Ljava_1lang_1Object_12_1classLit, {3:1}, 1, 0, 5, 1);
}

com_google_gwt_lang_Runtime_defineClass__Lcom_google_gwt_core_client_JavaScriptObject_2Lcom_google_gwt_core_client_JavaScriptObject_2Lcom_google_gwt_core_client_JavaScriptObject_2V(51, 227, {3:1, 28:1}, java_util_ArrayList_ArrayList__V);
_.add__ILjava_lang_Object_2V = function java_util_ArrayList_add__ILjava_lang_Object_2V(index_0, o){
  javaemul_internal_InternalPreconditions_checkCriticalPositionIndex__IIV(index_0, this.java_util_ArrayList_array.length);
  javaemul_internal_ArrayHelper_insertTo__Ljava_lang_Object_2ILjava_lang_Object_2V(this.java_util_ArrayList_array, index_0, o);
}
;
_.add__Ljava_lang_Object_2Z = function java_util_ArrayList_add__Ljava_lang_Object_2Z(o){
  return java_util_ArrayList_$add__Ljava_util_ArrayList_2Ljava_lang_Object_2Z(this, o);
}
;
_.contains__Ljava_lang_Object_2Z = function java_util_ArrayList_contains__Ljava_lang_Object_2Z(o){
  return java_util_ArrayList_$indexOf__Ljava_util_ArrayList_2Ljava_lang_Object_2II(this, o, 0) != -1;
}
;
_.get__ILjava_lang_Object_2 = function java_util_ArrayList_get__ILjava_lang_Object_2(index_0){
  return javaemul_internal_InternalPreconditions_checkCriticalElementIndex__IIV(index_0, this.java_util_ArrayList_array.length) , this.java_util_ArrayList_array[index_0];
}
;
_.isEmpty__Z = function java_util_ArrayList_isEmpty__Z(){
  return this.java_util_ArrayList_array.length == 0;
}
;
_.iterator__Ljava_util_Iterator_2 = function java_util_ArrayList_iterator__Ljava_util_Iterator_2(){
  return new java_util_ArrayList$1_ArrayList$1__Ljava_util_ArrayList_2V(this);
}
;
_.size__I = function java_util_ArrayList_size__I(){
  return this.java_util_ArrayList_array.length;
}
;
var com_google_gwt_lang_ClassLiteralHolder_Ljava_1util_1ArrayList_12_1classLit = java_lang_Class_createForClass__Ljava_lang_String_2Ljava_lang_String_2Lcom_google_gwt_core_client_JavaScriptObject_2Ljava_lang_Class_2Ljava_lang_Class_2($intern_52, 'ArrayList', 51);
function java_util_ArrayList$1_$next__Ljava_util_ArrayList$1_2Ljava_lang_Object_2(this$static){
  javaemul_internal_InternalPreconditions_checkCriticalElement__ZV(this$static.java_util_ArrayList$1_i < this$static.java_util_ArrayList$1_this$01.java_util_ArrayList_array.length);
  this$static.java_util_ArrayList$1_last = this$static.java_util_ArrayList$1_i++;
  return this$static.java_util_ArrayList$1_this$01.java_util_ArrayList_array[this$static.java_util_ArrayList$1_last];
}

function java_util_ArrayList$1_ArrayList$1__Ljava_util_ArrayList_2V(this$0){
  this.java_util_ArrayList$1_this$01 = this$0;
}

com_google_gwt_lang_Runtime_defineClass__Lcom_google_gwt_core_client_JavaScriptObject_2Lcom_google_gwt_core_client_JavaScriptObject_2Lcom_google_gwt_core_client_JavaScriptObject_2V(73, 1, {}, java_util_ArrayList$1_ArrayList$1__Ljava_util_ArrayList_2V);
_.hasNext__Z = function java_util_ArrayList$1_hasNext__Z(){
  return this.java_util_ArrayList$1_i < this.java_util_ArrayList$1_this$01.java_util_ArrayList_array.length;
}
;
_.next__Ljava_lang_Object_2 = function java_util_ArrayList$1_next__Ljava_lang_Object_2(){
  return java_util_ArrayList$1_$next__Ljava_util_ArrayList$1_2Ljava_lang_Object_2(this);
}
;
_.java_util_ArrayList$1_i = 0;
_.java_util_ArrayList$1_last = -1;
var com_google_gwt_lang_ClassLiteralHolder_Ljava_1util_1ArrayList$1_12_1classLit = java_lang_Class_createForClass__Ljava_lang_String_2Ljava_lang_String_2Lcom_google_gwt_core_client_JavaScriptObject_2Ljava_lang_Class_2Ljava_lang_Class_2($intern_52, 'ArrayList/1', 73);
function java_util_Collections_$clinit__V(){
  java_util_Collections_$clinit__V = com_google_gwt_lang_Runtime_emptyMethod__V;
  java_util_Collections_EMPTY_1LIST = new java_util_Collections$EmptyList_Collections$EmptyList__V;
}

function java_util_Collections_hashCode__Ljava_lang_Iterable_2I(collection){
  java_util_Collections_$clinit__V();
  var e, e$iterator, hashCode;
  hashCode = 0;
  for (e$iterator = collection.iterator__Ljava_util_Iterator_2(); e$iterator.hasNext__Z();) {
    e = e$iterator.next__Ljava_lang_Object_2();
    hashCode = hashCode + (e != null?java_lang_Object_hashCode_1_1I_1_1devirtual$__Ljava_lang_Object_2I(e):0);
    hashCode = hashCode | 0;
  }
  return hashCode;
}

function java_util_Collections_hashCode__Ljava_util_List_2I(list){
  java_util_Collections_$clinit__V();
  var e, e$iterator, hashCode;
  hashCode = 1;
  for (e$iterator = list.iterator__Ljava_util_Iterator_2(); e$iterator.hasNext__Z();) {
    e = e$iterator.next__Ljava_lang_Object_2();
    hashCode = 31 * hashCode + (e != null?java_lang_Object_hashCode_1_1I_1_1devirtual$__Ljava_lang_Object_2I(e):0);
    hashCode = hashCode | 0;
  }
  return hashCode;
}

var java_util_Collections_EMPTY_1LIST;
function java_util_Collections$EmptyList_Collections$EmptyList__V(){
}

com_google_gwt_lang_Runtime_defineClass__Lcom_google_gwt_core_client_JavaScriptObject_2Lcom_google_gwt_core_client_JavaScriptObject_2Lcom_google_gwt_core_client_JavaScriptObject_2V(153, 227, {3:1, 28:1}, java_util_Collections$EmptyList_Collections$EmptyList__V);
_.contains__Ljava_lang_Object_2Z = function java_util_Collections$EmptyList_contains__Ljava_lang_Object_2Z(object){
  return false;
}
;
_.get__ILjava_lang_Object_2 = function java_util_Collections$EmptyList_get__ILjava_lang_Object_2(location_0){
  javaemul_internal_InternalPreconditions_checkCriticalElementIndex__IIV(location_0, 0);
  return null;
}
;
_.iterator__Ljava_util_Iterator_2 = function java_util_Collections$EmptyList_iterator__Ljava_util_Iterator_2(){
  return java_util_Collections_$clinit__V() , java_util_Collections$EmptyListIterator_$clinit__V() , java_util_Collections$EmptyListIterator_INSTANCE;
}
;
_.listIterator__Ljava_util_ListIterator_2 = function java_util_Collections$EmptyList_listIterator__Ljava_util_ListIterator_2(){
  return java_util_Collections_$clinit__V() , java_util_Collections$EmptyListIterator_$clinit__V() , java_util_Collections$EmptyListIterator_INSTANCE;
}
;
_.size__I = function java_util_Collections$EmptyList_size__I(){
  return 0;
}
;
var com_google_gwt_lang_ClassLiteralHolder_Ljava_1util_1Collections$EmptyList_12_1classLit = java_lang_Class_createForClass__Ljava_lang_String_2Ljava_lang_String_2Lcom_google_gwt_core_client_JavaScriptObject_2Ljava_lang_Class_2Ljava_lang_Class_2($intern_52, 'Collections/EmptyList', 153);
function java_util_Collections$EmptyListIterator_$clinit__V(){
  java_util_Collections$EmptyListIterator_$clinit__V = com_google_gwt_lang_Runtime_emptyMethod__V;
  java_util_Collections$EmptyListIterator_INSTANCE = new java_util_Collections$EmptyListIterator_Collections$EmptyListIterator__V;
}

function java_util_Collections$EmptyListIterator_Collections$EmptyListIterator__V(){
}

com_google_gwt_lang_Runtime_defineClass__Lcom_google_gwt_core_client_JavaScriptObject_2Lcom_google_gwt_core_client_JavaScriptObject_2Lcom_google_gwt_core_client_JavaScriptObject_2V(154, 1, {}, java_util_Collections$EmptyListIterator_Collections$EmptyListIterator__V);
_.hasNext__Z = function java_util_Collections$EmptyListIterator_hasNext__Z(){
  return false;
}
;
_.hasPrevious__Z = function java_util_Collections$EmptyListIterator_hasPrevious__Z(){
  return false;
}
;
_.next__Ljava_lang_Object_2 = function java_util_Collections$EmptyListIterator_next__Ljava_lang_Object_2(){
  throw com_google_gwt_lang_Exceptions_toJs__Ljava_lang_Object_2Ljava_lang_Object_2(new java_util_NoSuchElementException_NoSuchElementException__V);
}
;
_.previous__Ljava_lang_Object_2 = function java_util_Collections$EmptyListIterator_previous__Ljava_lang_Object_2(){
  throw com_google_gwt_lang_Exceptions_toJs__Ljava_lang_Object_2Ljava_lang_Object_2(new java_util_NoSuchElementException_NoSuchElementException__V);
}
;
var java_util_Collections$EmptyListIterator_INSTANCE;
var com_google_gwt_lang_ClassLiteralHolder_Ljava_1util_1Collections$EmptyListIterator_12_1classLit = java_lang_Class_createForClass__Ljava_lang_String_2Ljava_lang_String_2Lcom_google_gwt_core_client_JavaScriptObject_2Ljava_lang_Class_2Ljava_lang_Class_2($intern_52, 'Collections/EmptyListIterator', 154);
function java_util_ConcurrentModificationDetector_checkStructuralChange__Ljava_lang_Object_2Ljava_util_Iterator_2V(host, iterator){
  if (iterator[$intern_53] != host[$intern_53]) {
    throw com_google_gwt_lang_Exceptions_toJs__Ljava_lang_Object_2Ljava_lang_Object_2(new java_util_ConcurrentModificationException_ConcurrentModificationException__V);
  }
}

function java_util_ConcurrentModificationDetector_structureChanged__Ljava_lang_Object_2V(map_0){
  var modCount;
  modCount = map_0[$intern_53] | 0;
  map_0[$intern_53] = modCount + 1;
}

function java_util_ConcurrentModificationException_ConcurrentModificationException__V(){
  java_lang_RuntimeException_RuntimeException__V.call(this);
}

com_google_gwt_lang_Runtime_defineClass__Lcom_google_gwt_core_client_JavaScriptObject_2Lcom_google_gwt_core_client_JavaScriptObject_2Lcom_google_gwt_core_client_JavaScriptObject_2V(181, 10, $intern_27, java_util_ConcurrentModificationException_ConcurrentModificationException__V);
var com_google_gwt_lang_ClassLiteralHolder_Ljava_1util_1ConcurrentModificationException_12_1classLit = java_lang_Class_createForClass__Ljava_lang_String_2Ljava_lang_String_2Lcom_google_gwt_core_client_JavaScriptObject_2Ljava_lang_Class_2Ljava_lang_Class_2($intern_52, 'ConcurrentModificationException', 181);
function java_util_HashMap_$equals__Ljava_util_HashMap_2Ljava_lang_Object_2Ljava_lang_Object_2Z(value1, value2){
  return com_google_gwt_lang_Cast_maskUndefined__Ljava_lang_Object_2Ljava_lang_Object_2(value1) === com_google_gwt_lang_Cast_maskUndefined__Ljava_lang_Object_2Ljava_lang_Object_2(value2) || value1 != null && java_lang_Object_equals_1Ljava_1lang_1Object_1_1Z_1_1devirtual$__Ljava_lang_Object_2Ljava_lang_Object_2Z(value1, value2);
}

function java_util_HashMap_HashMap__V(){
  java_util_AbstractHashMap_$reset__Ljava_util_AbstractHashMap_2V(this);
}

com_google_gwt_lang_Runtime_defineClass__Lcom_google_gwt_core_client_JavaScriptObject_2Lcom_google_gwt_core_client_JavaScriptObject_2Lcom_google_gwt_core_client_JavaScriptObject_2V(18, 135, {3:1, 18:1, 65:1}, java_util_HashMap_HashMap__V);
var com_google_gwt_lang_ClassLiteralHolder_Ljava_1util_1HashMap_12_1classLit = java_lang_Class_createForClass__Ljava_lang_String_2Ljava_lang_String_2Lcom_google_gwt_core_client_JavaScriptObject_2Ljava_lang_Class_2Ljava_lang_Class_2($intern_52, 'HashMap', 18);
function java_util_HashSet_$add__Ljava_util_HashSet_2Ljava_lang_Object_2Z(this$static, o){
  var old;
  old = java_util_AbstractHashMap_$put__Ljava_util_AbstractHashMap_2Ljava_lang_Object_2Ljava_lang_Object_2Ljava_lang_Object_2(this$static.java_util_HashSet_map, o, this$static);
  return old == null;
}

function java_util_HashSet_$contains__Ljava_util_HashSet_2Ljava_lang_Object_2Z(this$static, o){
  return java_util_AbstractHashMap_$containsKey__Ljava_util_AbstractHashMap_2Ljava_lang_Object_2Z(this$static.java_util_HashSet_map, o);
}

function java_util_HashSet_$remove__Ljava_util_HashSet_2Ljava_lang_Object_2Z(this$static, o){
  return java_util_AbstractHashMap_$remove__Ljava_util_AbstractHashMap_2Ljava_lang_Object_2Ljava_lang_Object_2(this$static.java_util_HashSet_map, o) != null;
}

function java_util_HashSet_HashSet__V(){
  this.java_util_HashSet_map = new java_util_HashMap_HashMap__V;
}

com_google_gwt_lang_Runtime_defineClass__Lcom_google_gwt_core_client_JavaScriptObject_2Lcom_google_gwt_core_client_JavaScriptObject_2Lcom_google_gwt_core_client_JavaScriptObject_2V(50, 226, {3:1, 66:1}, java_util_HashSet_HashSet__V);
_.add__Ljava_lang_Object_2Z = function java_util_HashSet_add__Ljava_lang_Object_2Z(o){
  return java_util_HashSet_$add__Ljava_util_HashSet_2Ljava_lang_Object_2Z(this, o);
}
;
_.contains__Ljava_lang_Object_2Z = function java_util_HashSet_contains__Ljava_lang_Object_2Z(o){
  return java_util_HashSet_$contains__Ljava_util_HashSet_2Ljava_lang_Object_2Z(this, o);
}
;
_.isEmpty__Z = function java_util_HashSet_isEmpty__Z(){
  return java_util_AbstractHashMap_$size__Ljava_util_AbstractHashMap_2I(this.java_util_HashSet_map) == 0;
}
;
_.iterator__Ljava_util_Iterator_2 = function java_util_HashSet_iterator__Ljava_util_Iterator_2(){
  var java_util_HashSet_$iterator__Ljava_util_HashSet_2Ljava_util_Iterator_2_outerIter_0;
  return java_util_HashSet_$iterator__Ljava_util_HashSet_2Ljava_util_Iterator_2_outerIter_0 = new java_util_AbstractHashMap$EntrySetIterator_AbstractHashMap$EntrySetIterator__Ljava_util_AbstractHashMap_2V((new java_util_AbstractHashMap$EntrySet_AbstractHashMap$EntrySet__Ljava_util_AbstractHashMap_2V((new java_util_AbstractMap$1_AbstractMap$1__Ljava_util_AbstractMap_2V(this.java_util_HashSet_map)).java_util_AbstractMap$1_this$01)).java_util_AbstractHashMap$EntrySet_this$01) , new java_util_AbstractMap$1$1_AbstractMap$1$1__Ljava_util_AbstractMap$1_2V(java_util_HashSet_$iterator__Ljava_util_HashSet_2Ljava_util_Iterator_2_outerIter_0);
}
;
_.size__I = function java_util_HashSet_size__I(){
  return java_util_AbstractHashMap_$size__Ljava_util_AbstractHashMap_2I(this.java_util_HashSet_map);
}
;
var com_google_gwt_lang_ClassLiteralHolder_Ljava_1util_1HashSet_12_1classLit = java_lang_Class_createForClass__Ljava_lang_String_2Ljava_lang_String_2Lcom_google_gwt_core_client_JavaScriptObject_2Ljava_lang_Class_2Ljava_lang_Class_2($intern_52, 'HashSet', 50);
function java_util_InternalHashCodeMap_$findEntryInChain__Ljava_util_InternalHashCodeMap_2Ljava_lang_Object_2_3Ljava_util_Map$Entry_2Ljava_util_Map$Entry_2(key, chain){
  var entry, entry$index, entry$max;
  for (entry$index = 0 , entry$max = chain.length; entry$index < entry$max; ++entry$index) {
    entry = chain[entry$index];
    if (java_util_HashMap_$equals__Ljava_util_HashMap_2Ljava_lang_Object_2Ljava_lang_Object_2Z(key, entry.getKey__Ljava_lang_Object_2())) {
      return entry;
    }
  }
  return null;
}

function java_util_InternalHashCodeMap_$getChainOrEmpty__Ljava_util_InternalHashCodeMap_2I_3Ljava_util_Map$Entry_2(this$static, hashCode){
  var chain;
  chain = this$static.java_util_InternalHashCodeMap_backingMap.get(hashCode);
  return chain == null?[]:chain;
}

function java_util_InternalHashCodeMap_$getEntry__Ljava_util_InternalHashCodeMap_2Ljava_lang_Object_2Ljava_util_Map$Entry_2(this$static, key){
  var hashCode;
  return java_util_InternalHashCodeMap_$findEntryInChain__Ljava_util_InternalHashCodeMap_2Ljava_lang_Object_2_3Ljava_util_Map$Entry_2Ljava_util_Map$Entry_2(key, java_util_InternalHashCodeMap_$getChainOrEmpty__Ljava_util_InternalHashCodeMap_2I_3Ljava_util_Map$Entry_2(this$static, key == null?0:(hashCode = java_lang_Object_hashCode_1_1I_1_1devirtual$__Ljava_lang_Object_2I(key) , hashCode | 0)));
}

function java_util_InternalHashCodeMap_$put__Ljava_util_InternalHashCodeMap_2Ljava_lang_Object_2Ljava_lang_Object_2Ljava_lang_Object_2(this$static, key, value_0){
  var chain, chain0, entry, hashCode, hashCode0;
  hashCode0 = key == null?0:(hashCode = java_lang_Object_hashCode_1_1I_1_1devirtual$__Ljava_lang_Object_2I(key) , hashCode | 0);
  chain0 = (chain = this$static.java_util_InternalHashCodeMap_backingMap.get(hashCode0) , chain == null?[]:chain);
  if (chain0.length == 0) {
    this$static.java_util_InternalHashCodeMap_backingMap.set(hashCode0, chain0);
  }
   else {
    entry = java_util_InternalHashCodeMap_$findEntryInChain__Ljava_util_InternalHashCodeMap_2Ljava_lang_Object_2_3Ljava_util_Map$Entry_2Ljava_util_Map$Entry_2(key, chain0);
    if (entry) {
      return entry.setValue__Ljava_lang_Object_2Ljava_lang_Object_2(value_0);
    }
  }
  com_google_gwt_lang_Array_setCheck__Ljava_lang_Object_2ILjava_lang_Object_2Ljava_lang_Object_2(chain0, chain0.length, new java_util_AbstractMap$SimpleEntry_AbstractMap$SimpleEntry__Ljava_lang_Object_2Ljava_lang_Object_2V(key, value_0));
  ++this$static.java_util_InternalHashCodeMap_size;
  java_util_ConcurrentModificationDetector_structureChanged__Ljava_lang_Object_2V(this$static.java_util_InternalHashCodeMap_host);
  return null;
}

function java_util_InternalHashCodeMap_$remove__Ljava_util_InternalHashCodeMap_2Ljava_lang_Object_2Ljava_lang_Object_2(this$static, key){
  var chain, chain0, entry, hashCode, hashCode0, i;
  hashCode0 = (hashCode = java_lang_Object_hashCode_1_1I_1_1devirtual$__Ljava_lang_Object_2I(key) , hashCode | 0);
  chain0 = (chain = this$static.java_util_InternalHashCodeMap_backingMap.get(hashCode0) , chain == null?[]:chain);
  for (i = 0; i < chain0.length; i++) {
    entry = chain0[i];
    if (java_util_HashMap_$equals__Ljava_util_HashMap_2Ljava_lang_Object_2Ljava_lang_Object_2Z(key, entry.getKey__Ljava_lang_Object_2())) {
      if (chain0.length == 1) {
        chain0.length = 0;
        this$static.java_util_InternalHashCodeMap_backingMap[$intern_55](hashCode0);
      }
       else {
        chain0.splice(i, 1);
      }
      --this$static.java_util_InternalHashCodeMap_size;
      java_util_ConcurrentModificationDetector_structureChanged__Ljava_lang_Object_2V(this$static.java_util_InternalHashCodeMap_host);
      return entry.getValue__Ljava_lang_Object_2();
    }
  }
  return null;
}

function java_util_InternalHashCodeMap_InternalHashCodeMap__Ljava_util_AbstractHashMap_2V(host){
  this.java_util_InternalHashCodeMap_backingMap = java_util_InternalJsMapFactory_newJsMap__Ljava_util_InternalJsMap_2();
  this.java_util_InternalHashCodeMap_host = host;
}

com_google_gwt_lang_Runtime_defineClass__Lcom_google_gwt_core_client_JavaScriptObject_2Lcom_google_gwt_core_client_JavaScriptObject_2Lcom_google_gwt_core_client_JavaScriptObject_2V(155, 1, {}, java_util_InternalHashCodeMap_InternalHashCodeMap__Ljava_util_AbstractHashMap_2V);
_.iterator__Ljava_util_Iterator_2 = function java_util_InternalHashCodeMap_iterator__Ljava_util_Iterator_2(){
  return new java_util_InternalHashCodeMap$1_InternalHashCodeMap$1__Ljava_util_InternalHashCodeMap_2V(this);
}
;
_.java_util_InternalHashCodeMap_size = 0;
var com_google_gwt_lang_ClassLiteralHolder_Ljava_1util_1InternalHashCodeMap_12_1classLit = java_lang_Class_createForClass__Ljava_lang_String_2Ljava_lang_String_2Lcom_google_gwt_core_client_JavaScriptObject_2Ljava_lang_Class_2Ljava_lang_Class_2($intern_52, 'InternalHashCodeMap', 155);
function java_util_InternalHashCodeMap$1_InternalHashCodeMap$1__Ljava_util_InternalHashCodeMap_2V(this$0){
  this.java_util_InternalHashCodeMap$1_this$01 = this$0;
  this.java_util_InternalHashCodeMap$1_chains = this.java_util_InternalHashCodeMap$1_this$01.java_util_InternalHashCodeMap_backingMap.entries();
  this.java_util_InternalHashCodeMap$1_chain = [];
}

com_google_gwt_lang_Runtime_defineClass__Lcom_google_gwt_core_client_JavaScriptObject_2Lcom_google_gwt_core_client_JavaScriptObject_2Lcom_google_gwt_core_client_JavaScriptObject_2V(94, 1, {}, java_util_InternalHashCodeMap$1_InternalHashCodeMap$1__Ljava_util_InternalHashCodeMap_2V);
_.next__Ljava_lang_Object_2 = function java_util_InternalHashCodeMap$1_next__Ljava_lang_Object_2(){
  return this.java_util_InternalHashCodeMap$1_lastEntry = this.java_util_InternalHashCodeMap$1_chain[this.java_util_InternalHashCodeMap$1_itemIndex++] , this.java_util_InternalHashCodeMap$1_lastEntry;
}
;
_.hasNext__Z = function java_util_InternalHashCodeMap$1_hasNext__Z(){
  var current;
  if (this.java_util_InternalHashCodeMap$1_itemIndex < this.java_util_InternalHashCodeMap$1_chain.length) {
    return true;
  }
  current = this.java_util_InternalHashCodeMap$1_chains.next();
  if (!current.done) {
    this.java_util_InternalHashCodeMap$1_chain = current.value[1];
    this.java_util_InternalHashCodeMap$1_itemIndex = 0;
    return true;
  }
  return false;
}
;
_.java_util_InternalHashCodeMap$1_itemIndex = 0;
_.java_util_InternalHashCodeMap$1_lastEntry = null;
var com_google_gwt_lang_ClassLiteralHolder_Ljava_1util_1InternalHashCodeMap$1_12_1classLit = java_lang_Class_createForClass__Ljava_lang_String_2Ljava_lang_String_2Lcom_google_gwt_core_client_JavaScriptObject_2Ljava_lang_Class_2Ljava_lang_Class_2($intern_52, 'InternalHashCodeMap/1', 94);
function java_util_InternalJsMapFactory_$clinit__V(){
  java_util_InternalJsMapFactory_$clinit__V = com_google_gwt_lang_Runtime_emptyMethod__V;
  java_util_InternalJsMapFactory_jsMapCtor = java_util_InternalJsMapFactory_getJsMapConstructor__Lcom_google_gwt_core_client_JavaScriptObject_2();
}

function java_util_InternalJsMapFactory_canHandleObjectCreateAndProto__Z(){
  if (!Object.create || !Object.getOwnPropertyNames) {
    return false;
  }
  var protoField = '__proto__';
  var map_0 = Object.create(null);
  if (map_0[protoField] !== undefined) {
    return false;
  }
  var keys_0 = Object.getOwnPropertyNames(map_0);
  if (keys_0.length != 0) {
    return false;
  }
  map_0[protoField] = 42;
  if (map_0[protoField] !== 42) {
    return false;
  }
  if (Object.getOwnPropertyNames(map_0).length == 0) {
    return false;
  }
  return true;
}

function java_util_InternalJsMapFactory_getJsMapConstructor__Lcom_google_gwt_core_client_JavaScriptObject_2(){
  function isCorrectIterationProtocol(){
    try {
      return (new Map).entries().next().done;
    }
     catch (e) {
      return false;
    }
  }

  if (typeof Map === $intern_1 && Map.prototype.entries && isCorrectIterationProtocol()) {
    return Map;
  }
   else {
    return java_util_InternalJsMapFactory_getJsMapPolyFill__Lcom_google_gwt_core_client_JavaScriptObject_2();
  }
}

function java_util_InternalJsMapFactory_getJsMapPolyFill__Lcom_google_gwt_core_client_JavaScriptObject_2(){
  function Stringmap(){
    this.obj = this.createObject();
  }

  ;
  Stringmap.prototype.createObject = function(key){
    return Object.create(null);
  }
  ;
  Stringmap.prototype.get = function(key){
    return this.obj[key];
  }
  ;
  Stringmap.prototype.set = function(key, value_0){
    this.obj[key] = value_0;
  }
  ;
  Stringmap.prototype[$intern_55] = function(key){
    delete this.obj[key];
  }
  ;
  Stringmap.prototype.keys = function(){
    return Object.getOwnPropertyNames(this.obj);
  }
  ;
  Stringmap.prototype.entries = function(){
    var keys_0 = this.keys();
    var map_0 = this;
    var nextIndex = 0;
    return {next:function(){
      if (nextIndex >= keys_0.length)
        return {done:true};
      var key = keys_0[nextIndex++];
      return {value:[key, map_0.get(key)], done:false};
    }
    };
  }
  ;
  if (!java_util_InternalJsMapFactory_canHandleObjectCreateAndProto__Z()) {
    Stringmap.prototype.createObject = function(){
      return {};
    }
    ;
    Stringmap.prototype.get = function(key){
      return this.obj[':' + key];
    }
    ;
    Stringmap.prototype.set = function(key, value_0){
      this.obj[':' + key] = value_0;
    }
    ;
    Stringmap.prototype[$intern_55] = function(key){
      delete this.obj[':' + key];
    }
    ;
    Stringmap.prototype.keys = function(){
      var result = [];
      for (var key in this.obj) {
        key.charCodeAt(0) == 58 && result.push(key.substring(1));
      }
      return result;
    }
    ;
  }
  return Stringmap;
}

function java_util_InternalJsMapFactory_newJsMap__Ljava_util_InternalJsMap_2(){
  java_util_InternalJsMapFactory_$clinit__V();
  return new java_util_InternalJsMapFactory_jsMapCtor;
}

var java_util_InternalJsMapFactory_jsMapCtor;
function java_util_InternalStringMap_$contains__Ljava_util_InternalStringMap_2Ljava_lang_String_2Z(this$static, key){
  return !(this$static.java_util_InternalStringMap_backingMap.get(key) === undefined);
}

function java_util_InternalStringMap_$get__Ljava_util_InternalStringMap_2Ljava_lang_String_2Ljava_lang_Object_2(this$static, key){
  return this$static.java_util_InternalStringMap_backingMap.get(key);
}

function java_util_InternalStringMap_$put__Ljava_util_InternalStringMap_2Ljava_lang_String_2Ljava_lang_Object_2Ljava_lang_Object_2(this$static, key, value_0){
  var oldValue;
  oldValue = this$static.java_util_InternalStringMap_backingMap.get(key);
  this$static.java_util_InternalStringMap_backingMap.set(key, value_0 === undefined?null:value_0);
  if (oldValue === undefined) {
    ++this$static.java_util_InternalStringMap_size;
    java_util_ConcurrentModificationDetector_structureChanged__Ljava_lang_Object_2V(this$static.java_util_InternalStringMap_host);
  }
   else {
    ++this$static.java_util_InternalStringMap_valueMod;
  }
  return oldValue;
}

function java_util_InternalStringMap_InternalStringMap__Ljava_util_AbstractHashMap_2V(host){
  this.java_util_InternalStringMap_backingMap = java_util_InternalJsMapFactory_newJsMap__Ljava_util_InternalJsMap_2();
  this.java_util_InternalStringMap_host = host;
}

com_google_gwt_lang_Runtime_defineClass__Lcom_google_gwt_core_client_JavaScriptObject_2Lcom_google_gwt_core_client_JavaScriptObject_2Lcom_google_gwt_core_client_JavaScriptObject_2V(156, 1, {}, java_util_InternalStringMap_InternalStringMap__Ljava_util_AbstractHashMap_2V);
_.iterator__Ljava_util_Iterator_2 = function java_util_InternalStringMap_iterator__Ljava_util_Iterator_2(){
  return new java_util_InternalStringMap$1_InternalStringMap$1__Ljava_util_InternalStringMap_2V(this);
}
;
_.java_util_InternalStringMap_size = 0;
_.java_util_InternalStringMap_valueMod = 0;
var com_google_gwt_lang_ClassLiteralHolder_Ljava_1util_1InternalStringMap_12_1classLit = java_lang_Class_createForClass__Ljava_lang_String_2Ljava_lang_String_2Lcom_google_gwt_core_client_JavaScriptObject_2Ljava_lang_Class_2Ljava_lang_Class_2($intern_52, 'InternalStringMap', 156);
function java_util_InternalStringMap$1_InternalStringMap$1__Ljava_util_InternalStringMap_2V(this$0){
  this.java_util_InternalStringMap$1_this$01 = this$0;
  this.java_util_InternalStringMap$1_entries = this.java_util_InternalStringMap$1_this$01.java_util_InternalStringMap_backingMap.entries();
  this.java_util_InternalStringMap$1_current = this.java_util_InternalStringMap$1_entries.next();
}

com_google_gwt_lang_Runtime_defineClass__Lcom_google_gwt_core_client_JavaScriptObject_2Lcom_google_gwt_core_client_JavaScriptObject_2Lcom_google_gwt_core_client_JavaScriptObject_2V(95, 1, {}, java_util_InternalStringMap$1_InternalStringMap$1__Ljava_util_InternalStringMap_2V);
_.next__Ljava_lang_Object_2 = function java_util_InternalStringMap$1_next__Ljava_lang_Object_2(){
  return this.java_util_InternalStringMap$1_last = this.java_util_InternalStringMap$1_current , this.java_util_InternalStringMap$1_current = this.java_util_InternalStringMap$1_entries.next() , new java_util_InternalStringMap$2_InternalStringMap$2__Ljava_util_InternalStringMap_2V(this.java_util_InternalStringMap$1_this$01, this.java_util_InternalStringMap$1_last, this.java_util_InternalStringMap$1_this$01.java_util_InternalStringMap_valueMod);
}
;
_.hasNext__Z = function java_util_InternalStringMap$1_hasNext__Z(){
  return !this.java_util_InternalStringMap$1_current.done;
}
;
var com_google_gwt_lang_ClassLiteralHolder_Ljava_1util_1InternalStringMap$1_12_1classLit = java_lang_Class_createForClass__Ljava_lang_String_2Ljava_lang_String_2Lcom_google_gwt_core_client_JavaScriptObject_2Ljava_lang_Class_2Ljava_lang_Class_2($intern_52, 'InternalStringMap/1', 95);
function java_util_InternalStringMap$2_$getValue__Ljava_util_InternalStringMap$2_2Ljava_lang_Object_2(this$static){
  if (this$static.java_util_InternalStringMap$2_this$01.java_util_InternalStringMap_valueMod != this$static.java_util_InternalStringMap$2_val$lastValueMod3) {
    return java_util_InternalStringMap_$get__Ljava_util_InternalStringMap_2Ljava_lang_String_2Ljava_lang_Object_2(this$static.java_util_InternalStringMap$2_this$01, this$static.java_util_InternalStringMap$2_val$entry2.value[0]);
  }
  return this$static.java_util_InternalStringMap$2_val$entry2.value[1];
}

function java_util_InternalStringMap$2_InternalStringMap$2__Ljava_util_InternalStringMap_2V(this$0, val$entry, val$lastValueMod){
  this.java_util_InternalStringMap$2_this$01 = this$0;
  this.java_util_InternalStringMap$2_val$entry2 = val$entry;
  this.java_util_InternalStringMap$2_val$lastValueMod3 = val$lastValueMod;
}

com_google_gwt_lang_Runtime_defineClass__Lcom_google_gwt_core_client_JavaScriptObject_2Lcom_google_gwt_core_client_JavaScriptObject_2Lcom_google_gwt_core_client_JavaScriptObject_2V(157, 234, $intern_54, java_util_InternalStringMap$2_InternalStringMap$2__Ljava_util_InternalStringMap_2V);
_.getKey__Ljava_lang_Object_2 = function java_util_InternalStringMap$2_getKey__Ljava_lang_Object_2(){
  return this.java_util_InternalStringMap$2_val$entry2.value[0];
}
;
_.getValue__Ljava_lang_Object_2 = function java_util_InternalStringMap$2_getValue__Ljava_lang_Object_2(){
  return java_util_InternalStringMap$2_$getValue__Ljava_util_InternalStringMap$2_2Ljava_lang_Object_2(this);
}
;
_.setValue__Ljava_lang_Object_2Ljava_lang_Object_2 = function java_util_InternalStringMap$2_setValue__Ljava_lang_Object_2Ljava_lang_Object_2(object){
  return java_util_InternalStringMap_$put__Ljava_util_InternalStringMap_2Ljava_lang_String_2Ljava_lang_Object_2Ljava_lang_Object_2(this.java_util_InternalStringMap$2_this$01, this.java_util_InternalStringMap$2_val$entry2.value[0], object);
}
;
_.java_util_InternalStringMap$2_val$lastValueMod3 = 0;
var com_google_gwt_lang_ClassLiteralHolder_Ljava_1util_1InternalStringMap$2_12_1classLit = java_lang_Class_createForClass__Ljava_lang_String_2Ljava_lang_String_2Lcom_google_gwt_core_client_JavaScriptObject_2Ljava_lang_Class_2Ljava_lang_Class_2($intern_52, 'InternalStringMap/2', 157);
function java_util_NoSuchElementException_NoSuchElementException__V(){
  java_lang_RuntimeException_RuntimeException__V.call(this);
}

com_google_gwt_lang_Runtime_defineClass__Lcom_google_gwt_core_client_JavaScriptObject_2Lcom_google_gwt_core_client_JavaScriptObject_2Lcom_google_gwt_core_client_JavaScriptObject_2V(36, 10, $intern_27, java_util_NoSuchElementException_NoSuchElementException__V);
var com_google_gwt_lang_ClassLiteralHolder_Ljava_1util_1NoSuchElementException_12_1classLit = java_lang_Class_createForClass__Ljava_lang_String_2Ljava_lang_String_2Lcom_google_gwt_core_client_JavaScriptObject_2Ljava_lang_Class_2Ljava_lang_Class_2($intern_52, 'NoSuchElementException', 36);
function java_util_Objects_equals__Ljava_lang_Object_2Ljava_lang_Object_2Z(a, b){
  return com_google_gwt_lang_Cast_maskUndefined__Ljava_lang_Object_2Ljava_lang_Object_2(a) === com_google_gwt_lang_Cast_maskUndefined__Ljava_lang_Object_2Ljava_lang_Object_2(b) || a != null && java_lang_Object_equals_1Ljava_1lang_1Object_1_1Z_1_1devirtual$__Ljava_lang_Object_2Ljava_lang_Object_2Z(a, b);
}

function java_util_Objects_hashCode__Ljava_lang_Object_2I(o){
  return o != null?java_lang_Object_hashCode_1_1I_1_1devirtual$__Ljava_lang_Object_2I(o):0;
}

function java_util_StringJoiner_$add__Ljava_util_StringJoiner_2Ljava_lang_CharSequence_2Ljava_util_StringJoiner_2(this$static, newElement){
  !this$static.java_util_StringJoiner_builder?(this$static.java_util_StringJoiner_builder = new java_lang_StringBuilder_StringBuilder__Ljava_lang_String_2V(this$static.java_util_StringJoiner_prefix)):java_lang_StringBuilder_$append__Ljava_lang_StringBuilder_2Ljava_lang_String_2Ljava_lang_StringBuilder_2(this$static.java_util_StringJoiner_builder, this$static.java_util_StringJoiner_delimiter);
  java_lang_StringBuilder_$append__Ljava_lang_StringBuilder_2Ljava_lang_CharSequence_2Ljava_lang_StringBuilder_2(this$static.java_util_StringJoiner_builder, newElement);
  return this$static;
}

function java_util_StringJoiner_StringJoiner__Ljava_lang_CharSequence_2Ljava_lang_CharSequence_2Ljava_lang_CharSequence_2V(prefix, suffix){
  this.java_util_StringJoiner_delimiter = ', ';
  this.java_util_StringJoiner_prefix = prefix;
  this.java_util_StringJoiner_suffix = suffix;
  this.java_util_StringJoiner_emptyValue = this.java_util_StringJoiner_prefix + ('' + this.java_util_StringJoiner_suffix);
}

com_google_gwt_lang_Runtime_defineClass__Lcom_google_gwt_core_client_JavaScriptObject_2Lcom_google_gwt_core_client_JavaScriptObject_2Lcom_google_gwt_core_client_JavaScriptObject_2V(87, 1, {}, java_util_StringJoiner_StringJoiner__Ljava_lang_CharSequence_2Ljava_lang_CharSequence_2Ljava_lang_CharSequence_2V);
_.toString__Ljava_lang_String_2 = function java_util_StringJoiner_toString__Ljava_lang_String_2(){
  return !this.java_util_StringJoiner_builder?this.java_util_StringJoiner_emptyValue:this.java_util_StringJoiner_suffix.length == 0?this.java_util_StringJoiner_builder.java_lang_AbstractStringBuilder_string:this.java_util_StringJoiner_builder.java_lang_AbstractStringBuilder_string + ('' + this.java_util_StringJoiner_suffix);
}
;
var com_google_gwt_lang_ClassLiteralHolder_Ljava_1util_1StringJoiner_12_1classLit = java_lang_Class_createForClass__Ljava_lang_String_2Ljava_lang_String_2Lcom_google_gwt_core_client_JavaScriptObject_2Ljava_lang_Class_2Ljava_lang_Class_2($intern_52, 'StringJoiner', 87);
function javaemul_internal_ArrayHelper_insertTo__Ljava_lang_Object_2ILjava_lang_Object_2V(array, index_0, value_0){
  array.splice(index_0, 0, value_0);
}

function javaemul_internal_InternalPreconditions_checkCriticalArgument__ZLjava_lang_Object_2V(expression){
  if (!expression) {
    throw com_google_gwt_lang_Exceptions_toJs__Ljava_lang_Object_2Ljava_lang_Object_2(new java_lang_IllegalArgumentException_IllegalArgumentException__Ljava_lang_String_2V('Exception can not suppress itself.'));
  }
}

function javaemul_internal_InternalPreconditions_checkCriticalArrayType__ZV(expression){
  if (!expression) {
    throw com_google_gwt_lang_Exceptions_toJs__Ljava_lang_Object_2Ljava_lang_Object_2(new java_lang_ArrayStoreException_ArrayStoreException__V);
  }
}

function javaemul_internal_InternalPreconditions_checkCriticalElement__ZV(expression){
  if (!expression) {
    throw com_google_gwt_lang_Exceptions_toJs__Ljava_lang_Object_2Ljava_lang_Object_2(new java_util_NoSuchElementException_NoSuchElementException__V);
  }
}

function javaemul_internal_InternalPreconditions_checkCriticalElementIndex__IIV(index_0, size_0){
  if (index_0 < 0 || index_0 >= size_0) {
    throw com_google_gwt_lang_Exceptions_toJs__Ljava_lang_Object_2Ljava_lang_Object_2(new java_lang_IndexOutOfBoundsException_IndexOutOfBoundsException__Ljava_lang_String_2V('Index: ' + index_0 + ', Size: ' + size_0));
  }
}

function javaemul_internal_InternalPreconditions_checkCriticalNotNull__Ljava_lang_Object_2Ljava_lang_Object_2(reference){
  if (reference == null) {
    throw com_google_gwt_lang_Exceptions_toJs__Ljava_lang_Object_2Ljava_lang_Object_2(new java_lang_NullPointerException_NullPointerException__V);
  }
  return reference;
}

function javaemul_internal_InternalPreconditions_checkCriticalNotNull__Ljava_lang_Object_2Ljava_lang_Object_2V(reference){
  if (!reference) {
    throw com_google_gwt_lang_Exceptions_toJs__Ljava_lang_Object_2Ljava_lang_Object_2(new java_lang_NullPointerException_NullPointerException__Ljava_lang_String_2V('Cannot suppress a null exception.'));
  }
}

function javaemul_internal_InternalPreconditions_checkCriticalPositionIndex__IIV(index_0, size_0){
  if (index_0 < 0 || index_0 > size_0) {
    throw com_google_gwt_lang_Exceptions_toJs__Ljava_lang_Object_2Ljava_lang_Object_2(new java_lang_IndexOutOfBoundsException_IndexOutOfBoundsException__Ljava_lang_String_2V('Index: ' + index_0 + ', Size: ' + size_0));
  }
}

function javaemul_internal_InternalPreconditions_checkCriticalType__ZV(expression){
  if (!expression) {
    throw com_google_gwt_lang_Exceptions_toJs__Ljava_lang_Object_2Ljava_lang_Object_2(new java_lang_ClassCastException_ClassCastException__V);
  }
}

function javaemul_internal_JsUtils_setPropertySafe__Ljava_lang_Object_2Ljava_lang_String_2Ljava_lang_Object_2V(map_0, key, value_0){
  try {
    map_0[key] = value_0;
  }
   catch (ignored) {
  }
}

function javaemul_internal_ObjectHashing_getHashCode__Ljava_lang_Object_2I(o){
  return o.$H || (o.$H = ++javaemul_internal_ObjectHashing_nextHashId);
}

var javaemul_internal_ObjectHashing_nextHashId = 0;
function javaemul_internal_StringHashCache_$clinit__V(){
  javaemul_internal_StringHashCache_$clinit__V = com_google_gwt_lang_Runtime_emptyMethod__V;
  javaemul_internal_StringHashCache_back = {};
  javaemul_internal_StringHashCache_front = {};
}

function javaemul_internal_StringHashCache_compute__Ljava_lang_String_2I(str){
  var hashCode, i, n, nBatch;
  hashCode = 0;
  n = str.length;
  nBatch = n - 4;
  i = 0;
  while (i < nBatch) {
    hashCode = str.charCodeAt(i + 3) + 31 * (str.charCodeAt(i + 2) + 31 * (str.charCodeAt(i + 1) + 31 * (str.charCodeAt(i) + 31 * hashCode)));
    hashCode = hashCode | 0;
    i += 4;
  }
  while (i < n) {
    hashCode = hashCode * 31 + java_lang_String_$charAt__Ljava_lang_String_2IC(str, i++);
  }
  hashCode = hashCode | 0;
  return hashCode;
}

function javaemul_internal_StringHashCache_getHashCode__Ljava_lang_String_2I(str){
  javaemul_internal_StringHashCache_$clinit__V();
  var hashCode, key, result;
  key = ':' + str;
  result = javaemul_internal_StringHashCache_front[key];
  if (!(result === undefined)) {
    return result;
  }
  result = javaemul_internal_StringHashCache_back[key];
  hashCode = result === undefined?javaemul_internal_StringHashCache_compute__Ljava_lang_String_2I(str):result;
  javaemul_internal_StringHashCache_increment__V();
  javaemul_internal_StringHashCache_front[key] = hashCode;
  return hashCode;
}

function javaemul_internal_StringHashCache_increment__V(){
  if (javaemul_internal_StringHashCache_count == 256) {
    javaemul_internal_StringHashCache_back = javaemul_internal_StringHashCache_front;
    javaemul_internal_StringHashCache_front = {};
    javaemul_internal_StringHashCache_count = 0;
  }
  ++javaemul_internal_StringHashCache_count;
}

var javaemul_internal_StringHashCache_back, javaemul_internal_StringHashCache_count = 0, javaemul_internal_StringHashCache_front;
function org_gwtbootstrap3_client_shared_event_ModalHiddenEvent_$clinit__V(){
  org_gwtbootstrap3_client_shared_event_ModalHiddenEvent_$clinit__V = com_google_gwt_lang_Runtime_emptyMethod__V;
  org_gwtbootstrap3_client_shared_event_ModalHiddenEvent_TYPE = new com_google_gwt_event_shared_GwtEvent$Type_GwtEvent$Type__V;
}

function org_gwtbootstrap3_client_shared_event_ModalHiddenEvent_ModalHiddenEvent__Lorg_gwtbootstrap3_client_ui_Modal_2Lcom_google_gwt_user_client_Event_2V(){
  org_gwtbootstrap3_client_shared_event_ModalHiddenEvent_$clinit__V();
}

com_google_gwt_lang_Runtime_defineClass__Lcom_google_gwt_core_client_JavaScriptObject_2Lcom_google_gwt_core_client_JavaScriptObject_2Lcom_google_gwt_core_client_JavaScriptObject_2V(193, 229, {}, org_gwtbootstrap3_client_shared_event_ModalHiddenEvent_ModalHiddenEvent__Lorg_gwtbootstrap3_client_ui_Modal_2Lcom_google_gwt_user_client_Event_2V);
_.dispatch__Lcom_google_gwt_event_shared_EventHandler_2V = function org_gwtbootstrap3_client_shared_event_ModalHiddenEvent_dispatch__Lcom_google_gwt_event_shared_EventHandler_2V(handler){
  com_google_gwt_lang_Cast_throwClassCastExceptionUnlessNull__Ljava_lang_Object_2Ljava_lang_Object_2(handler);
  null.$_nullMethod();
}
;
_.getAssociatedType__Lcom_google_gwt_event_shared_GwtEvent$Type_2 = function org_gwtbootstrap3_client_shared_event_ModalHiddenEvent_getAssociatedType__Lcom_google_gwt_event_shared_GwtEvent$Type_2(){
  return org_gwtbootstrap3_client_shared_event_ModalHiddenEvent_TYPE;
}
;
var org_gwtbootstrap3_client_shared_event_ModalHiddenEvent_TYPE;
var com_google_gwt_lang_ClassLiteralHolder_Lorg_1gwtbootstrap3_1client_1shared_1event_1ModalHiddenEvent_12_1classLit = java_lang_Class_createForClass__Ljava_lang_String_2Ljava_lang_String_2Lcom_google_gwt_core_client_JavaScriptObject_2Ljava_lang_Class_2Ljava_lang_Class_2($intern_56, 'ModalHiddenEvent', 193);
function org_gwtbootstrap3_client_shared_event_ModalHideEvent_$clinit__V(){
  org_gwtbootstrap3_client_shared_event_ModalHideEvent_$clinit__V = com_google_gwt_lang_Runtime_emptyMethod__V;
  org_gwtbootstrap3_client_shared_event_ModalHideEvent_TYPE = new com_google_gwt_event_shared_GwtEvent$Type_GwtEvent$Type__V;
}

function org_gwtbootstrap3_client_shared_event_ModalHideEvent_ModalHideEvent__Lorg_gwtbootstrap3_client_ui_Modal_2Lcom_google_gwt_user_client_Event_2V(){
  org_gwtbootstrap3_client_shared_event_ModalHideEvent_$clinit__V();
}

com_google_gwt_lang_Runtime_defineClass__Lcom_google_gwt_core_client_JavaScriptObject_2Lcom_google_gwt_core_client_JavaScriptObject_2Lcom_google_gwt_core_client_JavaScriptObject_2V(192, 229, {}, org_gwtbootstrap3_client_shared_event_ModalHideEvent_ModalHideEvent__Lorg_gwtbootstrap3_client_ui_Modal_2Lcom_google_gwt_user_client_Event_2V);
_.dispatch__Lcom_google_gwt_event_shared_EventHandler_2V = function org_gwtbootstrap3_client_shared_event_ModalHideEvent_dispatch__Lcom_google_gwt_event_shared_EventHandler_2V(handler){
  com_google_gwt_lang_Cast_throwClassCastExceptionUnlessNull__Ljava_lang_Object_2Ljava_lang_Object_2(handler);
  null.$_nullMethod();
}
;
_.getAssociatedType__Lcom_google_gwt_event_shared_GwtEvent$Type_2 = function org_gwtbootstrap3_client_shared_event_ModalHideEvent_getAssociatedType__Lcom_google_gwt_event_shared_GwtEvent$Type_2(){
  return org_gwtbootstrap3_client_shared_event_ModalHideEvent_TYPE;
}
;
var org_gwtbootstrap3_client_shared_event_ModalHideEvent_TYPE;
var com_google_gwt_lang_ClassLiteralHolder_Lorg_1gwtbootstrap3_1client_1shared_1event_1ModalHideEvent_12_1classLit = java_lang_Class_createForClass__Ljava_lang_String_2Ljava_lang_String_2Lcom_google_gwt_core_client_JavaScriptObject_2Ljava_lang_Class_2Ljava_lang_Class_2($intern_56, 'ModalHideEvent', 192);
function org_gwtbootstrap3_client_shared_event_ModalShowEvent_$clinit__V(){
  org_gwtbootstrap3_client_shared_event_ModalShowEvent_$clinit__V = com_google_gwt_lang_Runtime_emptyMethod__V;
  org_gwtbootstrap3_client_shared_event_ModalShowEvent_TYPE = new com_google_gwt_event_shared_GwtEvent$Type_GwtEvent$Type__V;
}

function org_gwtbootstrap3_client_shared_event_ModalShowEvent_ModalShowEvent__Lorg_gwtbootstrap3_client_ui_Modal_2Lcom_google_gwt_user_client_Event_2V(){
  org_gwtbootstrap3_client_shared_event_ModalShowEvent_$clinit__V();
}

com_google_gwt_lang_Runtime_defineClass__Lcom_google_gwt_core_client_JavaScriptObject_2Lcom_google_gwt_core_client_JavaScriptObject_2Lcom_google_gwt_core_client_JavaScriptObject_2V(190, 229, {}, org_gwtbootstrap3_client_shared_event_ModalShowEvent_ModalShowEvent__Lorg_gwtbootstrap3_client_ui_Modal_2Lcom_google_gwt_user_client_Event_2V);
_.dispatch__Lcom_google_gwt_event_shared_EventHandler_2V = function org_gwtbootstrap3_client_shared_event_ModalShowEvent_dispatch__Lcom_google_gwt_event_shared_EventHandler_2V(handler){
  com_google_gwt_lang_Cast_throwClassCastExceptionUnlessNull__Ljava_lang_Object_2Ljava_lang_Object_2(handler);
  null.$_nullMethod();
}
;
_.getAssociatedType__Lcom_google_gwt_event_shared_GwtEvent$Type_2 = function org_gwtbootstrap3_client_shared_event_ModalShowEvent_getAssociatedType__Lcom_google_gwt_event_shared_GwtEvent$Type_2(){
  return org_gwtbootstrap3_client_shared_event_ModalShowEvent_TYPE;
}
;
var org_gwtbootstrap3_client_shared_event_ModalShowEvent_TYPE;
var com_google_gwt_lang_ClassLiteralHolder_Lorg_1gwtbootstrap3_1client_1shared_1event_1ModalShowEvent_12_1classLit = java_lang_Class_createForClass__Ljava_lang_String_2Ljava_lang_String_2Lcom_google_gwt_core_client_JavaScriptObject_2Ljava_lang_Class_2Ljava_lang_Class_2($intern_56, 'ModalShowEvent', 190);
function org_gwtbootstrap3_client_shared_event_ModalShownEvent_$clinit__V(){
  org_gwtbootstrap3_client_shared_event_ModalShownEvent_$clinit__V = com_google_gwt_lang_Runtime_emptyMethod__V;
  org_gwtbootstrap3_client_shared_event_ModalShownEvent_TYPE = new com_google_gwt_event_shared_GwtEvent$Type_GwtEvent$Type__V;
}

function org_gwtbootstrap3_client_shared_event_ModalShownEvent_ModalShownEvent__Lorg_gwtbootstrap3_client_ui_Modal_2Lcom_google_gwt_user_client_Event_2V(){
  org_gwtbootstrap3_client_shared_event_ModalShownEvent_$clinit__V();
}

com_google_gwt_lang_Runtime_defineClass__Lcom_google_gwt_core_client_JavaScriptObject_2Lcom_google_gwt_core_client_JavaScriptObject_2Lcom_google_gwt_core_client_JavaScriptObject_2V(191, 229, {}, org_gwtbootstrap3_client_shared_event_ModalShownEvent_ModalShownEvent__Lorg_gwtbootstrap3_client_ui_Modal_2Lcom_google_gwt_user_client_Event_2V);
_.dispatch__Lcom_google_gwt_event_shared_EventHandler_2V = function org_gwtbootstrap3_client_shared_event_ModalShownEvent_dispatch__Lcom_google_gwt_event_shared_EventHandler_2V(handler){
  com_google_gwt_lang_Cast_throwClassCastExceptionUnlessNull__Ljava_lang_Object_2Ljava_lang_Object_2(handler);
  null.$_nullMethod();
}
;
_.getAssociatedType__Lcom_google_gwt_event_shared_GwtEvent$Type_2 = function org_gwtbootstrap3_client_shared_event_ModalShownEvent_getAssociatedType__Lcom_google_gwt_event_shared_GwtEvent$Type_2(){
  return org_gwtbootstrap3_client_shared_event_ModalShownEvent_TYPE;
}
;
var org_gwtbootstrap3_client_shared_event_ModalShownEvent_TYPE;
var com_google_gwt_lang_ClassLiteralHolder_Lorg_1gwtbootstrap3_1client_1shared_1event_1ModalShownEvent_12_1classLit = java_lang_Class_createForClass__Ljava_lang_String_2Ljava_lang_String_2Lcom_google_gwt_core_client_JavaScriptObject_2Ljava_lang_Class_2Ljava_lang_Class_2($intern_56, 'ModalShownEvent', 191);
function org_gwtbootstrap3_client_ui_base_ComplexWidget_$add__Lorg_gwtbootstrap3_client_ui_base_ComplexWidget_2Lcom_google_gwt_user_client_ui_Widget_2V(this$static, child){
  com_google_gwt_user_client_ui_ComplexPanel_$add__Lcom_google_gwt_user_client_ui_ComplexPanel_2Lcom_google_gwt_user_client_ui_Widget_2Lcom_google_gwt_user_client_Element_2V(this$static, child, this$static.com_google_gwt_user_client_ui_UIObject_element);
}

function org_gwtbootstrap3_client_ui_base_ComplexWidget_$insert__Lorg_gwtbootstrap3_client_ui_base_ComplexWidget_2Lcom_google_gwt_user_client_ui_Widget_2IV(this$static, child, beforeIndex){
  org_gwtbootstrap3_client_ui_base_ComplexWidget_$insert__Lorg_gwtbootstrap3_client_ui_base_ComplexWidget_2Lcom_google_gwt_user_client_ui_Widget_2Lcom_google_gwt_user_client_Element_2IZV(this$static, child, this$static.com_google_gwt_user_client_ui_UIObject_element, beforeIndex);
}

function org_gwtbootstrap3_client_ui_base_ComplexWidget_$insert__Lorg_gwtbootstrap3_client_ui_base_ComplexWidget_2Lcom_google_gwt_user_client_ui_Widget_2Lcom_google_gwt_user_client_Element_2IZV(this$static, child, container, beforeIndex){
  var beforeWidget;
  beforeIndex = com_google_gwt_user_client_ui_ComplexPanel_$adjustIndex__Lcom_google_gwt_user_client_ui_ComplexPanel_2Lcom_google_gwt_user_client_ui_Widget_2II(this$static, child, beforeIndex);
  com_google_gwt_user_client_ui_Widget_$removeFromParent__Lcom_google_gwt_user_client_ui_Widget_2V(child);
  com_google_gwt_user_client_ui_WidgetCollection_$insert__Lcom_google_gwt_user_client_ui_WidgetCollection_2Lcom_google_gwt_user_client_ui_Widget_2IV(this$static.com_google_gwt_user_client_ui_ComplexPanel_children, child, beforeIndex);
  beforeWidget = this$static.com_google_gwt_user_client_ui_ComplexPanel_children.com_google_gwt_user_client_ui_WidgetCollection_size > beforeIndex + 1?com_google_gwt_user_client_ui_WidgetCollection_$get__Lcom_google_gwt_user_client_ui_WidgetCollection_2ILcom_google_gwt_user_client_ui_Widget_2(this$static.com_google_gwt_user_client_ui_ComplexPanel_children, beforeIndex + 1):null;
  !beforeWidget?com_google_gwt_dom_client_Node_$appendChild__Lcom_google_gwt_dom_client_Node_2Lcom_google_gwt_dom_client_Node_2Lcom_google_gwt_dom_client_Node_2(container, com_google_gwt_user_client_DOM_resolve__Lcom_google_gwt_dom_client_Element_2Lcom_google_gwt_dom_client_Element_2(child.com_google_gwt_user_client_ui_UIObject_element)):com_google_gwt_dom_client_Node_$insertBefore__Lcom_google_gwt_dom_client_Node_2Lcom_google_gwt_dom_client_Node_2Lcom_google_gwt_dom_client_Node_2Lcom_google_gwt_dom_client_Node_2(container, child.com_google_gwt_user_client_ui_UIObject_element, beforeWidget.com_google_gwt_user_client_ui_UIObject_element);
  com_google_gwt_user_client_ui_Widget_$setParent__Lcom_google_gwt_user_client_ui_Widget_2Lcom_google_gwt_user_client_ui_Widget_2V(child, this$static);
}

function org_gwtbootstrap3_client_ui_base_ComplexWidget_ComplexWidget__V(){
  com_google_gwt_user_client_ui_ComplexPanel_ComplexPanel__V.call(this);
  this.org_gwtbootstrap3_client_ui_base_ComplexWidget_idMixin = new org_gwtbootstrap3_client_ui_base_mixin_IdMixin_IdMixin__Lcom_google_gwt_user_client_ui_UIObject_2V(this);
  new org_gwtbootstrap3_client_ui_base_mixin_PullMixin_PullMixin__Lcom_google_gwt_user_client_ui_UIObject_2V(this);
}

com_google_gwt_lang_Runtime_defineClass__Lcom_google_gwt_core_client_JavaScriptObject_2Lcom_google_gwt_core_client_JavaScriptObject_2Lcom_google_gwt_core_client_JavaScriptObject_2V(19, 41, $intern_46);
_.add__Lcom_google_gwt_user_client_ui_Widget_2V = function org_gwtbootstrap3_client_ui_base_ComplexWidget_add__Lcom_google_gwt_user_client_ui_Widget_2V(child){
  org_gwtbootstrap3_client_ui_base_ComplexWidget_$add__Lorg_gwtbootstrap3_client_ui_base_ComplexWidget_2Lcom_google_gwt_user_client_ui_Widget_2V(this, child);
}
;
_.remove__Lcom_google_gwt_user_client_ui_Widget_2Z = function org_gwtbootstrap3_client_ui_base_ComplexWidget_remove__Lcom_google_gwt_user_client_ui_Widget_2Z(w){
  var elem, parent_0;
  if (w.com_google_gwt_user_client_ui_Widget_parent != this) {
    return false;
  }
  try {
    com_google_gwt_user_client_ui_Widget_$setParent__Lcom_google_gwt_user_client_ui_Widget_2Lcom_google_gwt_user_client_ui_Widget_2V(w, null);
  }
   finally {
    elem = w.com_google_gwt_user_client_ui_UIObject_element;
    parent_0 = com_google_gwt_dom_client_DOMImpl_$getParentElement__Lcom_google_gwt_dom_client_DOMImpl_2Lcom_google_gwt_dom_client_Node_2Lcom_google_gwt_dom_client_Element_2(elem);
    !!parent_0 && parent_0.removeChild(elem);
    com_google_gwt_user_client_ui_WidgetCollection_$remove__Lcom_google_gwt_user_client_ui_WidgetCollection_2Lcom_google_gwt_user_client_ui_Widget_2V(this.com_google_gwt_user_client_ui_ComplexPanel_children, w);
  }
  return true;
}
;
var com_google_gwt_lang_ClassLiteralHolder_Lorg_1gwtbootstrap3_1client_1ui_1base_1ComplexWidget_12_1classLit = java_lang_Class_createForClass__Ljava_lang_String_2Ljava_lang_String_2Lcom_google_gwt_core_client_JavaScriptObject_2Ljava_lang_Class_2Ljava_lang_Class_2($intern_57, 'ComplexWidget', 19);
function org_gwtbootstrap3_client_ui_Anchor_$setHTML__Lorg_gwtbootstrap3_client_ui_Anchor_2Ljava_lang_String_2V(this$static, html){
  com_google_gwt_dom_client_Element_$setInnerHTML__Lcom_google_gwt_dom_client_Element_2Ljava_lang_String_2V(this$static.com_google_gwt_user_client_ui_UIObject_element, html);
}

function org_gwtbootstrap3_client_ui_Anchor_$setText__Lorg_gwtbootstrap3_client_ui_Anchor_2Ljava_lang_String_2V(this$static, text_0){
  org_gwtbootstrap3_client_ui_base_mixin_IconTextMixin_$setText__Lorg_gwtbootstrap3_client_ui_base_mixin_IconTextMixin_2Ljava_lang_String_2V(this$static.org_gwtbootstrap3_client_ui_Anchor_iconTextMixin, text_0);
}

function org_gwtbootstrap3_client_ui_Anchor_Anchor__V(){
  org_gwtbootstrap3_client_ui_base_ComplexWidget_ComplexWidget__V.call(this);
  new org_gwtbootstrap3_client_ui_base_mixin_PullMixin_PullMixin__Lcom_google_gwt_user_client_ui_UIObject_2V(this);
  new org_gwtbootstrap3_client_ui_base_mixin_DataToggleMixin_DataToggleMixin__Lcom_google_gwt_user_client_ui_UIObject_2V(this);
  new org_gwtbootstrap3_client_ui_base_mixin_DataParentMixin_DataParentMixin__Lcom_google_gwt_user_client_ui_UIObject_2V(this);
  this.org_gwtbootstrap3_client_ui_Anchor_iconTextMixin = new org_gwtbootstrap3_client_ui_base_mixin_IconTextMixin_IconTextMixin__Lorg_gwtbootstrap3_client_ui_base_ComplexWidget_2V(this);
  new org_gwtbootstrap3_client_ui_base_mixin_DataTargetMixin_DataTargetMixin__Lcom_google_gwt_user_client_ui_UIObject_2V(this);
  new org_gwtbootstrap3_client_ui_base_mixin_AttributeMixin_AttributeMixin__Lcom_google_gwt_user_client_ui_UIObject_2V(this);
  this.org_gwtbootstrap3_client_ui_Anchor_enabledMixin = new org_gwtbootstrap3_client_ui_base_mixin_EnabledMixin_EnabledMixin__Lcom_google_gwt_user_client_ui_UIObject_2V(this);
  com_google_gwt_user_client_ui_UIObject_$setElement__Lcom_google_gwt_user_client_ui_UIObject_2Lcom_google_gwt_user_client_Element_2V(this, com_google_gwt_dom_client_DOMImplTrident_$createElement__Lcom_google_gwt_dom_client_DOMImplTrident_2Lcom_google_gwt_dom_client_Document_2Ljava_lang_String_2Lcom_google_gwt_dom_client_Element_2($doc, 'a'));
  this.com_google_gwt_user_client_ui_UIObject_element.href = $intern_58;
  org_gwtbootstrap3_client_ui_base_mixin_IconTextMixin_$addTextWidgetToParent__Lorg_gwtbootstrap3_client_ui_base_mixin_IconTextMixin_2V(this.org_gwtbootstrap3_client_ui_Anchor_iconTextMixin);
}

com_google_gwt_lang_Runtime_defineClass__Lcom_google_gwt_core_client_JavaScriptObject_2Lcom_google_gwt_core_client_JavaScriptObject_2Lcom_google_gwt_core_client_JavaScriptObject_2V(77, 19, $intern_46, org_gwtbootstrap3_client_ui_Anchor_Anchor__V);
_.onAttach__V = function org_gwtbootstrap3_client_ui_Anchor_onAttach__V(){
  com_google_gwt_user_client_ui_Widget_$onAttach__Lcom_google_gwt_user_client_ui_Widget_2V(this);
}
;
_.onBrowserEvent__Lcom_google_gwt_user_client_Event_2V = function org_gwtbootstrap3_client_ui_Anchor_onBrowserEvent__Lcom_google_gwt_user_client_Event_2V(event_0){
  var com_google_gwt_dom_client_DOMImplTrident_$hasAttribute__Lcom_google_gwt_dom_client_DOMImplTrident_2Lcom_google_gwt_dom_client_Element_2Ljava_lang_String_2Z_node_0;
  switch (com_google_gwt_user_client_impl_DOMImpl_$eventGetTypeInt__Lcom_google_gwt_user_client_impl_DOMImpl_2Ljava_lang_String_2I(event_0.type)) {
    case 2:
    case 2048:
    case 1:
      if (com_google_gwt_dom_client_DOMImplTrident_$hasAttribute__Lcom_google_gwt_dom_client_DOMImplTrident_2Lcom_google_gwt_dom_client_Element_2Ljava_lang_String_2Z_node_0 = this.org_gwtbootstrap3_client_ui_Anchor_enabledMixin.org_gwtbootstrap3_client_ui_base_mixin_AbstractMixin_uiObject.com_google_gwt_user_client_ui_UIObject_element.getAttributeNode('disabled') , !!(com_google_gwt_dom_client_DOMImplTrident_$hasAttribute__Lcom_google_gwt_dom_client_DOMImplTrident_2Lcom_google_gwt_dom_client_Element_2Ljava_lang_String_2Z_node_0 && com_google_gwt_dom_client_DOMImplTrident_$hasAttribute__Lcom_google_gwt_dom_client_DOMImplTrident_2Lcom_google_gwt_dom_client_Element_2Ljava_lang_String_2Z_node_0.specified)) {
        return;
      }

  }
  com_google_gwt_user_client_ui_Widget_$onBrowserEvent__Lcom_google_gwt_user_client_ui_Widget_2Lcom_google_gwt_user_client_Event_2V(this, event_0);
}
;
_.setText__Ljava_lang_String_2V = function org_gwtbootstrap3_client_ui_Anchor_setText__Ljava_lang_String_2V(text_0){
  org_gwtbootstrap3_client_ui_Anchor_$setText__Lorg_gwtbootstrap3_client_ui_Anchor_2Ljava_lang_String_2V(this, text_0);
}
;
var com_google_gwt_lang_ClassLiteralHolder_Lorg_1gwtbootstrap3_1client_1ui_1Anchor_12_1classLit = java_lang_Class_createForClass__Ljava_lang_String_2Ljava_lang_String_2Lcom_google_gwt_core_client_JavaScriptObject_2Ljava_lang_Class_2Ljava_lang_Class_2($intern_59, 'Anchor', 77);
function org_gwtbootstrap3_client_ui_base_button_AbstractButton_$setDataDismiss__Lorg_gwtbootstrap3_client_ui_base_button_AbstractButton_2Lorg_gwtbootstrap3_client_ui_constants_ButtonDismiss_2V(this$static, dismiss){
  dismiss?com_google_gwt_dom_client_Element_$setAttribute__Lcom_google_gwt_dom_client_Element_2Ljava_lang_String_2Ljava_lang_String_2V(this$static.com_google_gwt_user_client_ui_UIObject_element, $intern_60, dismiss.org_gwtbootstrap3_client_ui_constants_ButtonDismiss_dismiss):(this$static.com_google_gwt_user_client_ui_UIObject_element.removeAttribute($intern_60) , undefined);
}

function org_gwtbootstrap3_client_ui_base_button_AbstractButton_AbstractButton__V(){
  org_gwtbootstrap3_client_ui_base_button_AbstractButton_AbstractButton__Lorg_gwtbootstrap3_client_ui_constants_ButtonType_2V.call(this, (org_gwtbootstrap3_client_ui_constants_ButtonType_$clinit__V() , org_gwtbootstrap3_client_ui_constants_ButtonType_DEFAULT));
}

function org_gwtbootstrap3_client_ui_base_button_AbstractButton_AbstractButton__Lorg_gwtbootstrap3_client_ui_constants_ButtonType_2V(type_0){
  org_gwtbootstrap3_client_ui_base_ComplexWidget_ComplexWidget__V.call(this);
  this.org_gwtbootstrap3_client_ui_base_button_AbstractButton_targetMixin = new org_gwtbootstrap3_client_ui_base_mixin_DataTargetMixin_DataTargetMixin__Lcom_google_gwt_user_client_ui_UIObject_2V(this);
  new org_gwtbootstrap3_client_ui_base_mixin_ActiveMixin_ActiveMixin__Lcom_google_gwt_user_client_ui_UIObject_2V(this);
  new org_gwtbootstrap3_client_ui_base_mixin_EnabledMixin_EnabledMixin__Lcom_google_gwt_user_client_ui_UIObject_2V(this);
  com_google_gwt_user_client_ui_UIObject_$setElement__Lcom_google_gwt_user_client_ui_UIObject_2Lcom_google_gwt_user_client_Element_2V(this, this.createElement__Lcom_google_gwt_dom_client_Element_2());
  this.com_google_gwt_user_client_ui_UIObject_element.className = 'btn';
  org_gwtbootstrap3_client_ui_base_helper_StyleHelper_removeEnumStyleNames__Lcom_google_gwt_user_client_ui_UIObject_2Ljava_lang_Class_2V(this, com_google_gwt_lang_ClassLiteralHolder_Lorg_1gwtbootstrap3_1client_1ui_1constants_1ButtonType_12_1classLit);
  !!type_0 && type_0.org_gwtbootstrap3_client_ui_constants_ButtonType_cssClass != null && type_0.org_gwtbootstrap3_client_ui_constants_ButtonType_cssClass.length != 0 && com_google_gwt_user_client_ui_UIObject_$addStyleName__Lcom_google_gwt_user_client_ui_UIObject_2Ljava_lang_String_2V(this, type_0.org_gwtbootstrap3_client_ui_constants_ButtonType_cssClass);
}

com_google_gwt_lang_Runtime_defineClass__Lcom_google_gwt_core_client_JavaScriptObject_2Lcom_google_gwt_core_client_JavaScriptObject_2Lcom_google_gwt_core_client_JavaScriptObject_2V(74, 19, $intern_46);
var com_google_gwt_lang_ClassLiteralHolder_Lorg_1gwtbootstrap3_1client_1ui_1base_1button_1AbstractButton_12_1classLit = java_lang_Class_createForClass__Ljava_lang_String_2Ljava_lang_String_2Lcom_google_gwt_core_client_JavaScriptObject_2Ljava_lang_Class_2Ljava_lang_Class_2($intern_61, 'AbstractButton', 74);
com_google_gwt_lang_Runtime_defineClass__Lcom_google_gwt_core_client_JavaScriptObject_2Lcom_google_gwt_core_client_JavaScriptObject_2Lcom_google_gwt_core_client_JavaScriptObject_2V(142, 74, $intern_46);
_.setText__Ljava_lang_String_2V = function org_gwtbootstrap3_client_ui_base_button_AbstractIconButton_setText__Ljava_lang_String_2V(text_0){
  org_gwtbootstrap3_client_ui_base_mixin_IconTextMixin_$setText__Lorg_gwtbootstrap3_client_ui_base_mixin_IconTextMixin_2Ljava_lang_String_2V(this.org_gwtbootstrap3_client_ui_base_button_AbstractIconButton_iconTextMixin, text_0);
}
;
var com_google_gwt_lang_ClassLiteralHolder_Lorg_1gwtbootstrap3_1client_1ui_1base_1button_1AbstractIconButton_12_1classLit = java_lang_Class_createForClass__Ljava_lang_String_2Ljava_lang_String_2Lcom_google_gwt_core_client_JavaScriptObject_2Ljava_lang_Class_2Ljava_lang_Class_2($intern_61, 'AbstractIconButton', 142);
function org_gwtbootstrap3_client_ui_base_button_AbstractToggleButton_$setDataToggle__Lorg_gwtbootstrap3_client_ui_base_button_AbstractToggleButton_2Lorg_gwtbootstrap3_client_ui_constants_Toggle_2V(this$static, toggle){
  org_gwtbootstrap3_client_ui_base_mixin_DataToggleMixin_$setDataToggle__Lorg_gwtbootstrap3_client_ui_base_mixin_DataToggleMixin_2Lorg_gwtbootstrap3_client_ui_constants_Toggle_2V(this$static.org_gwtbootstrap3_client_ui_base_button_AbstractToggleButton_toggleMixin, toggle);
  com_google_gwt_core_client_impl_SchedulerImpl_$scheduleDeferred__Lcom_google_gwt_core_client_impl_SchedulerImpl_2Lcom_google_gwt_core_client_Scheduler$ScheduledCommand_2V((com_google_gwt_core_client_impl_SchedulerImpl_$clinit__V() , com_google_gwt_core_client_impl_SchedulerImpl_INSTANCE), new org_gwtbootstrap3_client_ui_base_button_AbstractToggleButton$1_AbstractToggleButton$1__Lorg_gwtbootstrap3_client_ui_base_button_AbstractToggleButton_2V(this$static, toggle));
}

function org_gwtbootstrap3_client_ui_base_button_AbstractToggleButton_AbstractToggleButton__Lorg_gwtbootstrap3_client_ui_constants_ButtonType_2V(type_0){
  org_gwtbootstrap3_client_ui_base_button_AbstractButton_AbstractButton__V.call(this);
  this.org_gwtbootstrap3_client_ui_base_button_AbstractIconButton_iconTextMixin = new org_gwtbootstrap3_client_ui_base_mixin_IconTextMixin_IconTextMixin__Lorg_gwtbootstrap3_client_ui_base_ComplexWidget_2V(this);
  this.org_gwtbootstrap3_client_ui_base_button_AbstractToggleButton_toggleMixin = new org_gwtbootstrap3_client_ui_base_mixin_DataToggleMixin_DataToggleMixin__Lcom_google_gwt_user_client_ui_UIObject_2V(this);
  this.org_gwtbootstrap3_client_ui_base_button_AbstractToggleButton_separator = new org_gwtbootstrap3_client_ui_html_Text_Text__Ljava_lang_String_2V(' ');
  this.org_gwtbootstrap3_client_ui_base_button_AbstractToggleButton_caret = new org_gwtbootstrap3_client_ui_base_button_Caret_Caret__V;
  org_gwtbootstrap3_client_ui_base_helper_StyleHelper_removeEnumStyleNames__Lcom_google_gwt_user_client_ui_UIObject_2Ljava_lang_Class_2V(this, com_google_gwt_lang_ClassLiteralHolder_Lorg_1gwtbootstrap3_1client_1ui_1constants_1ButtonType_12_1classLit);
  !!type_0 && type_0.org_gwtbootstrap3_client_ui_constants_ButtonType_cssClass != null && type_0.org_gwtbootstrap3_client_ui_constants_ButtonType_cssClass.length != 0 && com_google_gwt_user_client_ui_UIObject_$addStyleName__Lcom_google_gwt_user_client_ui_UIObject_2Ljava_lang_String_2V(this, type_0.org_gwtbootstrap3_client_ui_constants_ButtonType_cssClass);
  org_gwtbootstrap3_client_ui_base_mixin_IconTextMixin_$addTextWidgetToParent__Lorg_gwtbootstrap3_client_ui_base_mixin_IconTextMixin_2V(this.org_gwtbootstrap3_client_ui_base_button_AbstractIconButton_iconTextMixin);
}

com_google_gwt_lang_Runtime_defineClass__Lcom_google_gwt_core_client_JavaScriptObject_2Lcom_google_gwt_core_client_JavaScriptObject_2Lcom_google_gwt_core_client_JavaScriptObject_2V(22, 142, $intern_62);
var com_google_gwt_lang_ClassLiteralHolder_Lorg_1gwtbootstrap3_1client_1ui_1base_1button_1AbstractToggleButton_12_1classLit = java_lang_Class_createForClass__Ljava_lang_String_2Ljava_lang_String_2Lcom_google_gwt_core_client_JavaScriptObject_2Ljava_lang_Class_2Ljava_lang_Class_2($intern_61, 'AbstractToggleButton', 22);
function org_gwtbootstrap3_client_ui_AnchorButton_$clinit__V(){
  org_gwtbootstrap3_client_ui_AnchorButton_$clinit__V = com_google_gwt_lang_Runtime_emptyMethod__V;
  com_google_gwt_user_client_ui_impl_HyperlinkImplIE_$clinit__V();
}

function org_gwtbootstrap3_client_ui_AnchorButton_AnchorButton__V(){
  org_gwtbootstrap3_client_ui_AnchorButton_$clinit__V();
  org_gwtbootstrap3_client_ui_AnchorButton_AnchorButton__Lorg_gwtbootstrap3_client_ui_constants_ButtonType_2V.call(this, (org_gwtbootstrap3_client_ui_constants_ButtonType_$clinit__V() , org_gwtbootstrap3_client_ui_constants_ButtonType_DEFAULT));
}

function org_gwtbootstrap3_client_ui_AnchorButton_AnchorButton__Lorg_gwtbootstrap3_client_ui_constants_ButtonType_2V(type_0){
  org_gwtbootstrap3_client_ui_base_button_AbstractToggleButton_AbstractToggleButton__Lorg_gwtbootstrap3_client_ui_constants_ButtonType_2V.call(this, type_0);
  this.com_google_gwt_user_client_ui_UIObject_element.href = $intern_58;
  this.com_google_gwt_user_client_ui_Widget_eventsToSink == -1?com_google_gwt_user_client_DOM_sinkEvents__Lcom_google_gwt_dom_client_Element_2IV(this.com_google_gwt_user_client_ui_UIObject_element, 1 | (this.com_google_gwt_user_client_ui_UIObject_element.__eventBits || 0)):(this.com_google_gwt_user_client_ui_Widget_eventsToSink |= 1);
}

com_google_gwt_lang_Runtime_defineClass__Lcom_google_gwt_core_client_JavaScriptObject_2Lcom_google_gwt_core_client_JavaScriptObject_2Lcom_google_gwt_core_client_JavaScriptObject_2V(52, 22, {5:1, 7:1, 4:1, 52:1, 22:1}, org_gwtbootstrap3_client_ui_AnchorButton_AnchorButton__V);
_.createElement__Lcom_google_gwt_dom_client_Element_2 = function org_gwtbootstrap3_client_ui_AnchorButton_createElement__Lcom_google_gwt_dom_client_Element_2(){
  return com_google_gwt_dom_client_DOMImplTrident_$createElement__Lcom_google_gwt_dom_client_DOMImplTrident_2Lcom_google_gwt_dom_client_Document_2Ljava_lang_String_2Lcom_google_gwt_dom_client_Element_2($doc, 'a');
}
;
_.onBrowserEvent__Lcom_google_gwt_user_client_Event_2V = function org_gwtbootstrap3_client_ui_AnchorButton_onBrowserEvent__Lcom_google_gwt_user_client_Event_2V(event_0){
  com_google_gwt_user_client_ui_Widget_$onBrowserEvent__Lcom_google_gwt_user_client_ui_Widget_2Lcom_google_gwt_user_client_Event_2V(this, event_0);
}
;
var com_google_gwt_lang_ClassLiteralHolder_Lorg_1gwtbootstrap3_1client_1ui_1AnchorButton_12_1classLit = java_lang_Class_createForClass__Ljava_lang_String_2Ljava_lang_String_2Lcom_google_gwt_core_client_JavaScriptObject_2Ljava_lang_Class_2Ljava_lang_Class_2($intern_59, 'AnchorButton', 52);
function org_gwtbootstrap3_client_ui_base_AbstractListItem_AbstractListItem__V(){
  org_gwtbootstrap3_client_ui_base_ComplexWidget_ComplexWidget__V.call(this);
  new org_gwtbootstrap3_client_ui_base_mixin_ActiveMixin_ActiveMixin__Lcom_google_gwt_user_client_ui_UIObject_2V(this);
  new org_gwtbootstrap3_client_ui_base_mixin_PullMixin_PullMixin__Lcom_google_gwt_user_client_ui_UIObject_2V(this);
  new org_gwtbootstrap3_client_ui_base_mixin_IdMixin_IdMixin__Lcom_google_gwt_user_client_ui_UIObject_2V(this);
  new org_gwtbootstrap3_client_ui_base_mixin_EnabledMixin_EnabledMixin__Lcom_google_gwt_user_client_ui_UIObject_2V(this);
  com_google_gwt_user_client_ui_UIObject_$setElement__Lcom_google_gwt_user_client_ui_UIObject_2Lcom_google_gwt_user_client_Element_2V(this, com_google_gwt_dom_client_DOMImplTrident_$createElement__Lcom_google_gwt_dom_client_DOMImplTrident_2Lcom_google_gwt_dom_client_Document_2Ljava_lang_String_2Lcom_google_gwt_dom_client_Element_2($doc, 'li'));
}

com_google_gwt_lang_Runtime_defineClass__Lcom_google_gwt_core_client_JavaScriptObject_2Lcom_google_gwt_core_client_JavaScriptObject_2Lcom_google_gwt_core_client_JavaScriptObject_2V(92, 19, $intern_46);
var com_google_gwt_lang_ClassLiteralHolder_Lorg_1gwtbootstrap3_1client_1ui_1base_1AbstractListItem_12_1classLit = java_lang_Class_createForClass__Ljava_lang_String_2Ljava_lang_String_2Lcom_google_gwt_core_client_JavaScriptObject_2Ljava_lang_Class_2Ljava_lang_Class_2($intern_57, 'AbstractListItem', 92);
function org_gwtbootstrap3_client_ui_base_AbstractAnchorListItem_$addClickHandler__Lorg_gwtbootstrap3_client_ui_base_AbstractAnchorListItem_2Lcom_google_gwt_event_dom_client_ClickHandler_2Lcom_google_gwt_event_shared_HandlerRegistration_2(this$static, handler){
  return com_google_gwt_user_client_ui_Widget_$addHandler__Lcom_google_gwt_user_client_ui_Widget_2Lcom_google_gwt_event_shared_EventHandler_2Lcom_google_gwt_event_shared_GwtEvent$Type_2Lcom_google_gwt_event_shared_HandlerRegistration_2(this$static.org_gwtbootstrap3_client_ui_base_AbstractAnchorListItem_anchor, handler, (com_google_gwt_event_dom_client_ClickEvent_$clinit__V() , com_google_gwt_event_dom_client_ClickEvent_$clinit__V() , com_google_gwt_event_dom_client_ClickEvent_TYPE));
}

com_google_gwt_lang_Runtime_defineClass__Lcom_google_gwt_core_client_JavaScriptObject_2Lcom_google_gwt_core_client_JavaScriptObject_2Lcom_google_gwt_core_client_JavaScriptObject_2V(140, 92, $intern_46);
var com_google_gwt_lang_ClassLiteralHolder_Lorg_1gwtbootstrap3_1client_1ui_1base_1AbstractAnchorListItem_12_1classLit = java_lang_Class_createForClass__Ljava_lang_String_2Ljava_lang_String_2Lcom_google_gwt_core_client_JavaScriptObject_2Ljava_lang_Class_2Ljava_lang_Class_2($intern_57, 'AbstractAnchorListItem', 140);
function org_gwtbootstrap3_client_ui_AnchorListItem_AnchorListItem__V(){
  org_gwtbootstrap3_client_ui_base_AbstractListItem_AbstractListItem__V.call(this);
  this.org_gwtbootstrap3_client_ui_base_AbstractAnchorListItem_anchor = new org_gwtbootstrap3_client_ui_Anchor_Anchor__V;
  com_google_gwt_user_client_ui_Widget_$addDomHandler__Lcom_google_gwt_user_client_ui_Widget_2Lcom_google_gwt_event_shared_EventHandler_2Lcom_google_gwt_event_dom_client_DomEvent$Type_2Lcom_google_gwt_event_shared_HandlerRegistration_2(this.org_gwtbootstrap3_client_ui_base_AbstractAnchorListItem_anchor, new org_gwtbootstrap3_client_ui_base_AbstractAnchorListItem$1_AbstractAnchorListItem$1__Lorg_gwtbootstrap3_client_ui_base_AbstractAnchorListItem_2V(this), (com_google_gwt_event_dom_client_ClickEvent_$clinit__V() , com_google_gwt_event_dom_client_ClickEvent_$clinit__V() , com_google_gwt_event_dom_client_ClickEvent_TYPE));
  com_google_gwt_user_client_ui_ComplexPanel_$add__Lcom_google_gwt_user_client_ui_ComplexPanel_2Lcom_google_gwt_user_client_ui_Widget_2Lcom_google_gwt_user_client_Element_2V(this, this.org_gwtbootstrap3_client_ui_base_AbstractAnchorListItem_anchor, this.com_google_gwt_user_client_ui_UIObject_element);
}

com_google_gwt_lang_Runtime_defineClass__Lcom_google_gwt_core_client_JavaScriptObject_2Lcom_google_gwt_core_client_JavaScriptObject_2Lcom_google_gwt_core_client_JavaScriptObject_2V(42, 140, $intern_46, org_gwtbootstrap3_client_ui_AnchorListItem_AnchorListItem__V);
_.setText__Ljava_lang_String_2V = function org_gwtbootstrap3_client_ui_AnchorListItem_setText__Ljava_lang_String_2V(text_0){
  org_gwtbootstrap3_client_ui_Anchor_$setText__Lorg_gwtbootstrap3_client_ui_Anchor_2Ljava_lang_String_2V(this.org_gwtbootstrap3_client_ui_base_AbstractAnchorListItem_anchor, text_0);
}
;
var com_google_gwt_lang_ClassLiteralHolder_Lorg_1gwtbootstrap3_1client_1ui_1AnchorListItem_12_1classLit = java_lang_Class_createForClass__Ljava_lang_String_2Ljava_lang_String_2Lcom_google_gwt_core_client_JavaScriptObject_2Ljava_lang_Class_2Ljava_lang_Class_2($intern_59, 'AnchorListItem', 42);
function org_gwtbootstrap3_client_ui_Badge_Badge__V(){
  org_gwtbootstrap3_client_ui_base_ComplexWidget_ComplexWidget__V.call(this);
  this.org_gwtbootstrap3_client_ui_Badge_text = new org_gwtbootstrap3_client_ui_html_Text_Text__V;
  com_google_gwt_user_client_ui_UIObject_$setElement__Lcom_google_gwt_user_client_ui_UIObject_2Lcom_google_gwt_user_client_Element_2V(this, com_google_gwt_dom_client_DOMImplTrident_$createElement__Lcom_google_gwt_dom_client_DOMImplTrident_2Lcom_google_gwt_dom_client_Document_2Ljava_lang_String_2Lcom_google_gwt_dom_client_Element_2($doc, $intern_47));
  this.com_google_gwt_user_client_ui_UIObject_element.className = 'badge';
}

com_google_gwt_lang_Runtime_defineClass__Lcom_google_gwt_core_client_JavaScriptObject_2Lcom_google_gwt_core_client_JavaScriptObject_2Lcom_google_gwt_core_client_JavaScriptObject_2V(206, 19, $intern_46, org_gwtbootstrap3_client_ui_Badge_Badge__V);
_.setText__Ljava_lang_String_2V = function org_gwtbootstrap3_client_ui_Badge_setText__Ljava_lang_String_2V(text_0){
  org_gwtbootstrap3_client_ui_html_Text_$setText__Lorg_gwtbootstrap3_client_ui_html_Text_2Ljava_lang_String_2V(this.org_gwtbootstrap3_client_ui_Badge_text, text_0);
  org_gwtbootstrap3_client_ui_base_ComplexWidget_$insert__Lorg_gwtbootstrap3_client_ui_base_ComplexWidget_2Lcom_google_gwt_user_client_ui_Widget_2IV(this, this.org_gwtbootstrap3_client_ui_Badge_text, 0);
}
;
var com_google_gwt_lang_ClassLiteralHolder_Lorg_1gwtbootstrap3_1client_1ui_1Badge_12_1classLit = java_lang_Class_createForClass__Ljava_lang_String_2Ljava_lang_String_2Lcom_google_gwt_core_client_JavaScriptObject_2Ljava_lang_Class_2Ljava_lang_Class_2($intern_59, 'Badge', 206);
function org_gwtbootstrap3_client_ui_Button_Button__V(){
  org_gwtbootstrap3_client_ui_base_button_AbstractToggleButton_AbstractToggleButton__Lorg_gwtbootstrap3_client_ui_constants_ButtonType_2V.call(this, (org_gwtbootstrap3_client_ui_constants_ButtonType_$clinit__V() , org_gwtbootstrap3_client_ui_constants_ButtonType_DEFAULT));
}

com_google_gwt_lang_Runtime_defineClass__Lcom_google_gwt_core_client_JavaScriptObject_2Lcom_google_gwt_core_client_JavaScriptObject_2Lcom_google_gwt_core_client_JavaScriptObject_2V(80, 22, $intern_62, org_gwtbootstrap3_client_ui_Button_Button__V);
_.createElement__Lcom_google_gwt_dom_client_Element_2 = function org_gwtbootstrap3_client_ui_Button_createElement__Lcom_google_gwt_dom_client_Element_2(){
  return $doc.createElement($intern_63);
}
;
var com_google_gwt_lang_ClassLiteralHolder_Lorg_1gwtbootstrap3_1client_1ui_1Button_12_1classLit = java_lang_Class_createForClass__Ljava_lang_String_2Ljava_lang_String_2Lcom_google_gwt_core_client_JavaScriptObject_2Ljava_lang_Class_2Ljava_lang_Class_2($intern_59, 'Button', 80);
function org_gwtbootstrap3_client_ui_html_UnorderedList_UnorderedList__V(){
  org_gwtbootstrap3_client_ui_base_ComplexWidget_ComplexWidget__V.call(this);
  com_google_gwt_user_client_ui_UIObject_$setElement__Lcom_google_gwt_user_client_ui_UIObject_2Lcom_google_gwt_user_client_Element_2V(this, com_google_gwt_dom_client_DOMImplTrident_$createElement__Lcom_google_gwt_dom_client_DOMImplTrident_2Lcom_google_gwt_dom_client_Document_2Ljava_lang_String_2Lcom_google_gwt_dom_client_Element_2($doc, 'ul'));
}

com_google_gwt_lang_Runtime_defineClass__Lcom_google_gwt_core_client_JavaScriptObject_2Lcom_google_gwt_core_client_JavaScriptObject_2Lcom_google_gwt_core_client_JavaScriptObject_2V(101, 19, $intern_46);
var com_google_gwt_lang_ClassLiteralHolder_Lorg_1gwtbootstrap3_1client_1ui_1html_1UnorderedList_12_1classLit = java_lang_Class_createForClass__Ljava_lang_String_2Ljava_lang_String_2Lcom_google_gwt_core_client_JavaScriptObject_2Ljava_lang_Class_2Ljava_lang_Class_2($intern_64, 'UnorderedList', 101);
function org_gwtbootstrap3_client_ui_DropDownMenu_DropDownMenu__V(){
  org_gwtbootstrap3_client_ui_html_UnorderedList_UnorderedList__V.call(this);
  new org_gwtbootstrap3_client_ui_base_mixin_PullMixin_PullMixin__Lcom_google_gwt_user_client_ui_UIObject_2V(this);
  this.com_google_gwt_user_client_ui_UIObject_element.className = 'dropdown-menu';
  this.com_google_gwt_user_client_ui_UIObject_element.setAttribute('role', 'menu');
}

com_google_gwt_lang_Runtime_defineClass__Lcom_google_gwt_core_client_JavaScriptObject_2Lcom_google_gwt_core_client_JavaScriptObject_2Lcom_google_gwt_core_client_JavaScriptObject_2V(198, 101, $intern_46, org_gwtbootstrap3_client_ui_DropDownMenu_DropDownMenu__V);
var com_google_gwt_lang_ClassLiteralHolder_Lorg_1gwtbootstrap3_1client_1ui_1DropDownMenu_12_1classLit = java_lang_Class_createForClass__Ljava_lang_String_2Ljava_lang_String_2Lcom_google_gwt_core_client_JavaScriptObject_2Ljava_lang_Class_2Ljava_lang_Class_2($intern_59, 'DropDownMenu', 198);
function org_gwtbootstrap3_client_ui_Heading_$setText__Lorg_gwtbootstrap3_client_ui_Heading_2Ljava_lang_String_2V(this$static, text_0){
  org_gwtbootstrap3_client_ui_html_Text_$setText__Lorg_gwtbootstrap3_client_ui_html_Text_2Ljava_lang_String_2V(this$static.org_gwtbootstrap3_client_ui_Heading_text, text_0);
  org_gwtbootstrap3_client_ui_base_ComplexWidget_$insert__Lorg_gwtbootstrap3_client_ui_base_ComplexWidget_2Lcom_google_gwt_user_client_ui_Widget_2IV(this$static, this$static.org_gwtbootstrap3_client_ui_Heading_text, 0);
}

function org_gwtbootstrap3_client_ui_Heading_Heading__Lorg_gwtbootstrap3_client_ui_constants_HeadingSize_2V(size_0){
  org_gwtbootstrap3_client_ui_base_ComplexWidget_ComplexWidget__V.call(this);
  new org_gwtbootstrap3_client_ui_html_Small_Small__V;
  this.org_gwtbootstrap3_client_ui_Heading_text = new org_gwtbootstrap3_client_ui_html_Text_Text__V;
  com_google_gwt_user_client_ui_UIObject_$setElement__Lcom_google_gwt_user_client_ui_UIObject_2Lcom_google_gwt_user_client_Element_2V(this, com_google_gwt_dom_client_DOMImplTrident_$createElement__Lcom_google_gwt_dom_client_DOMImplTrident_2Lcom_google_gwt_dom_client_Document_2Ljava_lang_String_2Lcom_google_gwt_dom_client_Element_2($doc, 'h' + size_0.org_gwtbootstrap3_client_ui_constants_HeadingSize_headingSize));
}

com_google_gwt_lang_Runtime_defineClass__Lcom_google_gwt_core_client_JavaScriptObject_2Lcom_google_gwt_core_client_JavaScriptObject_2Lcom_google_gwt_core_client_JavaScriptObject_2V(188, 19, $intern_46, org_gwtbootstrap3_client_ui_Heading_Heading__Lorg_gwtbootstrap3_client_ui_constants_HeadingSize_2V);
_.onAttach__V = function org_gwtbootstrap3_client_ui_Heading_onAttach__V(){
  com_google_gwt_user_client_ui_Widget_$onAttach__Lcom_google_gwt_user_client_ui_Widget_2V(this);
}
;
_.setText__Ljava_lang_String_2V = function org_gwtbootstrap3_client_ui_Heading_setText__Ljava_lang_String_2V(text_0){
  org_gwtbootstrap3_client_ui_Heading_$setText__Lorg_gwtbootstrap3_client_ui_Heading_2Ljava_lang_String_2V(this, text_0);
}
;
var com_google_gwt_lang_ClassLiteralHolder_Lorg_1gwtbootstrap3_1client_1ui_1Heading_12_1classLit = java_lang_Class_createForClass__Ljava_lang_String_2Ljava_lang_String_2Lcom_google_gwt_core_client_JavaScriptObject_2Ljava_lang_Class_2Ljava_lang_Class_2($intern_59, 'Heading', 188);
function org_gwtbootstrap3_client_ui_ListDropDown_$add__Lorg_gwtbootstrap3_client_ui_ListDropDown_2Lcom_google_gwt_user_client_ui_Widget_2V(this$static, child){
  if (com_google_gwt_lang_Cast_instanceOf__Ljava_lang_Object_2Lcom_google_gwt_core_client_JavaScriptObject_2Z(child, 22)) {
    if (!com_google_gwt_lang_Cast_instanceOf__Ljava_lang_Object_2Lcom_google_gwt_core_client_JavaScriptObject_2Z(child, 52)) {
      throw com_google_gwt_lang_Exceptions_toJs__Ljava_lang_Object_2Ljava_lang_Object_2(new java_lang_IllegalArgumentException_IllegalArgumentException__Ljava_lang_String_2V('Only buttons of type AnchorButton can be added to ListDropDown'));
    }
    child.com_google_gwt_user_client_ui_UIObject_element.className = $intern_65;
  }
  com_google_gwt_user_client_ui_ComplexPanel_$add__Lcom_google_gwt_user_client_ui_ComplexPanel_2Lcom_google_gwt_user_client_ui_Widget_2Lcom_google_gwt_user_client_Element_2V(this$static, child, this$static.com_google_gwt_user_client_ui_UIObject_element);
}

function org_gwtbootstrap3_client_ui_ListDropDown_ListDropDown__V(){
  org_gwtbootstrap3_client_ui_base_AbstractListItem_AbstractListItem__V.call(this);
  this.com_google_gwt_user_client_ui_UIObject_element.className = 'dropdown';
}

com_google_gwt_lang_Runtime_defineClass__Lcom_google_gwt_core_client_JavaScriptObject_2Lcom_google_gwt_core_client_JavaScriptObject_2Lcom_google_gwt_core_client_JavaScriptObject_2V(189, 92, $intern_46, org_gwtbootstrap3_client_ui_ListDropDown_ListDropDown__V);
_.add__Lcom_google_gwt_user_client_ui_Widget_2V = function org_gwtbootstrap3_client_ui_ListDropDown_add__Lcom_google_gwt_user_client_ui_Widget_2V(child){
  org_gwtbootstrap3_client_ui_ListDropDown_$add__Lorg_gwtbootstrap3_client_ui_ListDropDown_2Lcom_google_gwt_user_client_ui_Widget_2V(this, child);
}
;
var com_google_gwt_lang_ClassLiteralHolder_Lorg_1gwtbootstrap3_1client_1ui_1ListDropDown_12_1classLit = java_lang_Class_createForClass__Ljava_lang_String_2Ljava_lang_String_2Lcom_google_gwt_core_client_JavaScriptObject_2Ljava_lang_Class_2Ljava_lang_Class_2($intern_59, 'ListDropDown', 189);
com_google_gwt_lang_Runtime_defineClass__Lcom_google_gwt_core_client_JavaScriptObject_2Lcom_google_gwt_core_client_JavaScriptObject_2Lcom_google_gwt_core_client_JavaScriptObject_2V(178, 19, $intern_46);
var com_google_gwt_lang_ClassLiteralHolder_Lorg_1gwtbootstrap3_1client_1ui_1html_1Div_12_1classLit = java_lang_Class_createForClass__Ljava_lang_String_2Ljava_lang_String_2Lcom_google_gwt_core_client_JavaScriptObject_2Ljava_lang_Class_2Ljava_lang_Class_2($intern_64, 'Div', 178);
function org_gwtbootstrap3_client_ui_Modal_$add__Lorg_gwtbootstrap3_client_ui_Modal_2Lcom_google_gwt_user_client_ui_Widget_2V(this$static, w){
  if (com_google_gwt_lang_Cast_instanceOf__Ljava_lang_Object_2Lcom_google_gwt_core_client_JavaScriptObject_2Z(w, 38)) {
    com_google_gwt_user_client_ui_Widget_$removeFromParent__Lcom_google_gwt_user_client_ui_Widget_2V(this$static.org_gwtbootstrap3_client_ui_Modal_header);
    this$static.org_gwtbootstrap3_client_ui_Modal_header = com_google_gwt_lang_Cast_castTo__Ljava_lang_Object_2Lcom_google_gwt_core_client_JavaScriptObject_2Ljava_lang_Object_2(w, 38);
  }
  com_google_gwt_lang_Cast_instanceOf__Ljava_lang_Object_2Lcom_google_gwt_core_client_JavaScriptObject_2Z(w, 108)?com_google_gwt_user_client_ui_FlowPanel_$add__Lcom_google_gwt_user_client_ui_FlowPanel_2Lcom_google_gwt_user_client_ui_Widget_2V(this$static.org_gwtbootstrap3_client_ui_Modal_content, w):com_google_gwt_user_client_ui_ComplexPanel_$add__Lcom_google_gwt_user_client_ui_ComplexPanel_2Lcom_google_gwt_user_client_ui_Widget_2Lcom_google_gwt_user_client_Element_2V(this$static, w, this$static.com_google_gwt_user_client_ui_UIObject_element);
}

function org_gwtbootstrap3_client_ui_Modal_$bindJavaScriptEvents__Lorg_gwtbootstrap3_client_ui_Modal_2Lcom_google_gwt_dom_client_Element_2V(this$static, e){
  var target = this$static;
  var $modal = $wnd.jQuery(e);
  $modal.on($intern_66, function(evt){
    target.onShow__Lcom_google_gwt_user_client_Event_2V(evt);
  }
  );
  $modal.on($intern_67, function(evt){
    target.onShown__Lcom_google_gwt_user_client_Event_2V(evt);
  }
  );
  $modal.on($intern_68, function(evt){
    target.onHide__Lcom_google_gwt_user_client_Event_2V(evt);
  }
  );
  $modal.on($intern_69, function(evt){
    target.onHidden__Lcom_google_gwt_user_client_Event_2V(evt);
  }
  );
}

function org_gwtbootstrap3_client_ui_Modal_$modal__Lorg_gwtbootstrap3_client_ui_Modal_2Lcom_google_gwt_dom_client_Element_2Ljava_lang_String_2V(e, arg){
  $wnd.jQuery(e).modal(arg);
}

function org_gwtbootstrap3_client_ui_Modal_$setDataBackdrop__Lorg_gwtbootstrap3_client_ui_Modal_2Lorg_gwtbootstrap3_client_ui_constants_ModalBackdrop_2V(this$static, backdrop){
  backdrop?com_google_gwt_dom_client_Element_$setAttribute__Lcom_google_gwt_dom_client_Element_2Ljava_lang_String_2Ljava_lang_String_2V(this$static.com_google_gwt_user_client_ui_UIObject_element, $intern_70, backdrop.org_gwtbootstrap3_client_ui_constants_ModalBackdrop_backdrop):(this$static.com_google_gwt_user_client_ui_UIObject_element.removeAttribute($intern_70) , undefined);
}

function org_gwtbootstrap3_client_ui_Modal_$show__Lorg_gwtbootstrap3_client_ui_Modal_2V(this$static){
  this$static.com_google_gwt_user_client_ui_Widget_attached || com_google_gwt_user_client_ui_AbsolutePanel_$add__Lcom_google_gwt_user_client_ui_AbsolutePanel_2Lcom_google_gwt_user_client_ui_Widget_2V((com_google_gwt_user_client_ui_RootPanel_$clinit__V() , com_google_gwt_user_client_ui_RootPanel_get__Ljava_lang_String_2Lcom_google_gwt_user_client_ui_RootPanel_2()), this$static);
  org_gwtbootstrap3_client_ui_Modal_$modal__Lorg_gwtbootstrap3_client_ui_Modal_2Lcom_google_gwt_dom_client_Element_2Ljava_lang_String_2V(this$static.com_google_gwt_user_client_ui_UIObject_element, 'show');
}

function org_gwtbootstrap3_client_ui_Modal_$unbindAllHandlers__Lorg_gwtbootstrap3_client_ui_Modal_2Lcom_google_gwt_dom_client_Element_2V(e){
  var $e = $wnd.jQuery(e);
  $e.off($intern_66);
  $e.off($intern_67);
  $e.off($intern_68);
  $e.off($intern_69);
}

function org_gwtbootstrap3_client_ui_Modal_Modal__V(){
  org_gwtbootstrap3_client_ui_base_ComplexWidget_ComplexWidget__V.call(this);
  com_google_gwt_user_client_ui_UIObject_$setElement__Lcom_google_gwt_user_client_ui_UIObject_2Lcom_google_gwt_user_client_Element_2V(this, com_google_gwt_dom_client_DOMImplTrident_$createElement__Lcom_google_gwt_dom_client_DOMImplTrident_2Lcom_google_gwt_dom_client_Document_2Ljava_lang_String_2Lcom_google_gwt_dom_client_Element_2($doc, $intern_30));
  this.org_gwtbootstrap3_client_ui_Modal_content = new org_gwtbootstrap3_client_ui_base_modal_ModalContent_ModalContent__V;
  this.org_gwtbootstrap3_client_ui_Modal_dialog = new org_gwtbootstrap3_client_ui_base_modal_ModalDialog_ModalDialog__V;
  this.org_gwtbootstrap3_client_ui_Modal_header = new org_gwtbootstrap3_client_ui_ModalHeader_ModalHeader__V;
  this.com_google_gwt_user_client_ui_UIObject_element.className = 'modal';
  this.com_google_gwt_user_client_ui_UIObject_element.style['zIndex'] = '1050';
  com_google_gwt_user_client_ui_FlowPanel_$add__Lcom_google_gwt_user_client_ui_FlowPanel_2Lcom_google_gwt_user_client_ui_Widget_2V(this.org_gwtbootstrap3_client_ui_Modal_content, this.org_gwtbootstrap3_client_ui_Modal_header);
  com_google_gwt_user_client_ui_FlowPanel_$add__Lcom_google_gwt_user_client_ui_FlowPanel_2Lcom_google_gwt_user_client_ui_Widget_2V(this.org_gwtbootstrap3_client_ui_Modal_dialog, this.org_gwtbootstrap3_client_ui_Modal_content);
  org_gwtbootstrap3_client_ui_Modal_$add__Lorg_gwtbootstrap3_client_ui_Modal_2Lcom_google_gwt_user_client_ui_Widget_2V(this, this.org_gwtbootstrap3_client_ui_Modal_dialog);
}

com_google_gwt_lang_Runtime_defineClass__Lcom_google_gwt_core_client_JavaScriptObject_2Lcom_google_gwt_core_client_JavaScriptObject_2Lcom_google_gwt_core_client_JavaScriptObject_2V(98, 178, $intern_46, org_gwtbootstrap3_client_ui_Modal_Modal__V);
_.add__Lcom_google_gwt_user_client_ui_Widget_2V = function org_gwtbootstrap3_client_ui_Modal_add__Lcom_google_gwt_user_client_ui_Widget_2V(w){
  org_gwtbootstrap3_client_ui_Modal_$add__Lorg_gwtbootstrap3_client_ui_Modal_2Lcom_google_gwt_user_client_ui_Widget_2V(this, w);
}
;
_.onHidden__Lcom_google_gwt_user_client_Event_2V = function org_gwtbootstrap3_client_ui_Modal_onHidden__Lcom_google_gwt_user_client_Event_2V(evt){
  com_google_gwt_user_client_ui_Widget_$fireEvent__Lcom_google_gwt_user_client_ui_Widget_2Lcom_google_gwt_event_shared_GwtEvent_2V(this, new org_gwtbootstrap3_client_shared_event_ModalHiddenEvent_ModalHiddenEvent__Lorg_gwtbootstrap3_client_ui_Modal_2Lcom_google_gwt_user_client_Event_2V);
}
;
_.onHide__Lcom_google_gwt_user_client_Event_2V = function org_gwtbootstrap3_client_ui_Modal_onHide__Lcom_google_gwt_user_client_Event_2V(evt){
  com_google_gwt_user_client_ui_Widget_$fireEvent__Lcom_google_gwt_user_client_ui_Widget_2Lcom_google_gwt_event_shared_GwtEvent_2V(this, new org_gwtbootstrap3_client_shared_event_ModalHideEvent_ModalHideEvent__Lorg_gwtbootstrap3_client_ui_Modal_2Lcom_google_gwt_user_client_Event_2V);
}
;
_.onLoad__V = function org_gwtbootstrap3_client_ui_Modal_onLoad__V(){
  org_gwtbootstrap3_client_ui_Modal_$bindJavaScriptEvents__Lorg_gwtbootstrap3_client_ui_Modal_2Lcom_google_gwt_dom_client_Element_2V(this, this.com_google_gwt_user_client_ui_UIObject_element);
}
;
_.onShow__Lcom_google_gwt_user_client_Event_2V = function org_gwtbootstrap3_client_ui_Modal_onShow__Lcom_google_gwt_user_client_Event_2V(evt){
  com_google_gwt_user_client_ui_Widget_$fireEvent__Lcom_google_gwt_user_client_ui_Widget_2Lcom_google_gwt_event_shared_GwtEvent_2V(this, new org_gwtbootstrap3_client_shared_event_ModalShowEvent_ModalShowEvent__Lorg_gwtbootstrap3_client_ui_Modal_2Lcom_google_gwt_user_client_Event_2V);
}
;
_.onShown__Lcom_google_gwt_user_client_Event_2V = function org_gwtbootstrap3_client_ui_Modal_onShown__Lcom_google_gwt_user_client_Event_2V(evt){
  com_google_gwt_user_client_ui_Widget_$fireEvent__Lcom_google_gwt_user_client_ui_Widget_2Lcom_google_gwt_event_shared_GwtEvent_2V(this, new org_gwtbootstrap3_client_shared_event_ModalShownEvent_ModalShownEvent__Lorg_gwtbootstrap3_client_ui_Modal_2Lcom_google_gwt_user_client_Event_2V);
}
;
_.onUnload__V = function org_gwtbootstrap3_client_ui_Modal_onUnload__V(){
  org_gwtbootstrap3_client_ui_Modal_$unbindAllHandlers__Lorg_gwtbootstrap3_client_ui_Modal_2Lcom_google_gwt_dom_client_Element_2V(this.com_google_gwt_user_client_ui_UIObject_element);
}
;
var com_google_gwt_lang_ClassLiteralHolder_Lorg_1gwtbootstrap3_1client_1ui_1Modal_12_1classLit = java_lang_Class_createForClass__Ljava_lang_String_2Ljava_lang_String_2Lcom_google_gwt_core_client_JavaScriptObject_2Ljava_lang_Class_2Ljava_lang_Class_2($intern_59, 'Modal', 98);
function org_gwtbootstrap3_client_ui_gwt_FlowPanel_FlowPanel__V(){
  com_google_gwt_user_client_ui_FlowPanel_FlowPanel__V.call(this);
  new org_gwtbootstrap3_client_ui_base_mixin_DataSpyMixin_DataSpyMixin__Lcom_google_gwt_user_client_ui_UIObject_2V(this);
  this.org_gwtbootstrap3_client_ui_gwt_FlowPanel_idMixin = new org_gwtbootstrap3_client_ui_base_mixin_IdMixin_IdMixin__Lcom_google_gwt_user_client_ui_UIObject_2V(this);
  new org_gwtbootstrap3_client_ui_base_mixin_DataTargetMixin_DataTargetMixin__Lcom_google_gwt_user_client_ui_UIObject_2V(this);
}

com_google_gwt_lang_Runtime_defineClass__Lcom_google_gwt_core_client_JavaScriptObject_2Lcom_google_gwt_core_client_JavaScriptObject_2Lcom_google_gwt_core_client_JavaScriptObject_2V(44, 57, $intern_46);
var com_google_gwt_lang_ClassLiteralHolder_Lorg_1gwtbootstrap3_1client_1ui_1gwt_1FlowPanel_12_1classLit = java_lang_Class_createForClass__Ljava_lang_String_2Ljava_lang_String_2Lcom_google_gwt_core_client_JavaScriptObject_2Ljava_lang_Class_2Ljava_lang_Class_2($intern_71, 'FlowPanel', 44);
function org_gwtbootstrap3_client_ui_ModalBody_ModalBody__V(){
  org_gwtbootstrap3_client_ui_gwt_FlowPanel_FlowPanel__V.call(this);
  this.com_google_gwt_user_client_ui_UIObject_element.className = 'modal-body';
}

com_google_gwt_lang_Runtime_defineClass__Lcom_google_gwt_core_client_JavaScriptObject_2Lcom_google_gwt_core_client_JavaScriptObject_2Lcom_google_gwt_core_client_JavaScriptObject_2V(99, 44, $intern_72, org_gwtbootstrap3_client_ui_ModalBody_ModalBody__V);
var com_google_gwt_lang_ClassLiteralHolder_Lorg_1gwtbootstrap3_1client_1ui_1ModalBody_12_1classLit = java_lang_Class_createForClass__Ljava_lang_String_2Ljava_lang_String_2Lcom_google_gwt_core_client_JavaScriptObject_2Ljava_lang_Class_2Ljava_lang_Class_2($intern_59, 'ModalBody', 99);
function org_gwtbootstrap3_client_ui_ModalFooter_ModalFooter__V(){
  org_gwtbootstrap3_client_ui_gwt_FlowPanel_FlowPanel__V.call(this);
  this.com_google_gwt_user_client_ui_UIObject_element.className = 'modal-footer';
}

com_google_gwt_lang_Runtime_defineClass__Lcom_google_gwt_core_client_JavaScriptObject_2Lcom_google_gwt_core_client_JavaScriptObject_2Lcom_google_gwt_core_client_JavaScriptObject_2V(100, 44, $intern_72, org_gwtbootstrap3_client_ui_ModalFooter_ModalFooter__V);
var com_google_gwt_lang_ClassLiteralHolder_Lorg_1gwtbootstrap3_1client_1ui_1ModalFooter_12_1classLit = java_lang_Class_createForClass__Ljava_lang_String_2Ljava_lang_String_2Lcom_google_gwt_core_client_JavaScriptObject_2Ljava_lang_Class_2Ljava_lang_Class_2($intern_59, 'ModalFooter', 100);
function org_gwtbootstrap3_client_ui_ModalHeader_$setClosable__Lorg_gwtbootstrap3_client_ui_ModalHeader_2ZV(this$static){
  com_google_gwt_user_client_ui_ComplexPanel_$insert__Lcom_google_gwt_user_client_ui_ComplexPanel_2Lcom_google_gwt_user_client_ui_Widget_2Lcom_google_gwt_user_client_Element_2IZV(this$static, this$static.org_gwtbootstrap3_client_ui_ModalHeader_closeButton, this$static.com_google_gwt_user_client_ui_UIObject_element, 0);
}

function org_gwtbootstrap3_client_ui_ModalHeader_$setTitle__Lorg_gwtbootstrap3_client_ui_ModalHeader_2Ljava_lang_String_2V(this$static, title_0){
  org_gwtbootstrap3_client_ui_Heading_$setText__Lorg_gwtbootstrap3_client_ui_Heading_2Ljava_lang_String_2V(this$static.org_gwtbootstrap3_client_ui_ModalHeader_heading, title_0);
  !this$static.org_gwtbootstrap3_client_ui_ModalHeader_heading.com_google_gwt_user_client_ui_Widget_parent && com_google_gwt_user_client_ui_FlowPanel_$add__Lcom_google_gwt_user_client_ui_FlowPanel_2Lcom_google_gwt_user_client_ui_Widget_2V(this$static, this$static.org_gwtbootstrap3_client_ui_ModalHeader_heading);
}

function org_gwtbootstrap3_client_ui_ModalHeader_ModalHeader__V(){
  org_gwtbootstrap3_client_ui_gwt_FlowPanel_FlowPanel__V.call(this);
  this.org_gwtbootstrap3_client_ui_ModalHeader_heading = new org_gwtbootstrap3_client_ui_Heading_Heading__Lorg_gwtbootstrap3_client_ui_constants_HeadingSize_2V((org_gwtbootstrap3_client_ui_constants_HeadingSize_$clinit__V() , org_gwtbootstrap3_client_ui_constants_HeadingSize_H4));
  this.org_gwtbootstrap3_client_ui_ModalHeader_closeButton = new org_gwtbootstrap3_client_ui_base_button_CloseButton_CloseButton__V;
  this.com_google_gwt_user_client_ui_UIObject_element.className = 'modal-header';
  com_google_gwt_user_client_ui_UIObject_$setStyleName__Lcom_google_gwt_user_client_ui_UIObject_2Ljava_lang_String_2V(this.org_gwtbootstrap3_client_ui_ModalHeader_heading, 'modal-title');
  org_gwtbootstrap3_client_ui_base_button_AbstractButton_$setDataDismiss__Lorg_gwtbootstrap3_client_ui_base_button_AbstractButton_2Lorg_gwtbootstrap3_client_ui_constants_ButtonDismiss_2V(this.org_gwtbootstrap3_client_ui_ModalHeader_closeButton, (org_gwtbootstrap3_client_ui_constants_ButtonDismiss_$clinit__V() , org_gwtbootstrap3_client_ui_constants_ButtonDismiss_MODAL));
  com_google_gwt_user_client_ui_FlowPanel_$add__Lcom_google_gwt_user_client_ui_FlowPanel_2Lcom_google_gwt_user_client_ui_Widget_2V(this, this.org_gwtbootstrap3_client_ui_ModalHeader_closeButton);
}

com_google_gwt_lang_Runtime_defineClass__Lcom_google_gwt_core_client_JavaScriptObject_2Lcom_google_gwt_core_client_JavaScriptObject_2Lcom_google_gwt_core_client_JavaScriptObject_2V(38, 44, {5:1, 7:1, 4:1, 108:1, 38:1}, org_gwtbootstrap3_client_ui_ModalHeader_ModalHeader__V);
var com_google_gwt_lang_ClassLiteralHolder_Lorg_1gwtbootstrap3_1client_1ui_1ModalHeader_12_1classLit = java_lang_Class_createForClass__Ljava_lang_String_2Ljava_lang_String_2Lcom_google_gwt_core_client_JavaScriptObject_2Ljava_lang_Class_2Ljava_lang_Class_2($intern_59, 'ModalHeader', 38);
function org_gwtbootstrap3_client_ui_Navbar_Navbar__V(){
  org_gwtbootstrap3_client_ui_base_ComplexWidget_ComplexWidget__V.call(this);
  com_google_gwt_user_client_ui_UIObject_$setElement__Lcom_google_gwt_user_client_ui_UIObject_2Lcom_google_gwt_user_client_Element_2V(this, com_google_gwt_dom_client_DOMImplTrident_$createElement__Lcom_google_gwt_dom_client_DOMImplTrident_2Lcom_google_gwt_dom_client_Document_2Ljava_lang_String_2Lcom_google_gwt_dom_client_Element_2($doc, 'nav'));
  this.com_google_gwt_user_client_ui_UIObject_element.className = 'navbar';
  org_gwtbootstrap3_client_ui_base_helper_StyleHelper_addUniqueEnumStyleName__Lcom_google_gwt_user_client_ui_UIObject_2Ljava_lang_Class_2Lcom_google_gwt_dom_client_Style$HasCssName_2V(this, com_google_gwt_lang_ClassLiteralHolder_Lorg_1gwtbootstrap3_1client_1ui_1constants_1NavbarType_12_1classLit, (org_gwtbootstrap3_client_ui_constants_NavbarType_$clinit__V() , org_gwtbootstrap3_client_ui_constants_NavbarType_DEFAULT));
  this.com_google_gwt_user_client_ui_UIObject_element.setAttribute('role', 'navigation');
}

com_google_gwt_lang_Runtime_defineClass__Lcom_google_gwt_core_client_JavaScriptObject_2Lcom_google_gwt_core_client_JavaScriptObject_2Lcom_google_gwt_core_client_JavaScriptObject_2V(176, 19, $intern_46, org_gwtbootstrap3_client_ui_Navbar_Navbar__V);
var com_google_gwt_lang_ClassLiteralHolder_Lorg_1gwtbootstrap3_1client_1ui_1Navbar_12_1classLit = java_lang_Class_createForClass__Ljava_lang_String_2Ljava_lang_String_2Lcom_google_gwt_core_client_JavaScriptObject_2Ljava_lang_Class_2Ljava_lang_Class_2($intern_59, 'Navbar', 176);
function org_gwtbootstrap3_client_ui_NavbarBrand_NavbarBrand__V(){
  org_gwtbootstrap3_client_ui_Anchor_Anchor__V.call(this);
  this.com_google_gwt_user_client_ui_UIObject_element.className = 'navbar-brand';
}

com_google_gwt_lang_Runtime_defineClass__Lcom_google_gwt_core_client_JavaScriptObject_2Lcom_google_gwt_core_client_JavaScriptObject_2Lcom_google_gwt_core_client_JavaScriptObject_2V(184, 77, $intern_46, org_gwtbootstrap3_client_ui_NavbarBrand_NavbarBrand__V);
var com_google_gwt_lang_ClassLiteralHolder_Lorg_1gwtbootstrap3_1client_1ui_1NavbarBrand_12_1classLit = java_lang_Class_createForClass__Ljava_lang_String_2Ljava_lang_String_2Lcom_google_gwt_core_client_JavaScriptObject_2Ljava_lang_Class_2Ljava_lang_Class_2($intern_59, 'NavbarBrand', 184);
function org_gwtbootstrap3_client_ui_NavbarCollapse_NavbarCollapse__V(){
  org_gwtbootstrap3_client_ui_gwt_FlowPanel_FlowPanel__V.call(this);
  this.com_google_gwt_user_client_ui_UIObject_element.className = 'collapse';
  com_google_gwt_user_client_ui_UIObject_setStyleName__Lcom_google_gwt_dom_client_Element_2Ljava_lang_String_2ZV(this.com_google_gwt_user_client_ui_UIObject_element, $intern_22, true);
}

com_google_gwt_lang_Runtime_defineClass__Lcom_google_gwt_core_client_JavaScriptObject_2Lcom_google_gwt_core_client_JavaScriptObject_2Lcom_google_gwt_core_client_JavaScriptObject_2V(180, 44, $intern_46, org_gwtbootstrap3_client_ui_NavbarCollapse_NavbarCollapse__V);
var com_google_gwt_lang_ClassLiteralHolder_Lorg_1gwtbootstrap3_1client_1ui_1NavbarCollapse_12_1classLit = java_lang_Class_createForClass__Ljava_lang_String_2Ljava_lang_String_2Lcom_google_gwt_core_client_JavaScriptObject_2Ljava_lang_Class_2Ljava_lang_Class_2($intern_59, 'NavbarCollapse', 180);
function org_gwtbootstrap3_client_ui_NavbarCollapseButton_$newBarIcon__Lorg_gwtbootstrap3_client_ui_NavbarCollapseButton_2Lorg_gwtbootstrap3_client_ui_html_Span_2(){
  var span_0;
  span_0 = new org_gwtbootstrap3_client_ui_html_Span_Span__V;
  span_0.com_google_gwt_user_client_ui_UIObject_element.className = 'icon-bar';
  return span_0;
}

function org_gwtbootstrap3_client_ui_NavbarCollapseButton_NavbarCollapseButton__V(){
  new org_gwtbootstrap3_client_ui_base_mixin_PullMixin_PullMixin__Lcom_google_gwt_user_client_ui_UIObject_2V(this);
  this.org_gwtbootstrap3_client_ui_NavbarCollapseButton_button = new org_gwtbootstrap3_client_ui_Button_Button__V;
  com_google_gwt_user_client_ui_UIObject_$setStyleName__Lcom_google_gwt_user_client_ui_UIObject_2Ljava_lang_String_2V(this.org_gwtbootstrap3_client_ui_NavbarCollapseButton_button, 'navbar-toggle');
  org_gwtbootstrap3_client_ui_base_button_AbstractToggleButton_$setDataToggle__Lorg_gwtbootstrap3_client_ui_base_button_AbstractToggleButton_2Lorg_gwtbootstrap3_client_ui_constants_Toggle_2V(this.org_gwtbootstrap3_client_ui_NavbarCollapseButton_button, (org_gwtbootstrap3_client_ui_constants_Toggle_$clinit__V() , org_gwtbootstrap3_client_ui_constants_Toggle_COLLAPSE));
  org_gwtbootstrap3_client_ui_base_ComplexWidget_$add__Lorg_gwtbootstrap3_client_ui_base_ComplexWidget_2Lcom_google_gwt_user_client_ui_Widget_2V(this.org_gwtbootstrap3_client_ui_NavbarCollapseButton_button, org_gwtbootstrap3_client_ui_NavbarCollapseButton_$newBarIcon__Lorg_gwtbootstrap3_client_ui_NavbarCollapseButton_2Lorg_gwtbootstrap3_client_ui_html_Span_2());
  org_gwtbootstrap3_client_ui_base_ComplexWidget_$add__Lorg_gwtbootstrap3_client_ui_base_ComplexWidget_2Lcom_google_gwt_user_client_ui_Widget_2V(this.org_gwtbootstrap3_client_ui_NavbarCollapseButton_button, org_gwtbootstrap3_client_ui_NavbarCollapseButton_$newBarIcon__Lorg_gwtbootstrap3_client_ui_NavbarCollapseButton_2Lorg_gwtbootstrap3_client_ui_html_Span_2());
  org_gwtbootstrap3_client_ui_base_ComplexWidget_$add__Lorg_gwtbootstrap3_client_ui_base_ComplexWidget_2Lcom_google_gwt_user_client_ui_Widget_2V(this.org_gwtbootstrap3_client_ui_NavbarCollapseButton_button, org_gwtbootstrap3_client_ui_NavbarCollapseButton_$newBarIcon__Lorg_gwtbootstrap3_client_ui_NavbarCollapseButton_2Lorg_gwtbootstrap3_client_ui_html_Span_2());
  com_google_gwt_user_client_ui_Composite_$initWidget__Lcom_google_gwt_user_client_ui_Composite_2Lcom_google_gwt_user_client_ui_Widget_2V(this, this.org_gwtbootstrap3_client_ui_NavbarCollapseButton_button);
}

com_google_gwt_lang_Runtime_defineClass__Lcom_google_gwt_core_client_JavaScriptObject_2Lcom_google_gwt_core_client_JavaScriptObject_2Lcom_google_gwt_core_client_JavaScriptObject_2V(185, 222, $intern_9, org_gwtbootstrap3_client_ui_NavbarCollapseButton_NavbarCollapseButton__V);
var com_google_gwt_lang_ClassLiteralHolder_Lorg_1gwtbootstrap3_1client_1ui_1NavbarCollapseButton_12_1classLit = java_lang_Class_createForClass__Ljava_lang_String_2Ljava_lang_String_2Lcom_google_gwt_core_client_JavaScriptObject_2Ljava_lang_Class_2Ljava_lang_Class_2($intern_59, 'NavbarCollapseButton', 185);
function org_gwtbootstrap3_client_ui_NavbarHeader_NavbarHeader__V(){
  org_gwtbootstrap3_client_ui_gwt_FlowPanel_FlowPanel__V.call(this);
  this.com_google_gwt_user_client_ui_UIObject_element.className = 'navbar-header';
}

com_google_gwt_lang_Runtime_defineClass__Lcom_google_gwt_core_client_JavaScriptObject_2Lcom_google_gwt_core_client_JavaScriptObject_2Lcom_google_gwt_core_client_JavaScriptObject_2V(179, 44, $intern_46, org_gwtbootstrap3_client_ui_NavbarHeader_NavbarHeader__V);
var com_google_gwt_lang_ClassLiteralHolder_Lorg_1gwtbootstrap3_1client_1ui_1NavbarHeader_12_1classLit = java_lang_Class_createForClass__Ljava_lang_String_2Ljava_lang_String_2Lcom_google_gwt_core_client_JavaScriptObject_2Ljava_lang_Class_2Ljava_lang_Class_2($intern_59, 'NavbarHeader', 179);
function org_gwtbootstrap3_client_ui_NavbarNav_$setPull__Lorg_gwtbootstrap3_client_ui_NavbarNav_2Lorg_gwtbootstrap3_client_ui_constants_Pull_2V(this$static, pull){
  var navbarPull;
  org_gwtbootstrap3_client_ui_constants_NavbarPull_$clinit__V();
  pull == (org_gwtbootstrap3_client_ui_constants_Pull_$clinit__V() , org_gwtbootstrap3_client_ui_constants_Pull_LEFT)?(navbarPull = org_gwtbootstrap3_client_ui_constants_NavbarPull_LEFT):(navbarPull = org_gwtbootstrap3_client_ui_constants_NavbarPull_RIGHT);
  org_gwtbootstrap3_client_ui_base_helper_StyleHelper_removeEnumStyleNames__Lcom_google_gwt_user_client_ui_UIObject_2Ljava_lang_Class_2V(this$static, com_google_gwt_lang_ClassLiteralHolder_Lorg_1gwtbootstrap3_1client_1ui_1constants_1NavbarPull_12_1classLit);
  !!navbarPull && navbarPull.org_gwtbootstrap3_client_ui_constants_NavbarPull_cssClass != null && navbarPull.org_gwtbootstrap3_client_ui_constants_NavbarPull_cssClass.length != 0 && com_google_gwt_user_client_ui_UIObject_$addStyleName__Lcom_google_gwt_user_client_ui_UIObject_2Ljava_lang_String_2V(this$static, navbarPull.org_gwtbootstrap3_client_ui_constants_NavbarPull_cssClass);
}

function org_gwtbootstrap3_client_ui_NavbarNav_NavbarNav__V(){
  org_gwtbootstrap3_client_ui_html_UnorderedList_UnorderedList__V.call(this);
  this.com_google_gwt_user_client_ui_UIObject_element.className = 'nav';
  com_google_gwt_user_client_ui_UIObject_setStyleName__Lcom_google_gwt_dom_client_Element_2Ljava_lang_String_2ZV(this.com_google_gwt_user_client_ui_UIObject_element, 'navbar-nav', true);
}

com_google_gwt_lang_Runtime_defineClass__Lcom_google_gwt_core_client_JavaScriptObject_2Lcom_google_gwt_core_client_JavaScriptObject_2Lcom_google_gwt_core_client_JavaScriptObject_2V(102, 101, $intern_46, org_gwtbootstrap3_client_ui_NavbarNav_NavbarNav__V);
var com_google_gwt_lang_ClassLiteralHolder_Lorg_1gwtbootstrap3_1client_1ui_1NavbarNav_12_1classLit = java_lang_Class_createForClass__Ljava_lang_String_2Ljava_lang_String_2Lcom_google_gwt_core_client_JavaScriptObject_2Ljava_lang_Class_2Ljava_lang_Class_2($intern_59, 'NavbarNav', 102);
function org_gwtbootstrap3_client_ui_base_AbstractAnchorListItem$1_AbstractAnchorListItem$1__Lorg_gwtbootstrap3_client_ui_base_AbstractAnchorListItem_2V(this$0){
  this.org_gwtbootstrap3_client_ui_base_AbstractAnchorListItem$1_this$01 = this$0;
}

com_google_gwt_lang_Runtime_defineClass__Lcom_google_gwt_core_client_JavaScriptObject_2Lcom_google_gwt_core_client_JavaScriptObject_2Lcom_google_gwt_core_client_JavaScriptObject_2V(141, 1, $intern_21, org_gwtbootstrap3_client_ui_base_AbstractAnchorListItem$1_AbstractAnchorListItem$1__Lorg_gwtbootstrap3_client_ui_base_AbstractAnchorListItem_2V);
_.onClick__Lcom_google_gwt_event_dom_client_ClickEvent_2V = function org_gwtbootstrap3_client_ui_base_AbstractAnchorListItem$1_onClick__Lcom_google_gwt_event_dom_client_ClickEvent_2V(event_0){
  com_google_gwt_user_client_ui_Widget_$delegateEvent__Lcom_google_gwt_user_client_ui_Widget_2Lcom_google_gwt_user_client_ui_Widget_2Lcom_google_gwt_event_shared_GwtEvent_2V(this.org_gwtbootstrap3_client_ui_base_AbstractAnchorListItem$1_this$01, event_0);
}
;
var com_google_gwt_lang_ClassLiteralHolder_Lorg_1gwtbootstrap3_1client_1ui_1base_1AbstractAnchorListItem$1_12_1classLit = java_lang_Class_createForClass__Ljava_lang_String_2Ljava_lang_String_2Lcom_google_gwt_core_client_JavaScriptObject_2Ljava_lang_Class_2Ljava_lang_Class_2($intern_57, 'AbstractAnchorListItem/1', 141);
function org_gwtbootstrap3_client_ui_base_AbstractTextWidget_AbstractTextWidget__Lcom_google_gwt_dom_client_Element_2V(element){
  new org_gwtbootstrap3_client_ui_base_mixin_PullMixin_PullMixin__Lcom_google_gwt_user_client_ui_UIObject_2V(this);
  new org_gwtbootstrap3_client_ui_base_mixin_IdMixin_IdMixin__Lcom_google_gwt_user_client_ui_UIObject_2V(this);
  com_google_gwt_user_client_ui_UIObject_$setElement__Lcom_google_gwt_user_client_ui_UIObject_2Lcom_google_gwt_user_client_Element_2V(this, element);
}

com_google_gwt_lang_Runtime_defineClass__Lcom_google_gwt_core_client_JavaScriptObject_2Lcom_google_gwt_core_client_JavaScriptObject_2Lcom_google_gwt_core_client_JavaScriptObject_2V(199, 4, $intern_9);
_.setText__Ljava_lang_String_2V = function org_gwtbootstrap3_client_ui_base_AbstractTextWidget_setText__Ljava_lang_String_2V(text_0){
  com_google_gwt_dom_client_DOMImplTrident_$setInnerText__Lcom_google_gwt_dom_client_DOMImplTrident_2Lcom_google_gwt_dom_client_Element_2Ljava_lang_String_2V(this.com_google_gwt_user_client_ui_UIObject_element, text_0);
}
;
var com_google_gwt_lang_ClassLiteralHolder_Lorg_1gwtbootstrap3_1client_1ui_1base_1AbstractTextWidget_12_1classLit = java_lang_Class_createForClass__Ljava_lang_String_2Ljava_lang_String_2Lcom_google_gwt_core_client_JavaScriptObject_2Ljava_lang_Class_2Ljava_lang_Class_2($intern_57, 'AbstractTextWidget', 199);
function org_gwtbootstrap3_client_ui_base_button_AbstractToggleButton$1_AbstractToggleButton$1__Lorg_gwtbootstrap3_client_ui_base_button_AbstractToggleButton_2V(this$0, val$toggle){
  this.org_gwtbootstrap3_client_ui_base_button_AbstractToggleButton$1_this$01 = this$0;
  this.org_gwtbootstrap3_client_ui_base_button_AbstractToggleButton$1_val$toggle2 = val$toggle;
}

com_google_gwt_lang_Runtime_defineClass__Lcom_google_gwt_core_client_JavaScriptObject_2Lcom_google_gwt_core_client_JavaScriptObject_2Lcom_google_gwt_core_client_JavaScriptObject_2V(143, 1, {}, org_gwtbootstrap3_client_ui_base_button_AbstractToggleButton$1_AbstractToggleButton$1__Lorg_gwtbootstrap3_client_ui_base_button_AbstractToggleButton_2V);
_.execute__V = function org_gwtbootstrap3_client_ui_base_button_AbstractToggleButton$1_execute__V(){
  com_google_gwt_user_client_ui_Widget_$removeFromParent__Lcom_google_gwt_user_client_ui_Widget_2V(this.org_gwtbootstrap3_client_ui_base_button_AbstractToggleButton$1_this$01.org_gwtbootstrap3_client_ui_base_button_AbstractToggleButton_separator);
  com_google_gwt_user_client_ui_Widget_$removeFromParent__Lcom_google_gwt_user_client_ui_Widget_2V(this.org_gwtbootstrap3_client_ui_base_button_AbstractToggleButton$1_this$01.org_gwtbootstrap3_client_ui_base_button_AbstractToggleButton_caret);
  if (this.org_gwtbootstrap3_client_ui_base_button_AbstractToggleButton$1_val$toggle2 == (org_gwtbootstrap3_client_ui_constants_Toggle_$clinit__V() , org_gwtbootstrap3_client_ui_constants_Toggle_DROPDOWN)) {
    com_google_gwt_user_client_ui_UIObject_$addStyleName__Lcom_google_gwt_user_client_ui_UIObject_2Ljava_lang_String_2V(this.org_gwtbootstrap3_client_ui_base_button_AbstractToggleButton$1_this$01, $intern_65);
    com_google_gwt_user_client_ui_ComplexPanel_$add__Lcom_google_gwt_user_client_ui_ComplexPanel_2Lcom_google_gwt_user_client_ui_Widget_2Lcom_google_gwt_user_client_Element_2V(this.org_gwtbootstrap3_client_ui_base_button_AbstractToggleButton$1_this$01, this.org_gwtbootstrap3_client_ui_base_button_AbstractToggleButton$1_this$01.org_gwtbootstrap3_client_ui_base_button_AbstractToggleButton_separator, this.org_gwtbootstrap3_client_ui_base_button_AbstractToggleButton$1_this$01.com_google_gwt_user_client_ui_UIObject_element);
    com_google_gwt_user_client_ui_ComplexPanel_$add__Lcom_google_gwt_user_client_ui_ComplexPanel_2Lcom_google_gwt_user_client_ui_Widget_2Lcom_google_gwt_user_client_Element_2V(this.org_gwtbootstrap3_client_ui_base_button_AbstractToggleButton$1_this$01, this.org_gwtbootstrap3_client_ui_base_button_AbstractToggleButton$1_this$01.org_gwtbootstrap3_client_ui_base_button_AbstractToggleButton_caret, this.org_gwtbootstrap3_client_ui_base_button_AbstractToggleButton$1_this$01.com_google_gwt_user_client_ui_UIObject_element);
  }
}
;
var com_google_gwt_lang_ClassLiteralHolder_Lorg_1gwtbootstrap3_1client_1ui_1base_1button_1AbstractToggleButton$1_12_1classLit = java_lang_Class_createForClass__Ljava_lang_String_2Ljava_lang_String_2Lcom_google_gwt_core_client_JavaScriptObject_2Ljava_lang_Class_2Ljava_lang_Class_2($intern_61, 'AbstractToggleButton/1', 143);
function org_gwtbootstrap3_client_ui_base_button_Caret_Caret__V(){
  com_google_gwt_user_client_ui_UIObject_$setElement__Lcom_google_gwt_user_client_ui_UIObject_2Lcom_google_gwt_user_client_Element_2V(this, com_google_gwt_dom_client_DOMImplTrident_$createElement__Lcom_google_gwt_dom_client_DOMImplTrident_2Lcom_google_gwt_dom_client_Document_2Ljava_lang_String_2Lcom_google_gwt_dom_client_Element_2($doc, $intern_47));
  this.com_google_gwt_user_client_ui_UIObject_element.className = 'caret';
}

com_google_gwt_lang_Runtime_defineClass__Lcom_google_gwt_core_client_JavaScriptObject_2Lcom_google_gwt_core_client_JavaScriptObject_2Lcom_google_gwt_core_client_JavaScriptObject_2V(203, 4, $intern_9, org_gwtbootstrap3_client_ui_base_button_Caret_Caret__V);
var com_google_gwt_lang_ClassLiteralHolder_Lorg_1gwtbootstrap3_1client_1ui_1base_1button_1Caret_12_1classLit = java_lang_Class_createForClass__Ljava_lang_String_2Ljava_lang_String_2Lcom_google_gwt_core_client_JavaScriptObject_2Ljava_lang_Class_2Ljava_lang_Class_2($intern_61, 'Caret', 203);
function org_gwtbootstrap3_client_ui_base_button_CloseButton_CloseButton__V(){
  org_gwtbootstrap3_client_ui_base_button_AbstractButton_AbstractButton__V.call(this);
  this.com_google_gwt_user_client_ui_UIObject_element.className = 'close';
  this.com_google_gwt_user_client_ui_UIObject_element.innerHTML = '&times;';
}

com_google_gwt_lang_Runtime_defineClass__Lcom_google_gwt_core_client_JavaScriptObject_2Lcom_google_gwt_core_client_JavaScriptObject_2Lcom_google_gwt_core_client_JavaScriptObject_2V(187, 74, $intern_46, org_gwtbootstrap3_client_ui_base_button_CloseButton_CloseButton__V);
_.createElement__Lcom_google_gwt_dom_client_Element_2 = function org_gwtbootstrap3_client_ui_base_button_CloseButton_createElement__Lcom_google_gwt_dom_client_Element_2(){
  return $doc.createElement($intern_63);
}
;
var com_google_gwt_lang_ClassLiteralHolder_Lorg_1gwtbootstrap3_1client_1ui_1base_1button_1CloseButton_12_1classLit = java_lang_Class_createForClass__Ljava_lang_String_2Ljava_lang_String_2Lcom_google_gwt_core_client_JavaScriptObject_2Ljava_lang_Class_2Ljava_lang_Class_2($intern_61, 'CloseButton', 187);
function org_gwtbootstrap3_client_ui_base_helper_StyleHelper_addUniqueEnumStyleName__Lcom_google_gwt_user_client_ui_UIObject_2Ljava_lang_Class_2Lcom_google_gwt_dom_client_Style$HasCssName_2V(uiObject, enumClass, style){
  org_gwtbootstrap3_client_ui_base_helper_StyleHelper_removeEnumStyleNames__Lcom_google_gwt_user_client_ui_UIObject_2Ljava_lang_Class_2V(uiObject, enumClass);
  !!style && style.getCssName__Ljava_lang_String_2() != null && style.getCssName__Ljava_lang_String_2().length != 0 && com_google_gwt_user_client_ui_UIObject_$addStyleName__Lcom_google_gwt_user_client_ui_UIObject_2Ljava_lang_String_2V(uiObject, style.getCssName__Ljava_lang_String_2());
}

function org_gwtbootstrap3_client_ui_base_helper_StyleHelper_removeEnumStyleNames__Lcom_google_gwt_user_client_ui_UIObject_2Ljava_lang_Class_2V(uiObject, enumClass){
  var constant, constant$array, constant$index, constant$max, cssClass;
  for (constant$array = com_google_gwt_lang_Cast_castTo__Ljava_lang_Object_2Lcom_google_gwt_core_client_JavaScriptObject_2Ljava_lang_Object_2(enumClass.java_lang_Class_enumConstantsFunc && enumClass.java_lang_Class_enumConstantsFunc(), 9) , constant$index = 0 , constant$max = constant$array.length; constant$index < constant$max; ++constant$index) {
    constant = constant$array[constant$index];
    cssClass = com_google_gwt_lang_Cast_castTo__Ljava_lang_Object_2Lcom_google_gwt_core_client_JavaScriptObject_2Ljava_lang_Object_2(constant, 13).getCssName__Ljava_lang_String_2();
    cssClass != null && cssClass.length != 0 && com_google_gwt_user_client_ui_UIObject_setStyleName__Lcom_google_gwt_dom_client_Element_2Ljava_lang_String_2ZV(uiObject.com_google_gwt_user_client_ui_UIObject_element, cssClass, false);
  }
}

function org_gwtbootstrap3_client_ui_base_mixin_AbstractMixin_AbstractMixin__Lcom_google_gwt_user_client_ui_UIObject_2V(uiObject){
  this.org_gwtbootstrap3_client_ui_base_mixin_AbstractMixin_uiObject = uiObject;
}

com_google_gwt_lang_Runtime_defineClass__Lcom_google_gwt_core_client_JavaScriptObject_2Lcom_google_gwt_core_client_JavaScriptObject_2Lcom_google_gwt_core_client_JavaScriptObject_2V(16, 1, {});
var com_google_gwt_lang_ClassLiteralHolder_Lorg_1gwtbootstrap3_1client_1ui_1base_1mixin_1AbstractMixin_12_1classLit = java_lang_Class_createForClass__Ljava_lang_String_2Ljava_lang_String_2Lcom_google_gwt_core_client_JavaScriptObject_2Ljava_lang_Class_2Ljava_lang_Class_2($intern_73, 'AbstractMixin', 16);
function org_gwtbootstrap3_client_ui_base_mixin_ActiveMixin_ActiveMixin__Lcom_google_gwt_user_client_ui_UIObject_2V(uiObject){
  org_gwtbootstrap3_client_ui_base_mixin_AbstractMixin_AbstractMixin__Lcom_google_gwt_user_client_ui_UIObject_2V.call(this, uiObject);
}

com_google_gwt_lang_Runtime_defineClass__Lcom_google_gwt_core_client_JavaScriptObject_2Lcom_google_gwt_core_client_JavaScriptObject_2Lcom_google_gwt_core_client_JavaScriptObject_2V(107, 16, {}, org_gwtbootstrap3_client_ui_base_mixin_ActiveMixin_ActiveMixin__Lcom_google_gwt_user_client_ui_UIObject_2V);
var com_google_gwt_lang_ClassLiteralHolder_Lorg_1gwtbootstrap3_1client_1ui_1base_1mixin_1ActiveMixin_12_1classLit = java_lang_Class_createForClass__Ljava_lang_String_2Ljava_lang_String_2Lcom_google_gwt_core_client_JavaScriptObject_2Ljava_lang_Class_2Ljava_lang_Class_2($intern_73, 'ActiveMixin', 107);
function org_gwtbootstrap3_client_ui_base_mixin_AttributeMixin_AttributeMixin__Lcom_google_gwt_user_client_ui_UIObject_2V(uiObject){
  org_gwtbootstrap3_client_ui_base_mixin_AbstractMixin_AbstractMixin__Lcom_google_gwt_user_client_ui_UIObject_2V.call(this, uiObject);
}

com_google_gwt_lang_Runtime_defineClass__Lcom_google_gwt_core_client_JavaScriptObject_2Lcom_google_gwt_core_client_JavaScriptObject_2Lcom_google_gwt_core_client_JavaScriptObject_2V(202, 16, {}, org_gwtbootstrap3_client_ui_base_mixin_AttributeMixin_AttributeMixin__Lcom_google_gwt_user_client_ui_UIObject_2V);
var com_google_gwt_lang_ClassLiteralHolder_Lorg_1gwtbootstrap3_1client_1ui_1base_1mixin_1AttributeMixin_12_1classLit = java_lang_Class_createForClass__Ljava_lang_String_2Ljava_lang_String_2Lcom_google_gwt_core_client_JavaScriptObject_2Ljava_lang_Class_2Ljava_lang_Class_2($intern_73, 'AttributeMixin', 202);
function org_gwtbootstrap3_client_ui_base_mixin_DataParentMixin_DataParentMixin__Lcom_google_gwt_user_client_ui_UIObject_2V(uiObject){
  org_gwtbootstrap3_client_ui_base_mixin_AbstractMixin_AbstractMixin__Lcom_google_gwt_user_client_ui_UIObject_2V.call(this, uiObject);
}

com_google_gwt_lang_Runtime_defineClass__Lcom_google_gwt_core_client_JavaScriptObject_2Lcom_google_gwt_core_client_JavaScriptObject_2Lcom_google_gwt_core_client_JavaScriptObject_2V(201, 16, {}, org_gwtbootstrap3_client_ui_base_mixin_DataParentMixin_DataParentMixin__Lcom_google_gwt_user_client_ui_UIObject_2V);
var com_google_gwt_lang_ClassLiteralHolder_Lorg_1gwtbootstrap3_1client_1ui_1base_1mixin_1DataParentMixin_12_1classLit = java_lang_Class_createForClass__Ljava_lang_String_2Ljava_lang_String_2Lcom_google_gwt_core_client_JavaScriptObject_2Ljava_lang_Class_2Ljava_lang_Class_2($intern_73, 'DataParentMixin', 201);
function org_gwtbootstrap3_client_ui_base_mixin_DataSpyMixin_DataSpyMixin__Lcom_google_gwt_user_client_ui_UIObject_2V(uiObject){
  org_gwtbootstrap3_client_ui_base_mixin_AbstractMixin_AbstractMixin__Lcom_google_gwt_user_client_ui_UIObject_2V.call(this, uiObject);
}

com_google_gwt_lang_Runtime_defineClass__Lcom_google_gwt_core_client_JavaScriptObject_2Lcom_google_gwt_core_client_JavaScriptObject_2Lcom_google_gwt_core_client_JavaScriptObject_2V(104, 16, {}, org_gwtbootstrap3_client_ui_base_mixin_DataSpyMixin_DataSpyMixin__Lcom_google_gwt_user_client_ui_UIObject_2V);
var com_google_gwt_lang_ClassLiteralHolder_Lorg_1gwtbootstrap3_1client_1ui_1base_1mixin_1DataSpyMixin_12_1classLit = java_lang_Class_createForClass__Ljava_lang_String_2Ljava_lang_String_2Lcom_google_gwt_core_client_JavaScriptObject_2Ljava_lang_Class_2Ljava_lang_Class_2($intern_73, 'DataSpyMixin', 104);
function org_gwtbootstrap3_client_ui_base_mixin_DataTargetMixin_DataTargetMixin__Lcom_google_gwt_user_client_ui_UIObject_2V(uiObject){
  org_gwtbootstrap3_client_ui_base_mixin_AbstractMixin_AbstractMixin__Lcom_google_gwt_user_client_ui_UIObject_2V.call(this, uiObject);
}

com_google_gwt_lang_Runtime_defineClass__Lcom_google_gwt_core_client_JavaScriptObject_2Lcom_google_gwt_core_client_JavaScriptObject_2Lcom_google_gwt_core_client_JavaScriptObject_2V(62, 16, {}, org_gwtbootstrap3_client_ui_base_mixin_DataTargetMixin_DataTargetMixin__Lcom_google_gwt_user_client_ui_UIObject_2V);
var com_google_gwt_lang_ClassLiteralHolder_Lorg_1gwtbootstrap3_1client_1ui_1base_1mixin_1DataTargetMixin_12_1classLit = java_lang_Class_createForClass__Ljava_lang_String_2Ljava_lang_String_2Lcom_google_gwt_core_client_JavaScriptObject_2Ljava_lang_Class_2Ljava_lang_Class_2($intern_73, 'DataTargetMixin', 62);
function org_gwtbootstrap3_client_ui_base_mixin_DataToggleMixin_$setDataToggle__Lorg_gwtbootstrap3_client_ui_base_mixin_DataToggleMixin_2Lorg_gwtbootstrap3_client_ui_constants_Toggle_2V(this$static, toggle){
  toggle?com_google_gwt_dom_client_Element_$setAttribute__Lcom_google_gwt_dom_client_Element_2Ljava_lang_String_2Ljava_lang_String_2V(this$static.org_gwtbootstrap3_client_ui_base_mixin_AbstractMixin_uiObject.com_google_gwt_user_client_ui_UIObject_element, $intern_74, toggle.org_gwtbootstrap3_client_ui_constants_Toggle_toggle):(this$static.org_gwtbootstrap3_client_ui_base_mixin_AbstractMixin_uiObject.com_google_gwt_user_client_ui_UIObject_element.removeAttribute($intern_74) , undefined);
}

function org_gwtbootstrap3_client_ui_base_mixin_DataToggleMixin_DataToggleMixin__Lcom_google_gwt_user_client_ui_UIObject_2V(uiObject){
  org_gwtbootstrap3_client_ui_base_mixin_AbstractMixin_AbstractMixin__Lcom_google_gwt_user_client_ui_UIObject_2V.call(this, uiObject);
}

com_google_gwt_lang_Runtime_defineClass__Lcom_google_gwt_core_client_JavaScriptObject_2Lcom_google_gwt_core_client_JavaScriptObject_2Lcom_google_gwt_core_client_JavaScriptObject_2V(106, 16, {}, org_gwtbootstrap3_client_ui_base_mixin_DataToggleMixin_DataToggleMixin__Lcom_google_gwt_user_client_ui_UIObject_2V);
var com_google_gwt_lang_ClassLiteralHolder_Lorg_1gwtbootstrap3_1client_1ui_1base_1mixin_1DataToggleMixin_12_1classLit = java_lang_Class_createForClass__Ljava_lang_String_2Ljava_lang_String_2Lcom_google_gwt_core_client_JavaScriptObject_2Ljava_lang_Class_2Ljava_lang_Class_2($intern_73, 'DataToggleMixin', 106);
function org_gwtbootstrap3_client_ui_base_mixin_EnabledMixin_EnabledMixin__Lcom_google_gwt_user_client_ui_UIObject_2V(uiObject){
  org_gwtbootstrap3_client_ui_base_mixin_AbstractMixin_AbstractMixin__Lcom_google_gwt_user_client_ui_UIObject_2V.call(this, uiObject);
}

com_google_gwt_lang_Runtime_defineClass__Lcom_google_gwt_core_client_JavaScriptObject_2Lcom_google_gwt_core_client_JavaScriptObject_2Lcom_google_gwt_core_client_JavaScriptObject_2V(82, 16, {}, org_gwtbootstrap3_client_ui_base_mixin_EnabledMixin_EnabledMixin__Lcom_google_gwt_user_client_ui_UIObject_2V);
var com_google_gwt_lang_ClassLiteralHolder_Lorg_1gwtbootstrap3_1client_1ui_1base_1mixin_1EnabledMixin_12_1classLit = java_lang_Class_createForClass__Ljava_lang_String_2Ljava_lang_String_2Lcom_google_gwt_core_client_JavaScriptObject_2Ljava_lang_Class_2Ljava_lang_Class_2($intern_73, 'EnabledMixin', 82);
com_google_gwt_lang_Runtime_defineClass__Lcom_google_gwt_core_client_JavaScriptObject_2Lcom_google_gwt_core_client_JavaScriptObject_2Lcom_google_gwt_core_client_JavaScriptObject_2V(204, 16, {});
var com_google_gwt_lang_ClassLiteralHolder_Lorg_1gwtbootstrap3_1client_1ui_1base_1mixin_1TextMixin_12_1classLit = java_lang_Class_createForClass__Ljava_lang_String_2Ljava_lang_String_2Lcom_google_gwt_core_client_JavaScriptObject_2Ljava_lang_Class_2Ljava_lang_Class_2($intern_73, 'TextMixin', 204);
function org_gwtbootstrap3_client_ui_base_mixin_HTMLMixin_HTMLMixin__Lcom_google_gwt_user_client_ui_UIObject_2V(uiObject){
  this.org_gwtbootstrap3_client_ui_base_mixin_AbstractMixin_uiObject = uiObject;
}

com_google_gwt_lang_Runtime_defineClass__Lcom_google_gwt_core_client_JavaScriptObject_2Lcom_google_gwt_core_client_JavaScriptObject_2Lcom_google_gwt_core_client_JavaScriptObject_2V(205, 204, {}, org_gwtbootstrap3_client_ui_base_mixin_HTMLMixin_HTMLMixin__Lcom_google_gwt_user_client_ui_UIObject_2V);
var com_google_gwt_lang_ClassLiteralHolder_Lorg_1gwtbootstrap3_1client_1ui_1base_1mixin_1HTMLMixin_12_1classLit = java_lang_Class_createForClass__Ljava_lang_String_2Ljava_lang_String_2Lcom_google_gwt_core_client_JavaScriptObject_2Ljava_lang_Class_2Ljava_lang_Class_2($intern_73, 'HTMLMixin', 205);
function org_gwtbootstrap3_client_ui_base_mixin_IconTextMixin_$addTextWidgetToParent__Lorg_gwtbootstrap3_client_ui_base_mixin_IconTextMixin_2V(this$static){
  this$static.org_gwtbootstrap3_client_ui_base_mixin_IconTextMixin_widget.add__Lcom_google_gwt_user_client_ui_Widget_2V(this$static.org_gwtbootstrap3_client_ui_base_mixin_IconTextMixin_text);
}

function org_gwtbootstrap3_client_ui_base_mixin_IconTextMixin_$setText__Lorg_gwtbootstrap3_client_ui_base_mixin_IconTextMixin_2Ljava_lang_String_2V(this$static, text_0){
  org_gwtbootstrap3_client_ui_html_Text_$setText__Lorg_gwtbootstrap3_client_ui_html_Text_2Ljava_lang_String_2V(this$static.org_gwtbootstrap3_client_ui_base_mixin_IconTextMixin_text, text_0);
  com_google_gwt_core_client_impl_SchedulerImpl_$scheduleDeferred__Lcom_google_gwt_core_client_impl_SchedulerImpl_2Lcom_google_gwt_core_client_Scheduler$ScheduledCommand_2V((com_google_gwt_core_client_impl_SchedulerImpl_$clinit__V() , com_google_gwt_core_client_impl_SchedulerImpl_INSTANCE), new org_gwtbootstrap3_client_ui_base_mixin_IconTextMixin$1_IconTextMixin$1__Lorg_gwtbootstrap3_client_ui_base_mixin_IconTextMixin_2V(this$static));
}

function org_gwtbootstrap3_client_ui_base_mixin_IconTextMixin_IconTextMixin__Lorg_gwtbootstrap3_client_ui_base_ComplexWidget_2V(widget){
  this.org_gwtbootstrap3_client_ui_base_mixin_IconTextMixin_text = new org_gwtbootstrap3_client_ui_html_Text_Text__V;
  this.org_gwtbootstrap3_client_ui_base_mixin_IconTextMixin_separator = new org_gwtbootstrap3_client_ui_html_Text_Text__Ljava_lang_String_2V(' ');
  this.org_gwtbootstrap3_client_ui_base_mixin_IconTextMixin_badgeSeparator = new org_gwtbootstrap3_client_ui_html_Text_Text__Ljava_lang_String_2V(' ');
  org_gwtbootstrap3_client_ui_constants_IconSize_$clinit__V();
  org_gwtbootstrap3_client_ui_constants_IconFlip_$clinit__V();
  org_gwtbootstrap3_client_ui_constants_IconRotate_$clinit__V();
  this.org_gwtbootstrap3_client_ui_base_mixin_IconTextMixin_badge = new org_gwtbootstrap3_client_ui_Badge_Badge__V;
  this.org_gwtbootstrap3_client_ui_base_mixin_IconTextMixin_widget = widget;
}

com_google_gwt_lang_Runtime_defineClass__Lcom_google_gwt_core_client_JavaScriptObject_2Lcom_google_gwt_core_client_JavaScriptObject_2Lcom_google_gwt_core_client_JavaScriptObject_2V(105, 1, {}, org_gwtbootstrap3_client_ui_base_mixin_IconTextMixin_IconTextMixin__Lorg_gwtbootstrap3_client_ui_base_ComplexWidget_2V);
_.setText__Ljava_lang_String_2V = function org_gwtbootstrap3_client_ui_base_mixin_IconTextMixin_setText__Ljava_lang_String_2V(text_0){
  org_gwtbootstrap3_client_ui_base_mixin_IconTextMixin_$setText__Lorg_gwtbootstrap3_client_ui_base_mixin_IconTextMixin_2Ljava_lang_String_2V(this, text_0);
}
;
var com_google_gwt_lang_ClassLiteralHolder_Lorg_1gwtbootstrap3_1client_1ui_1base_1mixin_1IconTextMixin_12_1classLit = java_lang_Class_createForClass__Ljava_lang_String_2Ljava_lang_String_2Lcom_google_gwt_core_client_JavaScriptObject_2Ljava_lang_Class_2Ljava_lang_Class_2($intern_73, 'IconTextMixin', 105);
function org_gwtbootstrap3_client_ui_base_mixin_IconTextMixin$1_IconTextMixin$1__Lorg_gwtbootstrap3_client_ui_base_mixin_IconTextMixin_2V(this$0){
  this.org_gwtbootstrap3_client_ui_base_mixin_IconTextMixin$1_this$01 = this$0;
}

com_google_gwt_lang_Runtime_defineClass__Lcom_google_gwt_core_client_JavaScriptObject_2Lcom_google_gwt_core_client_JavaScriptObject_2Lcom_google_gwt_core_client_JavaScriptObject_2V(197, 1, {}, org_gwtbootstrap3_client_ui_base_mixin_IconTextMixin$1_IconTextMixin$1__Lorg_gwtbootstrap3_client_ui_base_mixin_IconTextMixin_2V);
_.execute__V = function org_gwtbootstrap3_client_ui_base_mixin_IconTextMixin$1_execute__V(){
  var position;
  this.org_gwtbootstrap3_client_ui_base_mixin_IconTextMixin$1_this$01.org_gwtbootstrap3_client_ui_base_mixin_IconTextMixin_text.com_google_gwt_user_client_ui_Widget_attached && com_google_gwt_user_client_ui_Widget_$removeFromParent__Lcom_google_gwt_user_client_ui_Widget_2V(this.org_gwtbootstrap3_client_ui_base_mixin_IconTextMixin$1_this$01.org_gwtbootstrap3_client_ui_base_mixin_IconTextMixin_text);
  this.org_gwtbootstrap3_client_ui_base_mixin_IconTextMixin$1_this$01.org_gwtbootstrap3_client_ui_base_mixin_IconTextMixin_separator.com_google_gwt_user_client_ui_Widget_attached && com_google_gwt_user_client_ui_Widget_$removeFromParent__Lcom_google_gwt_user_client_ui_Widget_2V(this.org_gwtbootstrap3_client_ui_base_mixin_IconTextMixin$1_this$01.org_gwtbootstrap3_client_ui_base_mixin_IconTextMixin_separator);
  this.org_gwtbootstrap3_client_ui_base_mixin_IconTextMixin$1_this$01.org_gwtbootstrap3_client_ui_base_mixin_IconTextMixin_badgeSeparator.com_google_gwt_user_client_ui_Widget_attached && com_google_gwt_user_client_ui_Widget_$removeFromParent__Lcom_google_gwt_user_client_ui_Widget_2V(this.org_gwtbootstrap3_client_ui_base_mixin_IconTextMixin$1_this$01.org_gwtbootstrap3_client_ui_base_mixin_IconTextMixin_badgeSeparator);
  this.org_gwtbootstrap3_client_ui_base_mixin_IconTextMixin$1_this$01.org_gwtbootstrap3_client_ui_base_mixin_IconTextMixin_badge.com_google_gwt_user_client_ui_Widget_attached && com_google_gwt_user_client_ui_Widget_$removeFromParent__Lcom_google_gwt_user_client_ui_Widget_2V(this.org_gwtbootstrap3_client_ui_base_mixin_IconTextMixin$1_this$01.org_gwtbootstrap3_client_ui_base_mixin_IconTextMixin_badge);
  position = 0;
  if (this.org_gwtbootstrap3_client_ui_base_mixin_IconTextMixin$1_this$01.org_gwtbootstrap3_client_ui_base_mixin_IconTextMixin_badge.org_gwtbootstrap3_client_ui_Badge_text.org_gwtbootstrap3_client_ui_html_Text_text.data != null && this.org_gwtbootstrap3_client_ui_base_mixin_IconTextMixin$1_this$01.org_gwtbootstrap3_client_ui_base_mixin_IconTextMixin_badge.org_gwtbootstrap3_client_ui_Badge_text.org_gwtbootstrap3_client_ui_html_Text_text.data.length > 0 && false) {
    org_gwtbootstrap3_client_ui_base_ComplexWidget_$insert__Lorg_gwtbootstrap3_client_ui_base_ComplexWidget_2Lcom_google_gwt_user_client_ui_Widget_2IV(this.org_gwtbootstrap3_client_ui_base_mixin_IconTextMixin$1_this$01.org_gwtbootstrap3_client_ui_base_mixin_IconTextMixin_widget, this.org_gwtbootstrap3_client_ui_base_mixin_IconTextMixin$1_this$01.org_gwtbootstrap3_client_ui_base_mixin_IconTextMixin_badge, position++);
    org_gwtbootstrap3_client_ui_base_ComplexWidget_$insert__Lorg_gwtbootstrap3_client_ui_base_ComplexWidget_2Lcom_google_gwt_user_client_ui_Widget_2IV(this.org_gwtbootstrap3_client_ui_base_mixin_IconTextMixin$1_this$01.org_gwtbootstrap3_client_ui_base_mixin_IconTextMixin_widget, this.org_gwtbootstrap3_client_ui_base_mixin_IconTextMixin$1_this$01.org_gwtbootstrap3_client_ui_base_mixin_IconTextMixin_badgeSeparator, position++);
  }
  this.org_gwtbootstrap3_client_ui_base_mixin_IconTextMixin$1_this$01.org_gwtbootstrap3_client_ui_base_mixin_IconTextMixin_text.org_gwtbootstrap3_client_ui_html_Text_text.data != null && this.org_gwtbootstrap3_client_ui_base_mixin_IconTextMixin$1_this$01.org_gwtbootstrap3_client_ui_base_mixin_IconTextMixin_text.org_gwtbootstrap3_client_ui_html_Text_text.data.length > 0 && org_gwtbootstrap3_client_ui_base_ComplexWidget_$insert__Lorg_gwtbootstrap3_client_ui_base_ComplexWidget_2Lcom_google_gwt_user_client_ui_Widget_2IV(this.org_gwtbootstrap3_client_ui_base_mixin_IconTextMixin$1_this$01.org_gwtbootstrap3_client_ui_base_mixin_IconTextMixin_widget, this.org_gwtbootstrap3_client_ui_base_mixin_IconTextMixin$1_this$01.org_gwtbootstrap3_client_ui_base_mixin_IconTextMixin_text, position++);
  if (this.org_gwtbootstrap3_client_ui_base_mixin_IconTextMixin$1_this$01.org_gwtbootstrap3_client_ui_base_mixin_IconTextMixin_badge.org_gwtbootstrap3_client_ui_Badge_text.org_gwtbootstrap3_client_ui_html_Text_text.data != null && this.org_gwtbootstrap3_client_ui_base_mixin_IconTextMixin$1_this$01.org_gwtbootstrap3_client_ui_base_mixin_IconTextMixin_badge.org_gwtbootstrap3_client_ui_Badge_text.org_gwtbootstrap3_client_ui_html_Text_text.data.length > 0) {
    org_gwtbootstrap3_client_ui_base_ComplexWidget_$insert__Lorg_gwtbootstrap3_client_ui_base_ComplexWidget_2Lcom_google_gwt_user_client_ui_Widget_2IV(this.org_gwtbootstrap3_client_ui_base_mixin_IconTextMixin$1_this$01.org_gwtbootstrap3_client_ui_base_mixin_IconTextMixin_widget, this.org_gwtbootstrap3_client_ui_base_mixin_IconTextMixin$1_this$01.org_gwtbootstrap3_client_ui_base_mixin_IconTextMixin_badgeSeparator, position++);
    org_gwtbootstrap3_client_ui_base_ComplexWidget_$insert__Lorg_gwtbootstrap3_client_ui_base_ComplexWidget_2Lcom_google_gwt_user_client_ui_Widget_2IV(this.org_gwtbootstrap3_client_ui_base_mixin_IconTextMixin$1_this$01.org_gwtbootstrap3_client_ui_base_mixin_IconTextMixin_widget, this.org_gwtbootstrap3_client_ui_base_mixin_IconTextMixin$1_this$01.org_gwtbootstrap3_client_ui_base_mixin_IconTextMixin_badge, position++);
  }
  this.org_gwtbootstrap3_client_ui_base_mixin_IconTextMixin$1_this$01.org_gwtbootstrap3_client_ui_base_mixin_IconTextMixin_badge.com_google_gwt_user_client_ui_UIObject_element.style['marginLeft'] = (com_google_gwt_dom_client_Style$Unit_$clinit__V() , '0.0px');
  this.org_gwtbootstrap3_client_ui_base_mixin_IconTextMixin$1_this$01.org_gwtbootstrap3_client_ui_base_mixin_IconTextMixin_badge.com_google_gwt_user_client_ui_UIObject_element.style['marginRight'] = '0.0px';
}
;
var com_google_gwt_lang_ClassLiteralHolder_Lorg_1gwtbootstrap3_1client_1ui_1base_1mixin_1IconTextMixin$1_12_1classLit = java_lang_Class_createForClass__Ljava_lang_String_2Ljava_lang_String_2Lcom_google_gwt_core_client_JavaScriptObject_2Ljava_lang_Class_2Ljava_lang_Class_2($intern_73, 'IconTextMixin/1', 197);
function org_gwtbootstrap3_client_ui_base_mixin_IdMixin_$setId__Lorg_gwtbootstrap3_client_ui_base_mixin_IdMixin_2Ljava_lang_String_2V(this$static, id_0){
  com_google_gwt_dom_client_Element_$setId__Lcom_google_gwt_dom_client_Element_2Ljava_lang_String_2V(this$static.org_gwtbootstrap3_client_ui_base_mixin_AbstractMixin_uiObject.com_google_gwt_user_client_ui_UIObject_element, id_0);
}

function org_gwtbootstrap3_client_ui_base_mixin_IdMixin_IdMixin__Lcom_google_gwt_user_client_ui_UIObject_2V(uiObject){
  org_gwtbootstrap3_client_ui_base_mixin_AbstractMixin_AbstractMixin__Lcom_google_gwt_user_client_ui_UIObject_2V.call(this, uiObject);
}

com_google_gwt_lang_Runtime_defineClass__Lcom_google_gwt_core_client_JavaScriptObject_2Lcom_google_gwt_core_client_JavaScriptObject_2Lcom_google_gwt_core_client_JavaScriptObject_2V(46, 16, {}, org_gwtbootstrap3_client_ui_base_mixin_IdMixin_IdMixin__Lcom_google_gwt_user_client_ui_UIObject_2V);
var com_google_gwt_lang_ClassLiteralHolder_Lorg_1gwtbootstrap3_1client_1ui_1base_1mixin_1IdMixin_12_1classLit = java_lang_Class_createForClass__Ljava_lang_String_2Ljava_lang_String_2Lcom_google_gwt_core_client_JavaScriptObject_2Ljava_lang_Class_2Ljava_lang_Class_2($intern_73, 'IdMixin', 46);
function org_gwtbootstrap3_client_ui_base_mixin_PullMixin_PullMixin__Lcom_google_gwt_user_client_ui_UIObject_2V(uiObject){
  org_gwtbootstrap3_client_ui_base_mixin_AbstractMixin_AbstractMixin__Lcom_google_gwt_user_client_ui_UIObject_2V.call(this, uiObject);
}

com_google_gwt_lang_Runtime_defineClass__Lcom_google_gwt_core_client_JavaScriptObject_2Lcom_google_gwt_core_client_JavaScriptObject_2Lcom_google_gwt_core_client_JavaScriptObject_2V(39, 16, {}, org_gwtbootstrap3_client_ui_base_mixin_PullMixin_PullMixin__Lcom_google_gwt_user_client_ui_UIObject_2V);
var com_google_gwt_lang_ClassLiteralHolder_Lorg_1gwtbootstrap3_1client_1ui_1base_1mixin_1PullMixin_12_1classLit = java_lang_Class_createForClass__Ljava_lang_String_2Ljava_lang_String_2Lcom_google_gwt_core_client_JavaScriptObject_2Ljava_lang_Class_2Ljava_lang_Class_2($intern_73, 'PullMixin', 39);
function org_gwtbootstrap3_client_ui_base_modal_ModalContent_ModalContent__V(){
  com_google_gwt_user_client_ui_FlowPanel_FlowPanel__V.call(this);
  this.com_google_gwt_user_client_ui_UIObject_element.className = 'modal-content';
}

com_google_gwt_lang_Runtime_defineClass__Lcom_google_gwt_core_client_JavaScriptObject_2Lcom_google_gwt_core_client_JavaScriptObject_2Lcom_google_gwt_core_client_JavaScriptObject_2V(182, 57, $intern_46, org_gwtbootstrap3_client_ui_base_modal_ModalContent_ModalContent__V);
var com_google_gwt_lang_ClassLiteralHolder_Lorg_1gwtbootstrap3_1client_1ui_1base_1modal_1ModalContent_12_1classLit = java_lang_Class_createForClass__Ljava_lang_String_2Ljava_lang_String_2Lcom_google_gwt_core_client_JavaScriptObject_2Ljava_lang_Class_2Ljava_lang_Class_2($intern_75, 'ModalContent', 182);
function org_gwtbootstrap3_client_ui_base_modal_ModalDialog_ModalDialog__V(){
  com_google_gwt_user_client_ui_FlowPanel_FlowPanel__V.call(this);
  this.com_google_gwt_user_client_ui_UIObject_element.className = 'modal-dialog';
}

com_google_gwt_lang_Runtime_defineClass__Lcom_google_gwt_core_client_JavaScriptObject_2Lcom_google_gwt_core_client_JavaScriptObject_2Lcom_google_gwt_core_client_JavaScriptObject_2V(183, 57, $intern_46, org_gwtbootstrap3_client_ui_base_modal_ModalDialog_ModalDialog__V);
var com_google_gwt_lang_ClassLiteralHolder_Lorg_1gwtbootstrap3_1client_1ui_1base_1modal_1ModalDialog_12_1classLit = java_lang_Class_createForClass__Ljava_lang_String_2Ljava_lang_String_2Lcom_google_gwt_core_client_JavaScriptObject_2Ljava_lang_Class_2Ljava_lang_Class_2($intern_75, 'ModalDialog', 183);
function org_gwtbootstrap3_client_ui_constants_ButtonDismiss_$clinit__V(){
  org_gwtbootstrap3_client_ui_constants_ButtonDismiss_$clinit__V = com_google_gwt_lang_Runtime_emptyMethod__V;
  org_gwtbootstrap3_client_ui_constants_ButtonDismiss_MODAL = new org_gwtbootstrap3_client_ui_constants_ButtonDismiss_ButtonDismiss__Ljava_lang_String_2ILjava_lang_String_2V('MODAL', 0, 'modal');
  org_gwtbootstrap3_client_ui_constants_ButtonDismiss_ALERT = new org_gwtbootstrap3_client_ui_constants_ButtonDismiss_ButtonDismiss__Ljava_lang_String_2ILjava_lang_String_2V('ALERT', 1, 'alert');
}

function org_gwtbootstrap3_client_ui_constants_ButtonDismiss_ButtonDismiss__Ljava_lang_String_2ILjava_lang_String_2V(enum$name, enum$ordinal, dismiss){
  java_lang_Enum_Enum__Ljava_lang_String_2IV.call(this, enum$name, enum$ordinal);
  this.org_gwtbootstrap3_client_ui_constants_ButtonDismiss_dismiss = dismiss;
}

function org_gwtbootstrap3_client_ui_constants_ButtonDismiss_values___3Lorg_gwtbootstrap3_client_ui_constants_ButtonDismiss_2(){
  org_gwtbootstrap3_client_ui_constants_ButtonDismiss_$clinit__V();
  return com_google_gwt_lang_Array_stampJavaTypeInfo__Ljava_lang_Class_2Lcom_google_gwt_core_client_JavaScriptObject_2Lcom_google_gwt_core_client_JavaScriptObject_2ILjava_lang_Object_2Ljava_lang_Object_2(com_google_gwt_lang_Array_getClassLiteralForArray__Ljava_lang_Class_2ILjava_lang_Class_2(com_google_gwt_lang_ClassLiteralHolder_Lorg_1gwtbootstrap3_1client_1ui_1constants_1ButtonDismiss_12_1classLit, 1), $intern_5, 81, 0, [org_gwtbootstrap3_client_ui_constants_ButtonDismiss_MODAL, org_gwtbootstrap3_client_ui_constants_ButtonDismiss_ALERT]);
}

com_google_gwt_lang_Runtime_defineClass__Lcom_google_gwt_core_client_JavaScriptObject_2Lcom_google_gwt_core_client_JavaScriptObject_2Lcom_google_gwt_core_client_JavaScriptObject_2V(81, 12, $intern_4, org_gwtbootstrap3_client_ui_constants_ButtonDismiss_ButtonDismiss__Ljava_lang_String_2ILjava_lang_String_2V);
var org_gwtbootstrap3_client_ui_constants_ButtonDismiss_ALERT, org_gwtbootstrap3_client_ui_constants_ButtonDismiss_MODAL;
var com_google_gwt_lang_ClassLiteralHolder_Lorg_1gwtbootstrap3_1client_1ui_1constants_1ButtonDismiss_12_1classLit = java_lang_Class_createForEnum__Ljava_lang_String_2Ljava_lang_String_2Lcom_google_gwt_core_client_JavaScriptObject_2Ljava_lang_Class_2Lcom_google_gwt_core_client_JavaScriptObject_2Lcom_google_gwt_core_client_JavaScriptObject_2Ljava_lang_Class_2($intern_76, 'ButtonDismiss', 81, org_gwtbootstrap3_client_ui_constants_ButtonDismiss_values___3Lorg_gwtbootstrap3_client_ui_constants_ButtonDismiss_2);
function org_gwtbootstrap3_client_ui_constants_ButtonType_$clinit__V(){
  org_gwtbootstrap3_client_ui_constants_ButtonType_$clinit__V = com_google_gwt_lang_Runtime_emptyMethod__V;
  org_gwtbootstrap3_client_ui_constants_ButtonType_DEFAULT = new org_gwtbootstrap3_client_ui_constants_ButtonType_ButtonType__Ljava_lang_String_2ILjava_lang_String_2V($intern_39, 0, 'btn-default');
  org_gwtbootstrap3_client_ui_constants_ButtonType_PRIMARY = new org_gwtbootstrap3_client_ui_constants_ButtonType_ButtonType__Ljava_lang_String_2ILjava_lang_String_2V('PRIMARY', 1, 'btn-primary');
  org_gwtbootstrap3_client_ui_constants_ButtonType_SUCCESS = new org_gwtbootstrap3_client_ui_constants_ButtonType_ButtonType__Ljava_lang_String_2ILjava_lang_String_2V('SUCCESS', 2, 'btn-success');
  org_gwtbootstrap3_client_ui_constants_ButtonType_INFO = new org_gwtbootstrap3_client_ui_constants_ButtonType_ButtonType__Ljava_lang_String_2ILjava_lang_String_2V('INFO', 3, 'btn-info');
  org_gwtbootstrap3_client_ui_constants_ButtonType_WARNING = new org_gwtbootstrap3_client_ui_constants_ButtonType_ButtonType__Ljava_lang_String_2ILjava_lang_String_2V('WARNING', 4, 'btn-warning');
  org_gwtbootstrap3_client_ui_constants_ButtonType_DANGER = new org_gwtbootstrap3_client_ui_constants_ButtonType_ButtonType__Ljava_lang_String_2ILjava_lang_String_2V('DANGER', 5, 'btn-danger');
  org_gwtbootstrap3_client_ui_constants_ButtonType_LINK = new org_gwtbootstrap3_client_ui_constants_ButtonType_ButtonType__Ljava_lang_String_2ILjava_lang_String_2V('LINK', 6, 'btn-link');
}

function org_gwtbootstrap3_client_ui_constants_ButtonType_ButtonType__Ljava_lang_String_2ILjava_lang_String_2V(enum$name, enum$ordinal, cssClass){
  java_lang_Enum_Enum__Ljava_lang_String_2IV.call(this, enum$name, enum$ordinal);
  this.org_gwtbootstrap3_client_ui_constants_ButtonType_cssClass = cssClass;
}

function org_gwtbootstrap3_client_ui_constants_ButtonType_values___3Lorg_gwtbootstrap3_client_ui_constants_ButtonType_2(){
  org_gwtbootstrap3_client_ui_constants_ButtonType_$clinit__V();
  return com_google_gwt_lang_Array_stampJavaTypeInfo__Ljava_lang_Class_2Lcom_google_gwt_core_client_JavaScriptObject_2Lcom_google_gwt_core_client_JavaScriptObject_2ILjava_lang_Object_2Ljava_lang_Object_2(com_google_gwt_lang_Array_getClassLiteralForArray__Ljava_lang_Class_2ILjava_lang_Class_2(com_google_gwt_lang_ClassLiteralHolder_Lorg_1gwtbootstrap3_1client_1ui_1constants_1ButtonType_12_1classLit, 1), $intern_5, 20, 0, [org_gwtbootstrap3_client_ui_constants_ButtonType_DEFAULT, org_gwtbootstrap3_client_ui_constants_ButtonType_PRIMARY, org_gwtbootstrap3_client_ui_constants_ButtonType_SUCCESS, org_gwtbootstrap3_client_ui_constants_ButtonType_INFO, org_gwtbootstrap3_client_ui_constants_ButtonType_WARNING, org_gwtbootstrap3_client_ui_constants_ButtonType_DANGER, org_gwtbootstrap3_client_ui_constants_ButtonType_LINK]);
}

com_google_gwt_lang_Runtime_defineClass__Lcom_google_gwt_core_client_JavaScriptObject_2Lcom_google_gwt_core_client_JavaScriptObject_2Lcom_google_gwt_core_client_JavaScriptObject_2V(20, 12, $intern_31, org_gwtbootstrap3_client_ui_constants_ButtonType_ButtonType__Ljava_lang_String_2ILjava_lang_String_2V);
_.getCssName__Ljava_lang_String_2 = function org_gwtbootstrap3_client_ui_constants_ButtonType_getCssName__Ljava_lang_String_2(){
  return this.org_gwtbootstrap3_client_ui_constants_ButtonType_cssClass;
}
;
var org_gwtbootstrap3_client_ui_constants_ButtonType_DANGER, org_gwtbootstrap3_client_ui_constants_ButtonType_DEFAULT, org_gwtbootstrap3_client_ui_constants_ButtonType_INFO, org_gwtbootstrap3_client_ui_constants_ButtonType_LINK, org_gwtbootstrap3_client_ui_constants_ButtonType_PRIMARY, org_gwtbootstrap3_client_ui_constants_ButtonType_SUCCESS, org_gwtbootstrap3_client_ui_constants_ButtonType_WARNING;
var com_google_gwt_lang_ClassLiteralHolder_Lorg_1gwtbootstrap3_1client_1ui_1constants_1ButtonType_12_1classLit = java_lang_Class_createForEnum__Ljava_lang_String_2Ljava_lang_String_2Lcom_google_gwt_core_client_JavaScriptObject_2Ljava_lang_Class_2Lcom_google_gwt_core_client_JavaScriptObject_2Lcom_google_gwt_core_client_JavaScriptObject_2Ljava_lang_Class_2($intern_76, 'ButtonType', 20, org_gwtbootstrap3_client_ui_constants_ButtonType_values___3Lorg_gwtbootstrap3_client_ui_constants_ButtonType_2);
function org_gwtbootstrap3_client_ui_constants_HeadingSize_$clinit__V(){
  org_gwtbootstrap3_client_ui_constants_HeadingSize_$clinit__V = com_google_gwt_lang_Runtime_emptyMethod__V;
  org_gwtbootstrap3_client_ui_constants_HeadingSize_H1 = new org_gwtbootstrap3_client_ui_constants_HeadingSize_HeadingSize__Ljava_lang_String_2IIV('H1', 0, 1);
  org_gwtbootstrap3_client_ui_constants_HeadingSize_H2 = new org_gwtbootstrap3_client_ui_constants_HeadingSize_HeadingSize__Ljava_lang_String_2IIV('H2', 1, 2);
  org_gwtbootstrap3_client_ui_constants_HeadingSize_H3 = new org_gwtbootstrap3_client_ui_constants_HeadingSize_HeadingSize__Ljava_lang_String_2IIV('H3', 2, 3);
  org_gwtbootstrap3_client_ui_constants_HeadingSize_H4 = new org_gwtbootstrap3_client_ui_constants_HeadingSize_HeadingSize__Ljava_lang_String_2IIV('H4', 3, 4);
  org_gwtbootstrap3_client_ui_constants_HeadingSize_H5 = new org_gwtbootstrap3_client_ui_constants_HeadingSize_HeadingSize__Ljava_lang_String_2IIV('H5', 4, 5);
  org_gwtbootstrap3_client_ui_constants_HeadingSize_H6 = new org_gwtbootstrap3_client_ui_constants_HeadingSize_HeadingSize__Ljava_lang_String_2IIV('H6', 5, 6);
}

function org_gwtbootstrap3_client_ui_constants_HeadingSize_HeadingSize__Ljava_lang_String_2IIV(enum$name, enum$ordinal, headingSize){
  java_lang_Enum_Enum__Ljava_lang_String_2IV.call(this, enum$name, enum$ordinal);
  this.org_gwtbootstrap3_client_ui_constants_HeadingSize_headingSize = headingSize;
}

function org_gwtbootstrap3_client_ui_constants_HeadingSize_values___3Lorg_gwtbootstrap3_client_ui_constants_HeadingSize_2(){
  org_gwtbootstrap3_client_ui_constants_HeadingSize_$clinit__V();
  return com_google_gwt_lang_Array_stampJavaTypeInfo__Ljava_lang_Class_2Lcom_google_gwt_core_client_JavaScriptObject_2Lcom_google_gwt_core_client_JavaScriptObject_2ILjava_lang_Object_2Ljava_lang_Object_2(com_google_gwt_lang_Array_getClassLiteralForArray__Ljava_lang_Class_2ILjava_lang_Class_2(com_google_gwt_lang_ClassLiteralHolder_Lorg_1gwtbootstrap3_1client_1ui_1constants_1HeadingSize_12_1classLit, 1), $intern_5, 25, 0, [org_gwtbootstrap3_client_ui_constants_HeadingSize_H1, org_gwtbootstrap3_client_ui_constants_HeadingSize_H2, org_gwtbootstrap3_client_ui_constants_HeadingSize_H3, org_gwtbootstrap3_client_ui_constants_HeadingSize_H4, org_gwtbootstrap3_client_ui_constants_HeadingSize_H5, org_gwtbootstrap3_client_ui_constants_HeadingSize_H6]);
}

com_google_gwt_lang_Runtime_defineClass__Lcom_google_gwt_core_client_JavaScriptObject_2Lcom_google_gwt_core_client_JavaScriptObject_2Lcom_google_gwt_core_client_JavaScriptObject_2V(25, 12, $intern_4, org_gwtbootstrap3_client_ui_constants_HeadingSize_HeadingSize__Ljava_lang_String_2IIV);
_.org_gwtbootstrap3_client_ui_constants_HeadingSize_headingSize = 0;
var org_gwtbootstrap3_client_ui_constants_HeadingSize_H1, org_gwtbootstrap3_client_ui_constants_HeadingSize_H2, org_gwtbootstrap3_client_ui_constants_HeadingSize_H3, org_gwtbootstrap3_client_ui_constants_HeadingSize_H4, org_gwtbootstrap3_client_ui_constants_HeadingSize_H5, org_gwtbootstrap3_client_ui_constants_HeadingSize_H6;
var com_google_gwt_lang_ClassLiteralHolder_Lorg_1gwtbootstrap3_1client_1ui_1constants_1HeadingSize_12_1classLit = java_lang_Class_createForEnum__Ljava_lang_String_2Ljava_lang_String_2Lcom_google_gwt_core_client_JavaScriptObject_2Ljava_lang_Class_2Lcom_google_gwt_core_client_JavaScriptObject_2Lcom_google_gwt_core_client_JavaScriptObject_2Ljava_lang_Class_2($intern_76, 'HeadingSize', 25, org_gwtbootstrap3_client_ui_constants_HeadingSize_values___3Lorg_gwtbootstrap3_client_ui_constants_HeadingSize_2);
function org_gwtbootstrap3_client_ui_constants_IconFlip_$clinit__V(){
  org_gwtbootstrap3_client_ui_constants_IconFlip_$clinit__V = com_google_gwt_lang_Runtime_emptyMethod__V;
  org_gwtbootstrap3_client_ui_constants_IconFlip_NONE = new org_gwtbootstrap3_client_ui_constants_IconFlip_IconFlip__Ljava_lang_String_2ILjava_lang_String_2V($intern_77, 0, '');
  org_gwtbootstrap3_client_ui_constants_IconFlip_HORIZONTAL = new org_gwtbootstrap3_client_ui_constants_IconFlip_IconFlip__Ljava_lang_String_2ILjava_lang_String_2V('HORIZONTAL', 1, 'fa-flip-horizontal');
  org_gwtbootstrap3_client_ui_constants_IconFlip_VERTICAL = new org_gwtbootstrap3_client_ui_constants_IconFlip_IconFlip__Ljava_lang_String_2ILjava_lang_String_2V('VERTICAL', 2, 'fa-flip-vertical');
}

function org_gwtbootstrap3_client_ui_constants_IconFlip_IconFlip__Ljava_lang_String_2ILjava_lang_String_2V(enum$name, enum$ordinal, cssClass){
  java_lang_Enum_Enum__Ljava_lang_String_2IV.call(this, enum$name, enum$ordinal);
  this.org_gwtbootstrap3_client_ui_constants_IconFlip_cssClass = cssClass;
}

function org_gwtbootstrap3_client_ui_constants_IconFlip_values___3Lorg_gwtbootstrap3_client_ui_constants_IconFlip_2(){
  org_gwtbootstrap3_client_ui_constants_IconFlip_$clinit__V();
  return com_google_gwt_lang_Array_stampJavaTypeInfo__Ljava_lang_Class_2Lcom_google_gwt_core_client_JavaScriptObject_2Lcom_google_gwt_core_client_JavaScriptObject_2ILjava_lang_Object_2Ljava_lang_Object_2(com_google_gwt_lang_Array_getClassLiteralForArray__Ljava_lang_Class_2ILjava_lang_Class_2(com_google_gwt_lang_ClassLiteralHolder_Lorg_1gwtbootstrap3_1client_1ui_1constants_1IconFlip_12_1classLit, 1), $intern_5, 63, 0, [org_gwtbootstrap3_client_ui_constants_IconFlip_NONE, org_gwtbootstrap3_client_ui_constants_IconFlip_HORIZONTAL, org_gwtbootstrap3_client_ui_constants_IconFlip_VERTICAL]);
}

com_google_gwt_lang_Runtime_defineClass__Lcom_google_gwt_core_client_JavaScriptObject_2Lcom_google_gwt_core_client_JavaScriptObject_2Lcom_google_gwt_core_client_JavaScriptObject_2V(63, 12, $intern_31, org_gwtbootstrap3_client_ui_constants_IconFlip_IconFlip__Ljava_lang_String_2ILjava_lang_String_2V);
_.getCssName__Ljava_lang_String_2 = function org_gwtbootstrap3_client_ui_constants_IconFlip_getCssName__Ljava_lang_String_2(){
  return this.org_gwtbootstrap3_client_ui_constants_IconFlip_cssClass;
}
;
var org_gwtbootstrap3_client_ui_constants_IconFlip_HORIZONTAL, org_gwtbootstrap3_client_ui_constants_IconFlip_NONE, org_gwtbootstrap3_client_ui_constants_IconFlip_VERTICAL;
var com_google_gwt_lang_ClassLiteralHolder_Lorg_1gwtbootstrap3_1client_1ui_1constants_1IconFlip_12_1classLit = java_lang_Class_createForEnum__Ljava_lang_String_2Ljava_lang_String_2Lcom_google_gwt_core_client_JavaScriptObject_2Ljava_lang_Class_2Lcom_google_gwt_core_client_JavaScriptObject_2Lcom_google_gwt_core_client_JavaScriptObject_2Ljava_lang_Class_2($intern_76, 'IconFlip', 63, org_gwtbootstrap3_client_ui_constants_IconFlip_values___3Lorg_gwtbootstrap3_client_ui_constants_IconFlip_2);
function org_gwtbootstrap3_client_ui_constants_IconRotate_$clinit__V(){
  org_gwtbootstrap3_client_ui_constants_IconRotate_$clinit__V = com_google_gwt_lang_Runtime_emptyMethod__V;
  org_gwtbootstrap3_client_ui_constants_IconRotate_NONE = new org_gwtbootstrap3_client_ui_constants_IconRotate_IconRotate__Ljava_lang_String_2ILjava_lang_String_2V($intern_77, 0, '');
  org_gwtbootstrap3_client_ui_constants_IconRotate_ROTATE_190 = new org_gwtbootstrap3_client_ui_constants_IconRotate_IconRotate__Ljava_lang_String_2ILjava_lang_String_2V('ROTATE_90', 1, 'fa-rotate-90');
  org_gwtbootstrap3_client_ui_constants_IconRotate_ROTATE_1180 = new org_gwtbootstrap3_client_ui_constants_IconRotate_IconRotate__Ljava_lang_String_2ILjava_lang_String_2V('ROTATE_180', 2, 'fa-rotate-180');
  org_gwtbootstrap3_client_ui_constants_IconRotate_ROTATE_1270 = new org_gwtbootstrap3_client_ui_constants_IconRotate_IconRotate__Ljava_lang_String_2ILjava_lang_String_2V('ROTATE_270', 3, 'fa-rotate-270');
}

function org_gwtbootstrap3_client_ui_constants_IconRotate_IconRotate__Ljava_lang_String_2ILjava_lang_String_2V(enum$name, enum$ordinal, cssClass){
  java_lang_Enum_Enum__Ljava_lang_String_2IV.call(this, enum$name, enum$ordinal);
  this.org_gwtbootstrap3_client_ui_constants_IconRotate_cssClass = cssClass;
}

function org_gwtbootstrap3_client_ui_constants_IconRotate_values___3Lorg_gwtbootstrap3_client_ui_constants_IconRotate_2(){
  org_gwtbootstrap3_client_ui_constants_IconRotate_$clinit__V();
  return com_google_gwt_lang_Array_stampJavaTypeInfo__Ljava_lang_Class_2Lcom_google_gwt_core_client_JavaScriptObject_2Lcom_google_gwt_core_client_JavaScriptObject_2ILjava_lang_Object_2Ljava_lang_Object_2(com_google_gwt_lang_Array_getClassLiteralForArray__Ljava_lang_Class_2ILjava_lang_Class_2(com_google_gwt_lang_ClassLiteralHolder_Lorg_1gwtbootstrap3_1client_1ui_1constants_1IconRotate_12_1classLit, 1), $intern_5, 47, 0, [org_gwtbootstrap3_client_ui_constants_IconRotate_NONE, org_gwtbootstrap3_client_ui_constants_IconRotate_ROTATE_190, org_gwtbootstrap3_client_ui_constants_IconRotate_ROTATE_1180, org_gwtbootstrap3_client_ui_constants_IconRotate_ROTATE_1270]);
}

com_google_gwt_lang_Runtime_defineClass__Lcom_google_gwt_core_client_JavaScriptObject_2Lcom_google_gwt_core_client_JavaScriptObject_2Lcom_google_gwt_core_client_JavaScriptObject_2V(47, 12, $intern_31, org_gwtbootstrap3_client_ui_constants_IconRotate_IconRotate__Ljava_lang_String_2ILjava_lang_String_2V);
_.getCssName__Ljava_lang_String_2 = function org_gwtbootstrap3_client_ui_constants_IconRotate_getCssName__Ljava_lang_String_2(){
  return this.org_gwtbootstrap3_client_ui_constants_IconRotate_cssClass;
}
;
var org_gwtbootstrap3_client_ui_constants_IconRotate_NONE, org_gwtbootstrap3_client_ui_constants_IconRotate_ROTATE_1180, org_gwtbootstrap3_client_ui_constants_IconRotate_ROTATE_1270, org_gwtbootstrap3_client_ui_constants_IconRotate_ROTATE_190;
var com_google_gwt_lang_ClassLiteralHolder_Lorg_1gwtbootstrap3_1client_1ui_1constants_1IconRotate_12_1classLit = java_lang_Class_createForEnum__Ljava_lang_String_2Ljava_lang_String_2Lcom_google_gwt_core_client_JavaScriptObject_2Ljava_lang_Class_2Lcom_google_gwt_core_client_JavaScriptObject_2Lcom_google_gwt_core_client_JavaScriptObject_2Ljava_lang_Class_2($intern_76, 'IconRotate', 47, org_gwtbootstrap3_client_ui_constants_IconRotate_values___3Lorg_gwtbootstrap3_client_ui_constants_IconRotate_2);
function org_gwtbootstrap3_client_ui_constants_IconSize_$clinit__V(){
  org_gwtbootstrap3_client_ui_constants_IconSize_$clinit__V = com_google_gwt_lang_Runtime_emptyMethod__V;
  org_gwtbootstrap3_client_ui_constants_IconSize_NONE = new org_gwtbootstrap3_client_ui_constants_IconSize_IconSize__Ljava_lang_String_2ILjava_lang_String_2V($intern_77, 0, '');
  org_gwtbootstrap3_client_ui_constants_IconSize_LARGE = new org_gwtbootstrap3_client_ui_constants_IconSize_IconSize__Ljava_lang_String_2ILjava_lang_String_2V('LARGE', 1, 'fa-lg');
  org_gwtbootstrap3_client_ui_constants_IconSize_TIMES2 = new org_gwtbootstrap3_client_ui_constants_IconSize_IconSize__Ljava_lang_String_2ILjava_lang_String_2V('TIMES2', 2, 'fa-2x');
  org_gwtbootstrap3_client_ui_constants_IconSize_TIMES3 = new org_gwtbootstrap3_client_ui_constants_IconSize_IconSize__Ljava_lang_String_2ILjava_lang_String_2V('TIMES3', 3, 'fa-3x');
  org_gwtbootstrap3_client_ui_constants_IconSize_TIMES4 = new org_gwtbootstrap3_client_ui_constants_IconSize_IconSize__Ljava_lang_String_2ILjava_lang_String_2V('TIMES4', 4, 'fa-4x');
  org_gwtbootstrap3_client_ui_constants_IconSize_TIMES5 = new org_gwtbootstrap3_client_ui_constants_IconSize_IconSize__Ljava_lang_String_2ILjava_lang_String_2V('TIMES5', 5, 'fa-5x');
}

function org_gwtbootstrap3_client_ui_constants_IconSize_IconSize__Ljava_lang_String_2ILjava_lang_String_2V(enum$name, enum$ordinal, cssClass){
  java_lang_Enum_Enum__Ljava_lang_String_2IV.call(this, enum$name, enum$ordinal);
  this.org_gwtbootstrap3_client_ui_constants_IconSize_cssClass = cssClass;
}

function org_gwtbootstrap3_client_ui_constants_IconSize_values___3Lorg_gwtbootstrap3_client_ui_constants_IconSize_2(){
  org_gwtbootstrap3_client_ui_constants_IconSize_$clinit__V();
  return com_google_gwt_lang_Array_stampJavaTypeInfo__Ljava_lang_Class_2Lcom_google_gwt_core_client_JavaScriptObject_2Lcom_google_gwt_core_client_JavaScriptObject_2ILjava_lang_Object_2Ljava_lang_Object_2(com_google_gwt_lang_Array_getClassLiteralForArray__Ljava_lang_Class_2ILjava_lang_Class_2(com_google_gwt_lang_ClassLiteralHolder_Lorg_1gwtbootstrap3_1client_1ui_1constants_1IconSize_12_1classLit, 1), $intern_5, 27, 0, [org_gwtbootstrap3_client_ui_constants_IconSize_NONE, org_gwtbootstrap3_client_ui_constants_IconSize_LARGE, org_gwtbootstrap3_client_ui_constants_IconSize_TIMES2, org_gwtbootstrap3_client_ui_constants_IconSize_TIMES3, org_gwtbootstrap3_client_ui_constants_IconSize_TIMES4, org_gwtbootstrap3_client_ui_constants_IconSize_TIMES5]);
}

com_google_gwt_lang_Runtime_defineClass__Lcom_google_gwt_core_client_JavaScriptObject_2Lcom_google_gwt_core_client_JavaScriptObject_2Lcom_google_gwt_core_client_JavaScriptObject_2V(27, 12, $intern_31, org_gwtbootstrap3_client_ui_constants_IconSize_IconSize__Ljava_lang_String_2ILjava_lang_String_2V);
_.getCssName__Ljava_lang_String_2 = function org_gwtbootstrap3_client_ui_constants_IconSize_getCssName__Ljava_lang_String_2(){
  return this.org_gwtbootstrap3_client_ui_constants_IconSize_cssClass;
}
;
var org_gwtbootstrap3_client_ui_constants_IconSize_LARGE, org_gwtbootstrap3_client_ui_constants_IconSize_NONE, org_gwtbootstrap3_client_ui_constants_IconSize_TIMES2, org_gwtbootstrap3_client_ui_constants_IconSize_TIMES3, org_gwtbootstrap3_client_ui_constants_IconSize_TIMES4, org_gwtbootstrap3_client_ui_constants_IconSize_TIMES5;
var com_google_gwt_lang_ClassLiteralHolder_Lorg_1gwtbootstrap3_1client_1ui_1constants_1IconSize_12_1classLit = java_lang_Class_createForEnum__Ljava_lang_String_2Ljava_lang_String_2Lcom_google_gwt_core_client_JavaScriptObject_2Ljava_lang_Class_2Lcom_google_gwt_core_client_JavaScriptObject_2Lcom_google_gwt_core_client_JavaScriptObject_2Ljava_lang_Class_2($intern_76, 'IconSize', 27, org_gwtbootstrap3_client_ui_constants_IconSize_values___3Lorg_gwtbootstrap3_client_ui_constants_IconSize_2);
function org_gwtbootstrap3_client_ui_constants_ModalBackdrop_$clinit__V(){
  org_gwtbootstrap3_client_ui_constants_ModalBackdrop_$clinit__V = com_google_gwt_lang_Runtime_emptyMethod__V;
  org_gwtbootstrap3_client_ui_constants_ModalBackdrop_TRUE = new org_gwtbootstrap3_client_ui_constants_ModalBackdrop_ModalBackdrop__Ljava_lang_String_2ILjava_lang_String_2V('TRUE', 0, $intern_7);
  org_gwtbootstrap3_client_ui_constants_ModalBackdrop_FALSE = new org_gwtbootstrap3_client_ui_constants_ModalBackdrop_ModalBackdrop__Ljava_lang_String_2ILjava_lang_String_2V('FALSE', 1, 'false');
  org_gwtbootstrap3_client_ui_constants_ModalBackdrop_STATIC = new org_gwtbootstrap3_client_ui_constants_ModalBackdrop_ModalBackdrop__Ljava_lang_String_2ILjava_lang_String_2V('STATIC', 2, 'static');
}

function org_gwtbootstrap3_client_ui_constants_ModalBackdrop_ModalBackdrop__Ljava_lang_String_2ILjava_lang_String_2V(enum$name, enum$ordinal, backdrop){
  java_lang_Enum_Enum__Ljava_lang_String_2IV.call(this, enum$name, enum$ordinal);
  this.org_gwtbootstrap3_client_ui_constants_ModalBackdrop_backdrop = backdrop;
}

function org_gwtbootstrap3_client_ui_constants_ModalBackdrop_values___3Lorg_gwtbootstrap3_client_ui_constants_ModalBackdrop_2(){
  org_gwtbootstrap3_client_ui_constants_ModalBackdrop_$clinit__V();
  return com_google_gwt_lang_Array_stampJavaTypeInfo__Ljava_lang_Class_2Lcom_google_gwt_core_client_JavaScriptObject_2Lcom_google_gwt_core_client_JavaScriptObject_2ILjava_lang_Object_2Ljava_lang_Object_2(com_google_gwt_lang_Array_getClassLiteralForArray__Ljava_lang_Class_2ILjava_lang_Class_2(com_google_gwt_lang_ClassLiteralHolder_Lorg_1gwtbootstrap3_1client_1ui_1constants_1ModalBackdrop_12_1classLit, 1), $intern_5, 58, 0, [org_gwtbootstrap3_client_ui_constants_ModalBackdrop_TRUE, org_gwtbootstrap3_client_ui_constants_ModalBackdrop_FALSE, org_gwtbootstrap3_client_ui_constants_ModalBackdrop_STATIC]);
}

com_google_gwt_lang_Runtime_defineClass__Lcom_google_gwt_core_client_JavaScriptObject_2Lcom_google_gwt_core_client_JavaScriptObject_2Lcom_google_gwt_core_client_JavaScriptObject_2V(58, 12, $intern_4, org_gwtbootstrap3_client_ui_constants_ModalBackdrop_ModalBackdrop__Ljava_lang_String_2ILjava_lang_String_2V);
var org_gwtbootstrap3_client_ui_constants_ModalBackdrop_FALSE, org_gwtbootstrap3_client_ui_constants_ModalBackdrop_STATIC, org_gwtbootstrap3_client_ui_constants_ModalBackdrop_TRUE;
var com_google_gwt_lang_ClassLiteralHolder_Lorg_1gwtbootstrap3_1client_1ui_1constants_1ModalBackdrop_12_1classLit = java_lang_Class_createForEnum__Ljava_lang_String_2Ljava_lang_String_2Lcom_google_gwt_core_client_JavaScriptObject_2Ljava_lang_Class_2Lcom_google_gwt_core_client_JavaScriptObject_2Lcom_google_gwt_core_client_JavaScriptObject_2Ljava_lang_Class_2($intern_76, 'ModalBackdrop', 58, org_gwtbootstrap3_client_ui_constants_ModalBackdrop_values___3Lorg_gwtbootstrap3_client_ui_constants_ModalBackdrop_2);
function org_gwtbootstrap3_client_ui_constants_NavbarPull_$clinit__V(){
  org_gwtbootstrap3_client_ui_constants_NavbarPull_$clinit__V = com_google_gwt_lang_Runtime_emptyMethod__V;
  org_gwtbootstrap3_client_ui_constants_NavbarPull_NONE = new org_gwtbootstrap3_client_ui_constants_NavbarPull_NavbarPull__Ljava_lang_String_2ILjava_lang_String_2V($intern_77, 0, '');
  org_gwtbootstrap3_client_ui_constants_NavbarPull_LEFT = new org_gwtbootstrap3_client_ui_constants_NavbarPull_NavbarPull__Ljava_lang_String_2ILjava_lang_String_2V('LEFT', 1, 'navbar-left');
  org_gwtbootstrap3_client_ui_constants_NavbarPull_RIGHT = new org_gwtbootstrap3_client_ui_constants_NavbarPull_NavbarPull__Ljava_lang_String_2ILjava_lang_String_2V('RIGHT', 2, 'navbar-right');
}

function org_gwtbootstrap3_client_ui_constants_NavbarPull_NavbarPull__Ljava_lang_String_2ILjava_lang_String_2V(enum$name, enum$ordinal, cssClass){
  java_lang_Enum_Enum__Ljava_lang_String_2IV.call(this, enum$name, enum$ordinal);
  this.org_gwtbootstrap3_client_ui_constants_NavbarPull_cssClass = cssClass;
}

function org_gwtbootstrap3_client_ui_constants_NavbarPull_values___3Lorg_gwtbootstrap3_client_ui_constants_NavbarPull_2(){
  org_gwtbootstrap3_client_ui_constants_NavbarPull_$clinit__V();
  return com_google_gwt_lang_Array_stampJavaTypeInfo__Ljava_lang_Class_2Lcom_google_gwt_core_client_JavaScriptObject_2Lcom_google_gwt_core_client_JavaScriptObject_2ILjava_lang_Object_2Ljava_lang_Object_2(com_google_gwt_lang_Array_getClassLiteralForArray__Ljava_lang_Class_2ILjava_lang_Class_2(com_google_gwt_lang_ClassLiteralHolder_Lorg_1gwtbootstrap3_1client_1ui_1constants_1NavbarPull_12_1classLit, 1), $intern_5, 59, 0, [org_gwtbootstrap3_client_ui_constants_NavbarPull_NONE, org_gwtbootstrap3_client_ui_constants_NavbarPull_LEFT, org_gwtbootstrap3_client_ui_constants_NavbarPull_RIGHT]);
}

com_google_gwt_lang_Runtime_defineClass__Lcom_google_gwt_core_client_JavaScriptObject_2Lcom_google_gwt_core_client_JavaScriptObject_2Lcom_google_gwt_core_client_JavaScriptObject_2V(59, 12, $intern_31, org_gwtbootstrap3_client_ui_constants_NavbarPull_NavbarPull__Ljava_lang_String_2ILjava_lang_String_2V);
_.getCssName__Ljava_lang_String_2 = function org_gwtbootstrap3_client_ui_constants_NavbarPull_getCssName__Ljava_lang_String_2(){
  return this.org_gwtbootstrap3_client_ui_constants_NavbarPull_cssClass;
}
;
var org_gwtbootstrap3_client_ui_constants_NavbarPull_LEFT, org_gwtbootstrap3_client_ui_constants_NavbarPull_NONE, org_gwtbootstrap3_client_ui_constants_NavbarPull_RIGHT;
var com_google_gwt_lang_ClassLiteralHolder_Lorg_1gwtbootstrap3_1client_1ui_1constants_1NavbarPull_12_1classLit = java_lang_Class_createForEnum__Ljava_lang_String_2Ljava_lang_String_2Lcom_google_gwt_core_client_JavaScriptObject_2Ljava_lang_Class_2Lcom_google_gwt_core_client_JavaScriptObject_2Lcom_google_gwt_core_client_JavaScriptObject_2Ljava_lang_Class_2($intern_76, 'NavbarPull', 59, org_gwtbootstrap3_client_ui_constants_NavbarPull_values___3Lorg_gwtbootstrap3_client_ui_constants_NavbarPull_2);
function org_gwtbootstrap3_client_ui_constants_NavbarType_$clinit__V(){
  org_gwtbootstrap3_client_ui_constants_NavbarType_$clinit__V = com_google_gwt_lang_Runtime_emptyMethod__V;
  org_gwtbootstrap3_client_ui_constants_NavbarType_DEFAULT = new org_gwtbootstrap3_client_ui_constants_NavbarType_NavbarType__Ljava_lang_String_2ILjava_lang_String_2V($intern_39, 0, 'navbar-default');
  org_gwtbootstrap3_client_ui_constants_NavbarType_INVERSE = new org_gwtbootstrap3_client_ui_constants_NavbarType_NavbarType__Ljava_lang_String_2ILjava_lang_String_2V('INVERSE', 1, 'navbar-inverse');
}

function org_gwtbootstrap3_client_ui_constants_NavbarType_NavbarType__Ljava_lang_String_2ILjava_lang_String_2V(enum$name, enum$ordinal, cssClass){
  java_lang_Enum_Enum__Ljava_lang_String_2IV.call(this, enum$name, enum$ordinal);
  this.org_gwtbootstrap3_client_ui_constants_NavbarType_cssClass = cssClass;
}

function org_gwtbootstrap3_client_ui_constants_NavbarType_values___3Lorg_gwtbootstrap3_client_ui_constants_NavbarType_2(){
  org_gwtbootstrap3_client_ui_constants_NavbarType_$clinit__V();
  return com_google_gwt_lang_Array_stampJavaTypeInfo__Ljava_lang_Class_2Lcom_google_gwt_core_client_JavaScriptObject_2Lcom_google_gwt_core_client_JavaScriptObject_2ILjava_lang_Object_2Ljava_lang_Object_2(com_google_gwt_lang_Array_getClassLiteralForArray__Ljava_lang_Class_2ILjava_lang_Class_2(com_google_gwt_lang_ClassLiteralHolder_Lorg_1gwtbootstrap3_1client_1ui_1constants_1NavbarType_12_1classLit, 1), $intern_5, 79, 0, [org_gwtbootstrap3_client_ui_constants_NavbarType_DEFAULT, org_gwtbootstrap3_client_ui_constants_NavbarType_INVERSE]);
}

com_google_gwt_lang_Runtime_defineClass__Lcom_google_gwt_core_client_JavaScriptObject_2Lcom_google_gwt_core_client_JavaScriptObject_2Lcom_google_gwt_core_client_JavaScriptObject_2V(79, 12, $intern_31, org_gwtbootstrap3_client_ui_constants_NavbarType_NavbarType__Ljava_lang_String_2ILjava_lang_String_2V);
_.getCssName__Ljava_lang_String_2 = function org_gwtbootstrap3_client_ui_constants_NavbarType_getCssName__Ljava_lang_String_2(){
  return this.org_gwtbootstrap3_client_ui_constants_NavbarType_cssClass;
}
;
var org_gwtbootstrap3_client_ui_constants_NavbarType_DEFAULT, org_gwtbootstrap3_client_ui_constants_NavbarType_INVERSE;
var com_google_gwt_lang_ClassLiteralHolder_Lorg_1gwtbootstrap3_1client_1ui_1constants_1NavbarType_12_1classLit = java_lang_Class_createForEnum__Ljava_lang_String_2Ljava_lang_String_2Lcom_google_gwt_core_client_JavaScriptObject_2Ljava_lang_Class_2Lcom_google_gwt_core_client_JavaScriptObject_2Lcom_google_gwt_core_client_JavaScriptObject_2Ljava_lang_Class_2($intern_76, 'NavbarType', 79, org_gwtbootstrap3_client_ui_constants_NavbarType_values___3Lorg_gwtbootstrap3_client_ui_constants_NavbarType_2);
function org_gwtbootstrap3_client_ui_constants_Pull_$clinit__V(){
  org_gwtbootstrap3_client_ui_constants_Pull_$clinit__V = com_google_gwt_lang_Runtime_emptyMethod__V;
  org_gwtbootstrap3_client_ui_constants_Pull_NONE = new org_gwtbootstrap3_client_ui_constants_Pull_Pull__Ljava_lang_String_2ILjava_lang_String_2V($intern_77, 0, '');
  org_gwtbootstrap3_client_ui_constants_Pull_LEFT = new org_gwtbootstrap3_client_ui_constants_Pull_Pull__Ljava_lang_String_2ILjava_lang_String_2V('LEFT', 1, 'pull-left');
  org_gwtbootstrap3_client_ui_constants_Pull_RIGHT = new org_gwtbootstrap3_client_ui_constants_Pull_Pull__Ljava_lang_String_2ILjava_lang_String_2V('RIGHT', 2, 'pull-right');
}

function org_gwtbootstrap3_client_ui_constants_Pull_Pull__Ljava_lang_String_2ILjava_lang_String_2V(enum$name, enum$ordinal, cssClass){
  java_lang_Enum_Enum__Ljava_lang_String_2IV.call(this, enum$name, enum$ordinal);
  this.org_gwtbootstrap3_client_ui_constants_Pull_cssClass = cssClass;
}

function org_gwtbootstrap3_client_ui_constants_Pull_values___3Lorg_gwtbootstrap3_client_ui_constants_Pull_2(){
  org_gwtbootstrap3_client_ui_constants_Pull_$clinit__V();
  return com_google_gwt_lang_Array_stampJavaTypeInfo__Ljava_lang_Class_2Lcom_google_gwt_core_client_JavaScriptObject_2Lcom_google_gwt_core_client_JavaScriptObject_2ILjava_lang_Object_2Ljava_lang_Object_2(com_google_gwt_lang_Array_getClassLiteralForArray__Ljava_lang_Class_2ILjava_lang_Class_2(com_google_gwt_lang_ClassLiteralHolder_Lorg_1gwtbootstrap3_1client_1ui_1constants_1Pull_12_1classLit, 1), $intern_5, 61, 0, [org_gwtbootstrap3_client_ui_constants_Pull_NONE, org_gwtbootstrap3_client_ui_constants_Pull_LEFT, org_gwtbootstrap3_client_ui_constants_Pull_RIGHT]);
}

com_google_gwt_lang_Runtime_defineClass__Lcom_google_gwt_core_client_JavaScriptObject_2Lcom_google_gwt_core_client_JavaScriptObject_2Lcom_google_gwt_core_client_JavaScriptObject_2V(61, 12, $intern_31, org_gwtbootstrap3_client_ui_constants_Pull_Pull__Ljava_lang_String_2ILjava_lang_String_2V);
_.getCssName__Ljava_lang_String_2 = function org_gwtbootstrap3_client_ui_constants_Pull_getCssName__Ljava_lang_String_2(){
  return this.org_gwtbootstrap3_client_ui_constants_Pull_cssClass;
}
;
var org_gwtbootstrap3_client_ui_constants_Pull_LEFT, org_gwtbootstrap3_client_ui_constants_Pull_NONE, org_gwtbootstrap3_client_ui_constants_Pull_RIGHT;
var com_google_gwt_lang_ClassLiteralHolder_Lorg_1gwtbootstrap3_1client_1ui_1constants_1Pull_12_1classLit = java_lang_Class_createForEnum__Ljava_lang_String_2Ljava_lang_String_2Lcom_google_gwt_core_client_JavaScriptObject_2Ljava_lang_Class_2Lcom_google_gwt_core_client_JavaScriptObject_2Lcom_google_gwt_core_client_JavaScriptObject_2Ljava_lang_Class_2($intern_76, 'Pull', 61, org_gwtbootstrap3_client_ui_constants_Pull_values___3Lorg_gwtbootstrap3_client_ui_constants_Pull_2);
function org_gwtbootstrap3_client_ui_constants_Toggle_$clinit__V(){
  org_gwtbootstrap3_client_ui_constants_Toggle_$clinit__V = com_google_gwt_lang_Runtime_emptyMethod__V;
  org_gwtbootstrap3_client_ui_constants_Toggle_BUTTON = new org_gwtbootstrap3_client_ui_constants_Toggle_Toggle__Ljava_lang_String_2ILjava_lang_String_2V('BUTTON', 0, 'button');
  org_gwtbootstrap3_client_ui_constants_Toggle_BUTTONS = new org_gwtbootstrap3_client_ui_constants_Toggle_Toggle__Ljava_lang_String_2ILjava_lang_String_2V('BUTTONS', 1, 'buttons');
  org_gwtbootstrap3_client_ui_constants_Toggle_COLLAPSE = new org_gwtbootstrap3_client_ui_constants_Toggle_Toggle__Ljava_lang_String_2ILjava_lang_String_2V('COLLAPSE', 2, 'collapse');
  org_gwtbootstrap3_client_ui_constants_Toggle_DROPDOWN = new org_gwtbootstrap3_client_ui_constants_Toggle_Toggle__Ljava_lang_String_2ILjava_lang_String_2V('DROPDOWN', 3, 'dropdown');
  org_gwtbootstrap3_client_ui_constants_Toggle_TAB = new org_gwtbootstrap3_client_ui_constants_Toggle_Toggle__Ljava_lang_String_2ILjava_lang_String_2V('TAB', 4, 'tab');
  org_gwtbootstrap3_client_ui_constants_Toggle_MODAL = new org_gwtbootstrap3_client_ui_constants_Toggle_Toggle__Ljava_lang_String_2ILjava_lang_String_2V('MODAL', 5, 'modal');
}

function org_gwtbootstrap3_client_ui_constants_Toggle_Toggle__Ljava_lang_String_2ILjava_lang_String_2V(enum$name, enum$ordinal, toggle){
  java_lang_Enum_Enum__Ljava_lang_String_2IV.call(this, enum$name, enum$ordinal);
  this.org_gwtbootstrap3_client_ui_constants_Toggle_toggle = toggle;
}

function org_gwtbootstrap3_client_ui_constants_Toggle_values___3Lorg_gwtbootstrap3_client_ui_constants_Toggle_2(){
  org_gwtbootstrap3_client_ui_constants_Toggle_$clinit__V();
  return com_google_gwt_lang_Array_stampJavaTypeInfo__Ljava_lang_Class_2Lcom_google_gwt_core_client_JavaScriptObject_2Lcom_google_gwt_core_client_JavaScriptObject_2ILjava_lang_Object_2Ljava_lang_Object_2(com_google_gwt_lang_Array_getClassLiteralForArray__Ljava_lang_Class_2ILjava_lang_Class_2(com_google_gwt_lang_ClassLiteralHolder_Lorg_1gwtbootstrap3_1client_1ui_1constants_1Toggle_12_1classLit, 1), $intern_5, 26, 0, [org_gwtbootstrap3_client_ui_constants_Toggle_BUTTON, org_gwtbootstrap3_client_ui_constants_Toggle_BUTTONS, org_gwtbootstrap3_client_ui_constants_Toggle_COLLAPSE, org_gwtbootstrap3_client_ui_constants_Toggle_DROPDOWN, org_gwtbootstrap3_client_ui_constants_Toggle_TAB, org_gwtbootstrap3_client_ui_constants_Toggle_MODAL]);
}

com_google_gwt_lang_Runtime_defineClass__Lcom_google_gwt_core_client_JavaScriptObject_2Lcom_google_gwt_core_client_JavaScriptObject_2Lcom_google_gwt_core_client_JavaScriptObject_2V(26, 12, $intern_4, org_gwtbootstrap3_client_ui_constants_Toggle_Toggle__Ljava_lang_String_2ILjava_lang_String_2V);
var org_gwtbootstrap3_client_ui_constants_Toggle_BUTTON, org_gwtbootstrap3_client_ui_constants_Toggle_BUTTONS, org_gwtbootstrap3_client_ui_constants_Toggle_COLLAPSE, org_gwtbootstrap3_client_ui_constants_Toggle_DROPDOWN, org_gwtbootstrap3_client_ui_constants_Toggle_MODAL, org_gwtbootstrap3_client_ui_constants_Toggle_TAB;
var com_google_gwt_lang_ClassLiteralHolder_Lorg_1gwtbootstrap3_1client_1ui_1constants_1Toggle_12_1classLit = java_lang_Class_createForEnum__Ljava_lang_String_2Ljava_lang_String_2Lcom_google_gwt_core_client_JavaScriptObject_2Ljava_lang_Class_2Lcom_google_gwt_core_client_JavaScriptObject_2Lcom_google_gwt_core_client_JavaScriptObject_2Ljava_lang_Class_2($intern_76, 'Toggle', 26, org_gwtbootstrap3_client_ui_constants_Toggle_values___3Lorg_gwtbootstrap3_client_ui_constants_Toggle_2);
com_google_gwt_lang_Runtime_defineClass__Lcom_google_gwt_core_client_JavaScriptObject_2Lcom_google_gwt_core_client_JavaScriptObject_2Lcom_google_gwt_core_client_JavaScriptObject_2V(194, 96, $intern_46);
var com_google_gwt_lang_ClassLiteralHolder_Lorg_1gwtbootstrap3_1client_1ui_1gwt_1HTMLPanel_12_1classLit = java_lang_Class_createForClass__Ljava_lang_String_2Ljava_lang_String_2Lcom_google_gwt_core_client_JavaScriptObject_2Ljava_lang_Class_2Ljava_lang_Class_2($intern_71, 'HTMLPanel', 194);
function org_gwtbootstrap3_client_ui_html_Small_Small__V(){
  org_gwtbootstrap3_client_ui_base_AbstractTextWidget_AbstractTextWidget__Lcom_google_gwt_dom_client_Element_2V.call(this, com_google_gwt_dom_client_DOMImplTrident_$createElement__Lcom_google_gwt_dom_client_DOMImplTrident_2Lcom_google_gwt_dom_client_Document_2Ljava_lang_String_2Lcom_google_gwt_dom_client_Element_2($doc, 'small'));
}

com_google_gwt_lang_Runtime_defineClass__Lcom_google_gwt_core_client_JavaScriptObject_2Lcom_google_gwt_core_client_JavaScriptObject_2Lcom_google_gwt_core_client_JavaScriptObject_2V(200, 199, $intern_9, org_gwtbootstrap3_client_ui_html_Small_Small__V);
var com_google_gwt_lang_ClassLiteralHolder_Lorg_1gwtbootstrap3_1client_1ui_1html_1Small_12_1classLit = java_lang_Class_createForClass__Ljava_lang_String_2Ljava_lang_String_2Lcom_google_gwt_core_client_JavaScriptObject_2Ljava_lang_Class_2Ljava_lang_Class_2($intern_64, 'Small', 200);
function org_gwtbootstrap3_client_ui_html_Span_Span__V(){
  com_google_gwt_user_client_ui_ComplexPanel_ComplexPanel__V.call(this);
  com_google_gwt_user_client_ui_UIObject_$setElement__Lcom_google_gwt_user_client_ui_UIObject_2Lcom_google_gwt_user_client_Element_2V(this, com_google_gwt_dom_client_DOMImplTrident_$createElement__Lcom_google_gwt_dom_client_DOMImplTrident_2Lcom_google_gwt_dom_client_Document_2Ljava_lang_String_2Lcom_google_gwt_dom_client_Element_2($doc, $intern_47));
  new org_gwtbootstrap3_client_ui_base_mixin_DataSpyMixin_DataSpyMixin__Lcom_google_gwt_user_client_ui_UIObject_2V(this);
  new org_gwtbootstrap3_client_ui_base_mixin_DataTargetMixin_DataTargetMixin__Lcom_google_gwt_user_client_ui_UIObject_2V(this);
  new org_gwtbootstrap3_client_ui_base_mixin_IdMixin_IdMixin__Lcom_google_gwt_user_client_ui_UIObject_2V(this);
  new org_gwtbootstrap3_client_ui_base_mixin_HTMLMixin_HTMLMixin__Lcom_google_gwt_user_client_ui_UIObject_2V(this);
}

com_google_gwt_lang_Runtime_defineClass__Lcom_google_gwt_core_client_JavaScriptObject_2Lcom_google_gwt_core_client_JavaScriptObject_2Lcom_google_gwt_core_client_JavaScriptObject_2V(195, 194, $intern_46, org_gwtbootstrap3_client_ui_html_Span_Span__V);
var com_google_gwt_lang_ClassLiteralHolder_Lorg_1gwtbootstrap3_1client_1ui_1html_1Span_12_1classLit = java_lang_Class_createForClass__Ljava_lang_String_2Ljava_lang_String_2Lcom_google_gwt_core_client_JavaScriptObject_2Ljava_lang_Class_2Ljava_lang_Class_2($intern_64, 'Span', 195);
function org_gwtbootstrap3_client_ui_html_Text_$setText__Lorg_gwtbootstrap3_client_ui_html_Text_2Ljava_lang_String_2V(this$static, txt){
  com_google_gwt_dom_client_Text_$setData__Lcom_google_gwt_dom_client_Text_2Ljava_lang_String_2V(this$static.org_gwtbootstrap3_client_ui_html_Text_text, txt);
}

function org_gwtbootstrap3_client_ui_html_Text_Text__V(){
  org_gwtbootstrap3_client_ui_html_Text_Text__Ljava_lang_String_2V.call(this, '');
}

function org_gwtbootstrap3_client_ui_html_Text_Text__Ljava_lang_String_2V(txt){
  this.org_gwtbootstrap3_client_ui_html_Text_text = com_google_gwt_dom_client_Document_$createTextNode__Lcom_google_gwt_dom_client_Document_2Ljava_lang_String_2Lcom_google_gwt_dom_client_Text_2($doc, txt);
  com_google_gwt_user_client_ui_UIObject_$setElement__Lcom_google_gwt_user_client_ui_UIObject_2Lcom_google_gwt_user_client_Element_2V(this, this.org_gwtbootstrap3_client_ui_html_Text_text);
}

com_google_gwt_lang_Runtime_defineClass__Lcom_google_gwt_core_client_JavaScriptObject_2Lcom_google_gwt_core_client_JavaScriptObject_2Lcom_google_gwt_core_client_JavaScriptObject_2V(24, 4, $intern_9, org_gwtbootstrap3_client_ui_html_Text_Text__V, org_gwtbootstrap3_client_ui_html_Text_Text__Ljava_lang_String_2V);
_.setText__Ljava_lang_String_2V = function org_gwtbootstrap3_client_ui_html_Text_setText__Ljava_lang_String_2V(txt){
  org_gwtbootstrap3_client_ui_html_Text_$setText__Lorg_gwtbootstrap3_client_ui_html_Text_2Ljava_lang_String_2V(this, txt);
}
;
var com_google_gwt_lang_ClassLiteralHolder_Lorg_1gwtbootstrap3_1client_1ui_1html_1Text_12_1classLit = java_lang_Class_createForClass__Ljava_lang_String_2Ljava_lang_String_2Lcom_google_gwt_core_client_JavaScriptObject_2Ljava_lang_Class_2Ljava_lang_Class_2($intern_64, 'Text', 24);
var $entry = (com_google_gwt_core_client_impl_Impl_$clinit__V() , com_google_gwt_core_client_impl_Impl_entry__Lcom_google_gwt_core_client_JavaScriptObject_2Lcom_google_gwt_core_client_JavaScriptObject_2);
var gwtOnLoad = gwtOnLoad = com_google_gwt_lang_ModuleUtils_gwtOnLoad__Lcom_google_gwt_core_client_JavaScriptObject_2Lcom_google_gwt_core_client_JavaScriptObject_2Lcom_google_gwt_core_client_JavaScriptObject_2Lcom_google_gwt_core_client_JavaScriptObject_2V;
com_google_gwt_lang_ModuleUtils_addInitFunctions__V(com_google_gwt_lang_com_100046flair_100046FLAIR_1_1EntryMethodHolder_init__V);
com_google_gwt_lang_ModuleUtils_setGwtProperty__Ljava_lang_String_2Lcom_google_gwt_core_client_JavaScriptObject_2V('permProps', [[['locale', 'default'], ['user.agent', 'ie8']]]);
$sendStats('moduleStartup', 'moduleEvalEnd');
gwtOnLoad(__gwtModuleFunction.__errFn, __gwtModuleFunction.__moduleName, __gwtModuleFunction.__moduleBase, __gwtModuleFunction.__softPermutationId,__gwtModuleFunction.__computePropValue);
$sendStats('moduleStartup', 'end');
$gwt && $gwt.permProps && __gwtModuleFunction.__moduleStartupDone($gwt.permProps);
//# sourceURL=flair-0.js

