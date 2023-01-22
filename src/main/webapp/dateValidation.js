    const options1={
    year: 'numeric',
    month: 'numeric',
    day: 'numeric',
}
    const checkIn11 = new Date()
    checkIn11.setDate(checkIn11.getDate()+1)
    const minCheckIn1 = checkIn11.toLocaleString("ru",options1).split('.').reverse().join('-')
    document.getElementById("checkInDate").setAttribute("min",minCheckIn1)
    const getCheckIn1 = document.getElementById("checkInDate")
    getCheckIn1.addEventListener('input', getN)
    function getN(){
    const checkOut1 = document.getElementById("checkInDate").value
    const checkOut21 = new Date(Date.parse(checkOut1))
    checkOut21.setDate(checkOut21.getDate()+1)
    const minCheckOut1 = checkOut21.toLocaleString("ru",options1).split('.').reverse().join('-')
    document.getElementById("checkOutDate").setAttribute("min",minCheckOut1)
}