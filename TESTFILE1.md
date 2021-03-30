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






### 참조
[https://ko.wikipedia.org/wiki/%EC%B5%9C%EA%B7%BC%EC%A0%91_%EC%A0%90%EC%8C%8D_%EB%AC%B8%EC%A0%9C](https://ko.wikipedia.org/wiki/%EC%B5%9C%EA%B7%BC%EC%A0%91_%EC%A0%90%EC%8C%8D_%EB%AC%B8%EC%A0%9C)


[https://dudri63.github.io/2019/01/19/algo8/](https://dudri63.github.io/2019/01/19/algo8/)






