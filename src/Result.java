public class Result {

    public Result(){}

    public Result(int cows, int bulls, boolean success){
        setCows(cows);
        setBulls(bulls);
        setSucceess(success);
    }

    private int cows;
    private int bulls;
    private boolean success;

    public int getCows() {
        return cows;
    }

    public void setCows(int cows) {
        this.cows = cows;
    }

    public int getBulls() {
        return bulls;
    }

    public void setBulls(int bulls) {
        this.bulls = bulls;
    }

    public boolean isSucceess() {
        return success;
    }

    public void setSucceess(boolean success) {
        this.success = success;
    }
}
