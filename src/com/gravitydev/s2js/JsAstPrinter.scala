package com.gravitydev.s2js

import StringUtil._

object JsAstPrinter {
	
	
	def print (tree:JsTree):String = tree match {
		case JsSourceFile (path, name, classes) => {
			"JsSourceFile (\n" +
			indent(
				"path: " + path + "\n" +
				"name: " + name + "\n" +
				"classes: " + printList(classes) + "\n"
			) +
			")"
		}
		case JsClass (name, pkg, parents, constructor, properties, methods) => {
			"JsClass (\n" +
			indent(
				"name: " + name + "\n" +
				"package: " + pkg + "\n" +
				"parents: " + parents + "\n" +
				"constructor: " + print(constructor) + "\n" +
				"properties: " + properties + "\n" +
				"methods: " + printList(methods) + "\n" 
			) +
			")"
		}
		case JsConstructor (name, params, constructorBody, classBody) => {
			"JsConstructor (\n" +
			indent(
				"name: " + name + "\n" +
				"params: " + params + "\n" +
				"constructorBody: " + printList(constructorBody) + "\n" +
				"classBody: " + printList(classBody) + "\n" 
			) +
			")"
		}
		case JsApply (fun, params) => {
			"JsApply (\n" +
			indent(
				"fun: " + print(fun) + "\n" +
				"params: " + printList(params) + "\n" 
			) +
			")"
		}
		case JsSelect(qualifier, name, t) => {
			"JsSelect(\n" +
			indent(
				"qualifier: " + print(qualifier) + ",\n" + 
				"name: " + name + "\n" +
				"type: " + t
			) +
			")"
		}
		case JsMethod (name, params, children, ret) => {
			"JsMethod(\n" +
			indent(
				"name: " + name + "\n" +
				"params: " + params + "\n" +
				"children: " + printList(children) + "\n" +
				"ret: " + ret + "\n" 
			) +
			")"
		}
		case JsBlock (children) => {
			"JsBlock(\n" + 
			indent(
				"children: " + printList(children) + "\n" 
			) +
			")"
		}
		case JsIf (cond, then, elsep) => {
			"JsIf(\n" +
			indent(
				"cond: " + cond + "\n" +
				"then: " + then + "\n" +
				"else: " + then + "\n" 
			) +
			")"
		}
		case x => x.toString
	}
	
	def printList (l: List[JsTree]) = {
		"[\n" +
		indent( l.map(print).mkString(",\n") ) + "\n" +
		"]"
	}

}
