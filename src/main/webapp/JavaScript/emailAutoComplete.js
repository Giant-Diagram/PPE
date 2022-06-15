function emailAutoComplete(element) {
    //get element and value
    const emailElement = element;
    let emailValue = String(emailElement.value);

    if (emailValue.includes('@') && !emailValue.endsWith('@') && !emailValue.endsWith('@ubs.com')){

        //delete everything behind the '@' and the '@' itself
        while(emailValue.slice(-1) !== '@'){
            emailValue = emailValue.slice(0,-1);
        }
        emailElement.value = emailValue.slice(0,-1);
    }else if (emailValue.endsWith('@')){
        emailElement.value = emailElement.value.replaceAll('@','@ubs.com');
    }
}