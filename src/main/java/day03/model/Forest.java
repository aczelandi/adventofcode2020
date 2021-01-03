package day03.model;

public record Forest(char[][]elements, int rowLength, int colLength) {

    @Override
    public String toString() {
        var str = new StringBuilder();
        for (int i = 0; i < rowLength; i++) {
            for (int j = 0; j < colLength; j++) {
                str.append(elements[i][j]);
            }
            str.append("\n");
        }

        str.append("Rows: ").append(rowLength).append("\n");
        str.append("Columns: ").append(colLength).append("\n");
        return str.toString();
    }
}