var notification = document.getElementById('profileNotification');
var closeBtn = document.getElementById('profileNotificationCloseButton');

closeBtn.addEventListener('click', closeNotification);

function closeNotification() {
    notification.style.display = 'none';
}
