const postId = document.getElementById('postId').value;
console.log(postId);

const commentsCtnr = document.getElementById('commentCtnr');

const allComments = [];
console.log(allComments)

const displayComments = (comments) => {
    commentsCtnr.innerHTML = comments.map(
        (c) => {
            return asComment(c)
        }
    ).join('')
}

function asComment(c) {
    let commentHtml = `<div id="commentCtnr-${c.postId}">`;

    commentHtml += `<h4>${c.user} (${c.created})</h4><br/>`;
    commentHtml += `<p>${c.message}</p>`;
    commentHtml += `</div>`
    return commentHtml;
}

fetch(`http://localhost:8080/api/${postId}/comments`)
    .then(response => response.json())
    .then(data => {
        for (let comment of data) {
            allComments.push(comment);
        }
        displayComments(allComments)

    })
