$('.swimmer span').hover(

  function(eventObj) {
    $(".opp_fly_out", $(this).parent()).show( "fold", 1000 );
  }

);

function closePopup(eventObj) {
  $(".opp_fly_out").hide( "explode", 1000 );
}

$(".opp_fly_out .close").click(closePopup);
$(".opp_fly_out").mouseleave(closePopup);
