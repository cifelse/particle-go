# Particle Go
A Java program that simulates a simple game with a Client-Server architecture, where users are spawned randomly inside an arena full of particles. Multiple users can see each other on the map. Likewise, users can see particles created by the server. The program utilizes Java sockets so that this could be run on multiple machines given that all of them are in the same network as the Server. This project is for the partial completion of the course STDISCM at De La Salle University. The course is headed by and submitted to Sir Ryan Austin Fernandez.

## Running Locally
1. Clone the repository.
```bash
git clone https://github.com/cifelse/particle-go.git
```
2. Run the [Server](https://github.com/cifelse/particle-go/blob/main/src/server/Server.java) (package: `server.Server.java`)
3. Run the [Client](https://github.com/cifelse/particle-go/blob/main/src/client/Client.java) (package: `client.Client.java`)
> [!IMPORTANT]
> If you are running the server and client on the same machine, you can use the 'localhost' as the IP address. If you are running the server and client on different machines, you can use the IP address of the machine where the server is running.

## Specifications
A user should be able to add particles to the environment with an initial position (x, y), an initial angle Θ (0 degrees is east, and degrees increase in an anticlockwise manner, e.g., 90 degrees is north), and a velocity V (in pixel per second). The particle will travel in a straight line, bouncing off the four walls of the canvas. Particles do not collide with other particles. All collisions are elastic, which means particles do not slow down or speed up after a collision. The canvas should be 1280x720 pixels. Coordinate (0,0) is the southwest corner of the canvas. Coordinate (1280,720) is the northeast corner.

Particles can be added in batches. This is in three forms:
- Provide an integer n indicating the number of particles to
add. Keep the velocity and angle constant. Provide a start
point and end point. Particles are added with a uniform
distance between the given start and end points.
- Provide an integer n indicating the number of particles to
add. Keep the start point and velocity constant. Provide a
start Θ and end Θ. Particles are added with uniform distance
between the given start Θ and end Θ.
- Provide an integer n indicating the number of particles to
add. Keep the start point and angle constant. Provide a start
velocity and end velocity. Particles are added with a uniform
difference between the given start and end velocities.
Additionally, a user can also add walls, given two endpoints
(x1, y1) and (x2, y2), which the particles will also bounce off
of.

Ensure that your screen resolution is high enough to show all particles on-screen. Show the FPS counter on-screen every 0.5 seconds.
