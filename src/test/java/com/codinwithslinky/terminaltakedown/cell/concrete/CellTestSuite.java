package com.codinwithslinky.terminaltakedown.cell.concrete;

import org.junit.platform.suite.api.*;

@Suite
@SelectClasses({
    CellGridTest.class,
    LetterCellTest.class,
    SymbolCellTest.class,
    LetterClusterTest.class,
    SymbolClusterTest.class,
    SimpleClusterStrategyTest.class
})

public class CellTestSuite {}