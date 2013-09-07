<%@page import="java.util.*"%>
<%@page import="java.sql.*" %>

<section id="reservation_container">	<!-- reservation -->
	<article id="view_res">
		<a href="#" title="modifyReservation">View/Modify Reservation</a>
	</article>
	<article id="heading" class="clear wrapper">
		<h1>Make a Reservation!</h1>
		<article id="trip_ways">
			<p class="trip_way">
					<input type="radio" id="one_way" name="trip_type" >
					<label for="trip_type">One Way</label>
				</p>	
				<p class="trip_way">
					<input type="radio" id="two_way" name="trip_type"  checked="checked">
					<label for="trip_type">Round Trip</label>
				</p>
 			</article>
			</article>
	<article id="content" class="clear">	<!-- content -->
		<article id="reservation">
			<form name="reservation_form">
				<label for="pick_location">pick up location</label>
				<p>
					<select name="pick_location" id="pick_location">
						
	          		</select>
				</p>
				<label for="drop_location">drop off location</label>
				<p>
					<select name="drop_location" id="drop_location">
							</select>
				</p>
				<article id="date">	<!-- date -->
					<label for="pick_date">pick up date</label>
					<p>
						<input type="text" id="pick_date" name="pick_date" class="datepicker pick"/>
						<select name="pick_time" id="pick_time">
							<%int i=0;
								for(i=0;i<=24;i++)
								{
									if(i<=12){					
								%>
									<option><%=i%>:00A.M</option>
								<%}
									else
									{
									%>
									<option><%=i%>:00P.M</option>
									<%
									}
								}
								%>									
						</select>
					</p>
					<label for="drop_date">drop off date</label>
					<p>
						<input type="text" id="drop_date" name="drop_date" class="datepicker drop" />
						<select name="drop_time" id="drop_time">
							<%
								i=0;
								for(i=0;i<=24;i++)
								{
									if(i<=12){					
								%>
									<option><%=i%>:00A.M</option>
								<%}
									else
									{
									%>
									<option><%=i%>:00P.M</option>
									<%
									}
								}
							%>
						</select>
					</p>
				</article>	<!-- end date -->
				<article id="travelInfo">
					 <label for="adultInfo">Adults</label>
				     <select id="adultInfo">
							<%
								i=0;
								for(i=0;i<=20;i++)
								{
									if(i<=20){					
								%>
									<option><%=i%></option>
								<%
									}
								}
							%>
						</select>
					 <label for="childrenInfo">Children</label>
				     <select id="childrenInfo">
							<%
								i=0;
								for(i=0;i<=20;i++)
								{
									if(i<=20){					
								%>
									<option><%=i%></option>
								<%
									}
								}
							%>
						</select>
					 <label for="infantsInfo">Infants</label>
				     <select id="infantsInfo">
							<%
								i=0;
								for(i=0;i<=10;i++)
								{
									if(i<=10){					
								%>
									<option><%=i%></option>
								<%
									}
								}
							%>
						</select>						      
				</article>
				<a id="search_vehicle" href="index.jsp?targetPage=vehicles.jsp">Search Vehicle</a>
<!-- 						<input type="submit" value="SUBMIT" class="submit">
						<label for="code">promo code</label>
						<p>
							<input type="text" id="code" placeholder="Ex:123" />
						</p> -->
			</form>
		</article>
		<article id="modifyReservation">
			<form name="modify_reservation">
				<article class="modify">
					<label for="confirmation_number">Confirmation Number</label>
					<p>
						<input type="text" id="confirmation_number" placeholder="Ex:12345"/>
					</p>
					<label for="last_name">Last Name</label>
					<p>
						<input type="text" id="last_name" placeholder="Ex:xyxyxy"/>
					</p>
				</article>
				<input type="button" class="back button" value="BACK">
				<input type="submit" class="button" value="CONTINUE">
			</form>
		</article>	
	</article>	<!-- end content -->
</section>	<!-- end reservation -->

<script type= "text/javascript">
$(document).ready(function(){
	
	$.ajax({
		type: 'GET',
		cache: false,
           contentType: 'application/json',  
           dataType: 'json',
           url: 'rest/location/getAll',
           success: function(data){
			var str = eval(data);
           	for(var i=0;i<str.collection.length;i++){
           		$("#pick_location").append($("<option></option>").val(str.collection[i].locationMasterID).html(str.collection[i].locationName) );
           		$("#drop_location").append($("<option></option>").val(str.collection[i].locationMasterID).html(str.collection[i].locationName) );
           	}
           },
           error: function(data){
			alert("Error occured while packaging."+data);
		}
	});
});


</script>

