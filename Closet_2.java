import java.util.Arrays;
import java.util.Comparator;
import java.util.Random;

public class Closet_2 {


    // 계산 ? ( 간소화 X )
    private void merge (int[][] A, int p, int k, int q ){
        double d = 0;
        int[][] CP_L = new int[2][2];
        int[][] CP_R = new int[2][2];
        int[][] CP_C = new int[2][2];
        double l=dist (A,CP_L,p,k); // -> 앞 부분 최소 거리
        double r=dist (A,CP_R,k+1,q); // -> 뒷 부분 최소 거리

        if (l<= r) d = l;
        else d = r;

        for(int i = 0; i<A.length;i++) {
            if ((A[k][0] - d) <= A[i][0]) {
                p = i;
                break;
            }
        }
        for(int i = A.length-1; i>=0;i--) {
            if ((A[k+1][0] + d) >= A[i][0]) {
                q = i;
                break;
            }
        }
        double c=dist (A,CP_C,p,q);

        // 거리 비교해서 가장 최소거리 출력 --> 거리 똑같은 건 구별 못했음, 이 함수 호출될 때마다 계속 출력 ....
        if ((l <= r) && (l <= c )) {
            System.out.print("최근접 점 쌍 : ");
            for (int i=0;i<2;i++) System.out.print("( " + CP_L[i][0]+","+CP_L[i][1]+" )" );
            System.out.println();
        }
        else if ((r <= l) && (r <=c)){
            System.out.print("최근접 점 쌍 : ");
            for (int i=0;i<2;i++) System.out.print("( " + CP_R[i][0]+","+CP_R[i][1]+" )" );
            System.out.println();
        }
        else if ((c <= l) && (c <=r)){
            System.out.print("최근접 점 쌍 : ");
            for (int i=0;i<2;i++) System.out.print("( " + CP_C[i][0]+","+CP_C[i][1] +" )"  );
            System.out.println();
        }

    }

    // 거리 구하기 공식
    private double dist(int[][] A, int[][] D, int p, int q){
        double d=0;
        int n=0;
        for ( int a = p; a< q; a++) {
            double x, y;
            x = Math.pow(A[a][0] - A[a + 1][0], 2);
            y = Math.pow(A[a][1] - A[a + 1][1], 2);
            double min = Math.sqrt(x + y);           
            if (a == p || min < d) {
                d = min;                
                n = a;
            }
        }
        for (int i = 0; i < 2; i++) {
            D[i][0] = A[n][0];
            D[i][1] = A[n][1];
            n++;
        }
        return d;
    }

    // 분할
    private void ClosestPair(int[][] A, int p, int q) { // 초기 값 p -> l (0 )  , q -> r ( S.length -1 : 9 )
        int i= q-p;
        if(i<=3) return; // 재귀 멈춤 조건
        int k = (p+q)/2; // 중간 index
        ClosestPair(A, p, k); // [10][2]로 가정했을 때, 초기 ( A, 0, 4 ) - > CP_L ( 왼쪽 재귀 호출)
        ClosestPair(A,k+1, q); // 초기 ( A, 5, 9 ) -> CP_R ( 오른쪽 재귀 호출
        merge(A, p, k, q);
    }

    public static void main(String[] args) {
        Random r = new Random();
        int[][] S = new int[10][2];
        for (int i = 0; i < S.length; i++) {
            for (int j = 0; j < S[i].length; j++) {
                S[i][j] = r.nextInt(100);
            }
        }
        // x 오름차순 정렬
        Arrays.sort(S, new Comparator<int[]>() {
            @Override
            public int compare(int[] o1, int[] o2) {
                if(o1[0] == o2[0]) return o1[1] -o2[1];
                else return o1[0] -o2[0];
            }
        }); // 완료
        for (int[] ints : S) {
            System.out.printf("( %d , %d )", ints[0], ints[1]);
            System.out.println();
        }
        Closet_2 closet = new Closet_2();
        closet.ClosestPair(S, 0, S.length-1);
    }
}
