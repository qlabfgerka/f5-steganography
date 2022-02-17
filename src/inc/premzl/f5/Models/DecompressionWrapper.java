package inc.premzl.f5.Models;

import java.util.List;

public class DecompressionWrapper {
    private List<int[]> blocks;
    private int height;
    private int width;

    public DecompressionWrapper(List<int[]> blocks, int height, int width) {
        this.blocks = blocks;
        this.height = height;
        this.width = width;
    }

    public List<int[]> getBlocks() {
        return blocks;
    }

    public void setBlocks(List<int[]> blocks) {
        this.blocks = blocks;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }
}
