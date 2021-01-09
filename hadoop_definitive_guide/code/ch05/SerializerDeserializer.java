import java.io.*;

import org.apache.hadoop.io.*;


public class SerializerDeserializer {

    public static byte[] serialize (Writable writable) throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        DataOutputStream dataOut = new DataOutputStream(out);

        writable.write(dataOut);
        dataOut.close();
        return out.toByteArray();
    }

    public static byte[] deserialize (Writable writable, byte[] bytes) throws IOException {
        ByteArrayInputStream in = new ByteArrayInputStream(bytes);
        DataInputStream dataIn = new DataInputStream(in);

        writable.readFields(dataIn);
        dataIn.close();
        return bytes;
    }

    public static void main(String[] args) {
        IntWritable writable = new IntWritable(163);

        try {
            // Serialize IntWritable
            byte[] bytes = serialize(writable);
            System.out.println("The number of bytes in an IntWritable is: " + bytes.length);
    
            // Seserialize IntWritable
            IntWritable newWritable = new IntWritable();
            deserialize(newWritable, bytes);
            System.out.println("The value of the IntWritable is: " + newWritable.get());
        }
        catch (IOException ioe) {
            System.out.println("Caught IOException.");
        }
    }
}