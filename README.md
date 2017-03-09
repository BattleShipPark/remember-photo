# remember-photo
## 아키텍쳐
최근에 Clean Architecture를 공부해 보고 있어서 적용해 봤습니다. presentation, domain, data의 계층으로 분리하고 presentation은 MVP의 구조를 따랐습니다.
데이터 저장은 최근에 사용해 본 ORMLite로 했습니다.

## 라이브러리
com.annimon:stream:1.1.6
com.android.support:appcompat-v7:25.2.0
com.android.support:recyclerview-v7:25.2.0
com.android.support:support-annotations:25.2.0
com.facebook.stetho:stetho:1.3.1
com.github.bumptech.glide:glide:3.7.0
com.j256.ormlite:ormlite-core:4.48
com.j256.ormlite:ormlite-android:4.48
com.jakewharton:butterknife:8.5.1
com.tbruyelle.rxpermissions:rxpermissions:0.9.3
io.reactivex:rxandroid:1.2.1
io.reactivex:rxjava:1.2.7
junit:junit:4.12
org.assertj:assertj-core:2.6.0
org.mockito:mockito-core:2.7.1

## 테스트
일부 클래스에 대한 자바 유닛 테스트만 작성한 상태입니다.

## 스토리 리스트
리싸이클러뷰로 그룹을 표시해야 하는데, DB에 저장된 형태와 다른 구조가 필요하므로 PresentationMapper를 통해서 다른 모델로 변환했습니다.
그룹의 헤더와 아이템을 표시하기 위해 두 개의 viewType이 필요했고, 각각에 대한 레이아웃 XML과 뷰홀더를 만들었습니다.
검색 기능은 넣지 못했습니다.

## 스토리 상세/편집/생성
한 화면에서 조회, 편집, 생성이 가능하도록 Mode로 구분해서 처리했습니다.
텍스트 입력 상태에 따라 우상단 버튼의 활성 상태를 제어하기 위해 TextWatcher와 PublishSubject를 사용해 봤습니다
이미지 원본을 볼 수 있는 기능은 넣지 못했습니다.

## 촬영
카메라 제어는 주로 하던 작업이 아니여서 이번에 개발자 문서 찾아보면서 작성했고, 기본 촬영만 가능하게 했습니다.

## 개선이 필요한 사항
검색
스토리 삭제, 사진 추가,삭제,드래그
이미지 상세 화면