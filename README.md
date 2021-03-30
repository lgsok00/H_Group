---
layout: single
title:  "Closest pair of points!"
date:   2021-03-30 22:00:00 +0900
categories: jekyll update
---
#### H_Group


## 과제: _분할-정복_ 방법으로 최근접 점의 쌍 찾기



### 1. 최근접 점쌍 문제

![Solution](https://upload.wikimedia.org/wikipedia/commons/thumb/3/37/Closest_pair_of_points.svg/225px-Closest_pair_of_points.svg.png)

 최근접 점쌍 문제 (closest pair problem)는 계산기하학의 문제로서, 2차원 평면상에  n 개의 점이 주어졌을 때, 사이의 거리가 가장 짧은 두 점을 찾아내는 문제이다.
가능한 모든 점쌍들의 거리를 비교해 최솟값을 찾는 알고리즘은 O(n2)의 시간을 요구한다. 하지만 유클리드 공간이나 정해진 d의 차원을 가지는 Lp 공간에서는 O(n log n)의 시간에 해결 될 수 있다.


### 2. 분할-정복 방법으로 최근접 점에 쌍 문제 풀기
![DnC](https://dudri63.github.io/image/algo8-2.png)

 n개의 점을 1/2로 분할하여 각각의 부분문제에서 최근점 점의 쌍을 찾고, 2개의 부분해 중에서 가장 짧은 거리를 가진 점의 쌍을 찾는다.
하지만 위 그림처럼 중간에 있는 점들 때문에 왼쪽 부분문제, 오른쪽 부분문제 가운데 부분을 모두 생각해야 한다. 따라서 단순히 2개로 
분할한 부분문제에서 더 짧은 거리의 점의 쌍이 전체 문제에서 최근접 점의 쌍이라고 할 수 없는 것이다.


### 3. 알고리즘
![Algorithm](https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdn%2FFrfzz%2FbtqJDFZIbu7%2FNQ50TWJHLCktdGOc5ky9M0%2Fimg.png)



### 4. 자바로 구현하기
{% highlight ruby %}
import java.util.Arrays;
import java.util.Comparator;
import java.util.Random;

public class Closet_2 {

    private void merge (int[][] A, int[][] D, int p, int k, int q ){
                    // ( 점들 배열 , 쌍 저장할 배열, 처음 index, 중간 index, 마지막 index )

        double d = 0; // 최소 거리 담을 변수

        int[][] CP_L = new int[2][2]; // S_L ( 왼쪽 ) 에서의 최근접 점의 쌍을 담는 배열
        int[][] CP_R = new int[2][2]; // S_R ( 오른쪽 )에서의 최근접 점의 쌍을 담는 배열
        int[][] CP_C = new int[2][2]; // S_C ( 중간영역 ) 에서의 최근접 점의 쌍을 담는 배열

        double l = dist(A,CP_L,p,k); // -> 앞 부분의 쌍들 중 최소 거리
        double r = dist(A,CP_R,k+1,q); // -> 뒷 부분의 쌍들 중 최소 거리

        // 조건 : l 과 r 중에 짧은 거리를 d라고 놓는다.
        if (l<= r) d = l;
        else d = r;

        // 중간 영역에 속하는 점들 중에 가장 앞에 위치한 x값의 index p에 저장하기
        for(int i = 0; i<A.length;i++) {
            if ((A[k][0] - d) <= A[i][0]) {
                p = i;
                break;
            }
        }
        // 중간 영역에 속하는 점들 중에 가장 뒤에 위치한 x값의 index q에 저장하기
        for(int i = A.length-1; i>=0;i--) {
            if ((A[k+1][0] + d) >= A[i][0]) {
                q = i;
                break;
            }
        }

        double c=dist (A,CP_C,p,q); // -> 중간 부분의 쌍들 중 최소 거리 저장

        // 거리 비교해서 가장 최소거리 좌표 D에 넣어주기 --> 거리 똑같은 건 구별 X
        if ((l <= r) && (l <= c )) { // l이 가장 작은 경우 배열 CP_L이 최근접 점 쌍이 되므로 D에 저장 !
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

    // 거리 구하기 공식
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
{% endhighlight %}

### 5. 결과
![Result]![KakaoTalk_20210330_143211809](https://user-images.githubusercontent.com/80325051/112938876-72e0ed80-9165-11eb-8894-24922d6130c8.png)



### 참조
[https://ko.wikipedia.org/wiki/%EC%B5%9C%EA%B7%BC%EC%A0%91_%EC%A0%90%EC%8C%8D_%EB%AC%B8%EC%A0%9C](https://ko.wikipedia.org/wiki/%EC%B5%9C%EA%B7%BC%EC%A0%91_%EC%A0%90%EC%8C%8D_%EB%AC%B8%EC%A0%9C)
[https://dudri63.github.io/2019/01/19/algo8/](https://dudri63.github.io/2019/01/19/algo8/)






