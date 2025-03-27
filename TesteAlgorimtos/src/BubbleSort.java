public class BubbleSort {

    // Complexidade no pior caso: O(n^2)
    // Complexidade no melhor caso: O(n^2)

    public static void bubblesort(int[] array) {

        for (int i = array.length-1; i>0; i--){
            for (int j=0; j<i; j++) {

                if(array[j]> array[j + 1]){

                    int temp = array[j];
                    array[j] = array[j + 1];
                    array[j + 1] = temp;
                }
            }
        }
    }


    public static void main(String[] args) {
        int array[] = {5, 3, 8, 6, 2};
        bubblesort(array);

        for (int i = 0; i < array.length; i++) {
            System.out.print(array[i] + " ");
        }
    }
}