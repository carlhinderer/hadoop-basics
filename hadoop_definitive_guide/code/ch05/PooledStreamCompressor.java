import java.io.*;

import org.apache.hadoop.io.compress.*;
import org.apache.hadoop.conf.*;
import org.apache.hadoop.io.IOUtils;
import org.apache.hadoop.util.ReflectionUtils;

public class PooledStreamCompressor {

    public static void main(String[]args) throws Exception {
        String codecClassname = args[0];
        Class<?> codecClass = Class.forName(codecClassname);
        Configuration conf = new Configuration();

        CompressionCodec codec = (CompressionCodec) ReflectionUtils.newInstance(codecClass,conf);
        Compressor compressor = null;

        try {
            compressor = CodecPool.getCompressor(codec);
            CompressionOutputStream out = codec.createOutputStream(System.out, compressor);
            IOUtils.copyBytes(System.in, out, 4096, false);
            out.finish();
        }
        finally {
            CodecPool.returnCompressor(compressor);
        }
    }
}