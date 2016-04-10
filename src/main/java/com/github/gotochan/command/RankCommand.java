package com.github.gotochan.command;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;

import com.github.gotochan.BMC;
import com.github.gotochan.resource.BMCHelp;

/**
 *
 * @author Hinyari_Gohan
 *
 */

public class RankCommand {
	
	public static String[] RankList = new String[]
			{
					ChatColor.GRAY + "Visitor",
					ChatColor.RED + "Red" ,
					ChatColor.GOLD + "Orange",
					ChatColor.YELLOW + "Yellow",
					ChatColor.GREEN + "Green",
					ChatColor.BLUE + "Blue",
					ChatColor.DARK_BLUE + "Indigo",
					ChatColor.DARK_PURPLE + "Violet",
					ChatColor.WHITE + "UltraViolet",
					ChatColor.LIGHT_PURPLE + "あなたは最高ランクに達しました。"
			};
	
	
	@SuppressWarnings({ "deprecation" })
	public static boolean runCommand(CommandSender sender, String label, String[] args)
	{
		Player player = (Player) sender;
		Scoreboard board = player.getScoreboard();
		Objective rankobject = board.getObjective("rank");
		Score MyStats = rankobject.getScore(player);
		String myname = sender.getName();
		int NowRank = MyStats.getScore();
		int NextRank = MyStats.getScore() + 1;
		if( args.length == 0 )
		{
			BMCHelp.Rankhelp(sender);
		}
		else
		{
			/**
			 * if( args[0].equalsIgnoreCase("show"))
			{
				sender.sendMessage(ChatColor.RED + "[Red] " + ChatColor.WHITE + "一般人");
				sender.sendMessage(ChatColor.GOLD + "[Orange] " + ChatColor.WHITE + "駆け出しクラフター");
				sender.sendMessage(ChatColor.YELLOW + "[Yellow] " + ChatColor.WHITE + "慣れてきた感じのクラフター");
				sender.sendMessage(ChatColor.DARK_GREEN + "[Green] " + ChatColor.WHITE + "中堅クラフター");
				sender.sendMessage(ChatColor.BLUE + "[Blue] " + ChatColor.WHITE + "セミプロクラフター");
				sender.sendMessage(ChatColor.DARK_BLUE + "[Indigo]" + ChatColor.WHITE + "プロクラフター");
				sender.sendMessage(ChatColor.DARK_PURPLE + "[Violet] " + ChatColor.WHITE + "神がかったクラフター");
				sender.sendMessage(ChatColor.WHITE + "[UltraViolet] " + "よくわからない何か");
			}
			 **/
			if ( args[0].equalsIgnoreCase("stats"))
			{
				if ( args.length == 1 )
				{
					sender.sendMessage(ChatColor.YELLOW + "========BMCサーバー ランクシステム========");
					sender.sendMessage("名前: " + myname);
					sender.sendMessage("現在のランク: " + RankList[NowRank]);
					sender.sendMessage("次のランク: " + RankList[NextRank]);
				}
				else if ( args.length == 2 )
				{
					Player other = getPlayer(args[1]);
					Score otherscore = rankobject.getScore(args[1]);
					int OtherNowRank = otherscore.getScore();
					int OtherNextRank = otherscore.getScore() + 1;
					if ( other == null )
					{
						sender.sendMessage(BMC.prefix + "そのプレイヤーはオフラインです。");
					}
					else
					{
						sender.sendMessage(ChatColor.YELLOW + "========BMCサーバー ランクシステム========");
						sender.sendMessage("名前: " + args[1]);
						sender.sendMessage("現在のランク: " + RankList[OtherNowRank]);
						sender.sendMessage("次のランク: " + RankList[OtherNextRank]);
					}
				}
			}
			else
			{
				return BMCHelp.Rankhelp(sender);
			}
		}
		return false;
	}
	private static Player getPlayer(String name)
	{
		for ( Player player : Bukkit.getOnlinePlayers() )
		{
			if ( player.getName().equals(name) )
			{
				return player;
			}
		}
		return null;
	}
}