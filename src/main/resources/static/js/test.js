function planet(object) {

    switch(object.id) {
        case "theSun" : 
         document.getElementById("text-to-change").innerHTML = "The Sun";
        break;
        case "mercury" : 
         document.getElementById("text-to-change").innerHTML = "Mercury";
        break;
        case "venus" : 
         document.getElementById("text-to-change").innerHTML = "Venus";
        break;
        case "theEarth" : 
         document.getElementById("text-to-change").innerHTML = "The Earth";
        break;
        case "theMoon" : 
         document.getElementById("text-to-change").innerHTML = "The Moon";
        break;
        case "mars" : 
         document.getElementById("text-to-change").innerHTML = "Mars";
        break;
        case "jupiter" : 
         document.getElementById("text-to-change").innerHTML = "Jupiter";
        break;
        case "saturn" : 
         document.getElementById("text-to-change").innerHTML = "Saturn";
        break;
        case "uranus" : 
         document.getElementById("text-to-change").innerHTML = "Uranus";
        break;
        case "neptune" : 
        document.getElementById("text-to-change").innerHTML = "Neptune";
       break;
    }
}

function normal() {
    document.getElementById("text-to-change").innerHTML = "Click or touch an object to explore";
}