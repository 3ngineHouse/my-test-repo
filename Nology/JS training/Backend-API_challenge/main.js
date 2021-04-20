console.log("script running")

const kanyeHeader = document.querySelector('#kanyeHeader')

const getSomeKanye = () => {
    fetch('https://api.kanye.rest')
    .then(res => res.json())
    .then(json => {
        kanyeHeader.innerHTML = json.quote
    })
}



getSomeKanye();