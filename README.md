<div align="center">
  <br />
  <img src="./readme_assets/milkmarket_logo.png" alt="milkmarket_logo" />
  <br />
  <h1>우리 유아를 위한 마켓</h1>
  <br />
</div>

## 목차

1. [**서비스 소개**](#1)
2. [**서비스 목표**](#2)
3. [**기술 스택**](#3)
4. [**시스템 아키텍처**](#4)
5. [**주요기능 및 데모영상**](#5)
6. [**UCC 보러가기**](#6)
7. [**협업 관리**](#7)
8. [**개발 멤버 소개**](#8)
9. [**프로젝트 기간**](#9)
10. [**프로젝트 관련 문서**](#10)

<br/>

<div id="1"></div>

## 💡 서비스 소개

### 지갑 사정은 좋지 않고 우리 아기에게 필요한 건 많을 땐, 우유마켓!

> 눈 깜짝 할 사이 커버리는 아이들 <br/> 유아용품은 큰 맘 먹고 구매해도, 사용주기가 너무나도 짧습니다. <br/> 우유마켓은 블록체인 기술을 적용하여 신뢰할 수 있는 거래를 통해 <br/> `유아용품 중고거래의 선순환이 가능한 환경을 조성`합니다.

#### 블록체인을 활용한 유아용품 중고거래 서비스, 우유마켓과 함께 하세요!

</br>

<div id="2"></div>

## 🥅 목표

1. 단순히 유아 용품을 거래하는 중고거래 사이트에서 머무르는 것이 아닌, 자유로운 커뮤니티 활동을 통해 육아에 대한 노하우나 고충에 대해 이야기할 수 있습니다. 이 과정에서 생긴 유저들 간의 라포가 또 다른 거래를 불러 일으키는 선순환이 작용하는 서비스를 구축합니다.

2. 스마트 컨트랙트를 통해 구현한 에스크로(escrow) 서비스를 적용하여 Dapp의 요소를 갖추고, 이를 통해 신뢰도가 높은 서비스를 구축합니다.

3. Elastic Search를 적용하여 상품검색 시 빠른 속도로 최적의 검색 결과를 선보입니다.

<br/>

<div id="3"></div>

## 🛠️ 기술 스택

<img src="https://img.shields.io/badge/Ethereum-3C3C3D?style=for-the-badge&logo=Ethereum&logoColor=white" style="height : auto; margin-left : 10px; margin-right : 10px;"/> <img src="https://img.shields.io/badge/Web3.js-F16822?style=for-the-badge&logo=Web3.js&logoColor=white" style="height : auto; margin-left : 10px; margin-right : 10px;"/> <img src="https://img.shields.io/badge/Elasticsearch-005571?style=for-the-badge&logo=Elasticsearch&logoColor=white" style="height : auto; margin-left : 10px; margin-right : 10px;"/> <br/>
<img src="https://img.shields.io/badge/Java-007396?style=for-the-badge&logo=Java&logoColor=#007396" style="height : auto; margin-left : 10px; margin-right : 10px;"/> <img src="https://img.shields.io/badge/Spring Boot-6DB33F?style=for-the-badge&logo=Spring Boot&logoColor=white" style="height : auto; margin-left : 10px; margin-right : 10px;"/> <img src="https://img.shields.io/badge/JSON Web Tokens-000000?style=for-the-badge&logo=JSON Web Tokens&logoColor=white" style="height : auto; margin-left : 10px; margin-right : 10px;"/> <img src="https://img.shields.io/badge/Spring Security-6DB33F?style=for-the-badge&logo=Spring Security&logoColor=white" style="height : auto; margin-left : 10px; margin-right : 10px;"/> <img src="https://img.shields.io/badge/Amazon S3-569A31?style=for-the-badge&logo=Amazon S3&logoColor=white" style="height : auto; margin-left : 10px; margin-right : 10px;"/><br>
<img src="https://img.shields.io/badge/Apache Maven-C71A36?style=for-the-badge&logo=Apache Maven&logoColor=white" style="height : auto; margin-left : 10px; margin-right : 10px;"/> <img src="https://img.shields.io/badge/Nginx-009639?style=for-the-badge&logo=NGINX&logoColor=white" style="height : auto; margin-left : 10px; margin-right : 10px;"/> <img src="https://img.shields.io/badge/Docker-2496ED?style=for-the-badge&logo=Docker&logoColor=white" style="height : auto; margin-left : 10px; margin-right : 10px;"/> <img src="https://img.shields.io/badge/Ubuntu-E95420?style=for-the-badge&logo=Ubuntu&logoColor=white" style="height : auto; margin-left : 10px; margin-right : 10px;"/><br>
<img src="https://img.shields.io/badge/Vue.js-4FC08D?style=for-the-badge&logo=Vue.js&logoColor=white" style="height : auto; margin-left : 10px; margin-right : 10px;"/> <img src="https://img.shields.io/badge/Node.js-339939?style=for-the-badge&logo=Node.js&logoColor=white" style="height : auto; margin-left : 10px; margin-right : 10px;"/> <img src="https://img.shields.io/badge/Firebase-FFCA28?style=for-the-badge&logo=Firebase&logoColor=white" style="height : auto; margin-left : 10px; margin-right : 10px;"/> <br>
<img src="https://img.shields.io/badge/Jira-0052CC?style=for-the-badge&logo=Jira&logoColor=white" style="height : auto; margin-left : 10px; margin-right : 10px;"/> <img src="https://img.shields.io/badge/GitLab-FCA121?style=for-the-badge&logo=GitLab&logoColor=white" style="height : auto; margin-left : 10px; margin-right : 10px;"/> <br/>

<details><summary> <b> 상세 기술스택 및 버전</b> </summary>

| 구분       | 기술스택        | 상세내용           | 버전        |
| ---------- | --------------- | ------------------ | ----------- |
| 공통       | 형상관리        | Gitlab             | \-          |
|            | 이슈관리        | Jira               | \-          |
|            | 커뮤니케이션    | Mattermost, Notion | \-          |
| BackEnd    | DB              | MySQL              | 5.7         |
|            |                 | JPA                | \-          |
|            | Java            | JavaSE             | 11          |
|            | Spring          | Spring             | 5.3.6       |
|            |                 | Spring Boot        | 2.4.5       |
|            | IDE             | Eclipse            | JEE 2020-06 |
|            |                 | IntelliJ           | 2021.3.1    |
|            | Cloud Storage   | AWS S3             | \-          |
|            | Build           | Maven              | 4.0.0       |
|            | Elastic         | ElasticSearch      | 7.15.1      |
|            | API Docs        | Swagger2           | 3.0.0       |
| FrontEnd   | HTML5           |                    | \-          |
|            | CSS3            |                    | \-          |
|            | JavaScript(ES6) |                    | \-          |
|            | Vue             | Vue.js             | 2.6.14      |
|            |                 | vuex               | 3.6.2       |
|            |                 | vuex-persist       | 4.1.0       |
|            | Firebase        |                    | 8.10.0      |
|            | IDE             | Visual Studio Code | 1.63.2      |
| BlockChain | Web3            | Solidity           | 0.8.12      |
|            |                 | Web3.js            | 1.5.3       |
|            |                 | truffle            | 5.5.5       |
|            |                 | Ganache            | 7.0.3       |
|            |                 | openzeppelin       | 4.5.0       |
| Server     | 서버            | AWS EC2            | \-          |
|            | 플랫폼          | Ubuntu             | 20.04.3 LTS |
|            | 배포            | Docker             | 20.10.12    |

</details>

<br />

<div id="4"></div>

## 🗂️ 시스템 아키텍처

|            시스템 구성            |
| :-------------------------------: |
| ![흐름도](/docs/서비스흐름도.jpg) |

| 디렉토리 구조                           |
| :-------------------------------------- |
| ![디렉토리구조](/docs/디렉토리구조.jpg) |

<div id="5"></div>

## 🖥️ 주요기능

### [MyPage] 지갑

- 고유 비밀번호를 입력하여 지갑을 생성합니다. 블록체인의 복잡한 과정을 대신해 비밀번호만 기억하면 거래가능하게 하였습니다.
- ETH 충전을 통해 이더를 구매합니다.
- MILK 충전을 통해 이더로 MILK를 구매합니다. 1이더(ETH)당 1000밀크(MILK)를 나타냅니다.

|                      지갑 충전하기                      |
| :-----------------------------------------------------: |
| <img src="./readme_assets/wallet.gif" alt="지갑충전" /> |

### [Shop] 상품구매

- 구매자가 Shop에서 원하는 상품을 선택하고 구매하기를 누르면 보유 밀크(MILK)에서 해당 가격만큼의 밀크(MILK)를 차감합니다.
- 판매자와 거래가 완료되면 수령 확인을 통해 거래를 확정합니다. 수령 확인을 하지 않으면 밀크가 제공되지 않습니다.
- 판매자가 판매자 확인을 눌러 판매를 확정 짓게 되면 구매자가 차감한 밀크가 정상적으로 들어옵니다.

|                       상품 구매하기                       |
| :-------------------------------------------------------: |
| <img src="./readme_assets/purchase.gif" alt="상품구매" /> |

### [Shop] 상품등록

- 판매 혹은 대여하고자 하는 상품을 등록할 수 있습니다.
- 상품명, 설명, 카테고리, 가격 등을 입력할 수 있으며 상품 이미지는 다중파일 업로드가 가능합니다.

|                       상품 등록하기                       |
| :-------------------------------------------------------: |
| <img src="./readme_assets/register.gif" alt="상품등록" /> |

### [Shop] 상품검색

- 상단 검색기능에서 키워드 검색을 통해 해당하는 상품들을 보여줍니다.
- Elastic Search를 적용하여 찾고 싶은 상품을 필터링(판매상태, 카테고리, 우리동네 여부)하여원하는 상품을 빠르게 찾을 수 있습니다.

|                        상품검색                         |
| :-----------------------------------------------------: |
| <img src="./readme_assets/search.gif" alt="상품검색" /> |

### [Shop] 상품 상세조회

- 상품 이미지, 판매 및 대여 정보를 확인할 수 있습니다.
- 판매자와 채팅, 관심상품 등록, 상품구매를 할 수 있습니다.
- 사용자가 선택한 상품과 관련있는 상품들을 하단에서 최대 4개까지 확인할 수 있습니다.
- 관련있는 상품들은 사용자가 선택한 상품의 카테고리와 지역이 같습니다.

|                        상품 상세조회                         |
| :----------------------------------------------------------: |
| <img src="./readme_assets/detail.gif" alt="상품 상세조회" /> |

### [Shop] 1:1 채팅

- 구매자와 판매자의 채팅이 가능해집니다.
- 구매자가 채팅을 요청하면 판매자에게 알림이 전달돼 수락, 거부를 할 수 있습니다.

|                       1:1 채팅                        |
| :---------------------------------------------------: |
| <img src="./readme_assets/chatting.gif" alt="채팅" /> |

### 메인페이지

- 우유마켓의 랜딩페이지입니다.
- Shop에 등록된 상품 중 관심등록이 많은 인기상품 최상위 5개를 보여주며 상품정보와 관심상품 등록 수, 등록시간을 확인할 수 있습니다.

|                          메인페이지                          |
| :----------------------------------------------------------: |
| <img src="./readme_assets/maingpage.gif" alt="메인페이지" /> |

### 커뮤니티

- 유아용품 관련 정보를 자유롭게 공유할 수 있는 커뮤니티입니다.
- 게시글에 대해 댓글을 남길 수 있습니다.

|                          커뮤니티                          |
| :--------------------------------------------------------: |
| <img src="./readme_assets/community.gif" alt="커뮤니티" /> |

<br/>

<div id="6"></div>

## 🎥 [UCC 보러가기](https://youtu.be/t1jL26PPLHM)

<br />

<div id="7"></div>

## 👥 협업 관리

|                         Jira BurnDown Chart                          |
| :------------------------------------------------------------------: |
| <img src="./readme_assets/burndown.png" alt="Jira BurnDown Chart" /> |

|                        Notion                         |
| :---------------------------------------------------: |
| <img src="./readme_assets/notion.PNG" alt="Notion" /> |

<br />

<div id="8"></div>

## 👪 개발 멤버 소개

<table>
    <tr>
        <td height="140px" align="center"> <a href="https://github.com/dev-yunhy96">
            <img src="https://avatars.githubusercontent.com/dev-yunhy96" width="140px" /> <br><br> 윤희영 <br>(Front-End) </a> <br></td>
        <td height="140px" align="center"> <a href="https://github.com/sahngrhee">
            <img src="https://avatars.githubusercontent.com/sahngrhee" width="140px" /> <br><br> 이상훈 <br>(Front-End) </a> <br></td>
        <td height="140px" align="center"> <a href="https://github.com/sangwooYi">
            <img src="https://avatars.githubusercontent.com/sangwooYi" width="140px" /> <br><br> 이상우 <br>(Full-stack) </a> <br></td>
        <td height="140px" align="center"> <a href="https://github.com/KwonDoHyuk">
            <img src="https://avatars.githubusercontent.com/KwonDoHyuk" width="140px" /> <br><br> 권도혁 <br>(Full-stack) </a> <br></td>
        <td height="140px" align="center"> <a href="https://github.com/dayaeLee777">
            <img src="https://avatars.githubusercontent.com/dayaeLee777" width="140px" /> <br><br> 이다예 <br>(Full-stack) </a> <br></td>
    </tr>
    <tr>
        <td align="center">UI/UX<br/>Vue<br/></td>
        <td align="center">UI/UX<br/>Vue<br/>Firebase</td>
        <td align="center">UI/UX<br/>Vue<br/>Blockchain<br/>REST API</td>
        <td align="center">REST API<br/>UI/UX<br/>Vue<br/></td>
        <td align="center">REST API<br/>Server<br/>ElasticSearch<br/>UI/UX</td>
    </tr>
</table>
<br />

<div id="9"></div>

## 📆 프로젝트 기간

### 22.2.21 ~ 22.4.8

- 기획 및 설계 : 22.2.21 ~ 3.2
- 프로젝트 구현 : 22.3.2 ~ 22.4.5
- 버그 수정 및 산출물 정리 : 22.4.6 ~ 8

<br />

<div id="10"></div>

## 📋 프로젝트 관련 문서

| 구분            |                                  링크                                   |
| :-------------- | :---------------------------------------------------------------------: |
| 공통코드        |                 [공통코드 바로가기](/docs/공통코드.md)                  |
| 컨벤션목록      |               [컨벤션목록 바로가기](/docs/컨벤션목록.md)                |
| 덤프파일        |                 [덤프파일 바로가기](/exec/03_DB덤프.md)                 |
| ERD             |                      [ERD 바로가기](/docs/ERD.JPG)                      |
| 빌드/배포       |       [빌드/배포 바로가기](/exec/01_서울_5반_A504_빌드및배포.pdf)       |
| 외부서비스 정보 | [외부서비스 정보 바로가기](/exec/02_서울_5반_A504_외부_서비스_정보.pdf) |
| 시연 시나리오   |    [시연 시나리오 바로가기](/exec/04_서울_5반_A504_시연시나리오.pdf)    |
| 발표자료        |                 [발표자료 바로가기](/docs/발표자료.pdf)                 |
