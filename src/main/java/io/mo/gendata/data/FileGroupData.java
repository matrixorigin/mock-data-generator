package io.mo.gendata.data;

public class FileGroupData {
    private String path;
    private int gid;
    private String[] row;
    
    public FileGroupData(String path, int gid, String[] row){
        this.gid = gid;
        this.path = path;
        this.row = row;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public int getGid() {
        return gid;
    }

    public void setGid(int gid) {
        this.gid = gid;
    }

    public String[] getRow() {
        return row;
    }

    public void setRow(String[] row) {
        this.row = row;
    }
}
