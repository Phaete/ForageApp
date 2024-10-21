import {Link} from "react-router-dom";



export default function Navbar() {

	return (
		<nav className={"flex flex-row boxed box-radius-b5 justify-between"}>
			<span className={"flat-button"}>
				<Link to={"/"}>Forage Tracker</Link>
			</span>
			<span>
				<Link to={"/map"}><button type={"button"}>Map</button></Link>
				<Link to={"/wiki"}><button type={"button"}>Wiki</button></Link>
				<Link to={"/dashboard"}><button type={"button"}>Dashboard</button></Link>
			</span>
		</nav>
	);
}