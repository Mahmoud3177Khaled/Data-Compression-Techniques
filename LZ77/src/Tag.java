package commp.assign1.lz77;

public class Tag {
    public int position;
    public int length;
    public String nextChar;

    Tag(int position, int length, String nextChar) {
        this.length = length;
        this.position = position;
        this.nextChar = nextChar;
    }

    @Override
    public String toString() {
        return "<" + this.position + ", " + this.length + ", " + this.nextChar + ">";
    }
}
