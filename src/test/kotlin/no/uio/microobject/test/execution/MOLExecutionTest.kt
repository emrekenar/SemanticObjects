package no.uio.microobject.test.execution

import no.uio.microobject.ast.expr.FALSEEXPR
import no.uio.microobject.ast.expr.LiteralExpr
import no.uio.microobject.ast.expr.LocalVar
import no.uio.microobject.ast.expr.TRUEEXPR
import no.uio.microobject.test.MicroObjectTest
import no.uio.microobject.type.*
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals

class MOLExecutionTest : MicroObjectTest() {
    init {
        "Add"{
            val v = LocalVar("v", INTTYPE)
            val (a,_) = initInterpreter(" Int v = 1 + 2; v = v + 2; v = v + -1; breakpoint;",StringLoad.STMT)
            a.makeStep()
            assertEquals(1, a.stack.size)
            assert(a.stack.peek().store.containsKey("v"))
            assertEquals(LiteralExpr("3", INTTYPE), a.stack.peek().store["v"])
            assertEquals(LiteralExpr("3", INTTYPE), a.evalTopMost(v))
            a.makeStep()
            assertEquals(1, a.stack.size)
            assert(a.stack.peek().store.containsKey("v"))
            assertEquals(LiteralExpr("5", INTTYPE), a.stack.peek().store["v"])
            assertEquals(LiteralExpr("5", INTTYPE), a.evalTopMost(v))
            a.makeStep()
            assertEquals(1, a.stack.size)
            assert(a.stack.peek().store.containsKey("v"))
            assertEquals(LiteralExpr("4", INTTYPE), a.stack.peek().store["v"])
            assertEquals(LiteralExpr("4", INTTYPE), a.evalTopMost(v))
        }
        "Minus"{
            val (a,_) = initInterpreter(" Int v = 2 - 1; v = v - -1; breakpoint;",StringLoad.STMT)
            a.makeStep()
            assertEquals(1, a.stack.size)
            assert(a.stack.peek().store.containsKey("v"))
            assertEquals(LiteralExpr("1", INTTYPE), a.stack.peek().store["v"])
            a.makeStep()
            assertEquals(1, a.stack.size)
            assert(a.stack.peek().store.containsKey("v"))
            assertEquals(LiteralExpr("2", INTTYPE), a.stack.peek().store["v"])
        }
        "Mult"{
            val (a,_) = initInterpreter(" Double v = 1.0 * 2.0; v = v * -1.0e0; breakpoint;",StringLoad.STMT)
            a.makeStep()
            assertEquals(1, a.stack.size)
            assert(a.stack.peek().store.containsKey("v"))
            assertEquals(LiteralExpr("2.0", DOUBLETYPE), a.stack.peek().store["v"])
            a.makeStep()
            assertEquals(1, a.stack.size)
            assert(a.stack.peek().store.containsKey("v"))
            assertEquals(LiteralExpr("-2.0", DOUBLETYPE), a.stack.peek().store["v"])
        }
        "Div"{
            val (a,_) = initInterpreter(" Double v = 2.0 / 1.0; v = v / -1.0e0; breakpoint;",StringLoad.STMT)
            a.makeStep()
            assertEquals(1, a.stack.size)
            assert(a.stack.peek().store.containsKey("v"))
            assertEquals(LiteralExpr("2.0", DOUBLETYPE), a.stack.peek().store["v"])
            a.makeStep()
            assertEquals(1, a.stack.size)
            assert(a.stack.peek().store.containsKey("v"))
            assertEquals(LiteralExpr("-2.0", DOUBLETYPE), a.stack.peek().store["v"])
        }
        "Bool"{
            val (a,_) = initInterpreter(" Boolean v = (2 > 0 & 2 >= 0) | ( 2 < 0 & !(2 <= 0)); breakpoint;",StringLoad.STMT)
            a.makeStep()
            assertEquals(1, a.stack.size)
            assert(a.stack.peek().store.containsKey("v"))
            assertEquals(TRUEEXPR, a.stack.peek().store["v"])
        }
        "Neq"{
            val (a,_) = initInterpreter(" Boolean v = 2 != 1; breakpoint;",StringLoad.STMT)
            a.makeStep()
            assertEquals(1, a.stack.size)
            assert(a.stack.peek().store.containsKey("v"))
            assertEquals(TRUEEXPR, a.stack.peek().store["v"])
        }
        "Eq"{
            val (a,_) = initInterpreter(" Boolean v = 2 == 1; breakpoint;",StringLoad.STMT)
            a.makeStep()
            assertEquals(1, a.stack.size)
            assert(a.stack.peek().store.containsKey("v"))
            assertEquals(FALSEEXPR, a.stack.peek().store["v"])
        }
        "destroy"{
            val (a, _) = initInterpreter("destroy", StringLoad.RES)
            executeUntilBreak(a)
            assertEquals(1, a.stack.size)
            assertNotEquals(LiteralExpr("null", ERRORTYPE), a.evalTopMost(LocalVar("list", BaseType("List"))))
            assertEquals(LiteralExpr("null", ERRORTYPE), a.evalTopMost(LocalVar("list2", BaseType("List"))))
        }
        "concat"{
            val (a, _) = initInterpreter("strings", StringLoad.RES)
            executeUntilBreak(a)
            assertEquals(1, a.stack.size)
            assertEquals(LiteralExpr("True", BOOLEANTYPE), a.evalTopMost(LocalVar("res", BOOLEANTYPE)))
        }
        "innerfields"{
            val (a, _) = initInterpreter("innerField1", StringLoad.RES)
            executeUntilBreak(a)
            assertEquals(1, a.stack.size)
            assertEquals(LiteralExpr("7", INTTYPE), a.evalTopMost(LocalVar("k", BOOLEANTYPE)))
        }
    }
}
