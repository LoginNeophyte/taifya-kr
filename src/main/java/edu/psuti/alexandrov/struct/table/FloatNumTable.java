package edu.psuti.alexandrov.struct.table;

import edu.psuti.alexandrov.lex.LexType;

public class FloatNumTable extends LexemTable {

    public FloatNumTable(String source) {
        super(source);
    }

    @Override
    public LexType lexType() {
        return LexType.FLOAT_NUM;
    }
}
