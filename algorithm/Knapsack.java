
class Knapsack {
    public static void main(String[] args) throws Exception {

        testknapsack();

        testMinTimeForDoubleWorkerMultiTask();

    }

    /**
     * 
     * <pre>
     *   01背包变种问题
     *   
     *   现在有个5个任务,cpu耗时为{ 3, 3, 7, 3, 1 },在一台双CPU的机器上,完成这些任务,最少需要多长时间？
     *   假设任务总耗时为sum,那么完美的情况是双CPU的时候在sum/2的时间完成。
     *   反过来,给定sum/2的时间,一个CPU最多完成多少任务量？
     *   假设可以完成的任务耗时是tasksTime,如果tasksTime>=sum/2,那完成任务至少需要tasksTime。
     *   如果tasksTime<sum/2,那么完成任务至少需要totalTime-taskTime。
     * 
     * 例如任务为{1,2,1,2,3,3},总时间12,一半时间取6,可以完成6,最少需要时间6,
     *  任务为{3,4,5,10,11},总时间33,一半时间取17,可以完成17,最少需要时间6,
     * 任务为{10,10,10,100},总时间130,一半时间取65,可以完成30,最少需要时间100
     * 
     * </pre>
     * 
     */
    private static void testMinTimeForDoubleWorkerMultiTask() {
        int[] taskTimes = { 10, 10, 10, 100 };
        int[] prices = taskTimes;

        int sum = 0;
        for (int i = 0; i < taskTimes.length; i++) {
            sum += taskTimes[i];
        }
        int totalTime = (sum - 1) / 2 + 1;
        int halfTimeMaxValue = knapsack(prices, taskTimes, totalTime);
        System.out.println(halfTimeMaxValue);
        System.out.println(Math.max(halfTimeMaxValue, sum - halfTimeMaxValue));

    }

    private static void testknapsack() {
        System.out.println("-----------------");
        int[] prices = { 6, 3, 5, 4, 6 };
        int[] weights = { 2, 2, 6, 5, 4 };
        int totalWeight = 8;
        System.out.println(knapsack(prices, weights, totalWeight));

    }

    /**
     * 
     * <pre>
       01背包问题：一个背包总容量为V,现在有N个物品,第i个 物品体积为weight[i],价值为value[i], 现在往背包里面装东西,怎么装能使背包的内物品价值最大？<br>
         分析：在物品比较少,背包容量比较小时怎么解决？
                   用一个数组f[i][j]表示,在只有i个物品,容量为j的情况下背包问题的最优解,那么当物品种类变大为i+1时,最优解是什么？
                   第i+1个物品可以选择放进背包或者不放进背包（这也就是0和1）, 
                          假设放进背包（前提是放得下）,那么f[i+1][j]=f[i][j-weight[i+1]+value[i+1]；
                          如果不放进背包,那么f[i+1][j]=f[i][j]。<br>
                   这就得出了状态转移方程： f[i+1][j]=max(f[i][j],f[i][j-weight[i+1]+value[i+1])。
     * 
     * </pre>
     * 
     */
    public static int knapsack(int[] prices, int[] weights, int totalWeight) {
        int totalNum = weights.length;
        int[][] values = new int[totalNum + 1][totalWeight + 1];

        for (int col = 0; col <= totalWeight; col++) {
            values[0][col] = 0;
        }

        for (int row = 0; row <= totalNum; row++) {
            values[row][0] = 0;
        }

        for (int item = 1; item <= totalNum; item++) {
            for (int weight = 1; weight <= totalWeight; weight++) {
                if (weights[item - 1] <= weight) {
                    values[item][weight] = Math.max(prices[item - 1] + values[item - 1][weight - weights[item - 1]],
                            values[item - 1][weight]);
                } else {
                    values[item][weight] = values[item - 1][weight];
                }
            }
        }

        for (int[] rows : values) {
            for (int col : rows) {
                System.out.format("%5d", col);
            }
            System.out.println();
        }

        return values[totalNum][totalWeight];
    }
}