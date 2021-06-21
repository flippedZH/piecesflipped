
let move_show_flag=false;
//棋盘规格
let goSize=boardSize;
//棋盘大小
let goWidth=500;
let goHeight=goWidth;

//棋盘边距 小方框大小
let goMargin=goWidth/(goSize+1);
//棋子半径
let piecesRadius=goMargin/2;

let lockQiRole=undefined;

//自动下一步
let autoNext=false;

let img=new Image();

//在画布画填充圆形
function fillCircle(cxt,x,y,r,color){
    cxt.beginPath();
    cxt.arc(x, y, r, 0, Math.PI * 2, true);
    cxt.closePath();
    cxt.fillStyle = color;
    cxt.fill();
}

//自定义初始化函数：创建画布画图对象 放图 画线 画点
function initDraw(){
    var c = document.getElementById("goBoard"); //jpg
    /* getContext是在画布绘图  */
    var cxt = c.getContext("2d"); //getContext() 方法返回一个用于在画布上绘图的环境。
    cxt.strokeStyle="black";//画布边框颜色
    /* 清空，重新画线等 */
    cxt.clearRect(0,0,goWidth,goHeight);//清空出一个goWidth,goHeight的矩形
    cxt.drawImage(img,0,0,goWidth,goHeight);//放入图片
    grid(cxt);//grid,自定义函数，画出棋盘线
    ninePoints(cxt); //9个黑点
    return cxt;
}

//画布上绘制棋子的函数  参数 ：画布绘制环境对象  位置  颜色
function drawPieces(cxt,i,j,color1,color2){
    //小小渲染一下
    var rg = cxt.createRadialGradient((j+1)*goMargin-3, (i+1)*goMargin-3, 1, (j+1)*goMargin-4, (i+1)*goMargin-4, 11);
    //从黑到白色的渐变
    rg.addColorStop(1, color1);
    rg.addColorStop(0,color2 );
    //绘制路径(线) 然后画圆轮廓
    cxt.beginPath();
    cxt.arc((j+1)*goMargin, (i+1)*goMargin,piecesRadius,0,2*Math.PI,false);
    //为图形轮廓填充 渐变样式
    cxt.fillStyle=rg;
    cxt.fill();
}

//显示手数：
function show_number(cxt){
    // 显示手数 通过字体显示
    if (move_show_flag) { //是否显示手数 默认不显示
        //给棋盘上的棋子标序号 显示手数
        for (let m =0; m <jumpPointer; m++) {
            //不知道这里x y为啥全部反过来了
            let r=record[m];
            let y=record[m][0];
            let x=record[m][1];

            // 判断是否被提子
            if (pan[y][x] === 0){
                continue;
            }
            if (r[2]===qiType.white) { //black
                cxt.fillStyle="black";
            } else {
                cxt.fillStyle="white";
            }
            cxt.font="bold "+piecesRadius+"px sans-serif";
            cxt.textAlign="center";
            cxt.fillText(m+1+"", (x+1)*goMargin, (y+1)*goMargin+(piecesRadius/2)-4);
        }
    }
}

//主绘制函数 入口
function draw() {
    var cxt =initDraw();
    $.ajax({
                  async:false,
                  url: "test_post",
                  type: "POST",
                  dataType: "json",
                  success: function (data) {
                  pan=data;
                  }
                  })

    console.log(pan);

    //根据pan[][]绘制黑棋
    for (var i = 0; i < goSize; i++) {
        for (var j = 0; j < goSize; j++) {
            //画黑子
            if (pan[i][j] === qiType.black) { //black
              drawPieces(cxt,i,j,"#202020","gray")
            }
            //画白棋子
            else if (pan[i][j] === qiType.white) { //white
                drawPieces(cxt,i,j,"#b9d4f2","white")
            }
            //不知道  真不知
            else if (pan[i][j] === qiType.fill) { // fill color
                cxt.beginPath();
                cxt.arc((j+1)*goMargin, (i+1)*goMargin,piecesRadius,0,2*Math.PI,false);
                cxt.fillStyle="red";
                cxt.fill();
            }
        }
    }
    show_number(cxt);
    // 特别显示最新的一手
    if (jumpPointer >= 0) {
        fillCircle(cxt,((record[jumpPointer][1]+1)*goMargin),((record[jumpPointer][0]+1)*goMargin),5,'red');
    }
    //提子显示
    $("#blackUp").text(blackUpCount);
    $("#whiteUp").text(whiteUpCount);
}

