public class FileSystemDoubleCat {
    
    public static void main(String[]args) throws Exception {
        String uri = args[0];
        Configuration conf = new Configuration();
        FileSystem fs = FileSystem.get(URI.create(uri),conf);
        FSDataInputStreamin=null;

        try {
            in = fs.open(newPath(uri));
            IOUtils.copyBytes(in,System.out,4096,false);
            in.seek(0);  // go back to the start of the file
            IOUtils.copyBytes(in,System.out,4096,false);
        }
        finally {
            IOUtils.closeStream(in);
        }
    }
}