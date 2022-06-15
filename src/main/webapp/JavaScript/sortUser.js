let ownerId = null;

function sortUser (element,sender){
     const value = element.value;
     const firstUse = element.getAttribute('firstUse');

     element.removeAttribute('firstUse');
     element.setAttribute('firstUse','false');

     users = replaceOwnerToAdmin();

     if (value !== 0 && element.children.length >= 17 && firstUse === 'false' && sender !== 'filter'){
         element.children[0].remove();
     }

     if (value == 1)
         users = users.sort((a, b) => (a.role > b.role ? 1 : -1));
     else if (value == 2)
         users = users.sort((a, b) => (a.role < b.role ? 1 : -1));
     else if (value == 3)
         users = users.sort((a, b) => (a.subject > b.subject) ? 1 : (typeof a.subject === 'undefined') ? 1 : (typeof b.subject === 'undefined' ? -1 : 0));
     else if (value == 4)
         users = users.sort((a, b) => (a.subject < b.subject) ? 1 : (typeof a.subject === 'undefined') ? -1 : (typeof b.subject === 'undefined' ? 1 : 0));
     else if (value == 5)
         users = users.sort((a, b) => (a.firstname > b.firstname ? 1 : -1));
     else if (value == 6)
         users = users.sort((a, b) => (a.firstname < b.firstname ? 1 : -1));
     else if (value == 7)
         users = users.sort((a, b) => (a.lastname > b.lastname ? 1 : -1));
     else if (value == 8)
         users = users.sort((a, b) => (a.lastname < b.lastname ? 1 : -1));
     else if (value == 9)
         users = users.sort((a, b) => (a.email > b.email ? 1 : -1));
     else if (value == 10)
         users = users.sort((a, b) => (a.email < b.email ? 1 : -1));
     else if (value == 11)
         users = users.sort((a, b) => (Number(a.gpn) > Number(b.gpn) ? 1 : -1));
     else if (value == 12)
         users = users.sort((a, b) => (Number(a.gpn) < Number(b.gpn) ? 1 : -1));
     else if (value == 13)
         users = users.sort((a, b) => (Number(a.startApprenticeship) > Number(b.startApprenticeship)) ? 1 : (typeof a.startApprenticeship === 'undefined') ? 1 : (typeof b.startApprenticeship === 'undefined' ? -1 : 0));
     else if (value == 14)
         users = users.sort((a, b) => (Number(a.startApprenticeship) < Number(b.startApprenticeship)) ? 1 : (typeof a.startApprenticeship === 'undefined') ? -1 : (typeof b.startApprenticeship === 'undefined' ? 1 : 0));
     else if (value == 15)
         users = users.sort((a, b) => (a.isConfirmed === b.isConfirmed)? 0 : a.isConfirmed? -1 : 1);
     else if (value == 16)
         users = users.sort((a, b) => (a.isConfirmed === b.isConfirmed)? 0 : a.isConfirmed? 1 : -1);


    users = replaceAdminToOwner();

     setUsers(users);
}

function replaceOwnerToAdmin (){
    for(let i = 0; i < users.length; i++){
        const user = users[i];
        if (user.role.toUpperCase() === 'OWNER'){
            ownerId = user.id;
            user.role = 'Admin';
            users[i] = user;
            break;
        }
    }
    return users;
}

function replaceAdminToOwner (){
    for(let i = 0; i < users.length; i++){
        const user = users[i];
        if (user.id === ownerId){
            user.role = 'Owner';
            users[i] = user;
            break;
        }
    }
    return users;
}