function drawAi() {
    var cxt =initDraw();
    //根据pan[][]绘制黑棋
    for (var i = 0; i < goSize; i++) {
        for (var j = 0; j < goSize; j++) {
            //画黑子
            if (pan[i][j] === qiType.black) { //black
                drawPieces(cxt,i,j,"#202020","gray")
            }
            //画白棋子
            else if (pan[i][j] === qiType.white) { //white
                drawPieces(cxt,i,j,"#b9d4f2","white")
            }
            //不知道  真不知
            else if (pan[i][j] === qiType.fill) { // fill color
                cxt.beginPath();
                cxt.arc((j+1)*goMargin, (i+1)*goMargin,piecesRadius,0,2*Math.PI,false);
                cxt.fillStyle="red";
                cxt.fill();
            }
        }
    }
    show_number(cxt);
    // 特别显示最新的一手
    if (jumpPointer >= 0) {
        fillCircle(cxt,((record[jumpPointer][1]+1)*goMargin),((record[jumpPointer][0]+1)*goMargin),5,'red');
    }
    //提子显示
    $("#blackUp").text(blackUpCount);
    $("#whiteUp").text(whiteUpCount);
}

//线条宽度
let lineWidth=1;
let lineColor="#000000";

//线
function grid(cxt) {
    /**
     * canvas 画线 为2px -0.5 是为了变成1px
     */
    //竖线
    for (let i = 0; i < goSize; i++) {
        cxt.beginPath();
        cxt.strokeStyle=lineColor;
        cxt.moveTo(0+goMargin,   (i+1)*goMargin-0.5);
        cxt.lineTo(goWidth-goMargin, (i+1)*goMargin-0.5);
        cxt.lineWidth = lineWidth;
        cxt.stroke();
    }
    //横线
    for (let i = 0; i < goSize; i++) {
        cxt.beginPath();
        cxt.strokeStyle=lineColor;
        cxt.moveTo((i+1)*goMargin-0.5,   0+goMargin);
        cxt.lineTo((i+1)*goMargin-0.5, goHeight-goMargin);
        cxt.lineWidth = lineWidth;
        cxt.stroke();
    }

    //A-S
    for (let i = 0; i < goSize; i++) {
        cxt.font="15px Arial";
        cxt.fillStyle="#000000";
        str=String.fromCharCode('A'.charCodeAt()+i)
        if(str<'I'){
            str=String.fromCharCode('A'.charCodeAt()+i)
        }
        else{
            str=String.fromCharCode('A'.charCodeAt()+i+1)}
        cxt.fillText(str,(i+1)*goMargin-7,18);
    }
    //1-19
    for (let i = 0; i < goSize; i++) {
        cxt.font="15px Arial";
        cxt.fillStyle="#000000";
        cxt.fillText(19-i ,10 ,(i+1)*goMargin+3);
    }
}
//天元与边角星
function ninePoints(cxt) {
	//19*19
	//left=3 right=19-1-3=15 center=9
    var np = [];
    let left=3,right=goSize-1-left,center=(goSize-1)/2;
    if(goSize<13){
        left=2;
        right=goSize-1-left;
    }
    //驱动式编程？
    get(center,center);//天元
    get(left,center);
    get(center,left);
    get(right,center);
    get(center,right);
    get(left,left);
    get(left,right);
    get(right,left);
    get(right,right);
    function get(x,y) {
        np.push([goMargin*(1+x),goMargin*(1+y)]);
    }
    for (var i = 0; i < np.length; i++) {
        cxt.beginPath();
        cxt.arc(np[i][0],np[i][1],3,0,2*Math.PI,false);
        cxt.fillStyle="#000000";
        cxt.fill();
    }

}

//获取ai反馈   (传入参数为鼠标落子位置)
function getFeedBack(x,y,color){
    let x_ai=undefined;
    let y_ai=undefined;
    let color_ai=undefined;
    var pos={
            "x":x,
            "y":y,
            "color":1,
            };
        $.ajax({
                    type:"post",
                    async:false,  //暂时别改动
                    contentType: "application/json;charset=UTF-8",
                    url:"/mouseDown",
                    data:JSON.stringify(pos),
                    dataType: "json", //返回数据类型
                    success:function (feedback) {
                        console.log("obj:",feedback)
                        var obj = JSON.parse(feedback);
                        x_ai=obj.x;
                        y_ai=obj.y;
                        color_ai=obj.color;
                        }
                   })
    console.log("y_ai,x_ai,color_ai:",y_ai,x_ai,color_ai)
    play(y_ai,x_ai,color_ai);
    drawAi();
}

