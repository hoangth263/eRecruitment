/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
function add(i) {
    var main = document.getElementById("main");
    var row= document.createElement("tr");
    var td1 = document.createElement("td");
    td1.setAttribute("class", "text-center");
    var td2 = document.createElement("td");
    td2.setAttribute("class", "text-center");
    
    var y = document.createElement("INPUT");
    y.setAttribute("type", "radio");
    y.setAttribute("value", i + 1);
    y.setAttribute("name", "correctOptions");
    y.setAttribute("required", true);
    
    var x = document.createElement("INPUT");
    x.setAttribute("type", "text");
    x.setAttribute("placeholder", "Enter Option");
    x.setAttribute("style", "width: 680px;");
    x.setAttribute("name", "option"+ (i + 1));
    x.setAttribute("required", true);

    td1.appendChild(y);
    td2.appendChild(x);
    row.appendChild(td1);
    row.appendChild(td2); 
    main.appendChild(row);
    
    var ad = "add(" + (i + 1) + ")";
    var addButton = document.getElementById("addButton");
    if (i == 9) {
        addButton.disabled = true;
    }
    addButton.setAttribute('onclick', ad);
//                addButton.value = ad;

    var rem = "remove(" + (i + 1) + ")";
    var removeButton = document.getElementById("removeButton");
    if (i == 2) {
        removeButton.disabled = false;
    }
    removeButton.setAttribute('onclick', rem);
//                removeButton.value = rem;
    document.getElementById("countInput").value = (i + 1);
}


function remove(i) {
    document.getElementById("main").deleteRow(i-1);
    var ad = "add(" + (i - 1) + ")";
    var addButton = document.getElementById("addButton");
    if (i == 10) {
        addButton.disabled = false;
    }
    addButton.setAttribute('onclick', ad);
//                addButton.value = ad;
    var rem = "remove(" + (i - 1) + ")";
    var removeButton = document.getElementById("removeButton");
    if (i == 3) {
        removeButton.disabled = true;
    }
    removeButton.setAttribute('onclick', rem);
//                removeButton.value = rem;
    document.getElementById("countInput").value = (i - 1);
}
