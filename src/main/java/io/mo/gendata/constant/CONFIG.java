package io.mo.gendata.constant;

public class CONFIG {
    //default input and output
    public static String INPUT = "def/";
    public static String OUTPUT = "data/";

    //default batch count per-writting
    public static int BATCH_COUNT = 500000;

    //public static int PER_BATCH_COUNT = 1000;

    public static int MAX_QUEUE_SIZE = 1000000;

    public static int THRESHOLD_MUTIL_THREAD = 10000;
    public static int THREAD_COUNT = Runtime.getRuntime().availableProcessors();

    public static String FIELD_SEPARATOR = ",";
    public static String LINE_SEPARATOR = "\n";
    public static String ESCAPE_SEPARATOR = "\\";
    public static String ENCLOSE_SEPARATOR = "\"";
}
