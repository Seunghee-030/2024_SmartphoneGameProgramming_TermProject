2024 1학기 스마트폰 게임 프로그래밍 프로젝트


- 게임에 대한 간단한 소개 (이름 변경 : ~Candy Chacher~->> Sweet Drops)
![image](https://github.com/Seunghee-030/Smartphone_Game_Programming/assets/73768560/db7ac2fe-e774-4bf8-8669-93c3fd1f7bbc)


https://github.com/Seunghee-030/Smartphone_Game_Programming/assets/73768560/faed0a7b-ba05-40af-b1ab-de8fca89a153



- 현재까지의 진행 상황 (항목별 진행 정도를 %로 표시할 것)
![image](https://github.com/Seunghee-030/Smartphone_Game_Programming/assets/73768560/c92ef363-b738-4849-9104-7fec0eb3459a)


- git commit 을 얼마나 자주 했는지 알 수 있는 자료 (github-insights-commits 포함)
![image](https://github.com/Seunghee-030/Smartphone_Game_Programming/assets/73768560/602a48c6-f216-45e4-88c4-6538eaafac7c)


* 시연영상

https://github.com/Seunghee-030/Smartphone_Game_Programming/assets/73768560/4ea5db94-e9db-4be7-ae99-0474cc9e1818


* <현재 사용 중인 Game 클래스>
> MainScene
> Monster
> Item
> Candy
> FireUnit
> Bouncer
> Bullet


* MainScene 클래스
>모든 클래스와 상호작용하며, 화면에 표시되는 배경, 캐릭터, 아이템 등을 관리하고 업데이트.
 >또한 터치 입력을 처리하고, 게임의 진행 상태를 업데이트하는 등의 역할.



* FireUnit 클래스
>구성: 화면 상하로 이동가능한 장애물 유닛. 비트맵 리소스 사용하여 그림, 애니메이션 표현.
 >상호작용: 터치 입력 받아 목표 위치 설정 및 총알 발사.
  >주요 역할 : 아이템을 방해하는 총알 발사, obstacle Layer에 그려짐



* Bullet 클래스
>구성: 게임에서 사용되는 총알. 비트맵 리소스 사용하여 그림 표현.
 >상호작용: 충돌 감지하여 특정 동작 수행.
  >주요 역할: 총알의 움직임, 충돌 관리, 몬스터 공격. 일정 범위를 벗어난 객체는 삭제 됨


* Item 클래스
>구성: 게임 아이템. 비트맵 리소스 사용하여 그림 표현.
 >상호작용: 몬스터와의 충돌 감지하여 효과 발생.
  >주요 역할: 아이템의 움직임, 충돌 관리, 점수 증가 등.


* Monster 클래스
>구성: 게임 주인공 몬스터. 비트맵 리소스 사용하여 그림, 애니메이션 표현.
 >상호작용: 다른 객체와의 충돌 감지하여 특정 동작 수행.
  >주요 역할: 몬스터의 움직임, 애니메이션 관리



* Bouncer 클래스
>구성: 양옆으로 이동가능한 장애물 유닛, 비트맵 리소스 사용, 충돌시 애니메이션 존재
 >상호작용: 터치 입력받아 이동가능, 아이템 튕겨내는 역할, 충돌체크
  >주요 역할: 바운서 움직임


* <어려웠던 점>
>인터페이스를 사용하여 객체들 간의 상호작용을 설계하는 것이 어려웠다.


