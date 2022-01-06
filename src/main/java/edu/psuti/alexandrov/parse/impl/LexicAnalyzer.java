package edu.psuti.alexandrov.parse.impl;

import edu.psuti.alexandrov.parse.SelfParcing;
import edu.psuti.alexandrov.struct.lex.LexUnit;
import edu.psuti.alexandrov.struct.table.*;
import edu.psuti.alexandrov.util.IOUtil;

import java.util.Objects;
import java.util.stream.Stream;
import static edu.psuti.alexandrov.struct.lex.LexType.*;

public class LexicAnalyzer extends SelfParcing<String> {

    private final ExternalFileTable keywords;
    private final ExternalFileTable delimiters;
    private final IdentifiersTable identifiers;
    private final BinaryNumTable binaries;
    private final OctetNumTable octets;
    private final HexNumTable hexs;
    private final DecimalNumTable decimals;
    private final FloatNumTable floats;

    public LexicAnalyzer() {
        keywords = new ExternalFileTable("tables//keywords");
        delimiters = new ExternalFileTable("tables//delimiters");
        identifiers = new IdentifiersTable(input());
        binaries = new BinaryNumTable(input());
        octets = new OctetNumTable(input());
        hexs = new HexNumTable(input());
        decimals = new DecimalNumTable(input());
        floats = new FloatNumTable(input());
    }

    @Override
    public String mask() {
        return DIRTY_LEX_SPLIT;
    }

    @Override
    public String input() {
        return IOUtil.readTxt("samples\\program1")
                     .replaceAll("\\s", LEX_DELIMITER);
    }

    @Override
    public String map(String sample) {
        return sample;
    }

    public Stream<LexUnit> lexUnits() {
        prepareContent();
        return content()
                .filter(lex -> !lex.equals(EMPTY))
                .map(lex -> keywords.find(KEYWORD, lex)
                        .or(() -> delimiters.find(DELIMITER, lex))
                        .or(() -> identifiers.find(IDENTIFIER, lex))
                        .or(() -> binaries.find(BINARY_NUM, lex))
                        .or(() -> octets.find(OCTET_NUM, lex))
                        .or(() -> hexs.find(HEX_NUM, lex))
                        .or(() -> decimals.find(DECIMAL_NUM, lex))
                        .or(() -> floats.find(FLOAT_NUM, lex))
                        .orElse(LexUnit.UNKNOWN)
                );
    }

    private void prepareContent() {
        if(Objects.isNull(content)) {
            parseSelf();
        }
    }
}