//鼠标左键按下去
function mousedownHandler(e) {//事件

    if(e.which!==1){ //不是鼠标左键
        return false;
    }
    var x, y;
    if (e.offsetX || e.offsetX == 0) {
        x = e.offsetX; //- imageView.offsetLeft;
        y = e.offsetY; //- imageView.offsetTop;
    }
    if (x < goMargin-10 || x > goWidth-goMargin+10)
        return;
    if (y < goMargin-10 || y > goHeight-goMargin+10)
        return;

    var xok = false;
    var yok = false;
    var x_;
    var y_;
    for (var i = 1; i <= goSize; i++) {
        if (x > i*goMargin-piecesRadius && x < i*goMargin+piecesRadius) {
            x = i*goMargin;
            xok = true;
            x_ = i - 1;
        }
        if (y > i*goMargin-piecesRadius && y < i*goMargin+piecesRadius) {
            y = i*goMargin;
            yok = true;
            y_ = i - 1;
        }
    }
    if (!xok || !yok)
        return;
    //全局变量lockQiRole来自页面
    // if(lockQiRole==qiType.black){
    // 	play(x_, y_,qiType.black);
    // }
    // else if(lockQiRole==qiType.white){
    // 	play(x_, y_,qiType.white);
    // }
    play(y_, x_,lockQiRole);
    drawAi();
    //这里不用交换，只需要getFeedBack 中函数play里面的x，y交换就行
    getFeedBack(x_, y_,lockQiRole);
}

//画半透明圆形
function mousemoveHandler(e) {
    var x, y;
    if (e.offsetX || e.offsetX == 0) {
    	//图片范围内的x，y坐标
        x = e.offsetX ;//- imageView.offsetLeft;
        y = e.offsetY ;//- imageView.offsetTop;
    }
    if (x < goMargin-10 || x > goWidth-goMargin+10)//goMargin：棋盘边距
        return;
    if (y < goMargin-10 || y > goHeight-goMargin+10)
        return;

    var xok = false;
    var yok = false;
    //设置事件触发条件
    for (var i = 1; i <= goSize; i++) {
        if (x > i*goMargin-piecesRadius && x < i*goMargin+piecesRadius) {
            x = i*goMargin;
            xok = true;
        }
        if (y > i*goMargin-piecesRadius && y < i*goMargin+piecesRadius) {
            y = i*goMargin;
            yok = true;
        }
    }
    if (!xok || !yok)
        return;

    //pathyes
    var c = document.getElementById("path");
    var cxt = c.getContext("2d");

    // clear the path
    cxt.clearRect(0,0,goWidth,goHeight);//不清除就会留下前面的影子

    cxt.beginPath();//准备fill

    cxt.arc(x,y,piecesRadius,0,2*Math.PI,false);
    //半透明黑或白
    if (whoIsPlay===qiType.black)
        cxt.fillStyle="rgba(2,2,3,0.3)";
    else
        cxt.fillStyle="rgba(255,255,255,0.5)";
    cxt.fill();
}

//清空
function mouseoutHandler(e) {
    var c = document.getElementById("path");
    var cxt = c.getContext("2d");
    cxt.clearRect(0,0,goWidth,goHeight);
}

function initBoard() {
    var c_path = document.getElementById("path"); //选择顶层画布
    c_path.addEventListener('mousedown', mousedownHandler, false);
    c_path.addEventListener('mousemove', mousemoveHandler, false);
    c_path.addEventListener('mouseout', mouseoutHandler, false);
    draw();// 避免图片在刷新中丢失
}


// window.onload可以是一个函数。如果是，这个函数将在页面加载完成之后被执行。

//if(typeof window.onload != 'function')也就是说window.onload还没有被赋值，
// 就直接把它赋值为func。否则，说明有程序先对其赋值了，那么在页面加载完成之后，应该先执行现有函数再执行自己的新的func。
function addLoadEvent(func) {
    var oldonload = window.onload;//现有函数
    if (typeof window.onload != 'function') {
        window.onload = func;
    } else {
        window.onload = function() {
            oldonload();
            func();
        }
    }
}

addLoadEvent(initBoard);

