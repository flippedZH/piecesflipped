function toZero() {
    for (var i = 0; i < 19; i++) {
        for (var j = 0; j < 19; j++) {
            pan[i][j] = 0;
        }
    }
}

function showNum(){
    if(move_show_flag==false){
        move_show_flag=true;
    }else {
        move_show_flag=false;
    }
    draw();
}

function clearBoard(){
    toZero();
    initDraw();
    jumpPointer=-1;
}
