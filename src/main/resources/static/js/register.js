window.addEventListener("load",function() {
    document.getElementById("myLink").addEventListener("click",function(e) {
        e.preventDefault(); // cancel the link
        document.getElementById("myForm").submit(); // but make sure nothing has name or ID="submit"
    });
});