# 소프트웨어품질 기말고사 (Final Exam) - Refactoring (Video Rental System)
## 팀원
- 김서권: 지능정보융합공학과 2기 (2024150629)
- 김승호: 컴퓨터공학과 바이오인공지능융합전공 2기 (2024244186)

## 결과물
1. 리팩터링 된 비디오 대여 시스템
    - GitHub URL: https://github.com/oh-gnues/IIE0004-VideoRental-Refactoring
    - 리팩토링된 소스 코드 전체
      - Structure
          ```
          ├── src
          │  ├── commands
          │  │  ├── Command.java
          │  │  ├── CommandRegistry.java
          │  │  ├── AbstractRentalCommand.java
          │  │  ├── ListCustomersCommand.java
          │  │  ├── ListVideosCommand.java
          │  │  ├── RegisterCustomerCommand.java
          │  │  ├── RegisterVideoCommand.java
          │  │  ├── RentVideoCommand.java
          │  │  ├── ReturnVideoCommand.java
          │  │  ├── GetCustomerReportCommand.java
          │  │  ├── ClearRentalsCommand.java
          │  │  └── InitializerCommand.java
          │  ├── customer
          │  │  ├── Customer.java
          │  │  ├── CustomerFormatter.java
          │  │  ├── CustomerReportData.java
          │  │  ├── CustomerReportFormatter.java
          │  │  └── CustomerService.java
          │  ├── rental
          │  │  ├── Rental.java
          │  │  └── RentalService.java
          │  ├── video
          │  │  ├── Video.java
          │  │  └── VideoService.java
          │  ├── Initializer.java
          │  └── VRUI.java
          ├── test
          │  └── VideoRentalTest.java # Junit 5
          └── README.md 
          ```
    
2. 리팩터링 보고서 또는 로그
    - 리팩터링 순서
        1. **Logic Bug (Rental.returnVideo)**
            - ISSUE URL: https://github.com/oh-gnues/IIE0004-VideoRental-Refactoring/issues/1
        2. **Logic Bug & Duplication**
            - ISSUE URL: https://github.com/oh-gnues/IIE0004-VideoRental-Refactoring/issues/2
        3. **God Class (VRUI)**
            - ISSUE URL: https://github.com/oh-gnues/IIE0004-VideoRental-Refactoring/issues/3
            - ISSUE URL: https://github.com/oh-gnues/IIE0004-VideoRental-Refactoring/issues/10
        4. **SRP Violation - Customer.getReport**
            - ISSUE URL: https://github.com/oh-gnues/IIE0004-VideoRental-Refactoring/issues/4
        5. **I/O 로직과 비즈니스 로직 혼합**
            - ISSUE URL: https://github.com/oh-gnues/IIE0004-VideoRental-Refactoring/issues/15
        6. **거대 Switch문**
            - ISSUE URL: https://github.com/oh-gnues/IIE0004-VideoRental-Refactoring/issues/18
        7.  **초기화 로직 집중화 및 모듈화**
            - ISSUE URL: https://github.com/oh-gnues/IIE0004-VideoRental-Refactoring/issues/20
        8.  **Duplicate (rentVideo & returnVideo)**
            - ISSUE URL: https://github.com/oh-gnues/IIE0004-VideoRental-Refactoring/issues/23
