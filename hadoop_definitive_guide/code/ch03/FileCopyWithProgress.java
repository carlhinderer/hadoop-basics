public class FileCopyWithProgress {
    
    public static void main(String[]args) throws Exception {
        String localSrc = args[0];
        String dst=args[1];
        InputStream in = new BufferedInputStream(newFileInputStream(localSrc));
        Configuration conf = new Configuration();
        FileSystem fs = FileSystem.get(URI.create(dst),conf);
        OutputStream out = fs.create(newPath(dst), new Progressable() {
            public void progress() {
                System.out.print(".");
            }
        });

        IOUtils.copyBytes(in,out,4096,true);
    }
}