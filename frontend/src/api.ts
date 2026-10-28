const API = "http://localhost:8080/api";

export function token(){ return localStorage.getItem("roamio_token"); }

export async function api(path:string, options:RequestInit={}) {
  const headers = new Headers(options.headers);
  headers.set("Content-Type","application/json");
  const t=token(); if(t) headers.set("Authorization",`Bearer ${t}`);
  const res=await fetch(API+path,{...options,headers});
  if(!res.ok) throw new Error(await res.text() || "Request failed");
  const text=await res.text();
  return text ? JSON.parse(text) : null;
}
