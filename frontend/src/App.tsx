import { useEffect, useState } from "react";
import { Link, Route, Routes, useNavigate } from "react-router-dom";
import { api, token } from "./api";

type Destination={id:number,name:string,country:string,description:string,imageUrl:string};
type Trip={id:number,destination:string,durationDays:number,travelers:number,budget:number,currency:string,interests:string,status:string};

function Nav(){
  const navigate=useNavigate();
  const logged=!!token();
  return <nav>
    <Link className="brand" to="/">ROAMIO</Link>
    <div className="navlinks">
      <Link to="/explore">Explore</Link>
      <Link to="/assistant">AI Planner</Link>
      {logged ? <><Link to="/dashboard">Dashboard</Link><button onClick={()=>{localStorage.clear();navigate("/");}}>Logout</button></>
              : <Link to="/login">Login</Link>}
    </div>
  </nav>
}

function Home(){
  return <main>
    <section className="hero">
      <p className="eyebrow">YOUR NEXT JOURNEY</p>
      <h1>Plan travel that feels<br/><em>made for you.</em></h1>
      <p>Tell Roamio what you want. Turn your ideas into a structured trip and discover local travel experts.</p>
      <Link className="primary" to="/assistant">Start planning →</Link>
    </section>
    <section className="section"><h2>How it works</h2><div className="grid3">
      <Card n="01" t="Tell us your idea" d="Describe your destination, duration, interests, group and budget."/>
      <Card n="02" t="Shape your trip" d="The planner turns your conversation into structured travel requirements."/>
      <Card n="03" t="Find local experts" d="Match your request with providers who understand the destination."/>
    </div></section>
  </main>
}
function Card({n,t,d}:{n:string,t:string,d:string}){return <div className="card"><span>{n}</span><h3>{t}</h3><p>{d}</p></div>}

function Explore(){
  const [items,setItems]=useState<Destination[]>([]);
  useEffect(()=>{api("/destinations").then(setItems).catch(console.error)},[]);
  return <main className="section"><p className="eyebrow">EXPLORE</p><h1>Places to go</h1>
    <div className="destgrid">{items.map(d=><article className="destination" key={d.id}>
      <img src={d.imageUrl}/><div><small>{d.country}</small><h2>{d.name}</h2><p>{d.description}</p></div>
    </article>)}</div>
  </main>
}

function Assistant(){
  const [message,setMessage]=useState(""); const [reply,setReply]=useState("");
  const [trip,setTrip]=useState({destination:"",durationDays:7,travelers:2,budget:2000,currency:"USD",interests:""});
  const send=async()=>{if(!message.trim())return; const r=await api("/ai/chat",{method:"POST",body:JSON.stringify({message})});setReply(r.reply);
    setTrip(x=>({...x,destination:r.extracted.destination!=="Not specified"?r.extracted.destination:x.destination,durationDays:r.extracted.durationDays||x.durationDays,travelers:r.extracted.travelers||x.travelers}));};
  const create=async()=>{if(!token()){alert("Please login first.");return;} await api("/trips",{method:"POST",body:JSON.stringify({...trip,requestText:message})});alert("Trip request saved!");};
  return <main className="section narrow"><p className="eyebrow">AI TRIP PLANNER</p><h1>What kind of trip<br/><em>are you imagining?</em></h1>
    <textarea value={message} onChange={e=>setMessage(e.target.value)} placeholder="Example: I want to visit Uganda for 7 days with my wife. We love wildlife and photography. Our budget is $3,000."/>
    <button className="primary" onClick={send}>Ask planner</button>
    {reply&&<div className="assistant"><strong>Roamio Assistant</strong><p>{reply}</p></div>}
    <div className="formgrid">
      <input placeholder="Destination" value={trip.destination} onChange={e=>setTrip({...trip,destination:e.target.value})}/>
      <input type="number" placeholder="Days" value={trip.durationDays} onChange={e=>setTrip({...trip,durationDays:+e.target.value})}/>
      <input type="number" placeholder="Travelers" value={trip.travelers} onChange={e=>setTrip({...trip,travelers:+e.target.value})}/>
      <input type="number" placeholder="Budget" value={trip.budget} onChange={e=>setTrip({...trip,budget:+e.target.value})}/>
      <input placeholder="Interests: wildlife, food, hiking" value={trip.interests} onChange={e=>setTrip({...trip,interests:e.target.value})}/>
    </div>
    <button className="secondary" onClick={create}>Save trip request</button>
  </main>
}

function Auth(){
  const [login,setLogin]=useState(true); const [name,setName]=useState(""); const [email,setEmail]=useState(""); const [password,setPassword]=useState("");
  const nav=useNavigate();
  const submit=async()=>{try{const r=await api(login?"/auth/login":"/auth/register",{method:"POST",body:JSON.stringify(login?{email,password}:{fullName:name,email,password,role:"TRAVELER"})});localStorage.setItem("roamio_token",r.token);nav("/dashboard");}catch(e){alert(String(e));}};
  return <main className="auth"><div className="authbox"><p className="eyebrow">{login?"WELCOME BACK":"CREATE ACCOUNT"}</p><h1>{login?"Log in":"Join Roamio"}</h1>
    {!login&&<input placeholder="Full name" value={name} onChange={e=>setName(e.target.value)}/>}
    <input placeholder="Email" value={email} onChange={e=>setEmail(e.target.value)}/>
    <input type="password" placeholder="Password" value={password} onChange={e=>setPassword(e.target.value)}/>
    <button className="primary" onClick={submit}>{login?"Login":"Register"}</button>
    <button className="linkbtn" onClick={()=>setLogin(!login)}>{login?"Create an account":"I already have an account"}</button>
  </div></main>
}

function Dashboard(){
  const [trips,setTrips]=useState<Trip[]>([]);
  useEffect(()=>{api("/trips").then(setTrips).catch(e=>alert(String(e)))},[]);
  return <main className="section"><p className="eyebrow">TRAVELER</p><h1>Your trips</h1>
    <Link className="primary" to="/assistant">+ New trip</Link>
    <div className="triplist">{trips.map(t=><div className="trip" key={t.id}><div><small>{t.status}</small><h2>{t.destination||"Trip request"}</h2><p>{t.durationDays} days · {t.travelers} travelers · {t.budget} {t.currency}</p></div><Link to={`/matches/${t.id}`}>Find experts →</Link></div>)}</div>
  </main>
}

function Matches({id}:{id:string}){
  const [items,setItems]=useState<any[]>([]);
  useEffect(()=>{api(`/matching/trip/${id}`).then(setItems).catch(e=>alert(String(e)))},[id]);
  return <main className="section"><p className="eyebrow">MATCHING</p><h1>Local experts</h1>
    {items.map(p=><div className="provider" key={p.providerId}><div><h2>{p.companyName}</h2><p>{p.specialties}</p></div><strong>{p.score}% match</strong></div>)}
  </main>
}

export default function App(){
 return <><Nav/><Routes>
   <Route path="/" element={<Home/>}/><Route path="/explore" element={<Explore/>}/><Route path="/assistant" element={<Assistant/>}/>
   <Route path="/login" element={<Auth/>}/><Route path="/dashboard" element={<Dashboard/>}/>
   <Route path="/matches/:id" element={<MatchRoute/>}/>
 </Routes></>
}
function MatchRoute(){ const id=location.pathname.split("/").pop()!; return <Matches id={id}/>; }
