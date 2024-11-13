import {Container} from "react-bootstrap";

export default function LandingPage() {

	return (
		<Container className={"flex flex-col"}>
			<img src={"src/forage/assets/images/Logo.png"}
				 alt={"Forage Tracker Logo"}
				 width={"300px"}
				 className={"align-self-center align-self-md-baseline"}/>
			<p className={"align-self-center align-self-md-baseline"}>Easily track wild edible plants and mushrooms in your area.</p>
		</Container>
	)
}