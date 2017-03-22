# reversi (2017-03-22)

## usage

## option

### -d depth of minmax tree (if depth over 6, return time will increase exponentially)
### -t player's turn (-1 is black, 1 is white)

ex) java -jar reversi-chan8.jar -d 5 -t -1

OR java -jar reversi-chan8.jar (use default value. depth will be 4, turn will be black)

## 클래스 기능 요약
### AlphaBetaPruner
Minimax tree를 Alphabeta 알고리즘을 이용하여 이번 턴에 최적인 수를 찾음

### CliHelper
사용자와 컴퓨터간의 플레이가 가능하도록 입력을 받고 그에 따른 알고리즘을 실행

### Reversi
Board의 상태와 이번 턴이 누구의 턴인지를 입력받아 어느 곳에 수를 둘 수 있는지를 모아 AlphaBetPruner 클래스에
최적의 수를 요청

### Configuration
Board의 width와 height를 json 형태로 설정할 수 있도록 함. 없다면 8, 8의 기본값을 사용