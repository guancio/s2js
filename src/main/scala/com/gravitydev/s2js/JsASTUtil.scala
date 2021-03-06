package com.gravitydev.s2js

object JsAstUtil {
		
	// method that can apply a function to all children of a Node
	def visitAst (tree:JsTree, fn:(JsTree)=>JsTree):JsTree = {
		def applyFn[T <: JsTree] (t:JsTree):T = fn(t).asInstanceOf[T]
		def applyFnList[T <: JsTree] (l:List[JsTree]):List[T] = l map (applyFn[T](_))
		
		tree match {
			case JsSourceFile(path,name,classes) => JsSourceFile(
				path, 
				name, 
				applyFnList(classes) 
			)
			case JsClass(name,pkg,parents,constructor,properties,methods) => {
				
				JsClass(
					name,
					pkg,
					applyFnList[JsSelect](parents),
					fn(constructor).asInstanceOf[JsConstructor],
					properties map (fn(_).asInstanceOf[JsProperty]),
					methods map (fn(_).asInstanceOf[JsMethod])
				)
			}
			case JsModule (owner,name,body,props,methods,classes,modules) => JsModule(
				fn(owner).asInstanceOf[JsRef],
				name,
				body map fn,
				props map (fn(_).asInstanceOf[JsProperty]),
				methods map (fn(_).asInstanceOf[JsMethod]),
				classes map (fn(_).asInstanceOf[JsClass]),
				modules map (fn(_).asInstanceOf[JsModule])
			)
			case JsConstructor(owner, params,constructorBody,classBody) => JsConstructor(
				fn(owner).asInstanceOf[JsRef],
				params map (fn(_).asInstanceOf[JsParam]),
				constructorBody map fn,
				classBody map fn
			)
			case JsApply(fun,params) => JsApply(
				fn(fun),
				params map fn
			)
			case JsTypeApply(fun,params) => JsTypeApply(
				fn(fun),
				params map fn
			)
			
			case JsSelect (qualifier, name, t, tpe) => JsSelect( fn(qualifier), name, t, tpe )
			
			case JsMethod(owner, name,params,body,ret) => JsMethod(
				fn(owner).asInstanceOf[JsRef],
				name,
				params map (fn(_).asInstanceOf[JsParam]),
				fn(body),
				fn(ret)
			)
			case JsBlock(stats, expr) => JsBlock(
				stats map fn,
				fn(expr)
			)
			case JsVar (id, tpe, rhs) => JsVar(id, fn(tpe), fn(rhs))
			
			case JsIf (cond, thenp, elsep) => JsIf(fn(cond), fn(thenp), fn(elsep))
			
			case JsAssign (lhs, rhs) => JsAssign (fn(lhs), fn(rhs))
			
			case JsUnaryOp (select, op) => JsUnaryOp(fn(select), op)
			case JsInfixOp (a, b, op) => JsInfixOp(fn(a), fn(b), op)
			
			case JsComparison (lhs, operator, rhs) => JsComparison (fn(lhs), operator, fn(rhs))
			
			case JsParam (name, tpe, default) => JsParam(name, fn(tpe), default map fn)
			
			case JsProperty (owner, name, tpt, rhs, mods) => JsProperty (fn(owner), name, fn(tpt), fn(rhs), mods)
			
			case JsNew (tpt) => JsNew( fn(tpt) )
			
			case JsArray (elements) => JsArray( elements map fn )
			
			case JsMap (elements) => JsMap ( elements map (fn(_).asInstanceOf[JsMapElement]))
			
			case JsMapElement (key, value) => JsMapElement(key, fn(value))
			
			case JsThrow (expr) => JsThrow(fn(expr))
			
			case JsFunction (params, body) => JsFunction (applyFnList[JsParam](params), fn(body))
			
			case JsReturn (expr) => JsReturn ( fn(expr) )
			
			case JsPackage (name, children) => JsPackage(name, applyFnList(children))
			
			case JsCast (subject, tpe) => JsCast(fn(subject), fn(tpe))
			
			case JsArrayAccess (array, index) => JsArrayAccess(fn(array), fn(index))
			
			case x:JsModuleRef => x
			case x:JsClassRef => x
			
			case x:JsType => x
			
			case x:JsSuper => x
			case x:JsIdent => x
			case x:JsLiteral => x
			case x:JsThis => x
			case x:JsBuiltInType => x
			case x => {
				println(x)
				
				x
			}
		}
	}
}
