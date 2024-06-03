package com.entrevistador.orquestador.utils;

public final class PdfFileTest {
    public static final String PDF_FILE = """
            %PDF-1.4\\n
            1 0 obj\\n
            << /Type /Catalog /Pages 2 0 R >>\\n
            endobj\\n
            2 0 obj\\n
            << /Type /Pages /Kids [3 0 R] /Count 1 >>\\n
            endobj\\n
            3 0 obj\\n
            << /Type /Page /Parent 2 0 R /MediaBox [0 0 612 792] /Contents 4 0 R >>\\n
            endobj\\n
            4 0 obj\\n
            << /Length 55 >>\\n
            stream\\n
            BT\\n
            /F1 24 Tf\\n
            100 700 Td\\n
            (Hello Test) Tj\\n
            ET\\n
            endstream\\n
            endobj\\n
            xref\\n
            0 5\\n
            0000000000 65535 f \\n
            0000000010 00000 n \\n
            0000000079 00000 n \\n
            0000000178 00000 n \\n
            0000000278 00000 n \\n
            trailer\\n
            << /Size 5 /Root 1 0 R >>\\n
            startxref\\n
            380\\n
            %%EOF
            """;
}
