##### # 포털
  - 기능목록 도출
  - 프로세스 정의 / 설계

#### # LX : 한국국토정보공사 공간정보본부
  - https://place.map.kakao.com/687647547
  
#### 플랫폼개발자 / 지자체개발자

####  개발관련 업무 지원 및 분석

##### # 팀 구조.
  - 이성현 본부장
  - 정기찬 팀장
  - 이재성 수석
  - 통합권한관리 : 하재용 수석
  - 개발자지원관리 : 김대현 수석

#### # 온품 3d 디지털트윈을 만든 라이브러리가 Umi.js (알리바바꺼 중국꺼임.)
  - Umi.js
    - Store 라이브러리.
  - 라이브러리 형태
  - LX공공사업이다 보니까. 
    - 중국기술안된다고함.
    - 개발자 지원 같은 프레임웍크를 제공하고 있음.
  - 지자체에서 사용하려고 보니
    - API지원 사이트에서 가져갔다 쓰려고하니 어 중국꺼네
    - 다른 공공기관도 Umi.js 사용하고 있네..
    - 저작권 문제도 없는거네요?
      - 저작권 문제로 걸어서 뭔가 하고 있음.
      - LX는 umi.js를 사용하고 싶지 앟음..
      - 업체가 이 라이브러리 못쓴다.

    - 본부의 입장은 Umi.js를 걷어내는?
      - LX와 상관없이 Umi.js로 만들어져잇음.
      - Server : Nginx
      - 백엔드 : 전자정부프레임워크
      - 관리자는 Vue.js

    - 걷어내고 기능추가
      - 기반틀을 만들고 기존 기능을 만들고 있음.
      - 개발 커뮤니케이션

    - 사업이 고도화 사업이 
      - 아키텍쳐및 개발환경
        - 관리포탈

      - 2D
      - 3D
        - 기능고도화의 3D화면 기준으로해서
        - 대략 개발할게 20개정도되는데
          - 이중에서 5개~4개정도 개발을 맡아서 소통하고 만들것을 체크하고 
          - 기능 완성을 하는것.
          - 개발을 하는 것이 아니라
            - 내용을 보고 점검하는 일

      - DB
      - 인프라
     
======================= ======================= ======================= 
#### # LX플랫폼 고도화
  - 작년 1차사업 완료 : 2022년 12일9일 ( 준공 완료 )
  - 고도화 사업 올해 1월부터 ~ 내년 2월 까지

##### # 온품 3d 디지털트윈 : three.js 세슘.
    - 세슘(cesium) 기본지도
    - 브이월드(vworld)
    - LOD를 언져서 모델링으로 지도 표시.
        - Level Of Detail.
        - City GML
            - Level 1 박스 : 층호 분리 안됨
            - Level 2 : 텍스쳐 가 붙음 : 3D 탁자 보이고 실제.
            - Level 2.5 ~ 3.0 : 층호 분리됨.
        - Indoor GML
            - Level 4 : 내부

##### # ASIS : 관리포털
    - 온품 관리자 화면
        - 시스템관리
        - 지도관리
        - .
        - .

    - 관리자 화면의 큰 분류.
        - 데이터쪽
        - 인프라과쪽
        - 2D : 웨이버스(사용자,관리자)가 만듬. << lx에 찍혀서 사업참여 못함.
          - AST >> 사용자 소스를 받아서 재배치 고도화
        - 3D

    - TOBE : 관리포털
        - 포탈공통 관리
        - 2D : 다있다.
        - 3D : 있거나, 다시 만들거나.
        - 데이터(파인) : 샌드박스 포함.
        - 인프라 : CMP (클라우드 매니지먼트 플랫폼) << 이노그리드
        - 통합권한관리
        - 개발자지원관리
            - 256개 지자체 에 확산 할 것 같다.
            - 지자체에서 개발한 요소에대한 확산.
            - 특화서비스
            - 지자체에서 만들고 싶은것을 외주 개발사에 프랫폼에 얻져서
        - 지자체권한관리

  - AST(에이에스티) 2D 평면
    - 관리자 
      - 운영관리
      - 데이터관리
      - 인프라관리
        
##### # 해야할일
  - 분석 단계부터 어떻게 할지 답을 만들어야함.
  - LX요구사항(고객)과의 요구사항 분석
    - 업무범위 분석
  - 기능목록
  - 프로세스 정의
  - 화면설계 << 기회 다른업체가 수행 << 난 아님.
  - 준비해서 다음주 수요일. 11월 15일.
    - 애 봐야함.

###### # 1차할일
  - 관리포탈.
  - IA(메뉴구조) 파악

  - 국토정보플랫폼 운영
    - 운영관리
        - 게시판관리
        - 코드관리
        - 메뉴관리
            - 프로그램 관리
            - 메뉴관리
            - 권한메뉴관리
    - 데이터관리
    - 인프라관리
    
=========== ============ ============ ===========
#### # ㅁㅇㄹㄴㄹㄴㅇ
    - 지도관리
    - 사용자관리
    - 모니터링
    - 충북시스템관리
=========== ============ ============ ===========
##### # WIFI: 1234onpoom
=============== =============== =============== 
통합대상 관련 사이트 주소

내부 : http://10.10.20.74:8080/portal/main.do
외부 : http://106.245.249.226:48080/portal/main.do
      http://106.245.249.226:48080/portal/login/getLoginView.do
id: onpoom1 pw: lxpf1234!
id: onpoom2 pw: lxpf1234!

LX 운영환경 LX 플랫폼
http://103.7.190.103/portal/main.do

ID : B60177  / PW : gyen6488!2(효두6488!2)

http://106.245.249.226:8087/koreaFront

LXP_manager/lxpf4321!

개발지원 사이트(상세기획필요)



##### # 지하객체
  - 테이블 : all_pipe
