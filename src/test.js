function test(){
  let calls = [];
  log('script start');

  setTimeout(function () {
    log('execute on next loop');
  }, 0);
  collectCall(calls,remoteCall,[0]);
  collectCall(calls,remoteCall1,[1]);
  collectCall(calls,remoteCall2,[2]);
  collectCall(calls,remoteCall1,[3]);

  commitCalls(calls);
  log('script end');
}

function remoteCall(idx){
  log('remoteCall-'+idx);
}
function remoteCall1(idx){
  log('remoteCall1-'+idx);
}
function remoteCall2(idx){
  log('remoteCall2-'+idx);
}
function collectCall(calls,callback,args){
  calls.push(callback);
  log("collect function \""+callback.name + "\" for batch commit");
  //Promise.resolve().then(function(){
  //  callback.apply(this,args);
  //});
}
function commitCalls(calls){
  log("Have "+calls.length+" remote call!");
  calls. forEach(function (item, index, array) {
    console.log(item, index);
    log('Execute collected micro tasks-"+index+"!');
  });
  //setTimeout(function () {
  //  log('Execute collected micro tasks!');
  //}, 0);
  calls.length=0;
  log("Have "+calls.length+" remote call!");

}
function log(message){
  console.log(message);
  document.getElementById("root").innerHTML = document.getElementById("root").innerHTML +"<p>"+ message;
}