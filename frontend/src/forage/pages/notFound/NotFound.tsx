import {Link} from "react-router-dom";
import {Container} from "react-bootstrap";

export default function NotFound() {
	return (
		<Container className={"flex flex-col"}>
			<h1>404 - Could not find page</h1>
			<p>Try one of these:</p>
			<Link to={"/"}>Home</Link>
			<Link to={"/wiki"}>Wiki</Link>
			<Link to={"/map"}>Map</Link>
			<Link to={"/dashboard"}>Dashboard</Link>
		</Container>
	)
}