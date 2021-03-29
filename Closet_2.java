import java.util.Arrays;
import java.util.Comparator;
import java.util.Random;

public class Closet_2 {

    private void merge (int[][] A, int[][] D, int p, int k, int q ){   // ( 점들 배열 , 쌍 저장할 배열, 처음 index, 중간 index, 마지막 index )
        double d = 0; // 최소 거리 담을 변수
        int[][] CP_L = new int[2][2]; // S_L ( 왼쪽 ) 에서의 최근접 점의 쌍을 담는 배열
        int[][] CP_R = new int[2][2]; // S_R ( 오른쪽 )에서의 최근접 점의 쌍을 담는 배열
        int[][] CP_C = new int[2][2]; // S_C ( 중간영역 ) 에서의 최근접 점의 쌍을 담는 배열

        double l = dist(A,CP_L,p,k); // -> 앞 부분의 쌍들 중 최소 거리 저장
        double r = dist(A,CP_R,k+1,q); // -> 뒷 부분의 쌍들 중 최소 거리 저장

        // 조건 : l 과 r 중에 짧은 거리를 d라고 가정
        if (l<= r) d = l;
        else d = r;

        // 중간 영역에 속하는 점들 중에 가장 앞에 위치한 x값의 index -> p에 저장하기
        for(int i = 0; i<A.length;i++) {
            if ((A[k][0] - d) <= A[i][0]) {
                p = i;
                break;
            }
        }
        // 중간 영역에 속하는 점들 중에 가장 뒤에 위치한 x값의 index -> q에 저장하기
        for(int i = A.length-1; i>=0;i--) {
            if ((A[k+1][0] + d) >= A[i][0]) {
                q = i;
                break;
            }
        }

        double c=dist (A,CP_C,p,q); // -> 중간 부분의 쌍들 중 최소 거리 저장

        // 거리 비교해서 가장 최소거리 좌표 D에 넣어주기 --> 거리 똑같은 건 구별 X
        if ((l <= r) && (l <= c )) { // l이 가장 작은 경우
            for (int i=0;i<2;i++){
                D[i][0]=CP_L[i][0];                D[i][1]=CP_L[i][1];
            }
        }
        else if ((r <= l) && (r <=c)){ // r이 가장 작은 경우
            for (int i=0;i<2;i++){
                D[i][0]=CP_R[i][0];                D[i][1]=CP_R[i][1];
            }
        }
        else if ((c <= l) && (c <=r)){ // c가 가장 작은 경우
            for (int i=0;i<2;i++){
                D[i][0]=CP_C[i][0];                D[i][1]=CP_C[i][1];
            }
        }
    }

    // 거리 구해주는 함수
    private double dist(int[][] A, int[][] D, int p, int q){ // ( 점들 배열 , 쌍 저장할 배열, 처음 index, 마지막 index )

        double min=0; // 최소거리를 담을 변수 선언
        int n=0; // 최소거리를 가질 때의 A 배열의 index 저장할 변수 선언

        for ( int a = p; a< q; a++) {
            double x = Math.pow(A[a][0] - A[a + 1][0], 2); // ( x_1 - x_2 ) ^2
            double y = Math.pow(A[a][1] - A[a + 1][1], 2); // ( y_1 - y_2 ) ^2
            double d = Math.sqrt(x + y); // 두 점 거리 d에 저장
            if (a == p || d < min) { // a가 제일 작을 때 ( 맨 처음 ) 와 min보다 d이 작아질 때
                min = d;             // min에 d 값 갱신
                n = a;              // 최소값이 갱신될때 마다 그 때 A 배열의 x좌표 index를 n에 저장
            }
        }

        /*  위에서 마지막에 저장된 n값이 가장 최소거리를 갖는 A배열의 최근접 점 쌍의 첫번째 x좌표 index 이므로 !
          최근접 점 쌍을 담을 배열 D에 A[n][], A[n+1][] 값 저장 */
        for (int i = 0; i < 2; i++) {
            D[i][0] = A[n][0];
            D[i][1] = A[n][1];
            n++;
        }
        return min;  // 마지막에 저장된 최소거리 return !
    }

    // 분할
    private void ClosestPair(int[][] A,int[][] D, int p, int q) {
        if(q-p<=3) return; // 재귀 멈춤 조건 ( S 에 있는 점의 수가 3개 이하면 분할 멈춤 )
        int k = (p+q)/2; // 중간 index
        ClosestPair(A,D, p, k); // - > CP_L ( 왼쪽 재귀 호출 )
        ClosestPair(A,D,k+1, q); // -> CP_R ( 오른쪽 재귀 호출 )
        merge(A, D, p, k, q);
    }


    public static void main(String[] args) {
        Random r = new Random();
        int[][] S = new int[10][2]; /* (x,y)에 있는 10개의 점을 담을 배열 선언
                                       -  [i][0] -> x좌표 , [i][1] -> y좌표
                                       -  점들의 개수를 N개로 가정할 경우 N > 4        */
        int[][] D = new int[2][2]; // 최근접 점 쌍을 담을 배열 선언 ( 쌍이므로 점의 개수를 2로 가정 )

        // ( x, y ) 에 각각 0~ 9 숫자를 랜덤 저장
        for (int i = 0; i < S.length; i++) {
            for (int j = 0; j < S[i].length; j++) {
                S[i][j] = r.nextInt(10);
            }
        }

        // x좌표 기준 오름차순으로 정렬
        Arrays.sort(S, new Comparator<int[]>() {
            @Override
            public int compare(int[] o1, int[] o2) {
                if(o1[0] == o2[0]) return o1[1] -o2[1];
                else return o1[0] -o2[0];
            }
        });

        // 점들의 좌표(위치) 출력
        for (int[] ints : S) {
            System.out.printf("( %d , %d )", ints[0], ints[1]);
            System.out.println();
        }

        Closet_2 closet = new Closet_2();
        closet.ClosestPair(S, D, 0, S.length-1);

        // 모든 실행 후 최근접 점 쌍 출력
        System.out.print("최근접 점 쌍 : ");
        for (int i=0;i<2;i++) System.out.print("( " + D[i][0]+","+D[i][1] +" )"  );
        System.out.println();
    }
}
