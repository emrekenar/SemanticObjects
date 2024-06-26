package no.uio.microobject.ast.stmt

import no.uio.microobject.ast.*
import no.uio.microobject.runtime.EvalResult
import no.uio.microobject.runtime.Interpreter
import no.uio.microobject.runtime.Memory
import no.uio.microobject.runtime.StackEntry
import no.uio.microobject.type.BaseType
import no.uio.microobject.type.Type

// Method call. We have the ABS-style split between calls and expressions to make the rules more simple
data class CallStmt(val target : Location, val callee : Location, val method : String, val params : List<Expression>, val pos : Int = -1, val declares : Type?) :
    Statement {
    override fun toString(): String = "$target := $callee.$method(${params.joinToString(",")})"
    override fun getRDF(): String {
        var s = """
            prog:stmt${this.hashCode()} rdf:type smol:CallStatement.
            prog:stmt${this.hashCode()} smol:hasTarget prog:loc${target.hashCode()}.
            prog:stmt${this.hashCode()} smol:hasCallee prog:loc${callee.hashCode()}.
            prog:stmt${this.hashCode()} smol:hasMethodName '${method}'.
            prog:stmt${this.hashCode()} smol:Line '$pos'^^xsd:integer.

        """.trimIndent()
        for (i in params.indices){
            s += "prog:stmt${this.hashCode()} smol:hasParameter [smol:hasParameterIndex $i ; smol:hasParameterValue prog:expr${params[i].hashCode()}; ].\n"
            s += params[i].getRDF()
        }
        return s + target.getRDF() + callee.getRDF()
    }

    override fun eval(heapObj: Memory, stackFrame: StackEntry, interpreter: Interpreter): EvalResult {
        val newObj = interpreter.eval(callee, stackFrame)
        val mt = interpreter.staticInfo.methodTable[(newObj.tag as BaseType).name]
            ?: throw Exception("This class is unknown: ${newObj.tag} when executing $this at l. pos")
        val m = mt[method]
            ?: throw Exception("This method is unknown: $method")
        val newMemory: Memory = mutableMapOf()
        newMemory["this"] = newObj
        for (i in m.params.indices) {
            newMemory[m.params[i]] = interpreter.eval(params[i], stackFrame)
        }
        return EvalResult(
            StackEntry(StoreReturnStmt(target), stackFrame.store, stackFrame.obj, stackFrame.id),
            listOf(StackEntry(m.stmt, newMemory, newObj, Names.getStackId()))
        )
    }
}